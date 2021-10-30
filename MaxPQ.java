import java.util.List;
import java.util.NoSuchElementException;

/*
 * Program name: MaxPQ.java
 * Author: Xin Li
 * Course: PA10
 * Description: a priority queue backed by a binary max-heap.
 * growCpacity: increase the capacity
 * enqueue: add item into queue
 * dequeue: remove item from queue
 * goUp and goDown: arrage the node up/down
 */
public class MaxPQ {
    private static final int CAPACITY = 10;
    private int size;
    private int capacity;
    private Node data[];

    public MaxPQ() {
        data = new Node[CAPACITY];
        size = 0;
        capacity = CAPACITY;
    }

    /**
     * double the capacity of queue
     */
    public void growCapacity() {
        this.capacity *= 2;
        Node tmp[] = new Node[this.capacity];
        for (int i = 0; i <= size; i++) {
            tmp[i] = data[i];
        }
        data = tmp;
    }

    /**
     * add an new item into the queue
     */
    public void enqueue(List<String> ladder, int priority) {
        enqueue(new Node(ladder, priority));
    }

    /**
     * add an new item into the queue
     */
    public void enqueue(Node node) {
        if (size + 1 >= capacity) growCapacity();
        data[++size] = node;
        goUp(size);
    }

    /**
     * let one node go up
     */
    private void goUp(int i) {
        if (i <= 1)
            return;
        int p = i / 2;
        if (data[p].priority < data[i].priority) {
            Node tmp = data[p];
            data[p] = data[i];
            data[i] = tmp;
            goUp(p);
        }
    }

    /**
     * dequeue one node
     */
    public List<String> dequeue() {
        if(size == 0)
            throw new NoSuchElementException("Can not dequeue from an empty queue!");
        Node ret = data[1];
        data[1] = data[size];
        data[size--] = null;
        goDown(1);
        return ret.ladder;
    }

    /**
     * let one node go down
     */
    public void goDown(int i) {
        if (size == 0 || i * 2 > size) return;
        Node p = data[i * 2];
        Node q = data[(i * 2) + 1];
        if (q == null) {
            cmpSwap(i, i*2);
            goDown(i*2);
        }else {
            if (q.priority > p.priority) {
                cmpSwap(i, i * 2 + 1);
                goDown(i * 2 + 1);
            } else if (p.priority > q.priority) {
                cmpSwap(i, i * 2);
                goDown(i * 2);
            }
        }
    }

    /**
     * compare and swap two nodes
     */
    public void cmpSwap(int i, int j) {
        if (data[i].priority < data[j].priority) {
            Node tmp = data[j];
            data[j] = data[i];
            data[i] = tmp;
        }
    }

    /**
     * check if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * convert the queue into string
     */
    public String toString() {
        String s = "{";

        for (int i = 1; i < size + 1; i++) {
            s += data[i].toString() + ", ";
        }
        if (s.length() > 1) {
            s = s.substring(0, s.length() - 2);
        }
        s += "}";
        return s;
    }

    /*
     * Node class
     */
    private class Node{
        List<String> ladder;
        int priority;

        /**
         * Constructor
         */
        public Node(List<String> l, int p) {
            this.ladder = l;
            this.priority = p;
        }

        /**
         * convert the node into string
         */
        public String toString() {
            return this.ladder + " (" + this.priority + ")";
        }
    }
}
