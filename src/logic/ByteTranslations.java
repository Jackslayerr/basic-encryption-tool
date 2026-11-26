package logic;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

// Turns bytes back into specific data types
public class ByteTranslations {
    public static String byteToString(byte[] bytes) {
        String string = new String(bytes, StandardCharsets.UTF_8);
        return string;
    }
    public static BigInteger byteToBigInt(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(bytes);
        return bigInteger;
    }
    public static RSAkey byteToKey(byte[] bytes) {
        return RSAkey.toKey(bytes);
    }
    public static int byteToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
