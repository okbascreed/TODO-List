package managers;

import tasks.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    // private static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTask(Task task) {
      super.createTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getHistory() {
        try {
            saveHistory();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return super.getHistory();
    }


    private void save() throws ManagerSaveException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write("id,type,name,status,description,epic,duration,dateTime");
            bufferedWriter.newLine();

            Collection<Task> values = getAllTasks();
            for (Task value : values) {
                bufferedWriter.write(value.getId() + "," + getTaskType(value) + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDescription() + "," + value.getDuration() + "," +
                        value.getStartTime());
                bufferedWriter.newLine();
            }

            Collection<Epic> valuesE = getAllEpicTasks();
            for (Epic value : valuesE) {
                bufferedWriter.write(value.getId() + "," + getTaskType(value) + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDescription() + "," + value.getDuration() + "," +
                        value.getStartTime());
                bufferedWriter.newLine();
            }

            Collection<Subtask> valuesS = getAllSubTasks();
            for (Subtask value : valuesS) {
                bufferedWriter.write(value.getId() + "," + getTaskType(value) + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDescription() + "," + value.getEpic().getId() + "," +
                        value.getDuration() + "," + value.getStartTime());
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить файл");
        }
    }

    private void saveHistory() throws ManagerSaveException {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.newLine();

            bufferedWriter.append(historyToString(getHistoryManager()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить историю в файл");
        }
    }

    public String toString(Task task) {
        String type = "";
        String owner = "";


        if (task instanceof Subtask) {
            type = TaskType.SUBTASK.toString();
            Subtask subtask = (Subtask) task;
            owner = subtask.getEpic().getId() + "";
        }
        if (task instanceof Epic) {
            type = TaskType.EPIC.toString();
        }
        if (task instanceof Task) {
            type = TaskType.TASK.toString();
        }

        String result = task.getId() + "," + type + "," + task.getName() + "," + task.getStatus() + "," +
                task.getDescription() + "," + owner + "," + task.getDuration() + "," + task.getStartTime();
        return result;
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        String name = values[2];
        String description = values[4];
        Integer id = Integer.valueOf(values[0]);
        TaskType type = TaskType.valueOf(values[1]);
        Status status = Status.valueOf(values[3]);
        Integer duration = Integer.valueOf(values[5]);
        LocalDateTime startTime = LocalDateTime.parse(values[6]);
        LocalDateTime endTime = null;
        if(values.length > 7){
            endTime = LocalDateTime.parse(values[7]);
        }

        switch (type) {
            case TASK:
                Task task = new Task(id, type, name, status, description, duration, startTime);
                return task;
            case EPIC:
                Epic epic = new Epic(id, type, name, status, description, duration, startTime, endTime);
                return epic;
            case SUBTASK:
                Integer epicId = Integer.valueOf(values[5]);
                InMemoryTaskManager taskManager = new InMemoryTaskManager();
                Epic epicForSubtask = taskManager.getAllEpicTasks().get(epicId);
                Subtask subtask = new Subtask(id, type, name, status, description, epicForSubtask, duration, startTime);
                return subtask;
            default:
                return null;
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        String result = "";
        for (Task task : history) {
            result += task.getId() + ",";

        }
        return result;
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> idList = new ArrayList<>();
        String[] values = value.split(",");
        for (int i = 0; i < values.length; i++) {
            idList.add(Integer.parseInt(values[i]));
        }
        return idList;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            String data = bufferedReader.readLine();

            while (bufferedReader.ready()) {
                data = bufferedReader.readLine();
                if (data.isBlank()) {
                    break;
                }

                String[] values = data.split(",");
                Integer id = Integer.valueOf(values[0]);
                TaskType type = TaskType.valueOf(values[1]);
                String name = values[2];
                Status status = Status.valueOf(values[3]);
                String description = values[4];
                Integer duration = Integer.valueOf(values[5]);
                LocalDateTime startTime = null;
                LocalDateTime endTime = null;
                if(!values[6].equals("null")){
                    startTime = LocalDateTime.parse(values[6]);
                }
                if(values.length > 8){
                    endTime = LocalDateTime.parse(values[7]);
                }


                if (data.contains("EPIC")) {
                    Epic epic = new Epic(id, type, name, status, description, duration, startTime, endTime);
                    fileBackedTasksManager.addEpic(epic);

                } else if (data.contains("SUBTASK")) {
                    int epicId = Integer.valueOf(values[5]);
                    Epic epic = fileBackedTasksManager.getAllEpicTasks().get(epicId);
                    Subtask subtask = new Subtask(id, type, name, status, description, epic, duration, startTime);
                    fileBackedTasksManager.addSubtask(subtask);
                } else {
                    Task task = new Task(id, type, name, status, description, duration, startTime);
                    fileBackedTasksManager.addTask(task);

                }
            }

            Files.readAllLines(file.toPath());

        } catch (IOException e) {
            System.out.println(e);
        }
        return fileBackedTasksManager;
    }
}
