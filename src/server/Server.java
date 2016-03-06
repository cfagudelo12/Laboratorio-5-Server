package server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Server {

    public static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "Protocolo,IP,latitud,longitud,altitud,velocidad";

    private static final String FILE_DIR = "./data/data.csv";

    public final static int TCP_PORT = 8080;

    public static FileWriter fileWriter;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean ejecutar = true;
        fileWriter = new FileWriter(FILE_DIR);
        fileWriter.append(FILE_HEADER.toString());
        fileWriter.append(NEW_LINE_SEPARATOR);
        ServerSocket tcp = null;
        UDPServerThread udp;
        try {
            tcp = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        udp = new UDPServerThread();
        udp.start();
        while (ejecutar) {
            new TCPServerThread(tcp.accept()).start();
            if(in.readLine().equals("STOP")) {
                ejecutar = false;
            }
        }
        udp.stopServer();
        fileWriter.flush();
        fileWriter.close();
    }
}
