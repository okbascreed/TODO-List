package tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

    private Epic epic;

    private LocalDateTime endTime;

    private LocalDateTime startTime;

    private int duration;

    public Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }
    public Subtask(int id, TaskType type, String name, Status status, String description, Epic epic){
        super(id, type, name, status, description);
        this.epic = epic;
    }

    public Subtask(int id, TaskType type, String name, Status status, String description, Epic epic, int duration,
                   LocalDateTime startTime){
        super(id, type, name, status, description, duration, startTime);
        this.epic = epic;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        Status epicStatus = epic.getStatus();
        epic.setStatus(epicStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(epic, subtask.epic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epic);
    }
    public Epic getEpic(){
        return epic;
    }

}
