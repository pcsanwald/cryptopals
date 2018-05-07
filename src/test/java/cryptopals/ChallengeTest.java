package cryptopals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.TreeMap;

import static cryptopals.Challenge3.guessString;
import static cryptopals.Challenge5.repeatingXOR;
import static org.junit.Assert.assertEquals;

public class ChallengeTest {

    @Test
    public void testHexToBinary() {
        String result = Challenge1.hexToBinary("1a");
        assertEquals("00011010", result);
    }

    @Test
    public void testXOR() {
        String actual = Challenge2.XOR("1c0111001f010100061a024b53535009181c","686974207468652062756c6c277320657965");
        String expected = "746865206b696420646f6e277420706c6179";
        assertEquals(expected, actual);
    }

    @Test
    public void testRepeatingXOR() {
        String toEncrypt = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";
        String key = "ICE";
        String expected = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";
        assertEquals(expected.toUpperCase(), repeatingXOR(toEncrypt, key));
    }

    @Test
    public void testSingleByteXOR() {
        String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        TreeMap<Double, List<String>> highScores = guessString(Challenge3.decodeString(input), null);
        assertEquals("Cooking MC's like a pound of bacon",highScores.lastEntry().getValue().get(0));
    }

    @Test
    public void testHammingDistance() {
        String s1 = "this is a test";
        String s2 = "wokka wokka!!!";
        assertEquals(Challenge6.hammingDistance(s1.getBytes(),s2.getBytes()),37);
    }

    @Test
    public void testTryKeysize() {
        double result = Challenge6.tryKeysize(2,"aabbccdd".getBytes());
        assertEquals(1.8, result, 0.001);
    }

    @Test
    public void testBase64Encoding() {
        String toDecode = "Q29va2luZyBNQydzIGxpa2UgYSBwb3VuZCBvZiBiYWNvbg==";
        String decoded = new String(Base64.getDecoder().decode(toDecode));
        assertEquals("Cooking MC's like a pound of bacon", decoded);
    }

    @Test
    public void testTransposeBlocks() {
        List<byte[]> blocks = new ArrayList<>();
        blocks.add("11".getBytes());
        blocks.add("22".getBytes());
        blocks.add("33".getBytes());
        blocks.add("44".getBytes());
        List<byte[]> tranposed = Challenge6.transposeBlocks(blocks);
        assertEquals(2,tranposed.size());
        assertEquals(4,tranposed.get(1).length);
    }

    @Test
    public void testSplitCypherText() {
        String text = "123451234512345123";
        List<byte[]> split = Challenge6.splitCypherText(text.getBytes(), 5);
        assertEquals(4, split.size());
    }

    @Test
    public void testPadBytes() {
        String text = "YELLOW SUBMARINE";
        byte[] result = Challenge9.padBytes(text.getBytes(),4);
        assertEquals(result.length, text.length()+4);
    }

}
