package managers;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Task;

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

    @Test
    void createTask() {
        super.createTask();
    }

    @Test
    void createEpic() {
        super.createEpic();
    }

    @Test
    void createSubtask() {
        super.createSubtask();
    }

    @Test
    void getTaskById() {
        super.getTaskById();
    }

    @Test
    void deleteTaskById() {
        super.deleteTaskById();
    }

    @Test
    void getAllTasks() {
        super.getAllTasks();
    }

    @Test
    void removeAllTasks() {
        super.removeAllTasks();
    }

    @Test
    void getAllSubTasks() {
        super.getAllSubTasks();
    }

    @Test
    void removeAllSubtasks() {
        super.removeAllSubtasks();
    }

    @Test
    void getSubtaskById() {
        super.getSubtaskById();
    }

    @Test
    void deleteSubtaskById() {
        super.deleteSubtaskById();
    }

    @Test
    void getAllEpicTasks() {
        super.getAllEpicTasks();
    }

    @Test
    void removeAllEpicTasks() {
        super.removeAllEpicTasks();
    }

    @Test
    void getEpicTaskById() {
        super.getEpicTaskById();
    }

    @Test
    void deleteEpicTaskById() {
        super.deleteEpicTaskById();
    }

    @Test
    void getHistory() {
        super.getHistory();
    }
}