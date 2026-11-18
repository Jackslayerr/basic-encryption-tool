import java.math.BigInteger;

public class RSAkey {
    BigInteger exponent;
    BigInteger modulus;
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
