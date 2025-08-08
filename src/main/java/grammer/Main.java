package grammer;

import java.util.HashMap;
import java.util.Map;

class Node {
    int key, value;
    Node left, right;
    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class LRUCache {
    Map<Integer, Node> hash = new HashMap<>(); // <key, 链表地址>
    Node head = new Node(0, 0);
    Node tail = new Node(0, 0);
    int empty;

    public LRUCache(int capacity) {
        head.right = tail;
        head.left = tail;
        tail.right = head;
        tail.left = head;
        empty = capacity;
    }

    void deleteNode(Node u) {
        u.left.right = u.right;
        u.right.left = u.left;
        hash.remove(u.key);
        empty++;
    }

    void addNode(int key, int value) {
        Node u = new Node(key, value);
        u.right = head.right;
        u.left = head;
        head.right = u;
        u.right.left = u;
        hash.put(key, u);
        empty--;
    }

    public int get(int key) {
        if (hash.containsKey(key)) {
            int value = hash.get(key).value;
            deleteNode(hash.get(key));
            addNode(key, value);
            return value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (hash.containsKey(key)) {
            deleteNode(hash.get(key));
        }

        if (empty == 0) {
            deleteNode(tail.left);
        }

        addNode(key, value);
    }
}