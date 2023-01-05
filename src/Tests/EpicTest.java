package Tests;

import org.junit.jupiter.api.*;
import ru.yandex.praktikum.tasks.Epic;
import ru.yandex.praktikum.tasks.Status;
import ru.yandex.praktikum.tasks.Subtask;
import ru.yandex.praktikum.tasks.TaskType;


class EpicTest {

    private static Epic epic;

    @BeforeAll
    public static void initEpic() {
        epic = new Epic(1, TaskType.EPIC, "TEST NAME", Status.NEW, "TEST DESCRIPTION");
    }

    @Test
    @DisplayName("Тест - Пустой список подзадач")
    void shouldReturnStatusInProgressForEpic() {
        initEpic();
        epic.updateEpicTaskStatus();

        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Тест - Все подзадачи со статусом NEW")
    void shouldReturnNewStatusForEpicWithAllNewSubtasks() {
        initEpic();
        Subtask subtask1 = new Subtask("subtask name1", "subtask description1", Status.NEW, epic);
        Subtask subtask2 = new Subtask("subtask name2", "subtask description2", Status.NEW, epic);
        Subtask subtask3 = new Subtask("subtask name3", "subtask description3", Status.NEW, epic);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);
        epic.updateEpicTaskStatus();
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    @DisplayName("Тест - Все подзадачи со статусом DONE")
    void shouldReturnDoneStatusForEpicWithAllDoneSubtasks() {
        initEpic();
        Subtask subtask1 = new Subtask("subtask name1", "subtask description1", Status.DONE, epic);
        Subtask subtask2 = new Subtask("subtask name2", "subtask description2", Status.DONE, epic);
        Subtask subtask3 = new Subtask("subtask name3", "subtask description3", Status.DONE, epic);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        epic.updateEpicTaskStatus();
        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    @DisplayName("Тест - Подзадачи со статусами NEW и DONE")
    void shouldReturnInProgressStatusForEpicWithDoneAndNewSubtasks() {
        initEpic();
        Subtask subtask1 = new Subtask("subtask name1", "subtask description1", Status.NEW, epic);
        Subtask subtask2 = new Subtask("subtask name2", "subtask description2", Status.DONE, epic);
        Subtask subtask3 = new Subtask("subtask name3", "subtask description3", Status.DONE, epic);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        epic.updateEpicTaskStatus();
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    @DisplayName("Тест - Подзадачи со статусом IN_PROGRESS")
    void shouldReturnInProgressStatusForEpicWithInProgressSubtasks() {
        initEpic();
        Subtask subtask1 = new Subtask("subtask name1", "subtask description1", Status.IN_PROGRESS, epic);
        Subtask subtask2 = new Subtask("subtask name2", "subtask description2", Status.IN_PROGRESS, epic);
        Subtask subtask3 = new Subtask("subtask name3", "subtask description3", Status.IN_PROGRESS, epic);
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        epic.updateEpicTaskStatus();
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}