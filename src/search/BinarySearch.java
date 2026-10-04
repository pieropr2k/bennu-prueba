package search;

// Busqueda binaria O(log n); requiere el arreglo ordenado.
public final class BinarySearch {

    private BinarySearch() { }

    // return indice del valor, o -1 si no existe.
    public static int indexOf(int[] sorted, int target) {
        int lo = 0, hi = sorted.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (sorted[mid] == target) return mid;
            if (sorted[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }
}