package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPServerThread extends Thread{

    private Socket clientSocket = null;

    public TCPServerThread(Socket pSocket) {
        clientSocket = pSocket;
    }

    public void run() {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String ip = clientSocket.getRemoteSocketAddress().toString();
            String in;
            in = reader.readLine();
            if(in.equals("HELLO")){
                writer.println("GO");
            }
            else{
                writer.println("ERROR");
                return;
            }
            in=reader.readLine();
            while(!in.equals("STOP")){
                System.out.println("TCP,"+in);
                Server.fileWriter.append("TCP,"+ip+","+in);
                Server.fileWriter.append(Server.NEW_LINE_SEPARATOR);
                in=reader.readLine();
            }
            reader.close();
            writer.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

