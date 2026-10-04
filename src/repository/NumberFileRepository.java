package repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Lee y escribe numeros en un archivo de texto
public class NumberFileRepository {
    private final Path path;

    public NumberFileRepository(String fileName) {
        this.path = Path.of(fileName);
    }
    public boolean exists() {
        return Files.exists(path);
    }
    public void save(int[] numbers) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(numbers[i]);
        }
        Files.writeString(path, sb.toString());
    }

    public int[] load() throws IOException {
        List<String> lines = Files.readAllLines(path);
        int[] result = new int[lines.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(lines.get(i));
        }
        return result;
    }

    public String fileName() {
        return path.getFileName().toString();
    }

    public boolean delete() throws IOException {
        return Files.deleteIfExists(path);
    }
}