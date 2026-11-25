package logic;
import java.math.BigInteger;

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
}
