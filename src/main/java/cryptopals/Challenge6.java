package cryptopals;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge6 {

    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","6.txt"));
        String base64EncodedText = strings.stream().collect(Collectors.joining());
        String encryptedText = new String(Base64.getDecoder().decode(base64EncodedText));

        List<String> results = crackReapeatingKeyXOR(encryptedText, 2, 40);
        System.out.println(results.toString());
    }

    public static List<String> crackReapeatingKeyXOR(String encryptedText, int minKeySize, int maxKeySize) {
        List<Tuple<Double, Integer>> guessedKeySize = guessKeySize(encryptedText, minKeySize, maxKeySize);
        Integer keySize = guessedKeySize.get(0).getRight();
        List<String> splitText = splitCypherText(encryptedText, keySize);
        List<String> transposedBlocks = transposeBlocks(splitText);

        TreeMap<Double, List<String>> scores = new TreeMap<>();
        for (String block : transposedBlocks) {
            scores = Challenge3.guessString(block, scores);
        }
        List<String> results = scores.lastEntry().getValue();
        return results;
    }

    public static List<String> transposeBlocks(List<String> blocks) {
        Map<Integer, StringBuilder> transposedBlocks = new HashMap<>();
        for (String block : blocks) {
            for (int index = 0; index < block.length(); index++) {
                if (!transposedBlocks.containsKey(index)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(block.charAt(index));
                    transposedBlocks.put(index, sb);
                } else {
                    transposedBlocks.get(index).append(block.charAt(index));
                }
            }
        }
        List<String> toReturn = transposedBlocks.values().stream().map(
                stringBuilder -> stringBuilder.toString()
        ).collect(Collectors.toList());
        return toReturn;
    }

    public static List<String> splitCypherText(String text, int splitBy) {
        List<String> results = new ArrayList<>();
        int buckets = (int)Math.ceil((double)text.length() / splitBy);
        int index = 0;
        for (int i = 0; i < buckets; i++) {
            if (index+splitBy >= text.length()) {
                results.add(text.substring(index, text.length()));
            } else {
                results.add(text.substring(index, index+splitBy));
            }
            index += splitBy;
        }
        return results;
    }

    private static String stringToBinary(String s) {
        StringBuilder result = new StringBuilder();
        for (char c: s.toCharArray()) {
            String paddedBinaryRepresentation = String.format("%16s", Integer.toBinaryString(c)).replace(' ', '0');
            result.append(paddedBinaryRepresentation);
        }
        return result.toString();
    }
    public static int hammingDistance(String s1, String s2) {
        String b1 = stringToBinary(s1);
        String b2 = stringToBinary(s2);

        int hammingDistance = 0;
        for (int i = 0; i < b1.length(); i++) {
            if (b1.charAt(i) != b2.charAt(i)) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    public static double tryKeysize(int keysize, String ciphertext) {
        String s1 = ciphertext.substring(0, keysize);
        String s2 = ciphertext.substring(keysize, 2 * keysize);
        return (double)hammingDistance(s1,s2) / keysize;
    }

    public static List<Tuple<Double, Integer>> guessKeySize(String cypherText, int minKeySize, int maxKeySize) {
        List<Tuple<Double, Integer>> results = new ArrayList<>();
        for (int keySize = minKeySize; keySize <= maxKeySize; keySize++) {
            results.add(new Tuple<>(tryKeysize(keySize, cypherText), keySize));
        }
        return results.stream().sorted(
                (o1, o2) -> o1.getLeft().compareTo(o2.getLeft())
        ).collect(Collectors.toList());
    }
}
