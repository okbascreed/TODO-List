package managers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class HttpTaskServer {

    private HttpServer httpServer;
    static FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("src/tasks/TasksHandlerFile.txt"));
    private static Gson gson = new Gson();

    public void startHttpServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/tasks/task/", new TaskHandler());
        httpServer.createContext("/tasks/subtask/", new SubTaskHandler());
        httpServer.createContext("/tasks/epic/", new EpicHandler());
        httpServer.createContext("/tasks/history/", new HistoryHandler());


        httpServer.start();

        System.out.println("Сервер запущен");
    }


    static class TasksHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String response = "";
            String method = exchange.getRequestMethod();
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            Set<Task> tasks = fileBackedTasksManager.getPrioritizedTasks();


            response = gson.toJson(tasks);


            exchange.sendResponseHeaders(200, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        }

    }

    static class TaskHandler implements HttpHandler {

        protected static Map<String, String> queryToMap(String query) {
            if (query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            String method = exchange.getRequestMethod();
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            switch (method) {
                case ("GET"):
                    if (pathSplit.length >= 2) {

                        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());

                        response = gson.toJson(fileBackedTasksManager.getTaskById(Integer.parseInt(params.get("id"))));

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        response = gson.toJson(fileBackedTasksManager.getAllTasks());

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;

                case ("POST"):

                    Task task = gson.fromJson(body, Task.class);

                    if (fileBackedTasksManager.getAllTasks().contains(task)) {
                        fileBackedTasksManager.updateTask(task);
                    } else {
                        fileBackedTasksManager.createTask(task);
                    }
                    response = "Задача успешно добавлена/обновлена";
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    break;

                case ("DELETE"):

                    if (pathSplit.length >= 2) {

                        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());

                        fileBackedTasksManager.deleteTaskById(Integer.parseInt(params.get("id")));

                        response = "Задача " + params.get("id") + " успешно удалена.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }

                    } else {
                        fileBackedTasksManager.removeAllTasks();

                        response = "Все Tasks успешно удалены.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;
            }
        }
    }

    static class SubTaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            String method = exchange.getRequestMethod();
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            switch (method) {
                case ("GET"):
                    if (pathSplit.length >= 2) {

                        Map<String, String> params = TaskHandler.queryToMap(exchange.getRequestURI().getQuery());

                        response = gson.toJson(fileBackedTasksManager.getSubtaskById(Integer.parseInt(params.get("id"))));

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        response = gson.toJson(fileBackedTasksManager.getAllSubTasks());

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;

                case ("POST"):

                    Subtask subtask = gson.fromJson(body, Subtask.class);

                    if (fileBackedTasksManager.getAllSubTasks().contains(subtask)) {
                        fileBackedTasksManager.updateSubtask(subtask);
                    } else {
                        fileBackedTasksManager.updateSubtask(subtask);
                    }
                    response = "Задача успешно добавлена/обновлена";
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    break;

                case ("DELETE"):

                    if (pathSplit.length >= 2) {

                        Map<String, String> params = TaskHandler.queryToMap(exchange.getRequestURI().getQuery());

                        fileBackedTasksManager.deleteSubtaskById(Integer.parseInt(params.get("id")));

                        response = "Задача " + params.get("id") + " успешно удалена.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }

                    } else {
                        fileBackedTasksManager.removeAllSubtasks();

                        response = "Все Tasks успешно удалены.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;
            }
        }
    }

    static class EpicHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            String method = exchange.getRequestMethod();
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            switch (method) {
                case ("GET"):
                    if (pathSplit.length >= 2) {

                        Map<String, String> params = TaskHandler.queryToMap(exchange.getRequestURI().getQuery());

                        response = gson.toJson(fileBackedTasksManager.getEpicTaskById(Integer.parseInt(params.get("id"))));

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        response = gson.toJson(fileBackedTasksManager.getAllEpicTasks());

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;

                case ("POST"):

                    Epic epic = gson.fromJson(body, Epic.class);

                    if (fileBackedTasksManager.getAllEpicTasks().contains(epic)) {
                        fileBackedTasksManager.updateEpicTask(epic);
                    } else {
                        fileBackedTasksManager.updateEpicTask(epic);
                    }
                    response = "Задача успешно добавлена/обновлена";
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    break;

                case ("DELETE"):

                    if (pathSplit.length >= 2) {

                        Map<String, String> params = TaskHandler.queryToMap(exchange.getRequestURI().getQuery());

                        fileBackedTasksManager.deleteEpicTaskById(Integer.parseInt(params.get("id")));

                        response = "Задача " + params.get("id") + " успешно удалена.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }

                    } else {
                        fileBackedTasksManager.removeAllEpicTasks();

                        response = "Все Epics успешно удалены.";

                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    break;
            }
        }
    }

    static class HistoryHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            //String method = exchange.getRequestMethod();
            //InputStream inputStream = exchange.getRequestBody();
           // String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String path = exchange.getRequestURI().getPath();
            String[] pathSplit = path.split("/");


            if (pathSplit.length >= 2) {

                response = gson.toJson(fileBackedTasksManager.getHistory());

                exchange.sendResponseHeaders(200, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
}





