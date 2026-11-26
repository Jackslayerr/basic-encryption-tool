package gui;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logic.Encryption;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import network.NetworkManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.io.IOException;


public class MainWindow extends JFrame {
    Encryption encrypt = new Encryption();
    JTextArea chat;
   public MainWindow() {
    // Basic Window Setup
        setTitle("Basic Encryption Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    // Top Section (ChatArea)
        chat = new JTextArea();
        chat.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(chat);
    // Bottem Section (Inputs)
        JPanel inputs = new JPanel(new GridLayout(1, 2));
        // CheckBox
            JPanel checkPanel = new JPanel();
            checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.Y_AXIS));
            // Button Options
            JRadioButton option1 = new JRadioButton("Unencrypted");
            JRadioButton option2 = new JRadioButton("RSA Encryption");
            option1.setSelected(true);
            // JRadioButton option2 = new JRadioButton("2");
            // JRadioButton option3 = new JRadioButton("3");
            // Button Group
            ButtonGroup group = new ButtonGroup();
            group.add(option1);
            group.add(option2);
            // Add to CheckBox
            checkPanel.add(option1);
            checkPanel.add(option2);
            JButton host = new JButton("Host");
            JButton connect = new JButton("Connect");
            checkPanel.add(host);
            checkPanel.add(connect);
        // Input Text
            JTextArea textInput = new JTextArea();
            JScrollPane textInputScroll = new JScrollPane(textInput);
        inputs.add(checkPanel);
        inputs.add(textInputScroll);
    // Send Button
        JButton sendMessage = new JButton("Send Message");
    // Assemble the Main Panel
        // ChatBox
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            mainPanel.add(chatScroll, gbc);
        // Inputs
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.4;
            mainPanel.add(inputs, gbc);
        // Send Button
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 0.1;
            mainPanel.add(sendMessage, gbc);
    // Add to frame
        add(mainPanel);
    // FUNCTIONALITY
        // sendButton Input
            sendMessage.addActionListener(e -> {
                String inputText = textInput.getText();
                if (!(inputText.isEmpty())) {
                    // Unencrypted
                    if (option1.isSelected()) {
                        chat.append("[You] (Sending Unencrypted): " + inputText + "\n");
                        try {
                            chat.append("[Network]: Sending message..." + "\n");
                            network.sendData(ByteBuffer.allocate(4).putInt(0).array());
                            network.sendData(inputText.getBytes(StandardCharsets.UTF_8));
                            chat.append("[Network]: Message Sent!" + "\n");
                            textInput.setText("");
                        } catch (IOException ex) {
                            chat.append("[Network]: Failed to send message: " + ex.getMessage() + "\n");
                        }
                    } else if (option2.isSelected()) {
                        chat.append("[You] (Sending Message via RSA): " + inputText + "\n");
                        try {
                            chat.append("[Network]: Requesting recipient public key" + "\n");
                            network.sendData(ByteBuffer.allocate(4).putInt(2).array());
                            chat.append("[Network]: Public key received" + "\n");
                            chat.append("[Encryption]: Encrypting message" + "\n");
                            BigInteger encryptedMessage = encrypt.encryptStringWithPubKey(inputText, network.recievedPubKey);
                            chat.append("[Network]: Sending encrypted message" + "\n");
                            network.sendData(ByteBuffer.allocate(4).putInt(1).array());
                            network.sendData(encryptedMessage.toByteArray());
                            chat.append("[Network]: Encrypted message sent!" + "\n");
                        } catch (IOException ex2) {
                            chat.append("[Network]: Failed to send message: " + ex2.getMessage() + "\n");
                        }
                    }
                }
            });
        // host Input
        host.addActionListener(e -> {
            network.startServer(5000);
        });
        // connect Input
        connect.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(this, "Enter the server IP address");
            if (ip != null && !ip.isBlank()) {
                network.connectToServer(ip.trim(), 5000);
            }
        });
   }
   // Network
   NetworkManager network = new NetworkManager(
    // Incoming messages
    message -> SwingUtilities.invokeLater(() -> {
        chat.append("[Them]: " + message + "\n");
    }),
    // Connection status
    status -> SwingUtilities.invokeLater(() -> {
        chat.append("[Network]: " + status + "\n");
    }),
    // Public Key
    encrypt
    );
    
}
