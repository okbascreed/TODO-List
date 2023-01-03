package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.HttpTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.net.URL;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NewHttpTaskServer {

    private HttpServer httpServer;
    static HttpTaskManager httpTaskManager;

    static {
        try {
            httpTaskManager = new HttpTaskManager(new URL("http://localhost:8078"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpTaskManager getHttpTaskManager() {
        return httpTaskManager;
    }

    private static Gson gson = new Gson();

    public void startHttpServer() throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8080), 0);
        //httpServer.createContext("/tasks", new HttpTaskServer.TasksHandler());
        httpServer.createContext("/tasks/task/", this::TaskHandler);
        /*httpServer.createContext("/tasks/subtask/", new HttpTaskServer.SubTaskHandler());
        httpServer.createContext("/tasks/epic/", new HttpTaskServer.EpicHandler());
        httpServer.createContext("/tasks/history/", new HttpTaskServer.HistoryHandler());*/


        httpServer.start();

        System.out.println("Сервер запущен");
    }

    public void stopHttpServer() {
        httpServer.stop(0);
    }


    public void TaskHandler(HttpExchange httpExchange) throws IOException {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case ("GET"): {
                    if (Pattern.matches("^/tasks/task/\\D", path)){
                        String pathId = path.replaceFirst("tasks/task/?id=", "");
                        int id = parsePathId(pathId);
                        if(id != -1){
                            String response = gson.toJson(httpTaskManager.getTaskById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Получен некорректный Id " + pathId);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                        break;
                    }
                    } if (Pattern.matches("^/tasks/task/$", path)){
                        String response = gson.toJson(httpTaskManager.getAllTasks());
                        sendText(httpExchange, response);
                        return;
                    } else {
                    httpExchange.sendResponseHeaders(405, 0);
                    break;










                }
                case ("DELETE"): {
                    if (Pattern.matches("^/tasks/task/\\d+$", path)){
                        String pathId = path.replaceFirst("tasks/task/?id=", "");
                        int id = parsePathId(pathId);
                        if(id != -1){
                            httpTaskManager.deleteTaskById(id);
                            System.out.println("Задача " + id + "успешно удалена.");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Получен некорректный Id " + pathId);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else {
                        httpExchange.sendResponseHeaders(405, 0);
                    }

                    break;
                }
                default:{
                    System.out.println("Ждали GET/POST/DELETE, а получили " + method);
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }


    private int parsePathId(String path){
        try{
            return Integer.parseInt(path);
        } catch (NumberFormatException exception){
            return -1;
        }
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }


}
