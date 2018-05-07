package cryptopals;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Challenge7 {

    static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","7.txt"));
        String base64EncodedText = strings.stream().collect(Collectors.joining());
        byte[] encryptedText = Base64.getDecoder().decode(base64EncodedText);
        String decryptedString = decryptECB(encryptedText, "YELLOW SUBMARINE");
        System.out.println(decryptedString);
    }

    public static String decryptECB(byte[] encryptedText, String cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(cipherText.getBytes(),"AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(encryptedText),"UTF-8");
    }

    public static String encryptECB(byte[] encryptedText, String cipherText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(cipherText.getBytes(),"AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return new String(cipher.doFinal(encryptedText),"UTF-8");
    }
}
