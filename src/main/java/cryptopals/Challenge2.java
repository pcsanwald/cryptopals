package cryptopals;

import static cryptopals.Challenge1.hexToBinary;
import static cryptopals.Challenge1.binaryToHex;

public class Challenge2 {

    public static void main(String[] args) {
        String one = "1c0111001f010100061a024b53535009181c";
        String two = "686974207468652062756c6c277320657965";
        String result = XOR(Challenge1.hexToBinary(one),Challenge1.hexToBinary(two));
        System.out.println(result);
    }

    public static String XOR(String first, String second) {
        String one = hexToBinary(first);
        String two = hexToBinary(second);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < one.length(); i++) {
            int i1 = (int)one.charAt(i);
            int i2 = (int)two.charAt(i);
            result.append(i1^i2);
        }
        return binaryToHex(result.toString());
    }
}
