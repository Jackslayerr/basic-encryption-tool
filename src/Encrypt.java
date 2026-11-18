import java.math.BigInteger;

public class Encrypt {
    public static RSAkey getPublicKey() {
        // Prime Number 1
        BigInteger p = EncryptionMath.generateBigPrime(512);
        // Prime Number 2
        BigInteger q = EncryptionMath.generateBigPrime(512);
        // Reassigns values if one of them is divisible by 65537
        while (EncryptionMath.validPrimesCheck(p, q) == false) {
            p = EncryptionMath.generateBigPrime(512);
            q = EncryptionMath.generateBigPrime(512);
        }
        // Modulus
        BigInteger n = p.multiply(q);
        // Exponent
        BigInteger e = BigInteger.valueOf(65537);
        // Return Public Key
        return new RSAkey(n, e);
    }
}
