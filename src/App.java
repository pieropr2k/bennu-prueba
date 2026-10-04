import repository.NumberFileRepository;
import search.BinarySearch;
import search.LinearSearch;
import sort.SortBenchmark;
import sort.SortBenchmark.Result;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class App {

    private static final int MAX_VALUE = 100000;
    private final Scanner in;
    private final NumberFileRepository generated = new NumberFileRepository("numeros.txt");
    private final NumberFileRepository sorted = new NumberFileRepository("numeros_ordenados.txt");
    private final SortBenchmark benchmark = SortBenchmark.withDefaults();

    // Usa System.in
    public App() {
        this(System.in);
    }

    // Permite inyectar otra entrada (tests, otro programa, etc.)
    public App(InputStream input) {
        this.in = new Scanner(input);
    }

    // Ejecuta el menú interactivo hasta que el usuario elige salir.
    public void run() {
        printMenu();
        while (true) {
            int option = readInt("Seleccione una opción : ");
            try {
                switch (option) {
                    case 0 -> printMenu();
                    case 1 -> generateFile();
                    case 2 -> showFile(generated, "archivo generado");
                    case 3 -> sortFile();
                    case 4 -> showFile(sorted, "archivo ordenado");
                    case 5 -> searchNumber();
                    case 6 -> {
                        deleteFiles();
                        System.out.println("¡Hasta luego!");
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (IOException e) {
                System.out.println("Error de archivo: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("Opciones");
        System.out.println("-------------------------");
        System.out.println("0 - Menu");
        System.out.println("1 - Genera nuevo archivo");
        System.out.println("2 - Lee archivo generado");
        System.out.println("3 - Ordena archivo");
        System.out.println("4 - Lee archivo ordenado");
        System.out.println("5 - Buscar numero en archivo");
        System.out.println("6 - Salir");
    }

    private void generateFile() throws IOException {
        int count;
        do {
            count = readInt("¿Cuántos números generar? elige uno del 1 al " + MAX_VALUE + ": ");
            if (count <= 0 || count > MAX_VALUE) {
                System.out.println("ERROR: La cantidad debe estar entre 1 y " + MAX_VALUE + ".");
            }
        } while (count <= 0 || count > MAX_VALUE);
        // generamos array de randoms
        Random random = new Random();
        int[] random_list = random.ints(count, 0, MAX_VALUE).toArray();
        generated.save(random_list);
        sorted.delete(); // borramos siempre el ordenado ya que generamos un archivo nuevo
        System.out.printf("%d números guardados en %s%n", count, generated.fileName());
    }

    private void showFile(NumberFileRepository repo, String label) throws IOException {
        if (!repo.exists()) {
            System.out.println("Aún no existe el " + label + ".");
            return;
        }
        int[] numbers = repo.load();
        System.out.println(Arrays.toString(numbers));
        System.out.println("Total: " + numbers.length);
    }

    private void sortFile() throws IOException {
        if (!generated.exists()) {
            System.out.println("Primero genera un archivo (opción 1).");
            return;
        }
        int[] data = generated.load();
        List<Result> results = benchmark.run(data);

        System.out.println("Comparación de algoritmos (" + data.length + " números):");
        List<Result> ordered = new ArrayList<>(results);
        // ordenamos por tiempo
        ordered.sort(Comparator.comparingLong(Result::nanos));
        for (Result r : ordered) {
            System.out.printf("  %-15s %10.3f ms%n", r.algorithm(), r.nanos() / 1_000_000.0);
        }
        sorted.save(results.get(0).sorted());
        System.out.println("Archivo ordenado guardado en " + sorted.fileName());
    }

    private void searchNumber() throws IOException {
        boolean useSorted = sorted.exists();
        if (!useSorted && !generated.exists()) {
            System.out.println("Primero tienes que generar un archivo (opcion 1)");
            return;
        }
        int target = readInt("Número a buscar : ");

        if (useSorted) {
            int idx = BinarySearch.indexOf(sorted.load(), target);
            System.out.println(idx >= 0
                    ? "Encontrado en la posición " + idx + " (búsqueda binaria, archivo ordenado)."
                    : "El número " + target + " no está en el archivo.");
        } else {
            int[] data = generated.load();
            int idx = LinearSearch.indexOf(data, target);
            System.out.println(idx >= 0
                    ? "Encontrado en la posición " + idx + " (búsqueda lineal)."
                    : "El número " + target + " no está en el archivo.");
        }
    }
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.hasNextLine() ? in.nextLine().trim() : "6";
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }
    private void deleteFiles() {
        for (NumberFileRepository repo : List.of(generated, sorted)) {
            try {
                if (repo.delete()) {
                    System.out.println("Archivo eliminado: " + repo.fileName());
                }
            } catch (IOException e) {
                System.out.println("No se pudo eliminar " + repo.fileName() + ": " + e.getMessage());
            }
        }
    }
}