package Tests;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.praktikum.managers.HttpTaskManager;
import ru.yandex.praktikum.managers.HttpTaskServer;
import ru.yandex.praktikum.managers.KVServer;
import ru.yandex.praktikum.tasks.Task;

import java.io.IOException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer kvServer;

    HttpTaskServer httpTaskServer;


    @BeforeEach
    void serversStart() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpServer();
        taskManager = httpTaskServer.getHttpTaskManager();

    }

    @AfterEach
    void serversStop() {
        kvServer.stop();
        httpTaskServer.stopHttpServer();
    }


    @Test
    void loadFromServer() throws IOException, InterruptedException {
        initTasks();
        taskManager.saveToServer();
        ArrayList<Task> arrayList = taskManager.getAllTasks();
        taskManager = taskManager.loadFromServer();
        ArrayList<Task> arrayList2 = taskManager.getAllTasks();
        assertEquals(arrayList, arrayList2);

    }

    @Test
    void saveToServer() throws IOException, InterruptedException {
        initTasks();
        taskManager.saveToServer();
        ArrayList<Task> arrayList = taskManager.getAllTasks();
        taskManager = taskManager.loadFromServer();
        ArrayList<Task> arrayList2 = taskManager.getAllTasks();
        assertEquals(arrayList, arrayList2);


    }

}