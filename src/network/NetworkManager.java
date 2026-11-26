package network;

import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import logic.ByteTranslations;
import logic.RSAkey;
import logic.Encryption;

public class NetworkManager {
    // Sockets
    private Socket socket;
    private ServerSocket serverSocket;

    // Handles Inputs and Outputs
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    // Public Key
    public RSAkey pubKey;

    // Encryption
    private Encryption encrypt;

    // Calls when a message arrives
    private Consumer<String> onMessage;
    // Calls when network status changes
    private Consumer<String> onStatus;
    // Represents what data is coming through
    private int dataType = -1;
    // Recent Reception
    private byte[] recentData;
    // Recent Reception Types
    public String recievedString;
    public BigInteger recievedBigInt;
    public RSAkey recievedPubKey;

    public NetworkManager(Consumer<String> onMessage, Consumer<String> onStatus, Encryption encrypt) {
        this.onMessage = onMessage;
        this.onStatus = onStatus;
        this.pubKey = encrypt.getPublicKey();
        this.encrypt = encrypt;
    }

    // Host a connection
    public void startServer(int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                onStatus.accept("Waiting for connection...");
                socket = serverSocket.accept();
                setupConnection();
                onStatus.accept("Client Connected!");
                onStatus.accept("Public keys exchanged");
                listen();
            } catch (IOException e) {
                onStatus.accept("Error: " + e);
            }
        }).start();
    }
    // Connect to another computer
    public void connectToServer(String ip, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                setupConnection();
                onStatus.accept("Client Connected!");
                listen();
            } catch (IOException e) {
                onStatus.accept("Error: " + e);
            }
        }).start();
    }
    // Setup input and output streams
    private void setupConnection() throws IOException {
        dataInput = new DataInputStream(socket.getInputStream());
        dataOutput = new DataOutputStream(socket.getOutputStream());
        sendData(ByteBuffer.allocate(4).putInt(3).array());
        sendData(pubKey.toBytes());
    }
    // Continously listen for messages
    // -1 Nothing (Waiting)
    // 0 = String
    // 1 = BigInteger
    // 2 = Request Public Key
    // 3 = Public Key
    private void listen() throws IOException {
        while (!socket.isClosed()) {
            recentData = receiveData();
            if (dataType == -1) {
                dataType = ByteTranslations.byteToInt(recentData);
                if (dataType == 2) {
                    onStatus.accept("Public key requested, sending...");
                    
                    onStatus.accept("Public key sent!");
                    dataType = -1;
                }
                continue;
            } else {
                if (dataType == 0) {
                    recievedString = ByteTranslations.byteToString(recentData);
                    onMessage.accept("(Unencrypted): " + recievedString);
                    dataType = -1;
                } else if (dataType == 1) {
                    recievedBigInt = ByteTranslations.byteToBigInt(recentData);
                    recievedString = encrypt.decryptString(recievedBigInt);
                    onMessage.accept("(Decrypted with RSA): " + recievedString);
                    dataType = -1;
                } else if (dataType == 3) {
                    recievedPubKey = ByteTranslations.byteToKey(recentData);
                    dataType = -1;
                }
            }
            // onMessage.accept(recentData);
        }
        onStatus.accept("Connection closed");
    }
    // Send Data
    public synchronized void sendData(byte[] data) throws IOException {
        if (socket == null || socket.isClosed() || dataOutput == null) {
            throw new IOException("Not connected");
        }
        dataOutput.writeInt(data.length);
        dataOutput.write(data);
        dataOutput.flush();
    }
    // Recieve Data
    public byte[] receiveData() throws IOException {
        int length = dataInput.readInt();
        if (length < 0 || length > 10_000_000) {
            throw new IOException("Invalid data length");
        }
        byte[] data = new byte[length];
        dataInput.readFully(data);
        return data;
    }
    // Close connection
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {

        }
    }
}
