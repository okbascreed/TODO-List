package ru.yandex.praktikum.tasks;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks = new ArrayList<>();

    @SerializedName("Epic Duration")
    private int duration;

    @SerializedName("Epic StartTime")
    private LocalDateTime startTime;

    @SerializedName("Epic EndTime")
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }



    public Epic(int id, TaskType type, String name, Status status, String description) {
        super(id, type, name, status, description);
    }
    public Epic(int id, TaskType type, String name, Status status, String description, int duration,
                LocalDateTime startTime){
        super(id, type, name, status, description);
    }

    public Epic(int id, TaskType type, String name, Status status, String description, int duration,
                LocalDateTime startTime, LocalDateTime endTime){
        super(id, type, name, status, description);
    }

    public void addSubtask(Subtask subtask) {
        Status status = getStatus();
        LocalDateTime subtaskStartTime = subtask.getStartTime();
        duration += subtask.getDuration();

        if(subtaskStartTime != null && subtask.getEndTime() !=null) {
            if (startTime == null) {
                startTime = subtaskStartTime;
            }
            if (subtaskStartTime.isBefore(startTime)) {
                startTime = subtaskStartTime;
            }
            if (endTime == null) {
                endTime = subtask.getEndTime();
            }
            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }
        if (status.equals(Status.NEW) && subtask.getStatus().equals(Status.NEW)) {
            super.setStatus(Status.NEW);
        } else if (status.equals(Status.DONE) && subtask.getStatus().equals(Status.DONE)) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }

        subtasks.add(subtask);
    }


    public LocalDateTime getStartTime(){
        return startTime;
    }

    @Override
    public LocalDateTime getEndTime(){
        return endTime;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void updateEpicTaskStatus() {
        int newStatus = 0;
        int doneStatus = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.NEW)) {
                newStatus++;
            }
            if (subtask.getStatus().equals(Status.DONE)) {
                doneStatus++;
            }
        }
        if (newStatus == subtasks.size() || subtasks.isEmpty()) {
            super.setStatus(Status.NEW);

        } else if ( doneStatus == subtasks.size()) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }
    }


    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }


    @Override
    public String toString() {
        return "Epic{" + "name=" + getName() + "," + "subtasks=" + subtasks + "," + "status=" + getStatus() +
                ", epicId=" + getId() + ", duration=" + duration + ", startTime=" + startTime +
                ", endTime=" + endTime +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Epic epic = (Epic) o;
        return Objects.equals(subtasks, epic.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }
}