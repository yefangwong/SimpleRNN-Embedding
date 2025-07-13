package idv.yf.ai.embedding;
import java.io.*;
import java.util.*;
public class EmbeddingLoader {
    public static Map<String, double[]> load(String filePath) throws IOException {
        Map<String, double[]> embeddings = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            String word = parts[0];
            double[] vector = new double[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                vector[i - 1] = Double.parseDouble(parts[i]);
            }
            embeddings.put(word, vector);
        }
        reader.close();
        return embeddings;
    }
}
