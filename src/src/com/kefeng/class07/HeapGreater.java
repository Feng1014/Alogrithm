package com.kefeng.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 能够替代系统PriorityQueue不具有的API
 */
public class HeapGreater<T> {

    private ArrayList<T> heap;
    private HashMap<T, Integer> hashMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        hashMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return hashMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    /**
     * 向堆添加元素后调整
     *
     * @param obj
     */
    public void push(T obj) {
        heap.add(obj);
        hashMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    /**
     * 弹出堆中元素,只能是堆顶元素
     *
     * @return
     */
    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        hashMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    /**
     * 删除堆中任意元素，不仅仅是堆顶元素；PriorityQueue不具备的API
     *
     * @param obj
     */
    public void remove(T obj) {
        /**使用最后一个元素替换删除元素*/
        T replacce = heap.get(heapSize - 1);
        /**获取要删除元素的位置*/
        int index = hashMap.get(obj);
        hashMap.remove(obj);
        /**删除最后一个元素的位置，将最后一个元素替换到obj的位置上*/
        heap.remove(--heapSize);
        if (obj != replacce) {
            heap.set(index, replacce);
            hashMap.put(replacce, index);
            resign(replacce);
        }
    }

    /**
     * 向堆中
     *
     * @param obj
     */
    public void resign(T obj) {
        heapInsert(hashMap.get(obj));
        heapify(hashMap.get(obj));
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int largest = (left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0) ? left + 1 : left;
            int maxIndex = comp.compare(heap.get(index), heap.get(largest)) < 0 ? index : largest;
            if (maxIndex == index) {
                break;
            }
            swap(index, maxIndex);
            index = maxIndex;
            left = 2 * index + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        hashMap.put(o1, j);
        hashMap.put(o2, i);
    }

}
