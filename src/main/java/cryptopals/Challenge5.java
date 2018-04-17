package cryptopals;

public class Challenge5 {

    public static void main(String[] args) {
        String toEncrypt = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";
        String key = "ICE";
        System.out.println(repeatingXOR(toEncrypt, key));
    }

    public static String repeatingXOR(String toEncrypt, String key) {
        int counter = 0;
        StringBuilder encryptedString = new StringBuilder();
        for (char charToEncrypt : toEncrypt.toCharArray()) {
            int encryptedChar = charToEncrypt ^ key.charAt(counter);
            encryptedString.append(String.format("%02X", encryptedChar));
            counter = (counter+1) % key.length();
       }
       return encryptedString.toString();
    }
}
