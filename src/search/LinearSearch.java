package search;

public class LinearSearch {
    private LinearSearch() { }

    // return indice de la primera coincidencia, o -1 si no existe.
    public static int indexOf(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) return i;
        }
        return -1;
    }
}
