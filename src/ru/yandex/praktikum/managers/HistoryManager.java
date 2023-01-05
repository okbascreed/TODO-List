package ru.yandex.praktikum.managers;

import ru.yandex.praktikum.tasks.Task;
import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();
}
