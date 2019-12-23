package maxHeap;

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

        private MaxHeap<Integer> heap = new MaxHeap<>(new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
                54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
                8, 130, 72, 46, 19, 139, 140, 32, 106)));

        private MaxHeap<Integer> heap2 = new MaxHeap<>(new ArrayList<>(Arrays.asList(60, 15, 69)));

        private ArrayList<Integer> array = new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
                54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
                8, 130, 72, 46, 19, 139, 140, 32, 106));

        private ArrayList<Integer> array2 = new ArrayList<>(Arrays.asList(60, 15, 69));
    }

    @Benchmark
    public void testGetMaxHeap3(BenchMarkState state) {
        state.heap2.getMax();
    }

    @Benchmark
    public void testGetMaxHeap50(BenchMarkState state) {
        state.heap.getMax();
    }

    @Benchmark
    public void testAddHeap3(BenchMarkState state) {
        state.heap2.add(38);
    }

    @Benchmark
    public void testAddHeap50(BenchMarkState state) {
        state.heap.add(38);
    }

    @Benchmark
    public void peekHeap3(BenchMarkState state) {
        state.heap2.peek();
    }

    @Benchmark
    public void peekHeap50(BenchMarkState state) {
        state.heap.peek();
    }

    @Benchmark
    public void createNewHeap(BenchMarkState state) {
        MaxHeap<Integer> heap = new MaxHeap<>(new ArrayList<>(Arrays.asList(60, 15, 69, 127, 73, 141, 96, 70,
            54, 36, 113, 75, 94, 57, 146, 3, 20, 143, 80, 21, 55, 51, 28, 76, 62, 48, 67, 81, 52, 65, 122, 114,
            8, 130, 72, 46, 19, 139, 140, 32, 106)));
    }

    @Benchmark
    public void setHeap50(BenchMarkState state) {
        state.heap.set(27, 60);
    }

    @Benchmark
    public void setHeap3(BenchMarkState state) {
        state.heap.set(0, 1);
    }

    @Benchmark
    public void arraySort50(BenchMarkState state) {
        state.heap.sortedArray(state.array);
    }

    @Benchmark
    public void arraySort3(BenchMarkState state) {
        state.heap2.sortedArray(state.array2);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BMTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
