package server;

import network.TCPConnection;
import network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connectionsList = new ArrayList<>();

    private ChatServer() {
        System.err.println("Server started .......");
        try(ServerSocket serverSocket = new ServerSocket(8888)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection Exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connectionsList.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceivedString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connectionsList.remove(tcpConnection);
        sendToAllConnections("Client disconnect: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection Exception: " + e);

    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (TCPConnection connection : connectionsList) {
            connection.sendString(value);
        }
    }
}
