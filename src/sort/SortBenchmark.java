package sort;
import java.util.ArrayList;
import java.util.List;

// Ejecuta cada estrategia sobre los mismos datos y mide el tiempo.
public class SortBenchmark {
    // nombre_algoritmo, tiempo, array ordenado
    public record Result(String algorithm, long nanos, int[] sorted) { }

    private final List<SortStrategy> strategies;

    public SortBenchmark(List<SortStrategy> strategies) {
        this.strategies = strategies;
    }

    public static SortBenchmark withDefaults() {
        return new SortBenchmark(List.of(
                new BubbleSort(), new InsertionSort(), new MergeSort(), new QuickSort()));
    }

    public List<Result> run(int[] data) {
        List<Result> results = new ArrayList<>();
        for (SortStrategy s : strategies) {
            long start = System.nanoTime();
            int[] sorted = s.sort(data);
            long elapsed = System.nanoTime() - start;
            results.add(new Result(s.name(), elapsed, sorted));
        }
        return results;
    }
}
