package heap;

/**
 * @author priyamvora
 * @created 27/04/2021
 */
public class Heap {
    private final int[] heap;
    private int size;
    private final int maxSize;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.heap = new int[maxSize + 1];
        this.heap[0] = Integer.MIN_VALUE;
        this.size = 0;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return 2 * pos;
    }

    private int rightChild(int pos) {
        return 2 * pos + 1;
    }

    private void insert(int value) {
        if (size >= maxSize) {
            return;
        }
        heap[++size] = value;
        int current = size;
        while (current > 0 && heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    private void heapify(int index) {
        while (true) {
            int left = 2 * index;
            int right = 2 * index + 1;
            if (left > this.size) {
                return;
            }
            if (right > this.size) {
                right = left;
            }

            int smallerValue = heap[left] <= heap[right] ? left : right;
            if (heap[index] > heap[smallerValue]) {
                swap(index, smallerValue);
                index = smallerValue;
            } else {
                return;
            }
        }
    }

    private int extractMin() {
        int min = heap[1];
        heap[1] = heap[size];
        size--;
        heapify(1);
        return min;
    }

    private void swap(int ind1, int ind2) {
        int temp = heap[ind1];
        heap[ind1] = heap[ind2];
        heap[ind2] = temp;
    }
}
