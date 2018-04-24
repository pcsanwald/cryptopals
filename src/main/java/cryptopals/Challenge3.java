package cryptopals;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Challenge3 {

    public static String decodeString(String input) {
        StringBuilder toDecode = new StringBuilder();
        for (int i = 0; i < input.length()-1; i+=2) {
            String character = input.charAt(i) + "" + input.charAt(i+1);
            char c = (char)Integer.parseInt(character, 16);
            toDecode.append(c);
        }
        return toDecode.toString();
    }

    public static String singleByteXOR(String input, char cipher) {
        String result = input.chars()
                .map(charInt -> (char)charInt ^ cipher)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return result;
    }

    public static void main(String[] args) {
        String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        TreeMap<Double, List<String>> highScores = guessString(decodeString(input), null);
        System.out.println(highScores.lastEntry().getValue());
    }

    public static TreeMap<Double, List<String>> guessString(String input, TreeMap<Double, List<String>> existingScores) {
        if (existingScores == null) {
            existingScores = new TreeMap<>();
        }
        //assume the encoding character is US-ASCII
        char[] ciphers = new char[255];
        for (int i = 0; i < 255; i++) {
            ciphers[i] = (char)i;
        }
        for (char c : ciphers) {
            String singleByteXOR = singleByteXOR(input, c);
            Double score = score2(singleByteXOR);
            if (existingScores.containsKey(score)) {
                existingScores.get(score).add(singleByteXOR);
            } else {
                List<String> resultList = new ArrayList<>();
                resultList.add(singleByteXOR);
                existingScores.put(score, resultList);
            }
        }
        return existingScores;
    }

    public static char guessCipher(String input) {
        List<Tuple<Character, Double>> existingScores = new ArrayList<>();
        //assume the encoding character is US-ASCII
        char[] ciphers = new char[255];
        for (int i = 0; i < 255; i++) {
            ciphers[i] = (char)i;
        }
        for (char c : ciphers) {
            String singleByteXOR = singleByteXOR(input, c);
            Double score = score2(singleByteXOR);
            existingScores.add(new Tuple<>(c, score));
        }
        Collections.sort(existingScores, (a, b) -> b.getRight().compareTo(a.getRight()));
        return existingScores.get(0).getLeft();
    }

    public static int getCount(String source, String searchFor) {
        int count = 0;
        int lastIndex = 0;
        while((lastIndex = source.indexOf(searchFor, lastIndex)) != -1) {
            count++;
            lastIndex++;
        }
        return count;
    }


    public static Double score2(String decoded) {
        Pattern letters = Pattern.compile("[A-Za-z\\s]");
        Matcher matcher = letters.matcher(decoded);
        int letterCount = 0;
        while (matcher.find()) {
            letterCount++;
        }
        if (letterCount == 0) {
            return 0.0;
        }
        double score = 0;
        String[] commonChars = { "e", "t", "a", "o", "i", "n", "s", "h", "r", "d", "l", "u", " " };
        double[] weights = { 0.1202, 0.091, 0.0812, 0.0768, 0.0731, 0.0695, 0.0628, 0.0602, 0.0592, 0.0432, 0.0398, 0.0288, 0.1918182 };
        for (int i = 0; i < commonChars.length; i++) {
            double frequency = getCount(decoded, commonChars[i]) / (double)letterCount;
            double distance = Math.abs(frequency - weights[i]);
            score += distance;
        }
        return 1 / score;
    }
}
