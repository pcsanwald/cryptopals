package cryptopals;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.TreeMap;

public class Challenge4 {


    /*
    This implementation was pretty easy because it mostly involved re-factoring the
    score function in challenge 3 to work better.
     */
    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","4.txt"));
        TreeMap<Double, List<String>> scores = new TreeMap<>();
        for (String encryptedText : strings) {
            scores = Challenge3.guessString(Challenge3.decodeString(encryptedText), scores);
        }
        System.out.println(scores.lastEntry().getValue().get(0));
    }
}
