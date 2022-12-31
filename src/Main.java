
import managers.*;
import tasks.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        /*KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpServer();


        HttpTaskManager httpTaskManager = httpTaskServer.getHttpTaskManager();

        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);

        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);

        httpTaskManager.getTaskById(2);

        httpTaskManager.saveToServer();

        httpTaskServer.stopHttpServer();

        httpTaskServer.startHttpServer();

        System.out.println(httpTaskManager.getAllTasks());
*/


       /* new KVServer().start();
        KVTaskClient client = new KVTaskClient(new URL("http://localhost:8080"));
        client.put("1671428237571", "test");*/


        /*HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpServer();*/


        /*HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080),0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();

        System.out.println("Сервер запущен");*/


        /*TaskManager inMemoryTaskManager = Managers.getDefault();

        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2023, 1, 1, 0, 0));

        Subtask subtask2 = new Subtask(1, TaskType.TASK, "test1", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Subtask subtask3 = new Subtask(2, TaskType.TASK, "test2", Status.NEW, "test2 des", epic1,
                80, LocalDateTime.of(2023, 1, 1, 10, 0));


        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createSubtask(subtask1);
        inMemoryTaskManager.createSubtask(subtask2);
        inMemoryTaskManager.createSubtask(subtask3);

        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);


        for (Task task : inMemoryTaskManager.getPrioritizedTasks()) {
            System.out.println(task);
        }
        System.out.println("-----------------------------------------------------------------------------------------");


        inMemoryTaskManager.deleteSubtaskById(2);

        for (Task task : inMemoryTaskManager.getPrioritizedTasks()) {
            System.out.println(task);
        }*/
    }

}