import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.*;
import tasks.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        new KVServer().start();
        KVTaskClient client = new KVTaskClient(new URL("http://localhost:8080"));
        client.put("1671428237571", "test");


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


  /*  static class TasksHandler implements HttpHandler {

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("src/tasks/TasksHandlerFile.txt"));


        private static Gson gson = new Gson();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";
            String method = exchange.getRequestMethod();
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            switch(method) {
                case("GET"):
                    if(pathSplit.length > 2){
                        int taskId = Integer.parseInt(pathSplit[2]);
                        response = gson.toJson(fileBackedTasksManager.getTaskById(taskId));

                        exchange.sendResponseHeaders(200,0);
                        try(OutputStream os = exchange.getResponseBody()){
                            os.write(response.getBytes());
                        }



                    } else {
                        response = gson.toJson(fileBackedTasksManager.getAllTasks());

                        exchange.sendResponseHeaders(200,0);
                        try(OutputStream os = exchange.getResponseBody()){
                            os.write(response.getBytes());
                        }



                    }
            }


        }


    }*/

}