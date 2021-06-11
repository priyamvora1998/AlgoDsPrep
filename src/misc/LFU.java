package misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author priyamvora
 * @created 22/05/2021
 */
public class LFU {
    Map<Integer, Integer> map;
    HeapNode[] heap;
    int size;
    int lastIndex;

    public LFU(int size) {
        map = new HashMap<>();
        heap = new HeapNode[size];
        this.size = size;
        this.lastIndex = 0;
    }

    private int getLeft(int i) {
        return 2 * i + 1;
    }

    private int getRight(int i) {
        return 2 * i + 2;
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private void heapify(int index) {
        int left = getLeft(index);
        int right = getRight(index);
        int min = index;
        if (left < this.lastIndex) {
            min = (heap[min].freq < heap[left].freq) ? index : left;
        }
        if (right < this.lastIndex) {
            min = (heap[min].freq < heap[right].freq) ? index : right;
        }
        if (min != index) {
            map.put(heap[index].ele, min);
            map.put(heap[min].ele, index);
            swap(min, index);
            heapify(min);
        }
    }

    private void swap(int index1, int index2) {
        HeapNode temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public void increment(int index) {
        heap[index].freq++;
        heapify(index);
    }

    public void insert(int ele) {
        if (this.lastIndex == this.size) {
            map.remove(heap[0].ele);
            heap[0] = heap[--this.lastIndex];
            heapify(0);
        }
        heap[lastIndex++] = new HeapNode(ele, 1);
        map.put(ele, lastIndex - 1);
        int index = lastIndex - 1;

        while (index >= 0 && heap[getParent(index)].freq > heap[index].freq) {
            map.put(heap[getParent(index)].ele, index);
            map.put(heap[index].ele, getParent(index));
            swap(getParent(index), index);
            index = getParent(index);
        }
    }
}

class HeapNode {
    int ele;
    int freq;

    public HeapNode(int ele, int freq) {
        this.ele = ele;
        this.freq = freq;
    }
}
