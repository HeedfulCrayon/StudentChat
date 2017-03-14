package studentChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by Nate on 2/23/2017.
 */
public class Server implements Runnable {

    private static int port = 8090;
    private ServerSocket serverSocket;
    private Socket acceptedSocket;
    protected static HashSet<String> userNames;

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
                ServerHandler sHandler = new ServerHandler(socket);
                new Thread(sHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
