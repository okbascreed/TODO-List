package managers;

import com.google.gson.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;

import static managers.HttpTaskManager.InterfaceSerializer.interfaceSerializer;

public class HttpTaskManager extends FileBackedTasksManager  {

    private KVTaskClient kvTaskClient;
    //private static Gson gson = new Gson();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(HistoryManager.class, interfaceSerializer(HttpTaskManager.class))
            .create();

    private URL url;

    public HttpTaskManager(URL url) throws IOException, InterruptedException {
        super(new File ("src/tasks/HttpTaskManagerFile.txt"));
        this.url = url;
        kvTaskClient = new KVTaskClient(url);
    }


    public HttpTaskManager loadFromServer(String key) throws IOException, InterruptedException {

        HttpTaskManager httpTaskManager = gson.fromJson(kvTaskClient.load(key), interfaceSerializer(HttpTaskManager.class));
        if(httpTaskManager == null) {
            System.out.println("По данному key данные отсутствуют.");
        }
        ArrayList<Task> tasks = httpTaskManager.getAllTasks();
        for (Task task : tasks) {
            this.addTask(task);
        }

        ArrayList<Epic> epics = httpTaskManager.getAllEpicTasks();
        for(Epic epic : epics){
            this.addEpic(epic);
        }

        ArrayList<Subtask> subtasks = httpTaskManager.getAllSubTasks();
        for(Subtask subtask : subtasks){
            this.addSubtask(subtask);
        }
        return httpTaskManager;
    }

    public void saveToServer(String key) throws IOException, InterruptedException {
        String jsonString = gson.toJson(this);
        kvTaskClient.put(key, jsonString);
    }

    public KVTaskClient getKvTaskClient(){
        return kvTaskClient;
    }

    /*public void createTask(Task task) throws IOException, InterruptedException {
        try {
            checkTaskIntersection(task);
        } catch (ManagerIntersectionException e) {
            System.out.println(e.getMessage());
            return;
        }
        id++;
        task.setId(id);
        //this.loadFromServer(key);
        this.addTask(task);
        this.prioritizedTasks.add(task);
        saveToServer(key);
    }*/


    @Override
    public String toString() {
        return "HttpTaskManager{}";
    }


      static final class InterfaceSerializer<HttpTaskManager>
            implements JsonSerializer<HttpTaskManager>, JsonDeserializer<HttpTaskManager> {

        private final Class<HttpTaskManager> implementationClass;

        private InterfaceSerializer(final Class<HttpTaskManager> implementationClass) {
            this.implementationClass = implementationClass;
        }

         static <HttpTaskManager> Type interfaceSerializer(final Class<HttpTaskManager> implementationClass) {
            return new InterfaceSerializer<>(implementationClass).getClass();
        }

        @Override
        public JsonElement serialize(final HttpTaskManager value, final Type type, final JsonSerializationContext context) {
            final Type targetType = value != null
                    ? value.getClass()
                    : type;
            return context.serialize(value, targetType);
        }

        @Override
        public HttpTaskManager deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
            return context.deserialize(jsonElement, implementationClass);
        }

    }


}




