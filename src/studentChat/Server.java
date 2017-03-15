package studentChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Nate on 2/23/2017.
 */
public class Server implements Runnable {
    private static int port = 8090;
    private ServerSocket serverSocket;
    private Socket acceptedSocket;
    protected static HashSet<ClientConnection> userNames;

    public Server() throws IOException {
        serverSocket = new ServerSocket(port);
        userNames = new HashSet<>();
    }

    @Override
    public void run() {
        System.out.println("SERVER:Starting");
        while (!serverSocket.isClosed() && serverSocket.isBound()) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                String name;
                if(!userNames.contains((name = input.readLine()))){
                    userNames.add(new ClientConnection(name,socket,output));
                    output.println("ACK");
                    output.flush();
                }else{
                    output.println("DENY");
                    output.flush();
                }
                ServerHandler sHandler = new ServerHandler(socket,name);
                new Thread(sHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
