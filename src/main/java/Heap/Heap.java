package Heap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Heap<T extends Comparable<T>> implements Queue<T> {

    private ArrayList<T> heap;
    MinMax minMax;

    public Heap(MinMax minMax) {
        this.minMax = minMax;
        this.heap = new ArrayList<>();
    }

    public Heap(ArrayList<T> elem, MinMax minMax) {
        this.minMax = minMax;
        this.heap = new ArrayList<>(elem);
        for (int i = heap.size() / 2; i >= 0; i--)
            sort(i);
    }

    public int getHeight() {
        return (int) Math.floor(log2(heap.size() + 1));
    }

    public static int log2(int x){
        return (int) (Math.log(x) / Math.log(2));
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void clear(){
        heap.clear();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) throw new NullPointerException();
        return heap.contains(o);
    }

    public T get(int index){
        return heap.get(index);
    }

    public void set(int index, T value){
        heap.set(index, value);
        for (int i = heap.size() / 2; i >= 0; i--)
            sort(i);
    }

    @Override
    public Iterator<T> iterator() {
        return new MaxHeapIterator(heap);
    }

    private class MaxHeapIterator implements java.util.Iterator<T> {
        private ArrayList<T> heap;
        private int index = -1;

        private MaxHeapIterator(ArrayList<T> heap) {
            this.heap = heap;
        }

        @Override
        public boolean hasNext() {
            return index <= heap.size() - 2;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            index++;
            return heap.get(index);
        }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }

    @Override
    public Object[] toArray() {
        return heap.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a == null)
            throw new NullPointerException();
        return heap.toArray(a);
    }

    @Override
    public boolean add(T item) {
        if (heap.contains(item) || item == null)
            return false;
        heap.add(item);
        int currentIndex = heap.size() - 1;
        int parentIndex = getParentIndex(currentIndex);
        if (this.minMax == MinMax.MAX) {
        while (currentIndex > 0 && heap.get(parentIndex).compareTo(heap.get(currentIndex)) < 0) {
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
        return true;
        }
        while (currentIndex > 0 && heap.get(parentIndex).compareTo(heap.get(currentIndex)) > 0) {
        swap(currentIndex, parentIndex);
        currentIndex = parentIndex;
        parentIndex = getParentIndex(currentIndex);
    }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (heap.contains(o) && heap.remove(heap.indexOf(o)) != null) {
            sort(0);
            return true;}
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (o == null) throw new NullPointerException();
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T ac : c) {
            if (this.add(ac))
                modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object ac : c)
            if (this.remove(ac))
                modified = true;
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(T t) {
        if (t == null) throw new NullPointerException();
        return add(t);
    }

    @Override
    public T remove() {
        T result = heap.get(0);
        if (result == null)
            throw new NoSuchElementException();
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        sort(0);
        return result;
    }

    @Override
    public T poll() {
        if (!heap.isEmpty()) {
            T result = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            sort(0);
            return result;
        }
        return null;
    }

    @Override
    public T element() {
        if (!heap.isEmpty())
            return heap.get(0);
        throw new NoSuchElementException();
    }

    @Override
    public T peek() {
        return heap.size() > 0 ? heap.get(0) : null;
    }

    private void swap(int currentIndex, int parentIndex) {
        T temp = heap.get(currentIndex);
        heap.set(currentIndex, heap.get(parentIndex));
        heap.set(parentIndex, temp);
    }

    public T getMax() {
        if (!(heap.size() > 0))
            return null;
        T result = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        sort(0);
        return result;
    }

    private void sort(int currentIndex) {
        int maxIndex = currentIndex;
        int rightIndex, leftIndex;
        while (currentIndex < heap.size()) {
            leftIndex = getLeftChildIndex(currentIndex);
            rightIndex = getRightChildIndex(currentIndex);
            if (this.minMax == MinMax.MAX) {
                if (leftIndex < heap.size() && heap.get(leftIndex).compareTo(heap.get(maxIndex)) > 0)
                    maxIndex = leftIndex;
                if (rightIndex < heap.size() && heap.get(rightIndex).compareTo(heap.get(maxIndex)) > 0)
                    maxIndex = rightIndex;
            } else {
                if (leftIndex < heap.size() && heap.get(leftIndex).compareTo(heap.get(maxIndex)) < 0)
                    maxIndex = leftIndex;
                if (rightIndex < heap.size() && heap.get(rightIndex).compareTo(heap.get(maxIndex)) < 0)
                    maxIndex = rightIndex;
            }
            if (maxIndex == currentIndex) {
                break;
            }
            swap(currentIndex, maxIndex);
            currentIndex = maxIndex;
        }
    }

    public void sortedArray(ArrayList<T> array) {
        if (array.isEmpty())
            throw new NoSuchElementException();
        Heap<T> heap = new Heap<T>(array, this.minMax);
        IntStream.range(0, array.size()).forEach(i -> array.set(i, heap.getMax()));
    }

    public Heap<T> merge(ArrayList<T> array){
        if (array.isEmpty())
            throw new NoSuchElementException();
        IntStream.range(0, this.size()).filter(i -> !array.contains(this.get(i)))
                .forEach(i -> array.add(this.get(i)));
        return new Heap<T>(array, this.minMax);
    }

    public static int getLeftChildIndex(int currentIndex){
        return 2 * currentIndex + 1;
    }

    public static int getRightChildIndex(int currentIndex){
        return 2 * currentIndex + 2;
    }

    public static int getParentIndex(int currentIndex) {
        return (currentIndex - 1) / 2;
    }

    public String print() {
        return IntStream.rangeClosed(0, heap.size() - 1)
                .mapToObj(k -> heap.get(k) + " ").collect(Collectors.joining());
    }
}
