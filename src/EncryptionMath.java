import java.math.BigInteger;
import java.security.SecureRandom;
public class EncryptionMath {
    // How many positive big integers less then n (p*q) share no factors with n
    public static BigInteger EulersTotent(BigInteger p, BigInteger q) {
        BigInteger pMinusOne = p.subtract(new BigInteger("1"));
        return pMinusOne.multiply(q.subtract(new BigInteger("1")));
    }
    // Checks if the two generated primes are divisible by 65537 (Returns false if so)
    public static boolean validPrimesCheck(BigInteger p, BigInteger q) {
        BigInteger remainderP = p.remainder(new BigInteger("65537"));
        BigInteger remainderQ = q.remainder(new BigInteger("65537"));
        if (remainderP.equals(new BigInteger("0")) || remainderQ.equals(new BigInteger("0"))) {
            return false;
        } else {
            return true;
        }
    }
    // Generates a secure random BigInteger prime number
    public static BigInteger generateBigPrime(int bitLength) {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }
}
