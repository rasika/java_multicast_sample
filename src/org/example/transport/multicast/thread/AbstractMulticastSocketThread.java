package org.example.transport.multicast.thread;

import org.example.exception.SoftClickConnectionException;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public abstract class AbstractMulticastSocketThread extends Thread {

    private static final Logger log = Logger.getLogger(AbstractMulticastSocketThread.class.getName());

    protected String multicastGroupIp = "";
    protected int multicastGroupPort = 6789;
    protected MulticastSocket multicastSocket = null;
    protected InetAddress multicastGroupAddress = null;

    public AbstractMulticastSocketThread(String multicastGroupIp, int multicastGroupPort) throws SoftClickConnectionException {
        this.multicastGroupIp = multicastGroupIp;
        this.multicastGroupPort = multicastGroupPort;
        init();
    }

    public AbstractMulticastSocketThread(String name, String multicastGroupIp, int multicastGroupPort)
            throws SoftClickConnectionException {
        super.setName(name);
        this.multicastGroupIp = multicastGroupIp;
        this.multicastGroupPort = multicastGroupPort;
        init();
    }

    protected void init() throws SoftClickConnectionException {
        try {
            // join a Multicast multicastGroupAddress and send the multicastGroupAddress salutations
            multicastGroupAddress = InetAddress.getByName(multicastGroupIp);
            multicastSocket = new MulticastSocket(multicastGroupPort);

            SocketAddress socketAddress = new InetSocketAddress(multicastGroupIp, multicastGroupPort);
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            multicastSocket.joinGroup(socketAddress, networkInterface);
        } catch (IOException e) {
            throw new SoftClickConnectionException(
                    "Error occurred while connecting Multicast Group" + e.getMessage());
        }
    }

    abstract public void run();
}
