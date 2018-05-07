package cryptopals;

import java.util.Arrays;

public class Challenge9 {

    public static void main(String[] args) {
        //String ""
    }

    public static byte[] padBytes(byte[] toPad, int padSize) {
        int newSize = toPad.length + padSize;
        byte[] toReturn = Arrays.copyOf(toPad, newSize);
        for (int i = toPad.length; i < newSize; i++) {
            char c = (char)padSize;
            toReturn[i] = (byte)c;
        }
        return toReturn;
    }
}
