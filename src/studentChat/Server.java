package studentChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Created by Nate on 2/23/2017.
 */
public class Server implements Runnable {

    static int _port = 8080;
    private ServerSocket serverSocket;
    private Socket acceptedSocket;
    public static HashSet<String> userNames;

    public Server() throws IOException {
        serverSocket = new ServerSocket(_port);
        userNames = new HashSet<>();
    }


//    public void startServer() {
//        Runnable serverTask = new Runnable() {
//            @Override
//            public void run() {
//                int port = 8080;
//                ServerSocket serverSocket;
//                try {
//                    serverSocket = new ServerSocket(port);
//                    while (!serverSocket.isClosed() && !serverSocket.isBound()) {
//                        Socket socket = serverSocket.accept();
//                        ServerHandler sHandler = new ServerHandler(socket);
//
//                        new Thread(sHandler).start();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }

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
