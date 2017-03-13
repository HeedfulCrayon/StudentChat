package studentChat;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by Nate on 2/23/2017.
 */
public class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Boolean running;

    public ServerHandler(Socket s) throws IOException {
        socket = s;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        running = true;
    }

    @Override
    public void run() {
        System.out.println("SERVER:Accepted Client : Address - "
                + socket.getInetAddress().getHostName());
        String reply;
        String userName = "";
        try{
//            String userName = input.readLine();
//            System.out.println("SERVER:Username read: " + userName);
//            // Check hashset for username
////            while((input.read() >= 0)){
////                output.write("test");
////                output.flush();
////            }
//            //Thread.sleep(5000);
//            output.println("ACK\r\n");
//            System.out.println("SERVER:ACK Sent");
//            output.flush();
            System.out.println("SERVER:Starting Loop");
            int count = 0;
            while(running){
                if (input.ready()){
                    reply = input.readLine();
                    if (count == 0){
                        if (Server.userNames.isEmpty() ){
                            Server.userNames.add(reply);
                            userName = reply;
                            output.write("ACK\r\n");
                            output.flush();
                        }else if (Server.userNames.contains(reply)){
                            output.write("DENY\r\n");
                            output.flush();
                        }
                    }else if (reply.equals("quit")) {
                        output.write("bye!\r\n");
                        output.flush();
                        running = false;
                    }else{
                        System.out.println(reply);
                        output.write(userName + ": " + reply + "\r\n");
                        output.flush();
                    }
                    count++;
                }
            }
            System.out.println("SERVER:Loop exited");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // Multiple try-catch blocks are required to allow us to guarantee that close is called on each item
            // Note that input and output should be closed before the socket
            try {
                System.out.println("SERVER:socket closed");
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            output.close();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
