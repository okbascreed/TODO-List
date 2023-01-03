package managers;

import org.junit.jupiter.api.Assertions;
import tasks.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    public void initTasks() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 00));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
    }

    public void initEpics() {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
    }

    public void initSubtasks() {
        Epic epic = new Epic("epic", "epic description", Status.NEW);
        Subtask subtask1 = new Subtask("subtask1", "subtask description", Status.NEW, epic);
        Subtask subtask2 = new Subtask("subtask2", "subtask description", Status.NEW, epic);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
    }

    void createTask() {
        Task task1 = new Task("task1", "task1 descrp", Status.NEW);
        taskManager.createTask(task1);
        final Task savedTask = taskManager.getTaskById(1);
        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task1, savedTask, "Задачи не совпадают.");
        final ArrayList<Task> tasks = taskManager.getAllTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.get(0), "Задачи не совпадают.");

    }

    void createTaskIntersectionCheck() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 12, 0));
        taskManager.createTask(task1);
        taskManager.createTask(task2);


        assertEquals(1, taskManager.getPrioritizedTasks().size());

    }

    void getAllTasks() {
        Task task1 = new Task("task1", "task1 descrp", Status.NEW);
        Task task2 = new Task("task2", "task2 descrp", Status.NEW);
        Task task3 = new Task("task3", "task3 descrp", Status.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        ArrayList<Task> arrayList = taskManager.getAllTasks();
        ArrayList<Task> arrayList2 = new ArrayList<>(List.of(task1, task2, task3));
        Assertions.assertAll(
                () -> assertEquals(3, arrayList.size(), "Неверное количество задач."),
                () -> assertEquals(arrayList2.size(), arrayList.size(), "Неверное количество задач.")
        );

        int count = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(arrayList2.get(i).getName())
                    && arrayList.get(i).getDescription().equals(arrayList2.get(i).getDescription())
                    && arrayList.get(i).getStatus().equals(arrayList2.get(i).getStatus())) {
                count++;
            }
        }
        Assertions.assertEquals(arrayList2.size(), count);
    }

    void getAllTasksWithEmptyList() {
        ArrayList<Task> arrayList = taskManager.getAllTasks();
        Assertions.assertTrue(arrayList.isEmpty());
    }

    void getAllTasksWithBadId() {
        initTasks();
        ArrayList<Task> arrayList = taskManager.getAllTasks();
        Task task = taskManager.getTaskById(9999999);

        Assertions.assertNull(task);
    }

    void removeAllTasks() {
        initTasks();
        taskManager.removeAllTasks();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ошибка удаления задач.");

    }

    void removeAllTasksWithEmptyList() {
        taskManager.removeAllTasks();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ошибка удаления задач.");
    }

    void removeAllTasksWithBadId() {
        initTasks();
        taskManager.removeAllTasks();
        Assertions.assertNull(taskManager.getTaskById(999999), "Ошибка удаления задач.");
    }

    void getTaskById() {
        initTasks();
        Task task = taskManager.getTaskById(1);
        Assertions.assertEquals(1, task.getId());
    }

    void getTaskByIdWithEmptyList() {
        Assertions.assertNull(taskManager.getTaskById(1));
    }

    void getTaskByIdWithBadId() {
        initTasks();
        Task task = taskManager.getTaskById(9999999);
        Assertions.assertNull(task);
    }

    void updateTask() {
        Task task1 = new Task("task1", "task1 descrp", Status.NEW);
        Task task2 = new Task("task1", "updated description", Status.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        task1.setDescription("updated description");
        taskManager.updateTask(task1);
        ArrayList<Task> tasks = taskManager.getAllTasks();


        Assertions.assertEquals(tasks.get(0).getDescription(), tasks.get(1).getDescription(),
                "Описания задач не совпадают");

        taskManager.removeAllTasks();
        ArrayList<Task> tasks1 = taskManager.getAllTasks();
        taskManager.updateTask(task1);
        Assertions.assertTrue(tasks1.isEmpty(),
                "Ошибка обновления задачи");

    }

    void deleteTaskById() {
        initTasks();
        taskManager.deleteTaskById(1);
        ArrayList<Task> tasks = taskManager.getAllTasks();

        Assertions.assertFalse(tasks.contains(taskManager.getTaskById(1)), "Ошибка удаления задачи");

        taskManager.removeAllTasks();
        ArrayList<Task> tasks1 = taskManager.getAllTasks();

        taskManager.deleteTaskById(1);
        taskManager.deleteTaskById(99);

        Assertions.assertTrue(tasks1.isEmpty(), "Ошибка удаления задачи");

    }

    void getAllSubTasks() {
        initSubtasks();
        ArrayList<Subtask> subtasks = taskManager.getAllSubTasks();

        Assertions.assertEquals(2, subtasks.size());
        Assertions.assertEquals(taskManager.getSubtaskById(2), subtasks.get(0));
        Assertions.assertEquals(taskManager.getEpicTaskById(1), subtasks.get(0).getEpic());

    }

    void getAllSubtasksWithEmptyList() {
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ошибка удаления задач.");
    }

    void getAllSubtasksWithBadId() {
        initSubtasks();
        ArrayList<Subtask> arrayList = taskManager.getAllSubTasks();
        Task subtask = taskManager.getSubtaskById(999999999);

        Assertions.assertNull(subtask);
    }

    void removeAllSubtasks() {
        initSubtasks();
        taskManager.removeAllSubtasks();
        Assertions.assertTrue(taskManager.getAllSubTasks().isEmpty(), "Ошибка удаления задач.");
    }

    void removeAllSubTasksWithEmptyList() {
        taskManager.removeAllSubtasks();
        Assertions.assertTrue(taskManager.getAllSubTasks().isEmpty(), "Ошибка удаления задач.");
        Assertions.assertNull(taskManager.getEpicTaskById(0));
    }

    void removeAllSubTasksWithBadId() {
        initSubtasks();
        taskManager.removeAllSubtasks();
        Assertions.assertNull(taskManager.getTaskById(999999), "Ошибка удаления задач.");
    }

    void getSubtaskById() {
        initSubtasks();
        Task subtask = taskManager.getSubtaskById(2);
        Assertions.assertEquals(2, subtask.getId());
    }

    void getSubtaskByIdWithEmptyList() {
        Assertions.assertNull(taskManager.getSubtaskById(1));
    }

    void getSubtaskByIdWithBadId() {
        initSubtasks();
        Task task = taskManager.getSubtaskById(9999999);
        Assertions.assertNull(task);
    }

    void createSubtask() {
        Epic epic1 = new Epic("Build a house", "build a new house");
        Subtask subtask1 = new Subtask("Buy nails", "buy nails", Status.NEW, epic1);

        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);

        final Task savedSubtask = taskManager.getSubtaskById(2);

        Assertions.assertNotNull(savedSubtask, "Задача не найдена.");
        Assertions.assertEquals(subtask1, savedSubtask, "Задачи не совпадают.");
        final ArrayList<Subtask> subtasks = taskManager.getAllSubTasks();
        assertNotNull(subtasks, "Задачи на возвращаются.");

        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask1, subtasks.get(0), "Задачи не совпадают.");
        assertEquals(epic1, subtasks.get(0).getEpic(), "Задачи не совпадают.");

    }

    void updateSubtask() {
        Epic epic1 = new Epic("Build a house", "build a new house");
        Subtask subtask1 = new Subtask("Buy nails", "buy nails", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Buy nails", "updated description", Status.NEW, epic1);

        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        subtask1.setDescription("updated description");
        taskManager.updateSubtask(subtask1);
        ArrayList<Subtask> subtasks = taskManager.getAllSubTasks();


        Assertions.assertEquals(subtasks.get(0).getDescription(), subtasks.get(1).getDescription(),
                "Описания задач не совпадают");

        taskManager.removeAllSubtasks();
        ArrayList<Subtask> subtasks1 = taskManager.getAllSubTasks();
        taskManager.updateTask(subtask1);
        Assertions.assertTrue(subtasks1.isEmpty(),
                "Ошибка обновления задачи");


    }

    void deleteSubtaskById() {
        initSubtasks();
        taskManager.deleteSubtaskById(2);
        ArrayList<Subtask> subtasks = taskManager.getAllSubTasks();


        taskManager.removeAllSubtasks();
        ArrayList<Subtask> subtasks1 = taskManager.getAllSubTasks();

        taskManager.deleteTaskById(1);
        taskManager.deleteTaskById(99);

        Assertions.assertTrue(subtasks1.isEmpty(), "Ошибка удаления задачи");
    }

    void getAllEpicTasks() {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);


        ArrayList<Epic> arrayList = taskManager.getAllEpicTasks();
        ArrayList<Epic> arrayList2 = new ArrayList<>(List.of(epic1, epic2, epic3));
        Assertions.assertAll(
                () -> assertEquals(3, arrayList.size(), "Неверное количество эпиков."),
                () -> assertEquals(arrayList2.size(), arrayList.size(), "Неверное количество эпиков.")
        );

        int count = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(arrayList2.get(i).getName())
                    && arrayList.get(i).getDescription().equals(arrayList2.get(i).getDescription())
                    && arrayList.get(i).getStatus().equals(arrayList2.get(i).getStatus())) {
                count++;
            }
        }
        Assertions.assertEquals(arrayList2.size(), count);

    }

    void getAllEpicTasksWithEmptyList() {
        ArrayList<Epic> arrayList = taskManager.getAllEpicTasks();
        Assertions.assertTrue(arrayList.isEmpty());
    }

    void getAllEpicTasksWithBadId() {
        initEpics();
        ArrayList<Epic> arrayList = taskManager.getAllEpicTasks();
        Task task = taskManager.getTaskById(9999999);

        Assertions.assertNull(task);
    }

    void removeAllEpicTasks() {
        initEpics();
        taskManager.removeAllEpicTasks();
        Assertions.assertTrue(taskManager.getAllEpicTasks().isEmpty(), "Ошибка удаления задач.");
    }

    void removeAllEpicTasksWithEmptyList() {
        taskManager.removeAllEpicTasks();
        Assertions.assertTrue(taskManager.getAllEpicTasks().isEmpty(), "Ошибка удаления задач.");
        Assertions.assertNull(taskManager.getEpicTaskById(0));
    }

    void removeAllEpicTasksWithBadId() {
        initEpics();
        taskManager.removeAllEpicTasks();
        Assertions.assertNull(taskManager.getEpicTaskById(999999), "Ошибка удаления задач.");
    }

    void getEpicTaskById() {
        initEpics();
        Task task = taskManager.getEpicTaskById(1);
        Assertions.assertEquals(1, task.getId());
    }

    void getEpicTaskByIdWithEmptyList() {
        Assertions.assertNull(taskManager.getEpicTaskById(1));
    }

    void getEpicTaskByIdWithBadId() {
        initEpics();
        Task task = taskManager.getTaskById(9999999);
        Assertions.assertNull(task);
    }

    void createEpic() {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);
        final Task savedEpic = taskManager.getEpicTaskById(1);
        Assertions.assertNotNull(savedEpic, "Задача не найдена.");
        Assertions.assertEquals(epic1, savedEpic, "Задачи не совпадают.");
        final ArrayList<Epic> epics = taskManager.getAllEpicTasks();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(3, epics.size(), "Неверное количество задач.");
        assertEquals(epic1, epics.get(0), "Задачи не совпадают.");

    }

    void createEpicTaskIntersectionCheck() {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");
        Epic epic2 = new Epic(2, TaskType.EPIC, "epic 2", Status.NEW, "epic2 test");

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        assertEquals(1, taskManager.getPrioritizedTasks().size());

    }

    void updateEpicTask() {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2023, 1, 1, 20, 0));

        Subtask subtask2 = new Subtask(2, TaskType.TASK, "subtask2", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Subtask subtask3 = new Subtask(3, TaskType.TASK, "subtask3", Status.DONE, "test2 des", epic1,
                80, LocalDateTime.of(2023, 1, 1, 10, 0));

        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);

        LocalDateTime ldtUpdate = LocalDateTime.of(2023, 1, 1, 21, 0);

        subtask1.setStartTime(ldtUpdate);
        taskManager.updateSubtask(subtask1);
        epic1.addSubtask(subtask1);
        taskManager.updateEpicTask(epic1);

        Assertions.assertEquals(ldtUpdate.plusMinutes(subtask1.getDuration()), epic1.getEndTime());
    }

    void deleteEpicTaskById() {
        initEpics();
        taskManager.deleteEpicTaskById(1);
        ArrayList<Epic> epics = taskManager.getAllEpicTasks();


        taskManager.removeAllEpicTasks();
        ArrayList<Epic> epics1 = taskManager.getAllEpicTasks();

        taskManager.deleteEpicTaskById(1);
        taskManager.deleteEpicTaskById(99);

        Assertions.assertTrue(epics1.isEmpty(), "Ошибка удаления задачи");
    }

    void getEpicSubtasks() {
        initSubtasks();
        ArrayList<Subtask> subtasks = taskManager.getEpicSubtasks(1);

        assertEquals(2, subtasks.size());
        assertNotNull(subtasks.get(1));
    }

    void getEpicStatus() {
        Epic epic = new Epic("epic", "epic description", Status.NEW);
        Subtask subtask1 = new Subtask("subtask1", "subtask description", Status.NEW, epic);
        Subtask subtask2 = new Subtask("subtask2", "subtask description", Status.DONE, epic);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Status.IN_PROGRESS, taskManager.getEpicStatus(epic));
    }

    void getHistory() {
        initTasks();
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);

        List<Task> history = taskManager.getHistory();

        assertNotNull(history);
        assertEquals(3, history.size());

    }

    void getTaskType() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 00));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);


        assertEquals("TASK", taskManager.getTaskType(task1));
    }

    void checkTaskIntersection() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));

        taskManager.createTask(task1);

        assertThrows(ManagerIntersectionException.class, () -> {
            Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                    80, LocalDateTime.of(2023, 1, 1, 12, 0));
            taskManager.checkTaskIntersection(task2);
        });

        assertDoesNotThrow(() -> {
            Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                    80, LocalDateTime.of(2023, 1, 1, 20, 0));
            taskManager.checkTaskIntersection(task2);
        });

    }
}
