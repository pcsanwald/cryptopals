package cryptopals;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

public class Challenge8 {

    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","8.txt"));
        ArrayList<Tuple<String, Long>> scores = new ArrayList<>();
        for (String s : strings) {
            long count = repeatedBlocks(16, s);
            scores.add(new Tuple<>(s, count));
        }
        Collections.sort(scores, (a, b) -> b.getRight().compareTo(a.getRight()));
        int index = strings.indexOf(scores.get(0).getLeft());
        System.out.println(strings.get(index));
    }

    public static long repeatedBlocks(int blocksize, String cipherText) {
        List<String> blocks = splitCypherText(cipherText, blocksize);
        long score = 0;
        for (String s : blocks) {
            int frequency = Collections.frequency(blocks,s);
            if (frequency > 1) {
                score += frequency;
            }
        }
        return score;
    }

    public static List<String> splitCypherText(String text, int splitBy) {
        List<String> results = new ArrayList<>();
        int buckets = (int)Math.ceil((double)text.length() / splitBy);
        int index = 0;
        for (int i = 0; i < buckets; i++) {
            if (index+splitBy >= text.length()) {
                String result = text.substring(index,text.length());
                results.add(result);
            } else {
                String result = text.substring(index,index+splitBy);
                results.add(result);
            }
            index += splitBy;
        }
        return results;
    }
}
