package managers;

import com.google.gson.*;

import com.google.gson.reflect.TypeToken;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;

    private HashMap<Integer, Task> tasksList = new HashMap();
    private HashMap<Integer, Epic> epicsList = new HashMap();
    private HashMap<Integer, Subtask> subtasksList = new HashMap();

    private LinkedHashMap<Integer, Task> history = new LinkedHashMap<>();

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private URL url;

    public HttpTaskManager(URL url) throws IOException, InterruptedException {
        super(new File("src/tasks/HttpTaskManagerFile.txt"));
        this.url = url;
        kvTaskClient = new KVTaskClient(url);
    }

    public HttpTaskManager loadFromServer() throws IOException, InterruptedException {

        HttpTaskManager httpTaskManager = this;

        List<Task> loadedTasks = gson.fromJson(kvTaskClient.load("Tasks"), new TypeToken<List<Task>>() {
        }.getType());

        for (Task task : loadedTasks) {
            tasksList.put(task.getId(), task);
        }

        List<Epic> loadedEpics = gson.fromJson(kvTaskClient.load("Epics"), new TypeToken<List<Epic>>() {
        }.getType());

        for (Epic epic : loadedEpics) {
            epicsList.put(epic.getId(), epic);
        }

        List<Subtask> loadedSubtasks = gson.fromJson(kvTaskClient.load("SubTasks"), new TypeToken<List<Subtask>>() {
        }.getType());

        for (Subtask subtask : loadedSubtasks) {
            subtasksList.put(subtask.getId(), subtask);
        }

        return httpTaskManager;
    }

    public void saveToServer() throws IOException, InterruptedException {
        kvTaskClient.put("Tasks", gson.toJson(new ArrayList<>(tasksList.values())));
        kvTaskClient.put("Epics", gson.toJson(new ArrayList<>(epicsList.values())));
        kvTaskClient.put("SubTasks", gson.toJson(new ArrayList<>(subtasksList.values())));
        kvTaskClient.put("History", gson.toJson(new ArrayList<>(history.values())));
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        tasksList.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        epicsList.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        subtasksList.put(subtask.getId(), subtask);
    }

    @Override
    public Task getTaskById(int id){
        Task task = tasksList.get(id);
        if(task != null){
            history.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public void deleteTaskById(int id){
        tasksList.remove(id);
        history.remove(id);
    }

    @Override
    public ArrayList<Task> getAllTasks(){
        super.getAllTasks();
        return new ArrayList<>(tasksList.values());
    }

    @Override
    public void removeAllTasks(){
        tasksList.clear();
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks(){
        return new ArrayList<>(subtasksList.values());
    }

    @Override
    public void removeAllSubtasks(){
        subtasksList.clear();
    }

    @Override
    public Subtask getSubtaskById(int id){
        Subtask subtask = subtasksList.get(id);
        history.put(subtask.getId(), subtask);
        return subtask;
    }

    @Override
    public void deleteSubtaskById(int id){
        subtasksList.remove(id);
        history.remove(id);
    }

    @Override
    public ArrayList<Epic> getAllEpicTasks(){
        return new ArrayList<>(epicsList.values());
    }

    @Override
    public void removeAllEpicTasks(){
        epicsList.clear();
    }

    @Override
    public Task getEpicTaskById(int id){
        Epic epic = epicsList.get(id);
        history.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void deleteEpicTaskById(int id){
        epicsList.remove(id);
        history.remove(id);
    }

    @Override
    public List<Task> getHistory(){
        return new ArrayList<>(history.values());
    }

}







