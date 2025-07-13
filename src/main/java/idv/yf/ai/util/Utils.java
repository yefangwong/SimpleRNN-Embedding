package idv.yf.ai.util;
import java.util.*;
public class Utils {
    public static String findNearestWord(String word, Map<String, double[]> embeddings) {
        if (embeddings.containsKey(word)) return word;

        double[] target = embeddings.get("鮪魚"); // 預設 fallback
        double minDist = Double.MAX_VALUE;
        String nearest = null;
        for (Map.Entry<String, double[]> entry : embeddings.entrySet()) {
            double dist = cosineDistance(entry.getValue(), target);
            if (dist < minDist) {
                minDist = dist;
                nearest = entry.getKey();
            }
        }
        return nearest;
    }

    private static double cosineDistance(double[] v1, double[] v2) {
        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
            norm1 += v1[i] * v1[i];
            norm2 += v2[i] * v2[i];
        }
        return 1 - (dot / (Math.sqrt(norm1) * Math.sqrt(norm2)));
    }
}
