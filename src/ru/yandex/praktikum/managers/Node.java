package ru.yandex.praktikum.managers;

import ru.yandex.praktikum.tasks.Task;

public class Node {

    private Node previousNode;

    private Node nextNode;

    private Task task;

    public boolean hasNext() {
        return nextNode != null;
    }

    public Node getNext() {
        return nextNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public Node(Task task, Node previousNode, Node nextNode) {
        this.task = task;
        this.previousNode = previousNode;
        this.nextNode = nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Task getTask() {
        return task;
    }
}
