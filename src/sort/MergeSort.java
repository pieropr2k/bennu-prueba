package sort;
import java.util.Arrays;

// O(n log n) siempre, usa memoria auxiliar O(n).
public class MergeSort implements SortStrategy {

    @Override
    public String name() {
        return "Merge Sort";
    }

    @Override
    public int[] sort(int[] data) {
        int[] a = Arrays.copyOf(data, data.length);
        mergeSort(a, new int[a.length], 0, a.length - 1);
        return a;
    }

    private void mergeSort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) >>> 1;
        mergeSort(a, tmp, lo, mid);
        mergeSort(a, tmp, mid + 1, hi);
        merge(a, tmp, lo, mid, hi);
    }

    private void merge(int[] a, int[] tmp, int lo, int mid, int hi) {
        System.arraycopy(a, lo, tmp, lo, hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            a[k++] = tmp[i] <= tmp[j] ? tmp[i++] : tmp[j++];
        }
        while (i <= mid) a[k++] = tmp[i++];
        while (j <= hi) a[k++] = tmp[j++];
    }
}
