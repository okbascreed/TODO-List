package ru.yandex.praktikum.managers;

import ru.yandex.praktikum.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    CustomLinkedList customLinkedList = new CustomLinkedList();

    @Override
    public void add(Task task) {
        customLinkedList.removeNode(task.getId());
            customLinkedList.linkLast(task);

    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getAllTasks();
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(id);
    }

    public class CustomLinkedList {

        private Map<Integer, Node> nodeMap = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLast(Task task) {

            if (head == null) {
                Node firstNode = new Node(task, null, null);
                head = firstNode;
                nodeMap.put(task.getId(), firstNode);
                return;
            }
            if (tail == null) {
                Node newNode = new Node(task, head, null);
                if(task.equals(customLinkedList.head.getTask())) {
                    return;
                }
                head.setNextNode(newNode);
                tail = newNode;
                nodeMap.put(task.getId(), newNode);
                return;
            }
            Node newNode = new Node(task, tail, null);
            tail.setNextNode(newNode);
            tail = newNode;
            nodeMap.put(task.getId(), newNode);
            if (nodeMap.size() > 10) {
                nodeMap.remove(head.getTask().getId());
            }
        }

        private List<Task> getAllTasks() {
            ArrayList<Task> history = new ArrayList<>();
            Node node = head;
            while (node != null) {
                history.add(node.getTask());
                node = node.getNext();
            }
            return history;
        }

        private void removeNode(int id) {
            if (nodeMap.get(id) != null) {
                Node currentNode = nodeMap.get(id);

                if (currentNode == head) {
                    if(currentNode.getNext() != null){
                        Node newHead = currentNode.getNext();
                        newHead.setPreviousNode(null);
                        Task task = head.getTask();
                        int taskId = task.getId();
                        nodeMap.remove(taskId);
                        head = newHead;
                    } else {
                        nodeMap.clear();
                    }


                } else if (currentNode == tail) {
                    Node previousNode = tail.getPreviousNode();
                    previousNode.setNextNode(null);
                    Task task = tail.getTask();
                    int taskId = task.getId();
                    nodeMap.remove(taskId);
                    tail = previousNode;
                } else {
                    Node nextNode = currentNode.getNext();
                    Node previousNode = currentNode.getPreviousNode();
                    Task task = currentNode.getTask();
                    int taskId = task.getId();
                    nodeMap.remove(taskId);
                    if (previousNode != null) {
                        previousNode.setNextNode(nextNode);
                    }
                    if (nextNode != null) {
                        nextNode.setPreviousNode(previousNode);
                    }
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomLinkedList that = (CustomLinkedList) o;
            return Objects.equals(nodeMap, that.nodeMap) && Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodeMap, head, tail);
        }
    }
}
