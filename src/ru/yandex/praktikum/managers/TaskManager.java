package ru.yandex.praktikum.managers;

import ru.yandex.praktikum.tasks.Epic;
import ru.yandex.praktikum.tasks.Status;
import ru.yandex.praktikum.tasks.Subtask;
import ru.yandex.praktikum.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    ArrayList<Task> getAllTasks();

    void removeAllTasks();

    Task getTaskById(int id);

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTaskById(int id);

    ArrayList<Subtask> getAllSubTasks();

    void removeAllSubtasks();

    Task getSubtaskById(int id);

    void createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void deleteSubtaskById(int id);

    ArrayList<Epic> getAllEpicTasks();

    void removeAllEpicTasks();

    Task getEpicTaskById(int id);

    void createEpic(Epic epic);

    void updateEpicTask(Epic epic);

    void deleteEpicTaskById(int id);

    ArrayList<Subtask> getEpicSubtasks(int id);

    Status getEpicStatus(Epic epic);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();

    void checkTaskIntersection(Task task) throws ManagerIntersectionException;

    String getTaskType(Task task);

}