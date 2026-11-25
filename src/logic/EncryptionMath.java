package logic;
import java.math.BigInteger;
import java.security.SecureRandom;
public class EncryptionMath {
    // Creates secure random object
    private static final SecureRandom random = new SecureRandom();
    // How many positive big integers less then n (p*q) share no factors with n
    public static BigInteger eulersTotent(BigInteger p, BigInteger q) {
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        return pMinusOne.multiply(q.subtract(BigInteger.ONE));
    }
    // Checks if p - 1 and q - 1 are divisible by 65537 (Returns false if so)
    public static boolean validPrimesCheck(BigInteger p, BigInteger q) {
        // p - 1, q - 1
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        BigInteger qMinusOne = q.subtract(BigInteger.ONE);
        // The remainder of (p-1)/65537
        BigInteger remainderPMinusOne = pMinusOne.remainder(new BigInteger("65537"));
        // The remainder of (q-1)/65537
        BigInteger remainderQMinusOne = qMinusOne.remainder(new BigInteger("65537"));
        // Returns false if a remainder is 0, otherwise true
        return !(remainderPMinusOne.equals(BigInteger.ZERO) || remainderQMinusOne.equals(BigInteger.ZERO));
    }
    // Generates a secure random BigInteger prime number
    public static BigInteger generateBigPrime(int bitLength) {
        return BigInteger.probablePrime(bitLength, random);
    }
}
