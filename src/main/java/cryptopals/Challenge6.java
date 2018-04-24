package cryptopals;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Challenge6 {

    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","6.txt"));
        String base64EncodedText = strings.stream().collect(Collectors.joining());
        byte[] encryptedText = Base64.getDecoder().decode(base64EncodedText);
        String results = crackReapeatingKeyXOR(encryptedText, 2, 40);
        System.out.println(results);
    }

    public static String crackReapeatingKeyXOR(byte[] encryptedText, int minKeySize, int maxKeySize) {
        List<Tuple<Double, Integer>> guessedKeySize = guessKeySize(encryptedText, minKeySize, maxKeySize);
        Integer keySize = guessedKeySize.get(0).getRight();
        List<byte[]> splitText = splitCypherText(encryptedText, keySize);
        List<byte[]> transposedBlocks = transposeBlocks(splitText);

        StringBuilder cipher = new StringBuilder();
        for (byte[] block : transposedBlocks) {
            String byteString = new String(block, Charset.forName("us-ascii"));
            cipher.append(Challenge3.guessCipher(byteString));
        }
        String result = cipher.toString();
        return result;
    }

    public static List<byte[]> transposeBlocks(List<byte[]> blocks) {
        Map<Integer, List<Byte>> transposedBlocks = new HashMap<>();
        for (byte[] block : blocks) {
            for (int index = 0; index < block.length; index++) {
                if (!transposedBlocks.containsKey(index)) {
                    List<Byte> list = new ArrayList();
                    list.add(block[index]);
                    transposedBlocks.put(index, list);
                } else {
                    transposedBlocks.get(index).add(block[index]);
                }
            }
        }
        List<byte[]> toReturn = new ArrayList<>();
        for (List<Byte> arr : transposedBlocks.values()) {
            byte[] bytes = toByteArray(arr);
            toReturn.add(bytes);
        }
        return toReturn;
    }

    public static byte[] toByteArray(List<Byte> in) {
        int n = in.size();
        byte[] out = new byte[n];
        for (int i = 0; i < n; i++) {
            out[i] = in.get(i);
        }
        return out;
    }

    public static List<byte[]> splitCypherText(byte[] text, int splitBy) {
        List<byte[]> results = new ArrayList<>();
        int buckets = (int)Math.ceil((double)text.length / splitBy);
        int index = 0;
        for (int i = 0; i < buckets; i++) {
            if (index+splitBy >= text.length) {
                results.add(Arrays.copyOfRange(text, index, text.length));
            } else {
                results.add(Arrays.copyOfRange(text, index, index+splitBy));
            }
            index += splitBy;
        }
        return results;
    }


    public static String byteToString(byte[] byteArray) {
       StringBuilder result = new StringBuilder();
       for (byte b : byteArray) {
           result.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
       }
       return result.toString();
    }

    public static int hammingDistance(byte[] first, byte[] second) {
        String b1 = byteToString(first);
        String b2 = byteToString(second);

        int hammingDistance = 0;
        for (int i = 0; i < b1.length(); i++) {
            if (b1.charAt(i) != b2.charAt(i)) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    public static double tryKeysize(int keysize, byte[] ciphertext) {
        byte[] b1 = Arrays.copyOfRange(ciphertext, 0, keysize);
        byte[] b2 = Arrays.copyOfRange(ciphertext, keysize, 2*keysize);
        byte[] b3 = Arrays.copyOfRange(ciphertext, keysize*2, 3*keysize);
        byte[] b4 = Arrays.copyOfRange(ciphertext, keysize*3, 4*keysize);

        double distanceAverage = ((double)(
                hammingDistance(b1,b2)+
                hammingDistance(b2,b3)+
                hammingDistance(b3,b4)+
                hammingDistance(b1,b4)+
                hammingDistance(b1,b3)) / 5);
        return distanceAverage / keysize;
    }

    public static List<Tuple<Double, Integer>> guessKeySize(byte[] cypherText, int minKeySize, int maxKeySize) {
        List<Tuple<Double, Integer>> results = new ArrayList<>();
        for (int keySize = minKeySize; keySize <= maxKeySize; keySize++) {
            results.add(new Tuple<>(tryKeysize(keySize, cypherText), keySize));
        }
        List<Tuple<Double, Integer>> toReturn = results.stream().sorted(
                Comparator.comparing(Tuple::getLeft)
        ).collect(Collectors.toList());
        return toReturn;
    }
}
