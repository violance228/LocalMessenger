import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import network.TCPConnection;
import network.TCPConnectionListener;

import java.io.IOException;

public class ChatController implements TCPConnectionListener {

    private final String IP_ADDR = "192.200.100.117";
    private final int PORT = 6666;

    @FXML
    private TextArea messege;

    @FXML
    private TextArea log;

    @FXML
    private Label nickName;

//    private ClientServerMain main;
    private TCPConnection connection;

    public void setMain(ClientServerMain main) {
//        this.main = main;
    }

    @FXML
    public void actionPerformed() {

        String msg = messege.getText();
        if (msg.equals("")) return;
        messege.setText(null);

        connection.sendString(nickName.getText() + " - " + msg);
    }

    private synchronized void printMsg(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                log.appendText(msg + "\n");
            }
        });
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection ready...");
    }

    @Override
    public void onReceivedString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection close...");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception " + e);
    }

    public void upConnection() {
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            printMsg("Connection exception " + e);
        }
    }
}