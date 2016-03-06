package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServerThread extends Thread {
    public final static int UDP_PORT = 9000;

    private DatagramSocket socket;
    private byte[] receiveData;
    private boolean ejecutar;

    public UDPServerThread() throws IOException {
        socket = new DatagramSocket(UDP_PORT);
        ejecutar = true;
    }

    public void run() {
        try{
            while (ejecutar) {
                receiveData = new byte[256];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("UDP,/"+sentence);
                Server.fileWriter.append("UDP,/"+sentence);
                Server.fileWriter.append(Server.NEW_LINE_SEPARATOR);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    public void stopServer() {
        ejecutar=false;
    }
}
