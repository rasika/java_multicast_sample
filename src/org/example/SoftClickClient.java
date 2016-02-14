package org.example;

import org.example.exception.SoftClickConnectionException;
import org.example.transport.multicast.thread.MulticastSocketClientThread;

import java.util.logging.Logger;

public class SoftClickClient {

    private static final Logger log = Logger.getLogger(SoftClickClient.class.getName());

    private String clientName = "";
    private String multicastAddress = "";
    private int multicastPort = 8888;

    public SoftClickClient(String clientName, String multicastAddress, int multicastPort) {
        this.clientName = clientName;
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    public void searchServers(int leastServerCount)
            throws SoftClickConnectionException {
        MulticastSocketClientThread multicastSocketClientThread = new MulticastSocketClientThread(clientName,
                multicastAddress, multicastPort);
        multicastSocketClientThread.setLeastListenBeaconCount(leastServerCount);
        multicastSocketClientThread.start();
    }
}
