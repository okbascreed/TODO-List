package ru.yandex.praktikum.managers;

import java.io.IOException;
import java.net.URL;

public class Managers {

    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager(new URL("http://localhost:8078"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
