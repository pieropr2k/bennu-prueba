package sort;
import java.util.Arrays;

// O(n log n) promedio, O(n^2) en el peor caso. Pivote = elemento central.
public class QuickSort implements SortStrategy {

    @Override
    public String name() {
        return "Quick Sort";
    }

    @Override
    public int[] sort(int[] data) {
        int[] a = Arrays.copyOf(data, data.length);
        quickSort(a, 0, a.length - 1);
        return a;
    }

    private void quickSort(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = partition(a, lo, hi);
            // Recursion sobre la mitad mas chica para acotar la pila
            if (p - lo < hi - p) {
                quickSort(a, lo, p - 1);
                lo = p + 1;
            } else {
                quickSort(a, p + 1, hi);
                hi = p - 1;
            }
        }
    }

    private int partition(int[] a, int lo, int hi) {
        swap(a, (lo + hi) >>> 1, hi);
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) swap(a, i++, j);
        }
        swap(a, i, hi);
        return i;
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
