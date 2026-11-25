import java.math.BigInteger;

import javax.swing.SwingUtilities;

import gui.MainWindow;
import logic.Encryption;
public class Main {
    public static void main(String[] args) {
        
        // BigInteger secretMessage = encrypt.encryptString("Yo");
        // System.out.println(encrypt.decryptString(secretMessage));
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
