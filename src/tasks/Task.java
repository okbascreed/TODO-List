package tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private Integer id;
    private Status status;
    private TaskType type;
    private int duration;
    private LocalDateTime startTime;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
     }

    public Task(Integer id, TaskType type, String name, Status status, String description) {
        this(name, description, status);
        this.id = id;
        this.type = type;
    }
    public Task(Integer id, TaskType type, String name, Status status, String description, int duration,
                LocalDateTime startTime) {
        this(name, description, status);
        this.duration = duration;
        this.startTime = startTime;
        this.id = id;
        this.type = type;
    }

    public LocalDateTime getEndTime(){
        return startTime.plusMinutes(duration);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public int getDuration(){
        return duration;
    }
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
}