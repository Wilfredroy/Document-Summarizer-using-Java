import java.util.*;
import java.util.stream.Collectors;

public class Summarizer {
    public static List<String> improvedSummarize(String text, int numLines) {
        String[] lines = text.split("\\n+");
        if (lines.length == 0) return List.of("⚠️ No lines found. Try another file.");

        Map<String, Integer> wordFreq = new HashMap<>();

        for (String line : lines) {
            for (String word : line.toLowerCase().split("\\W+")) {
                if (word.length() > 3 && !List.of("this", "that", "with", "have", "from", "your", "you").contains(word)) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }
        }

        Map<String, Double> lineScores = new LinkedHashMap<>();
        for (String line : lines) {
            double score = 0;
            int count = 0;
            for (String word : line.toLowerCase().split("\\W+")) {
                if (wordFreq.containsKey(word)) {
                    score += wordFreq.get(word);
                    count++;
                }
            }
            if (!line.trim().isEmpty()) {
                lineScores.put(line.trim(), count > 0 ? score / count : 0);
            }
        }

        return lineScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(numLines)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
