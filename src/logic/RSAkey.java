package logic;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class RSAkey {
    private final BigInteger exponent;
    private final BigInteger modulus;
    public RSAkey(BigInteger modulus, BigInteger exponent) {
        this.exponent = exponent;
        this.modulus = modulus;
    }
    public BigInteger getExponent() {
        return exponent;
    }
    public BigInteger getModulus() {
        return modulus;
    }
    // Converts this class into bytes
    public byte[] toBytes() {
        byte[] modulusBytes = modulus.toByteArray();
        byte[] exponentBytes = exponent.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(4+modulusBytes.length+4+exponentBytes.length);
        buffer.putInt(modulusBytes.length);
        buffer.put(modulusBytes);
        buffer.putInt(exponentBytes.length);
        buffer.put(exponentBytes);
        return buffer.array();
    }
    // Convert bytes into this class
    public static RSAkey toKey(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int modulusLength = buffer.getInt();
        byte[] modulusBytes = new byte[modulusLength];
        buffer.get(modulusBytes);
        int exponentLength = buffer.getInt();
        byte[] exponentBytes = new byte[exponentLength];
        buffer.get(exponentBytes);
        BigInteger modulus = new BigInteger(modulusBytes);
        BigInteger exponent = new BigInteger(exponentBytes);
        return new RSAkey(modulus, exponent);
    }
}
