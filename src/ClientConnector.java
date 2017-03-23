import blackjack.message.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Nate on 3/22/2017.
 */
public class ClientConnector {
    private InetAddress inetAddr;
    private Socket socket;
    private ObjectOutputStream oOutput;
    private ObjectInputStream oInput;
    public ClientConnector(){
        String IP = "137.190.250.174";
        int port = 8989;
        Connect(IP,port);
    }

    private void checkDenial(Boolean ACK){
        if(ACK == true){
            StartMessageGUI();
        }
    }

    private void Connect(String ip, int port) {
        try{
            inetAddr = InetAddress.getByName(ip);
            socket = new Socket(inetAddr,port);
            oOutput = new ObjectOutputStream(socket.getOutputStream());
            oOutput.writeObject(blackjack.message.MessageFactory.getLoginMessage("Nate"));
            oOutput.flush();
            oInput = new ObjectInputStream(socket.getInputStream());
            new Thread(new Reader(socket,oInput,this)).start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void StartMessageGUI(){
        new ClientGUI(oInput,oOutput);
    }

    private class Reader implements Runnable {
        private Socket socket;
        private boolean running = true;
        private ObjectInputStream input;
        private ClientConnector clientConnector;
        private Reader(Socket s,ObjectInputStream in,ClientConnector cc){
            socket = s;
            input = in;
            clientConnector = cc;
        }
        @Override
        public void run() {
            Message reply;
            try {
                while(running){
                    reply = (Message)input.readObject();
                    if (reply.getType() == Message.MessageType.ACK){
                        System.out.println("ACK Recv'd");
                        running = false;
                        clientConnector.checkDenial(true);
                    }
                    else if (reply.getType() == Message.MessageType.DENY){
                        System.out.println("DENY Recv'd");
                        running = false;
                        input.close();
                        socket.close();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
