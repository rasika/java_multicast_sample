package org.example;

import org.example.exception.SoftClickConnectionException;
import org.example.transport.multicast.thread.MulticastSocketClientThread;
import org.example.transport.multicast.thread.MulticastSocketServerThread;

import java.util.logging.Logger;

public class SoftClickServer {

    private static final Logger log = Logger.getLogger(SoftClickServer.class.getName());

    private String clientName = "";
    private String multicastAddress = "";
    private int multicastPort = 8888;

    public SoftClickServer(String clientName, String multicastAddress, int multicastPort) {
        this.clientName = clientName;
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    public void startSendingBeacon(int beaconDelaySeconds)
            throws SoftClickConnectionException {
        MulticastSocketServerThread multicastSocketServerThread = new MulticastSocketServerThread(clientName,
                multicastAddress, multicastPort);
        multicastSocketServerThread.setBeaconDelaySeconds(beaconDelaySeconds);
        multicastSocketServerThread.start();
    }
}
