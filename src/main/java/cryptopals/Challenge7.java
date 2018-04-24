package cryptopals;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Challenge7 {

    public static void main(String[] args) throws Exception {
        List<String> strings = Files.readAllLines(FileSystems.getDefault()
                .getPath("/Users", "paulsanwald", "Desktop","7.txt"));
        String base64EncodedText = strings.stream().collect(Collectors.joining());
        byte[] encryptedText = Base64.getDecoder().decode(base64EncodedText);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec("YELLOW SUBMARINE".getBytes(),"AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String decryptedString = new String(cipher.doFinal(encryptedText),"UTF-8");
        System.out.println(decryptedString);
    }
}
