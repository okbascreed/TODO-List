package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void init() {
        taskManager = new InMemoryTaskManager();
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
    void createTaskIntersectionCheck(){
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
    void getAllSubtasksWithEmptyList(){
        super.getAllSubtasksWithEmptyList();
    }

    @Test
    void getAllSubtasksWithBadId(){
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
    void getAllEpicTasksWithBadId(){
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

    void getEpicTaskByIdWithEmptyList(){
        super.getEpicTaskByIdWithEmptyList();
    }

    @Test
    void getEpicTaskByIdWithBadId(){
        super.getEpicTaskByIdWithBadId();
    }

    @Test
    void createEpic() {
        super.createEpic();
    }

    @Test
    void createEpicTaskIntersectionCheck(){
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
    void checkTaskIntersection(){
        super.checkTaskIntersection();
    }

}
