
package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    File file = new File("src/FileBackedTaskManagerTest.txt");

    @BeforeEach
    void init() {
        taskManager = new FileBackedTasksManager(file);
        file.delete();
    }

    @Test
    void loadFromFile() {
        initEpics();
        ArrayList<Epic> arrayList = taskManager.getAllEpicTasks();
        FileBackedTasksManager manager1 = taskManager.loadFromFile(file);
        ArrayList<Epic> arrayList2 = manager1.getAllEpicTasks();
        assertEquals(arrayList, arrayList2);
    }

    @Test
    void save(){
        initTasks();

        List<String> result = new ArrayList<>();

        result.add("id,type,name,status,description,epic,duration,dateTime");
        result.add("1,TASK,test1,NEW,test1 des,60,2023-01-01T12:00");
        result.add("2,TASK,test2,NEW,test2 des,80,2023-01-01T10:00");
        result.add("3,TASK,test3,NEW,test3 des,40,null");

        List<String> data = new ArrayList<>();

        try {

            data = Files.readAllLines(file.toPath());

        } catch (IOException e) {
            System.out.println(e);
        }
        assertEquals(result, data);

    }

    @Test
    void saveHistory() {
        initTasks();
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);

        taskManager.getHistory();

        String result = ("id,type,name,status,description,epic,duration,dateTime" +
                "1,TASK,test1,NEW,test1 des,60,2023-01-01T12:00"
                + "2,TASK,test2,NEW,test2 des,80,2023-01-01T10:00"
                + "3,TASK,test3,NEW,test3 des,40,null1,2,3,");

        String data = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            data = bufferedReader.readLine();

            while (bufferedReader.ready()) {
                data += bufferedReader.readLine();
                if (data.isBlank()) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        assertEquals(result, data);
    }

    @Test
    void toStringCheck() {
        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        taskManager.createTask(task1);

        String str = taskManager.toString(task1);
        String test = "1,TASK,test1,NEW,test1 des,,60,2023-01-01T12:00";

        assertEquals(test, str);

    }

    @Test
    void fromString() {
        String test = "1,TASK,test1,NEW,test1 des,60,2023-01-01T12:00";

        Task task1 = new Task(1, TaskType.TASK, "test1", Status.NEW, "test1 des",
                60, LocalDateTime.of(2023, 1, 1, 12, 0));
        Task task2 = taskManager.fromString(test);

        assertEquals(task1, task2);
    }

    @Test
    void historyToString() {
        initTasks();
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.getHistory();

        String test = "1,2,3,";
        String historyToString = taskManager.historyToString(taskManager.getHistoryManager());

        assertEquals(test, historyToString);
    }

    @Test
    void historyFromString() {
        initTasks();
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.getHistory();
        String str = "1,2,3,";
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> list2 = taskManager.historyFromString(str);

        assertEquals(list, list2);
    }

    @Test
    void getAllTasks() {
        super.getAllTasks();
    }

    @Test
    void getAllTasksWithEmptyList() {
        super.getAllTasksWithEmptyList();
    }

    @Test
    void getAllTasksWithBadId() {
        super.getAllTasksWithBadId();
    }

    @Test
    void removeAllTasks() {
        super.removeAllTasks();
    }

    @Test
    void removeAllTasksWithEmptyList() {
        super.removeAllTasksWithEmptyList();
    }

    @Test
    void removeAllTasksWithBadID() {
        super.removeAllTasksWithBadId();
    }

    @Test
    void getTaskById() {
        super.getTaskById();
    }

    @Test
    void getTaskByIdWithEmptyList() {
        super.getTaskByIdWithEmptyList();
    }

    @Test
    void getTaskByIdWithBadId() {
        super.getTaskByIdWithBadId();
    }


    @Test
    void createTask() {
        super.createTask();
    }

    @Test
    void createTaskIntersectionCheck() {
        super.createTaskIntersectionCheck();
    }

    @Test
    void updateTask() {
        super.updateTask();
    }

    @Test
    void deleteTaskById() {
        super.deleteTaskById();
    }

    @Test
    void getAllSubTasks() {
        super.getAllSubTasks();
    }

    @Test
    void getAllSubtasksWithEmptyList() {
        super.getAllSubtasksWithEmptyList();
    }

    @Test
    void getAllSubtasksWithBadId() {
        super.getAllSubtasksWithBadId();
    }

    @Test
    void removeAllSubtasks() {
        super.removeAllSubtasks();
    }

    @Test
    void removeAllSubTasksWithEmptyList() {
        super.removeAllSubTasksWithEmptyList();
    }

    @Test
    void removeAllSubTasksWithBadId() {
        super.removeAllSubTasksWithBadId();
    }

    @Test
    void getSubtaskById() {
        super.getSubtaskById();
    }

    @Test
    void getSubtaskByIdWithEmptyList() {
        super.getSubtaskByIdWithEmptyList();
    }

    @Test
    void getSubtaskByIdWithBadId() {
        super.getSubtaskByIdWithBadId();
    }

    @Test
    void createSubtask() {
        super.createSubtask();
    }

    @Test
    void updateSubtask() {
        super.updateSubtask();
    }

    @Test
    void deleteSubtaskById() {
        super.deleteSubtaskById();
    }

    @Test
    void getAllEpicTasks() {
        super.getAllEpicTasks();
    }

    @Test
    void getAllEpicTasksWithEmptyList() {
        super.getAllEpicTasksWithEmptyList();
    }

    @Test
    void getAllEpicTasksWithBadId() {
        super.getAllEpicTasksWithBadId();
    }

    @Test
    void removeAllEpicTasks() {
        super.removeAllEpicTasks();
    }

    @Test
    void removeAllEpicTasksWithEmptyList() {
        super.removeAllEpicTasksWithEmptyList();
    }

    @Test
    void removeAllEpicTasksWithBadId() {
        super.removeAllEpicTasksWithBadId();
    }

    @Test
    void getEpicTaskById() {
        super.getEpicTaskById();
    }

    @Test
    void getEpicTaskByIdWithEmptyList() {
        super.getEpicTaskByIdWithEmptyList();
    }

    @Test
    void getEpicTaskByIdWithBadId() {
        super.getEpicTaskByIdWithBadId();
    }

    @Test
    void createEpic() {
        super.createEpic();
    }

    @Test
    void createEpicTaskIntersectionCheck() {
        super.createEpicTaskIntersectionCheck();
    }

    @Test
    void updateEpicTask() {
        super.updateEpicTask();
    }

    @Test
    void deleteEpicTaskById() {
        super.deleteEpicTaskById();
    }

    @Test
    void getEpicSubtasks() {
        super.getEpicSubtasks();
    }

    @Test
    void getEpicStatus() {
        super.getEpicStatus();
    }

    @Test
    void getHistory() {
        super.getHistory();
    }

    @Test
    void getTaskType() {
        super.getTaskType();
    }

    @Test
    void checkTaskIntersection() {
        super.checkTaskIntersection();
    }

}
