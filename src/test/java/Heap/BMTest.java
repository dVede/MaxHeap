package Heap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class BMTest {

    @State(Scope.Benchmark)
    public static class BenchMarkState {

        private Heap<Integer> big1 = new Heap<Integer>(new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
                54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
                8, 130, 72, 46, 19, 139, 140, 32, 106)), MinMax.MAX);

        private Heap<Integer> big2 = new Heap<Integer>(new ArrayList<>(Arrays.asList(122, 196, 192, 136, 90, 33, 17,
                57, 65, 41, 126, 107, 129, 145, 199, 15, 15, 90, 130, 58, 149, 116, 115, 17, 60, 130, 118, 41, 26,
                36, 158, 147, 31, 150, 82, 120, 182, 99, 177, 47, 140, 102, 153, 69, 47, 152, 83, 61, 42, 13)), MinMax.MAX);

        private Heap<Integer> small1 = new Heap<Integer>(new ArrayList<>(Arrays.asList(60, 15, 69)), MinMax.MAX);

        private Heap<Integer> small2 = new Heap<Integer>(new ArrayList<>(Arrays.asList(36, 45, 49)), MinMax.MAX);

        private ArrayList<Integer> array = new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
                54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
                8, 130, 72, 46, 19, 139, 140, 32, 106));

        private ArrayList<Integer> array2 = new ArrayList<>(Arrays.asList(60, 15, 69));
    }

    @Benchmark
    public void testGetMaxHeap3(BenchMarkState state) {
        state.small1.getMax();
    }

    @Benchmark
    public void testGetMaxHeap50(BenchMarkState state) {
        state.big1.getMax();
    }

    @Benchmark
    public void testAddHeap3(BenchMarkState state) {
        state.small1.add(38);
    }

    @Benchmark
    public void testAddHeap50(BenchMarkState state) {
        state.big1.add(38);
    }

    @Benchmark
    public void peekHeap3(BenchMarkState state) {
        state.small1.peek();
    }

    @Benchmark
    public void peekHeap50(BenchMarkState state) {
        state.big1.peek();
    }

    @Benchmark
    public void createNewHeap(BenchMarkState state) {
        Heap<Integer> heap = new Heap<Integer>(new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
            54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
            8, 130, 72, 46, 19, 139, 140, 32, 106)), MinMax.MAX);
    }

    @Benchmark
    public void setHeap50(BenchMarkState state) {
        state.big1.set(27, 60);
    }

    @Benchmark
    public void setHeap3(BenchMarkState state) {
        state.big1.set(0, 1);
    }

    @Benchmark
    public void arraySort50(BenchMarkState state) {
        state.big1.sortedArray(state.array);
    }

    @Benchmark
    public void arraySort3(BenchMarkState state) {
        state.small1.sortedArray(state.array2);
    }

    @Benchmark
    public void merge3(BenchMarkState state) {
        state.small1.merge(state.array2);
    }

    @Benchmark
    public void merge50(BenchMarkState state) {
        state.big1.merge(state.array);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BMTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
