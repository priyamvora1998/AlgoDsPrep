package misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author priyamvora
 * @created 01/04/2021
 */

class Node {
    Node prev, next;
    Integer data;
    Long expiry; // Milliseconds

    public Node(int data, Integer ttl) {
        this.data = data;
        if (ttl != null) {
            this.expiry = (System.currentTimeMillis() + ttl * 1000L);
        }
    }

    public Node(int data) {
        this.data = data;
    }

    public Node() {
    }
}


public class LRUCache {
    private Node head;
    private Node tail;
    private final int maxSize;
    private final Map<Integer, Node> map;
    private int currentSize;

    public LRUCache(int maxSize) {
        this.head = null;
        this.tail = null;
        this.maxSize = maxSize;
        this.map = new HashMap<>();
        this.currentSize = 0;
    }

    public void printCache() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println();
        temp = tail;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.prev;
        }
        System.out.println();
    }

    public void addToHead(Node node) {
        node.next = head;
        head.prev = node;
        node.prev = null;
        head = node;
    }

    public void addToCache(int key, Integer ttlInSeconds) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            if (node == tail) {
                tail = node.prev;
            }
            node.prev.next = node.next;
            addToHead(node);
        } else {
            Node node = new Node(key, ttlInSeconds);
            if (currentSize < maxSize) {
                if (head == null) {
                    head = node;
                    tail = head;
                } else {
                    addToHead(node);
                }
                currentSize++;
            } else {
                map.remove(tail.data);
                addToHead(node);
                tail = tail.prev;
                tail.next = null;
            }
            map.put(key, node);
        }
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        if (node == tail) {
            tail = node.prev;
        }
        node.prev.next = node.next;
        addToHead(node);
        return node.data;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(1);
        lruCache.addToCache(10, 30);
        lruCache.printCache();
        System.out.println();
        lruCache.addToCache(20, 30);
        lruCache.printCache();
        System.out.println();
        lruCache.addToCache(30, 30);
        lruCache.printCache();
        System.out.println();
        lruCache.addToCache(20, 30);
        lruCache.printCache();

    }
}
