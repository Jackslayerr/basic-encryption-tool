import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Encryption {
    // Prime Number 1 (Secret)
    private BigInteger p = EncryptionMath.generateBigPrime(512);
    // Prime Number 2 (Secret)
    private BigInteger q = EncryptionMath.generateBigPrime(512);
    // Modulus (Public)
    BigInteger n;
    // Exponent (Public)
    BigInteger e = BigInteger.valueOf(65537);
    RSAkey pubKey;
    private RSAkey privKey;
    // Constructor
    public Encryption() {
        // Reassigns values if one of them is divisible by 65537
        while (EncryptionMath.validPrimesCheck(p, q) == false) {
            p = EncryptionMath.generateBigPrime(512);
            q = EncryptionMath.generateBigPrime(512);
        }
        // Generate Modulus
        n = p.multiply(q);
        // Generates key pair
        pubKey = new RSAkey(n, e);
        privKey = generatePrivateKey();
    }
    // Return Public Key
    public RSAkey getPublicKey() {
        return new RSAkey(n, e);
    }
    // Generates the private key
    private RSAkey generatePrivateKey() {
        // Eulers Totent of p and q
        BigInteger eulTot = EncryptionMath.EulersTotent(p, q);
        // Private exponent (Private)
        BigInteger d = e.modInverse(eulTot);
        return new RSAkey(n, d);
    }
    // Scramble a String
    public BigInteger encryptString(String text) {
        // Converts the text into bytes
        byte[] textToBytes = text.getBytes(StandardCharsets.UTF_8);
        // Turns those bytes into a BigInteger number
        BigInteger bytesToNumber = new BigInteger(1, textToBytes);
        // Turns the number into an encrypted number
        return bytesToNumber.modPow(e, n);
    }
    // Turns the string back into a message
    public String decryptString(BigInteger cipherText) {
        // Decreypts the cipherText number
        BigInteger decryptNumber = cipherText.modPow(privKey.getExponent(), privKey.getModulus());
        // Turns that decrypted number into bytes
        byte[] decryptBytes = decryptNumber.toByteArray();
        // Removes an extra 0 byte at the begining if present (0 byte not needed)
        if (decryptBytes.length > 1 && decryptBytes[0] == 0) {
            decryptBytes = Arrays.copyOfRange(decryptBytes, 1, decryptBytes.length);
        }
        // Turns the decrypted bytes back into the orginal message and returns the value
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }
    
}
