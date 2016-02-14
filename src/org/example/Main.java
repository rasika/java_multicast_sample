package org.example;

import org.example.exception.SoftClickConnectionException;
import org.example.transport.multicast.thread.MulticastSocketClientThread;

import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(MulticastSocketClientThread.class.getName());

    public static void main(String[] args) {
        try {
            //initialize server and client

            String mode = "";
            String name = "";
            String multicastIp = "";
            int multicastPort = 0;
            int param = 0;

            try {
                mode = args[0];
                name = args[1];
                multicastIp = args[2];
                multicastPort = Integer.parseInt(args[3]);
                param = Integer.parseInt(args[4]);
            } catch (ArrayIndexOutOfBoundsException e) {
                printHelp();
            }

            if (mode.equals("server")) {
                SoftClickServer server1 = new SoftClickServer(name, multicastIp, multicastPort);
                server1.startSendingBeacon(param);
            } else if (mode.equals("client")) {
                SoftClickClient client = new SoftClickClient(name, multicastIp, multicastPort);
                client.searchServers(param);
            } else {
                printHelp();
            }
        } catch (SoftClickConnectionException e) {
            log.severe("Error on initializing server/client" + e.getMessage());
        }
    }

    private static void printHelp() {
        System.out.println("Please use following syntax");
        System.out.println("====Run as server====");
        System.out.println(
                "java -jar multicast.jar <mode> <name> <multicast_ip> <multicast_port> <beacon_delay_in_sec>");
        System.out.println("java -jar multicast.jar server server1 224.0.0.3 8888 5");
        System.out.println("====Run as client====");
        System.out.println(
                "java -jar multicast.jar <mode> <name> <multicast_ip> <multicast_port> <param> <max_beacon_listen>");
        System.out.println("java -jar multicast.jar client client1 224.0.0.3 8888 2");
        System.exit(0);
    }
}
