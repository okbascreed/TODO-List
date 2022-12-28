package managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;
import tasks.TaskType;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    KVServer kvServer;

    HttpTaskServer httpTaskServer;

    HttpTaskManager httpTaskManager;

    HttpTaskServerTest() throws IOException, InterruptedException {
    }


    @BeforeEach
    void serversStart() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpServer();
        httpTaskManager = new HttpTaskManager(new URL("http://localhost:8078"));
    }
    @AfterEach
    void serversStop(){
        kvServer.stop();
        httpTaskServer.stopHttpServer();
    }

    @Test
    void TaskGetMethodHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);

        httpTaskManager.saveToServer("test_1");


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        httpTaskManager.loadFromServer("test_1");

        assertEquals(httpTaskManager.getAllTasks(), response);


    }





    @Test
    void startHttpServer() {
    }
}