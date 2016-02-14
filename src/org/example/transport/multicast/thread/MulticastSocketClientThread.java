package org.example.transport.multicast.thread;

import org.example.exception.SoftClickConnectionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Logger;

public class MulticastSocketClientThread extends AbstractMulticastSocketThread {

    private static final Logger log = Logger.getLogger(MulticastSocketClientThread.class.getName());
    protected final int RECV_BUFFER_SIZE = 1000;

    private int leastListenBeaconCount = 5;
    private int listenedBeaconCount = 0;

    public MulticastSocketClientThread(String multicastGroupIp, int multicastGroupPort)
            throws SoftClickConnectionException {
        super(multicastGroupIp, multicastGroupPort);
    }

    public MulticastSocketClientThread(String clientName, String multicastGroupIp, int multicastGroupPort)
            throws SoftClickConnectionException {
        super(clientName, multicastGroupIp, multicastGroupPort);
    }

    public void setLeastListenBeaconCount(int leastListenBeaconCount) {
        this.leastListenBeaconCount = leastListenBeaconCount;
    }

    private void listenBeacon(){
        while (listenedBeaconCount < leastListenBeaconCount) {
            try {
                // get their responses!
                byte[] buf = new byte[RECV_BUFFER_SIZE];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                multicastSocket.receive(recv);
                String helloBeacon = new String(recv.getData(), recv.getOffset(), recv.getLength());
                log.info("***[" + currentThread().getName() + "] Received:" + helloBeacon + "***");
                listenedBeaconCount++;
            } catch (IOException e) {
                log.severe("error: " + e.getMessage());
                //e.printStackTrace();
            }
        }

        try {
            // OK, I'm done talking - leave the multicastGroupAddress...
            multicastSocket.leaveGroup(multicastGroupAddress);
        } catch (IOException e) {
            log.severe("error: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public void run() {
        listenBeacon();
    }
}
