package sort;
import java.util.Arrays;

// O(n^2) en promedio, O(n) si ya esta casi ordenado.
public class InsertionSort implements SortStrategy {

    @Override
    public String name() {
        return "Insertion Sort";
    }

    @Override
    public int[] sort(int[] data) {
        int[] a = Arrays.copyOf(data, data.length);
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
        return a;
    }
}
