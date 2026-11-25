package gui;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logic.Encryption;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.math.BigInteger;

public class MainWindow extends JFrame {
    Encryption encrypt = new Encryption();
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
        JTextArea chat = new JTextArea();
        chat.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(chat);
    // Bottem Section (Inputs)
        JPanel inputs = new JPanel(new GridLayout(1, 2));
        // CheckBox
            JPanel checkPanel = new JPanel(new GridLayout(3,1));
            // Button Options
            JRadioButton option1 = new JRadioButton("RSA Encryption");
            // JRadioButton option2 = new JRadioButton("2");
            // JRadioButton option3 = new JRadioButton("3");
            // Button Group
            ButtonGroup group = new ButtonGroup();
            group.add(option1);
            // group.add(option2);
            // group.add(option3);
            // Add to CheckBox
            checkPanel.add(option1);
            // checkPanel.add(option2);
            // checkPanel.add(option3);
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
                    BigInteger encryptNumber = encrypt.encryptString(inputText);
                    String decryptMessage = encrypt.decryptString(encryptNumber);
                    chat.append("[You]: " + inputText + "\n");
                    chat.append("[Encrypt]: " + encryptNumber + "\n");
                    chat.append("[Decrypt]: " + decryptMessage + "\n");
                    textInput.setText("");
                }
            });
   }
}
