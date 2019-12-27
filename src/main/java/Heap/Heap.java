package Heap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Heap<T extends Comparable<T>> implements Queue<T> {

    private ArrayList<T> heap;
    public boolean isMax;

    public Heap() {
        this.isMax = true;
        this.heap = new ArrayList<>();
    }

    public Heap(ArrayList<T> elem) {
        this.isMax = true;
        this.heap = new ArrayList<>(elem);
        for (int i = heap.size() / 2; i >= 0; i--)
            sortDown(i);
    }

    public void changeHeapDirection(){
        isMax = !isMax;
        for (int i = heap.size() / 2; i >= 0; i--)
            sortDown(i);
    }

    public static int getHeightOfRoot(int x) {
        return (int) Math.floor(log2(x + 1));
    }

    public int getHeight() {
        return (int) Math.floor(log2(heap.size() + 1));
    }

    private static int log2(int x){
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
        if (index < 0 || index >= this.size())
            throw new IndexOutOfBoundsException();
        return heap.get(index);
    }

    public T set(int index, T value){
        if (index < 0 || index >= this.size())
            throw new IndexOutOfBoundsException();
        T oldValue = this.get(index);
        heap.set(index, value);
        sort(index);
        return oldValue;
    }

    @Override
    public Iterator<T> iterator() {
        return new MaxHeapIterator(heap);
    }

    private class MaxHeapIterator implements java.util.Iterator<T> {
        private ArrayList<T> heap;
        private int last = -1;
        private int index = 0;

        private MaxHeapIterator(ArrayList<T> heap) {
            this.heap = heap;
        }

        @Override
        public boolean hasNext() {
            return index < heap.size();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            last = index++;
            return heap.get(last);
        }

        @Override
        public void remove() {
            heap.remove(index = last);
            last = -1;
        }

        @Override
        public String toString() {
            return "MaxHeapIterator{" +
                    "heap=" + heap +
                    ", last=" + last +
                    ", index=" + index +
                    '}';
        }
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
        if (this.isMax) {
            while (currentIndex > 0 && heap.get(parentIndex).compareTo(heap.get(currentIndex)) < 0) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = getParentIndex(currentIndex);
            }
        return true;
        } else {
            while (currentIndex > 0 && heap.get(parentIndex).compareTo(heap.get(currentIndex)) > 0) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
                parentIndex = getParentIndex(currentIndex);
            }
            return true;
        }
    }


    @Override
    public boolean remove(Object o) {
        if (heap.contains(o)) {
            int index = heap.indexOf(o);
            swap(index, heap.size() - 1);
            heap.remove(heap.size() - 1);
            sort(index);
            return true;
        }
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
        if (heap.retainAll(c)) {
            for (int i = heap.size() / 2; i >= 0; i--)
                sortDown(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean offer(T t) {
        if (t != null)
            return add(t);
        throw new NullPointerException();
    }

    @Override
    public T remove() {
        T result = poll();
        if (result != null)
            return result;
        throw new NoSuchElementException();
    }

    @Override
    public T poll() {
        if (!heap.isEmpty()) {
            T result = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            sortDown(0);
            return result;
        }
        return null;
    }

    @Override
    public T element() {
        T result = peek();
        if (result != null)
            return heap.get(0);
        throw new NoSuchElementException();
    }

    @Override
    public T peek() {
        return !heap.isEmpty() ? heap.get(0) : null;
    }

    private void swap(int currentIndex, int parentIndex) {
        T temp = heap.get(currentIndex);
        heap.set(currentIndex, heap.get(parentIndex));
        heap.set(parentIndex, temp);
    }

    private void sort(int currentIndex) {
        if (currentIndex != 0) {
            if (heap.get(currentIndex).compareTo(heap.get(getParentIndex(currentIndex))) > 0)
                sortUp(currentIndex);
        }
        else sortDown(currentIndex);
    }

    private void sortUp(int currentIndex) {
        int parentIndex;
        int maxIndex = currentIndex;
        while (currentIndex > 0) {
            parentIndex = getParentIndex(maxIndex);
            if (this.isMax) {
                if (heap.get(parentIndex).compareTo(heap.get(maxIndex)) < 0)
                    maxIndex = parentIndex;
            } else if (heap.get(parentIndex).compareTo(heap.get(maxIndex)) > 0)
                    maxIndex = parentIndex;
            if (maxIndex == currentIndex) break;
            swap(maxIndex, currentIndex);
            currentIndex = maxIndex;
        }
    }

    private void sortDown(int currentIndex) {
        int maxIndex = currentIndex;
        int rightIndex, leftIndex;
        while (currentIndex < heap.size()) {
            leftIndex = getLeftChildIndex(currentIndex);
            rightIndex = getRightChildIndex(currentIndex);
            if (this.isMax) {
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
            if (maxIndex == currentIndex) break;
            swap(currentIndex, maxIndex);
            currentIndex = maxIndex;
        }
    }

    public static <T extends Comparable<T>> void sortedArray(ArrayList<T> array) {
        if (array.isEmpty())
            throw new NoSuchElementException();
        Heap<T> heap = new Heap<>(array);
        IntStream.range(0, array.size()).forEach(i -> array.set(i, heap.poll()));
    }

    public Heap<T> merge(ArrayList<T> array) {
        if (array.isEmpty())
            throw new NoSuchElementException();
        IntStream.range(0, this.size()).filter(i -> !array.contains(this.get(i)))
                .forEach(i -> array.add(this.get(i)));
        return new Heap<>(array);
    }

    public static int getLeftChildIndex(int currentIndex) {
        return 2 * currentIndex + 1;
    }

    public static int getRightChildIndex(int currentIndex) {
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
