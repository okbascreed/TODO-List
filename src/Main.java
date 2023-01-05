
import ru.yandex.praktikum.managers.HttpTaskManager;
import ru.yandex.praktikum.managers.HttpTaskServer;
import ru.yandex.praktikum.managers.KVServer;
import ru.yandex.praktikum.tasks.Status;
import ru.yandex.praktikum.tasks.Task;
import ru.yandex.praktikum.tasks.TaskType;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
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


    }

}