import java.math.BigInteger;

public static void main(String[] args) {
    Encryption encrypt = new Encryption();
    BigInteger secretMessage = encrypt.encryptString("Hi my name is Jack! This message is encrypted");
    System.out.println(encrypt.decryptString(secretMessage));
}