package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.managers.InMemoryHistoryManager;
import ru.yandex.praktikum.managers.InMemoryTaskManager;
import ru.yandex.praktikum.tasks.Status;
import ru.yandex.praktikum.tasks.Task;
import ru.yandex.praktikum.tasks.TaskType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest extends InMemoryHistoryManager {
    private static InMemoryTaskManager taskManager;
    private static InMemoryHistoryManager historyManager;

    @BeforeEach
    void init() {
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.getTaskById(1);
        List<Task> history = taskManager.getHistory();

        assertTrue(history.contains(task1));

    }

    @Test
    void addTwice() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);


        List<Task> history = taskManager.getHistory();

        assertEquals(2, history.size());

    }


    @Test
    void removeFirstItem() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);

        Task task4 = new Task(4, TaskType.TASK, "test4", Status.NEW, "test4 des",
                40, null);
        Task task5 = new Task(5, TaskType.TASK, "test5", Status.NEW, "test5 des",
                40, null);
        Task task6 = new Task(6, TaskType.TASK, "test6", Status.NEW, "test6 des",
                40, null);
        Task task7 = new Task(7, TaskType.TASK, "test7", Status.NEW, "test7 des",
                40, null);
        Task task8 = new Task(8, TaskType.TASK, "test8", Status.NEW, "test8 des",
                40, null);
        Task task9 = new Task(9, TaskType.TASK, "test9", Status.NEW, "test9 des",
                40, null);
        Task task10 = new Task(10, TaskType.TASK, "test10", Status.NEW, "test10 des",
                40, null);
        Task task11 = new Task(11, TaskType.TASK, "test11", Status.NEW, "test11 des",
                40, null);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        taskManager.createTask(task4);
        taskManager.createTask(task5);
        taskManager.createTask(task6);
        taskManager.createTask(task7);
        taskManager.createTask(task8);
        taskManager.createTask(task9);
        taskManager.createTask(task10);
        taskManager.createTask(task11);

        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.getTaskById(4);
        taskManager.getTaskById(5);
        taskManager.getTaskById(6);
        taskManager.getTaskById(7);
        taskManager.getTaskById(8);
        taskManager.getTaskById(9);
        taskManager.getTaskById(10);
        taskManager.getTaskById(11);


        taskManager.deleteTaskById(task1.getId());

        List<Task> history = historyManager.getHistory();

        assertFalse(history.contains(task1));
    }

    @Test
    void removeMiddleItem() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();

        assertFalse(history.contains(task2));
    }

    @Test
    void removeLastItem() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();

        assertFalse(history.contains(task3));
    }


    @Test
    void getHistoryCheck() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        List<Task> history = historyManager.getHistory();

        assertEquals(0, history.size());

    }


}