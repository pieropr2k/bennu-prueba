package sort;

import java.util.Arrays;

// O(n^2). Simple, pero lento con muchos datos.
public class BubbleSort implements SortStrategy {

    @Override
    public String name() {
        return "Bubble Sort";
    }

    @Override
    public int[] sort(int[] data) {
        int[] a = Arrays.copyOf(data, data.length);
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return a;
    }
}
