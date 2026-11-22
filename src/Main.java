import java.math.BigInteger;
public class Main {
    public static void main(String[] args) {
        Encryption encrypt = new Encryption();
        BigInteger secretMessage = encrypt.encryptString("");
        System.out.println(encrypt.decryptString(secretMessage));
    }
}
