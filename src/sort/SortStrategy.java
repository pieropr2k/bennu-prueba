package sort;

// Strategy: cada algoritmo de ordenamiento implementa esta interfaz.
public interface SortStrategy {
    String name();
    // Devuelve un arreglo nuevo ordenado; no modifica el original.
    int[] sort(int[] data);
}