package managers;

import com.google.gson.Gson;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class HttpTaskManager extends FileBackedTasksManager{

    private KVTaskClient kvTaskClient;
    private static Gson gson = new Gson();


    public HttpTaskManager(URL url) throws IOException, InterruptedException {
        super(url);
        kvTaskClient = new KVTaskClient(url);
    }

    public HttpTaskManager loadFromServer(String key) throws IOException, InterruptedException {
       return gson.fromJson(KVTaskClient.load(key), HttpTaskManager.class);
    }



}
