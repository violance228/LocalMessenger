package network;

public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onReceivedString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);

}
