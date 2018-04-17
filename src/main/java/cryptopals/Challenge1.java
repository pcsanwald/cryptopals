package cryptopals;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;

public class Challenge1 {

    private static HashMap<Long, Character> lookupTable = new HashMap<>();

    static {
        initLookupTable();
    }

    public static BitSet stringToBitSet(String input1) {
        String input = new StringBuilder(input1).reverse().toString();
        BitSet bits = new BitSet(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '1') {
                bits.set(i);
            } else {
                bits.set(i, false);
            }
        }
        return bits;
    }

    public static String hexToBinary(String hexString) {
        String unpaddedBinary = new BigInteger(hexString, 16).toString(2);
        return String.format("%"+hexString.length()*4+"s", unpaddedBinary).replace(" ", "0");
    }

    public static String binaryToHex(String binaryString) {
        return new BigInteger(binaryString, 2).toString(16);
    }

    private static String hexToBase64(String input) {
        String binary = hexToBinary(input);
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= binary.length() / 6; i++) {
            int start = (i * 6) - 6;
            int end = (i * 6);
            String stringChunk = binary.substring(start,end);
            BitSet chunk = stringToBitSet(stringChunk);
            long number = chunk.toLongArray()[0];
            result.append(lookupTable.get(number));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        System.out.println("result is "+hexToBase64(input));
    }

    private static void initLookupTable() {
        lookupTable.put(0L, 'A');
        lookupTable.put(1L, 'B');
        lookupTable.put(2L, 'C');
        lookupTable.put(3L, 'D');
        lookupTable.put(4L, 'E');
        lookupTable.put(5L, 'F');
        lookupTable.put(6L, 'G');
        lookupTable.put(7L, 'H');
        lookupTable.put(8L, 'I');
        lookupTable.put(9L, 'J');
        lookupTable.put(10L, 'K');
        lookupTable.put(11L, 'L');
        lookupTable.put(12L, 'M');
        lookupTable.put(13L, 'N');
        lookupTable.put(14L, 'O');
        lookupTable.put(15L, 'P');
        lookupTable.put(16L, 'Q');
        lookupTable.put(17L, 'R');
        lookupTable.put(18L, 'S');
        lookupTable.put(19L, 'T');
        lookupTable.put(20L, 'U');
        lookupTable.put(21L, 'V');
        lookupTable.put(22L, 'W');
        lookupTable.put(23L, 'X');
        lookupTable.put(24L, 'Y');
        lookupTable.put(25L, 'Z');
        lookupTable.put(26L, 'a');
        lookupTable.put(27L, 'b');
        lookupTable.put(28L, 'c');
        lookupTable.put(29L, 'd');
        lookupTable.put(30L, 'e');
        lookupTable.put(31L, 'f');
        lookupTable.put(32L, 'g');
        lookupTable.put(33L, 'h');
        lookupTable.put(34L, 'i');
        lookupTable.put(35L, 'j');
        lookupTable.put(36L, 'k');
        lookupTable.put(37L, 'l');
        lookupTable.put(38L, 'm');
        lookupTable.put(39L, 'n');
        lookupTable.put(40L, 'o');
        lookupTable.put(41L, 'p');
        lookupTable.put(42L, 'q');
        lookupTable.put(43L, 'r');
        lookupTable.put(44L, 's');
        lookupTable.put(45L, 't');
        lookupTable.put(46L, 'u');
        lookupTable.put(47L, 'v');
        lookupTable.put(48L, 'w');
        lookupTable.put(49L, 'x');
        lookupTable.put(50L, 'y');
        lookupTable.put(51L, 'z');
        lookupTable.put(52L, '0');
        lookupTable.put(53L, '1');
        lookupTable.put(54L, '2');
        lookupTable.put(55L, '3');
        lookupTable.put(56L, '4');
        lookupTable.put(57L, '5');
        lookupTable.put(58L, '6');
        lookupTable.put(59L, '7');
        lookupTable.put(60L, '8');
        lookupTable.put(61L, '9');
        lookupTable.put(62L, '+');
        lookupTable.put(63L, '/');
    }
}
