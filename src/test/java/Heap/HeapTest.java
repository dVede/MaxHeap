package Heap;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class HeapTest {

    private Heap<Integer> maxHeap = new Heap<Integer>();
    private Heap<Integer> newHeap = new Heap<Integer>();

    private <T extends Comparable<T>> boolean sortCheck(@NotNull Heap<T> heap) {
        if (heap.isMax)
            return IntStream.range(0, heap.size() / 2)
                    .noneMatch(i -> 2 * i + 1 < heap.size() && heap.get(i).compareTo(heap.get(2 * i + 1)) < 0
                            || 2 * i + 2 < heap.size() && heap.get(i).compareTo((heap.get(2 * i + 2))) < 0);
        else
            return IntStream.range(0, heap.size() / 2)
                .noneMatch(i -> 2 * i + 1 < heap.size() && heap.get(i).compareTo(heap.get(2 * i + 1)) > 0
                        || 2 * i + 2 < heap.size() && heap.get(i).compareTo((heap.get(2 * i + 2))) > 0);
    }

    @Test
    public void peekTest() {
        assertNull(maxHeap.peek());
        maxHeap.addAll(Arrays.asList(12, 15, 8, 4, 23, 24, 12, 14));
        assertEquals((Integer) 24,  maxHeap.peek());
        maxHeap.remove();
        assertEquals((Integer) 23,  maxHeap.peek());
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void sizeTest() {
        assertEquals(0,  maxHeap.size());
        maxHeap.addAll(Arrays.asList(12, 15, 8, 4, 23, 24, 12, 14));
        assertEquals(7,  maxHeap.size());
        maxHeap.addAll(Arrays.asList(182, 125, 48, 94, 23, 224, 612, 147));
        assertEquals(14,  maxHeap.size());
    }

    @Test
    public void isEmptyTest() {
        maxHeap.add(15);
        maxHeap.addAll(Arrays.asList(12, 15, 8, 4, 23, 24, 12, 14));
        assertFalse(maxHeap.isEmpty());
    }

    @Test
    public void getIndexTestTest() {
        assertEquals(56, Heap.getRightChildIndex(27));
        assertEquals(53, Heap.getLeftChildIndex(26));
        assertEquals(1638, Heap.getParentIndex(3278));
    }

    @Test
    public void clearTest() {
        maxHeap.addAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996));
        maxHeap.clear();
        assertTrue(maxHeap.isEmpty());
    }

    @Test
    public void elementTest() {
        maxHeap.addAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996));
        assertEquals((Integer) 99996, maxHeap.element());
        maxHeap.add(99999);
        assertEquals((Integer) 99999, maxHeap.element());
    }

    @Test(expected = NoSuchElementException.class)
    public void elementNoSuchExceptionTest() {
        maxHeap.element();
    }

    @Test
    public void addAllTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 8, 4, 24, 19, 25));
        assertTrue(maxHeap.addAll(Arrays.asList(16, 15, 8, 4, 24, 26)));
        maxHeap.add(26);
        assertFalse(maxHeap.addAll(Arrays.asList(16, 15, 4, 8)));
        assertFalse(maxHeap.addAll(Collections.singletonList(null)));
        maxHeap.add(3);
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void addTest() {
        assertTrue(maxHeap.add(5));
        assertTrue(maxHeap.add(19));
        assertFalse(maxHeap.add(null));
        assertFalse(maxHeap.add(5));
        maxHeap.add(20);
        maxHeap.add(30);
        maxHeap.add(25);
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void containsTest() {
        maxHeap.addAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996));
        assertTrue(maxHeap.contains(14));
        assertFalse(maxHeap.contains(3));
    }

    @Test(expected = NullPointerException.class)
    public void containsNullPointerTest()  {
        maxHeap.add(15);
        maxHeap.contains(null);
    }

    @Test
    public void containsAllTest() {
        maxHeap.addAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996));
        assertTrue(maxHeap.containsAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996)));
        assertFalse(maxHeap.containsAll(Arrays.asList(5, 4)));
    }

    @Test(expected = NullPointerException.class)
    public void containsAllNullPointerTest()  {
        maxHeap.addAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, 99996));
        maxHeap.containsAll(Arrays.asList(12, 15, 8, 33, 23, 28, 12, 14, null));
    }

    @Test
    public void iteratorTest() {
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        Iterator<Integer> iter = maxHeap.iterator();
        assertTrue(iter.hasNext());
        assertEquals((Integer) 14, iter.next());
        assertEquals((Integer) 12, iter.next());
        assertEquals((Integer) 8, iter.next());
        assertEquals((Integer) 11, iter.next());
        assertEquals((Integer) 1, iter.next());
        assertFalse(iter.hasNext());

    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNoElementTest() {
        maxHeap.addAll(Collections.singletonList(12));
        Iterator<Integer> iter = maxHeap.iterator();
        iter.next();
        iter.next();
    }

    @Test
    public void toArrayTest(){
        maxHeap.addAll(Arrays.asList(12, 15, 8));
        Integer[] array = new Integer[3];
        array[0] = 15;
        array[1] = 12;
        array[2] = 8;
        assertArrayEquals(array, maxHeap.toArray());
    }

    @Test
    public void printTest(){
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        assertEquals("14 12 8 11 1 ", maxHeap.print());
    }

    @Test
    public void removeTest() {
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        assertTrue(maxHeap.remove(11));
        assertFalse(maxHeap.remove(229));
        assertFalse(maxHeap.remove(null));
        maxHeap.add(5);
        maxHeap.add(19);
        maxHeap.remove(1);
        maxHeap.add(35);
        maxHeap.add(519);
        maxHeap.add(52);
        maxHeap.add(195);
        maxHeap.remove(14);;
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void removeAllTest() {
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        assertTrue(maxHeap.removeAll(Arrays.asList(12, 1)));
        assertFalse(maxHeap.removeAll(Arrays.asList(536, 17, 29)));
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void offerTest() {
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        assertTrue(maxHeap.offer(18));
        assertFalse(maxHeap.offer(12));
        assertTrue(sortCheck(maxHeap));
    }

    @Test(expected = NullPointerException.class)
    public void offerNullPointerTest() {
        maxHeap.addAll(Arrays.asList(12, 11, 8, 14, 1));
        assertFalse(maxHeap.offer(null));
    }

    @Test
    public void pollTest(){
        assertNull(maxHeap.poll());
        IntStream.range(0, 49).forEach(i -> newHeap.add(2 * i * (int) Math.pow(-1, i)));
        IntStream.range(0, 50).forEach(i -> maxHeap.add(2 * i * (int) Math.pow(-1, i)));
        assertEquals(maxHeap.peek(),  maxHeap.poll());
        newHeap.poll();
        newHeap.poll();
        assertTrue(sortCheck(newHeap));
    }

    @Test
    public void remove2Test(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14));
        maxHeap.remove();
        assertEquals((Integer) 49, maxHeap.remove());
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void sortedArrayTest(){
        ArrayList<Integer> array = new ArrayList<>();
        ArrayList<Integer> check = new ArrayList<>(Arrays.asList(38, 19, 18, 16, 15, 11, 10, 5, 4));
        array.add(5);
        array.add(16);
        array.add(4);
        array.add(15);
        array.add(19);
        array.add(38);
        array.add(11);
        array.add(10);
        array.add(18);
        maxHeap.sortedArray(array);
        assertEquals(check, array);
    }

    @Test(expected = NoSuchElementException.class)
    public void sortedArrayNoSuchTest(){
        ArrayList<Integer> array = new ArrayList<>();
        maxHeap.sortedArray(array);
    }

    @Test
    public void heapCreateTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14));
        assertEquals(maxHeap.peek(),  maxHeap.poll());
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void getTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        assertEquals((Integer) 83, maxHeap.get(0));
        assertEquals((Integer) 18, maxHeap.get(9));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getIndexOutTest(){
        maxHeap.get(-1);
    }

    @Test
    public void setTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        maxHeap.set(9, 125);
        assertTrue(sortCheck(maxHeap));
        maxHeap.set(0, 1);
        assertTrue(sortCheck(maxHeap));
        assertEquals((Integer) 83, maxHeap.set(0, 84));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setIndexOutTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        maxHeap.set(40, 33);
    }

    @Test
    public void heapHeightTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        assertEquals(3 , maxHeap.getHeight());
        maxHeap.addAll(Arrays.asList(99, 195, 843, 429, 233, 254, 124, 164, 177, 181));
        assertEquals(4 , maxHeap.getHeight());
    }

    @Test
    public void mergeTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        ArrayList<Integer> check = new ArrayList<>(Arrays.asList(38, 19, 18, 16, 15, 11, 10, 5, 4));
        assertTrue(sortCheck(maxHeap.merge(check)));
        assertEquals(16 ,maxHeap.merge(check).size());
    }

    @Test(expected = NoSuchElementException.class)
    public void mergeNoSuchTest(){
        maxHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));
        ArrayList<Integer> check = new ArrayList<>();
        maxHeap.merge(check);
    }

    @Test
    public void heapMinTest(){
        Heap<Integer> minHeap = new Heap<Integer>();
        minHeap.changeHeapDirection();
        minHeap.addAll(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14, 17, 18));

        minHeap.add(1);
        assertTrue(sortCheck(minHeap));
        assertEquals((Integer) 1, minHeap.poll());
    }

    @Test
    public void heapCreateTest2(){
        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(16, 15, 83, 49, 23, 24, 12, 14));
        maxHeap = new Heap<>(array);
        assertTrue(sortCheck(maxHeap));
    }

    @Test
    public void lineModel() {
        Line line = new Line(15, 36 , 17 , 48);
        assertEquals(15, line.getX1());
        assertEquals(36, line.getY1());
        assertEquals(17, line.getX2());
        assertEquals(48, line.getY2());
        line.setX1(1);
        line.setY1(2);
        line.setX2(3);
        line.setY2(4);
        assertEquals(1, line.getX1());
        assertEquals(2, line.getY1());
        assertEquals(3, line.getX2());
        assertEquals(4, line.getY2());
    }

    @Test
    public void GuiTest() {
        Gui DisplayBST = new Gui();
    }
}
