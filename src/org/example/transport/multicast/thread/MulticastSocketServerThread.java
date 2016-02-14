package org.example.transport.multicast.thread;

import org.example.exception.SoftClickConnectionException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Logger;

public class MulticastSocketServerThread extends AbstractMulticastSocketThread {

    private static final Logger log = Logger.getLogger(MulticastSocketServerThread.class.getName());
    private int beaconDelaySeconds = 1;

    public MulticastSocketServerThread(String multicastGroupIp, int multicastGroupPort) throws SoftClickConnectionException {
        super(multicastGroupIp, multicastGroupPort);
    }

    public MulticastSocketServerThread(String serverName, String multicastGroupIp, int multicastGroupPort)
            throws SoftClickConnectionException {
        super(serverName, multicastGroupIp, multicastGroupPort);
    }

    public void setBeaconDelaySeconds(int beaconDelaySeconds) {
        this.beaconDelaySeconds = beaconDelaySeconds;
    }

    private void sendBeacon(){
        try {
            String msg = "Hello from " + currentThread().getName();

            DatagramPacket helloBeacon = new DatagramPacket(msg.getBytes(), msg.length(), multicastGroupAddress,
                    multicastGroupPort);
            multicastSocket.send(helloBeacon);
            log.info("***[" + currentThread().getName() + "] Sent: " + msg + "***");
        } catch (IOException e) {
            log.severe("error: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    public void run() {
        boolean isSendBeacon = true;
        while(isSendBeacon) {
            sendBeacon();
            try{
                Thread.sleep(beaconDelaySeconds*1000);
            }catch (InterruptedException e){
                //do nothing
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
}
