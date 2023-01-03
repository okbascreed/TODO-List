package managers;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskServerTest {

    KVServer kvServer;

    HttpTaskServer httpTaskServer;

    HttpTaskManager httpTaskManager;

    @BeforeEach
    void serversStart() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpServer();
        httpTaskManager = httpTaskServer.getHttpTaskManager();
    }

    @AfterEach
    void serversStop() {
        kvServer.stop();
        httpTaskServer.stopHttpServer();
    }

    @Test
    void AllTasksGetEndpointHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);

        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);

        Gson gson = new Gson();


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getPrioritizedTasks()), response.body());

    }


    @Test
    void TaskGetEndpointHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);


        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getAllTasks()), response.body());
    }

    @Test
    void TaskGetByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);


        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getTaskById(1)), response.body());

    }

    @Test
    void TaskDeleteByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);

        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Task> actual = httpTaskManager.getAllTasks();

        assertEquals(2, actual.size());
        assertEquals(200, response.statusCode());

    }


    @Test
    void TaskDeleteEndpointHandlerTest() throws IOException, InterruptedException {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);
        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Task> actual = httpTaskManager.getAllTasks();

        assertEquals(0, actual.size());
        assertEquals(200, response.statusCode());

    }

    @Test
    void TaskPostEndpointHandlerTest()throws IOException, InterruptedException{
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));

        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        httpTaskManager.createTask(task1);
        assertEquals("Задача успешно добавлена/обновлена", response.body());
        assertEquals(200, response.statusCode());

    }

    @Test
    void AllSubtasksGetMethodHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2024, 1, 1, 0, 0));

        Subtask subtask2 = new Subtask(1, TaskType.TASK, "test1", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2024, 2, 1, 12, 0));
        Subtask subtask3 = new Subtask(2, TaskType.TASK, "test2", Status.NEW, "test2 des", epic1,
                80, LocalDateTime.of(2024, 3, 1, 10, 0));

         httpTaskManager.createEpic(epic1);

        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);

        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getAllSubTasks()), response.body());
    }

    @Test
    void SubtaskGetByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2024, 1, 1, 0, 0));

        Subtask subtask2 = new Subtask(1, TaskType.TASK, "test1", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2024, 2, 1, 12, 0));
        Subtask subtask3 = new Subtask(2, TaskType.TASK, "test2", Status.NEW, "test2 des", epic1,
                80, LocalDateTime.of(2024, 3, 1, 10, 0));

        httpTaskManager.createEpic(epic1);

        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);


        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getSubtaskById(3)), response.body());
        assertEquals(200, response.statusCode());

    }

    @Test
    void SubtaskPostEndpointHandlerTest()throws IOException, InterruptedException{
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2024, 1, 1, 0, 0));

        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask1))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        httpTaskManager.createSubtask(subtask1);
        assertEquals("Задача успешно добавлена/обновлена", response.body());
        assertEquals(200, response.statusCode());

    }


    @Test
    void SubtaskTaskDeleteByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2024, 1, 1, 0, 0));

        Subtask subtask2 = new Subtask(1, TaskType.TASK, "test1", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2024, 2, 1, 12, 0));
        Subtask subtask3 = new Subtask(2, TaskType.TASK, "test2", Status.NEW, "test2 des", epic1,
                80, LocalDateTime.of(2024, 3, 1, 10, 0));

        httpTaskManager.createEpic(epic1);

        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Subtask> actual = httpTaskManager.getAllSubTasks();

        assertEquals(2, actual.size());
        assertEquals(200, response.statusCode());

    }


    @Test
    void SubtaskDeleteEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1, TaskType.EPIC, "epic 1", Status.NEW, "epic1 test");

        Subtask subtask1 = new Subtask(1, TaskType.SUBTASK, "subtask1", Status.NEW, "subtask1 test",
                epic1, 60, LocalDateTime.of(2024, 1, 1, 0, 0));

        Subtask subtask2 = new Subtask(1, TaskType.TASK, "test1", Status.NEW, "test1 des", epic1,
                60, LocalDateTime.of(2024, 2, 1, 12, 0));
        Subtask subtask3 = new Subtask(2, TaskType.TASK, "test2", Status.NEW, "test2 des", epic1,
                80, LocalDateTime.of(2024, 3, 1, 10, 0));

        httpTaskManager.createEpic(epic1);

        httpTaskManager.createSubtask(subtask1);
        httpTaskManager.createSubtask(subtask2);
        httpTaskManager.createSubtask(subtask3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Task> actual = httpTaskManager.getAllTasks();

        assertEquals(0, actual.size());
        assertEquals(200, response.statusCode());

    }

    @Test
    void EpicGetEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);

        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);


        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getAllEpicTasks()), response.body());
    }

    @Test
    void EpicGetByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);

        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);


        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getEpicTaskById(1)), response.body());

    }

    @Test
    void EpicDeleteByIdEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);

        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Epic> actual = httpTaskManager.getAllEpicTasks();

        assertEquals(2, actual.size());
        assertEquals(200, response.statusCode());

    }


    @Test
    void EpicDeleteEndpointHandlerTest() throws IOException, InterruptedException {
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);
        Epic epic2 = new Epic("epic2", "epic2 descrp", Status.NEW);
        Epic epic3 = new Epic("epic3", "epic3 descrp", Status.NEW);

        httpTaskManager.createEpic(epic1);
        httpTaskManager.createEpic(epic2);
        httpTaskManager.createEpic(epic3);


        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ArrayList<Epic> actual = httpTaskManager.getAllEpicTasks();

        assertEquals(0, actual.size());
        assertEquals(200, response.statusCode());

    }

    @Test
    void EpicPostEndpointHandlerTest()throws IOException, InterruptedException{
        Epic epic1 = new Epic("epic1", "epic1 descrp", Status.NEW);

        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1))).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        httpTaskManager.createEpic(epic1);
        assertEquals("Задача успешно добавлена/обновлена", response.body());
        assertEquals(200, response.statusCode());

    }


    @Test
    void HistoryEndpointHandlerTest() throws IOException, InterruptedException{
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = new Task(2, TaskType.TASK, "test2", Status.NEW, "test2 des",
                80, LocalDateTime.of(2023, 1, 1, 10, 0));
        Task task3 = new Task(3, TaskType.TASK, "test3", Status.NEW, "test3 des",
                40, null);



        httpTaskManager.createTask(task1);
        httpTaskManager.createTask(task2);
        httpTaskManager.createTask(task3);


        httpTaskManager.getTaskById(3);



        Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(gson.toJson(httpTaskManager.getHistory()), response.body());
        assertEquals(200, response.statusCode());



    }







}