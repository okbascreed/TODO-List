package managers;

import tasks.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();
    protected final LocalDateTimeComparator comparator = new LocalDateTimeComparator();
    protected Set<Task> prioritizedTasks = new TreeSet<>(comparator);
    Integer id = 0;

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void createTask(Task task) {
        try {
            checkTaskIntersection(task);
        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
            return;
        }
        id++;
        task.setId(id);
        tasks.put(id, task);
        prioritizedTasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        try {
            checkTaskIntersection(task);
            int taskId = task.getId();
            if (tasks.containsKey(taskId)) {
                tasks.put(taskId, task);
            }
            Set<Task> update = new TreeSet<>(comparator);
            for (Task prioritizedTask : prioritizedTasks) {
                update.add(prioritizedTask);
            }
            prioritizedTasks = update;

        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void deleteTaskById(int id) {

        if (historyManager.getHistory().contains(id)) {
            historyManager.remove(id);
        }
        if(tasks.get(id) != null){
            prioritizedTasks.remove(tasks.get(id));
        }
            tasks.remove(id);
    }


    @Override
    public ArrayList<Subtask> getAllSubTasks() {
            return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllSubtasks() {
        if(!subtasks.isEmpty()){
            subtasks.clear();
        }
    }

    @Override
    public Task getSubtaskById(int id) {
        Task task = subtasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        try {
            checkTaskIntersection(subtask);
        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
            return;
        }
        id++;
        subtask.setId(id);
        subtasks.put(id, subtask);
        prioritizedTasks.add(subtask);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        try {
            checkTaskIntersection(subtask);
            int subtaskId = subtask.getId();
            if (tasks.containsKey(subtaskId)) {
                tasks.put(subtaskId, subtask);
            }
            Set<Task> update = new TreeSet<>(comparator);
            for (Task prioritizedTask : prioritizedTasks) {
                update.add(prioritizedTask);
            }
            prioritizedTasks = update;

            Epic epic = subtask.getEpic();
            epic.updateEpicTaskStatus();


        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if(subtask != null) {
            Epic epic = subtask.getEpic();
            prioritizedTasks.remove(subtasks.get(id));
            subtasks.remove(id);
            epic.getSubtasks().remove(subtask);
            int count = 0;
            for (Subtask subtask1 : epic.getSubtasks()) {
                count += subtask1.getDuration();
            }

            epic.setDuration(count);
            epic.updateEpicTaskStatus();

            historyManager.remove(id);
        }
    }

    @Override
    public ArrayList<Epic> getAllEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public void removeAllEpicTasks() {
        epicTasks.clear();
    }

    @Override
    public Task getEpicTaskById(int id) {
        Task task = epicTasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void createEpic(Epic epic) {
        try {
            checkTaskIntersection(epic);
        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
        }
        id++;
        epic.setId(id);
        epicTasks.put(id, epic);
        prioritizedTasks.add(epic);
    }

    @Override
    public void updateEpicTask(Epic epic) {
        try {
            checkTaskIntersection(epic);
            int epicTaskId = epic.getId();
            if (epicTasks.containsKey(epicTaskId)) {
                epicTasks.put(epicTaskId, epic);
            }

            Set<Task> update = new TreeSet<>(comparator);
            for (Task prioritizedTask : prioritizedTasks) {
                update.add(prioritizedTask);
            }
            prioritizedTasks = update;
        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void deleteEpicTaskById(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks = getEpicSubtasks(id);
        if(subtasks !=null){
            for (Subtask subtask : subtasks) {
                historyManager.remove(subtask.getId());
            }
        }

        prioritizedTasks.remove(epicTasks.get(id));
        epicTasks.remove(id);
        historyManager.remove(id);

    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        Epic epic = epicTasks.get(id);
        if(epic != null){
            return epic.getSubtasks();
        }
        return new ArrayList<Subtask>();
    }

    @Override
    public Status getEpicStatus(Epic epic) {
        return epic.getStatus();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    public String getTaskType(Task task) {

        String type = "";
        if (task instanceof Subtask) {
            type = TaskType.SUBTASK.toString();
        } else {
            if (task instanceof Epic) {
                type = TaskType.EPIC.toString();
            } else {
                if (task instanceof Task) {
                    type = TaskType.TASK.toString();
                }
            }
        }
        return type;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    protected void addEpic(Epic epic) {
        int id = epic.getId();
        epicTasks.put(id, epic);
    }

    protected void addSubtask(Subtask subtask) {
        int id = subtask.getId();
        subtasks.put(id, subtask);
    }

    protected void addTask(Task task) {
        int id = task.getId();
        tasks.put(id, task);
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void checkTaskIntersection(Task task) throws ManagerIntersectionException {
        for (Task prioritizedTask : prioritizedTasks) {
            if (task instanceof Epic && prioritizedTask instanceof Subtask) {
                return;
            }
            if (task.equals(prioritizedTask)) {
                continue;
            }
            if (prioritizedTask.getStartTime() != null) {
                if (task.getStartTime() != null && (
                        (task.getStartTime().isAfter(prioritizedTask.getStartTime())
                                && task.getStartTime().isBefore(prioritizedTask.getEndTime()))
                                || (task.getEndTime().isAfter(prioritizedTask.getStartTime())
                                && task.getEndTime().isBefore(prioritizedTask.getEndTime()))
                                || task.getStartTime().equals(prioritizedTask.getStartTime())
                                || task.getEndTime().equals(prioritizedTask.getEndTime()))
                ) {

                    throw new ManagerIntersectionException("Задача " + task.getName() +
                            " пересекается по времени выполнения с " + prioritizedTask.getName());
                }
            }
        }
    }
}

