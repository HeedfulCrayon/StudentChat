import blackjack.message.Message;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private String userName = "Nate";
    public ClientConnector(){
        String IP = "52.35.72.251";
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
            oOutput.writeObject(blackjack.message.MessageFactory.getLoginMessage(userName));
            oOutput.flush();
            oInput = new ObjectInputStream(socket.getInputStream());
            new Thread(new Reader(socket,oInput,this)).start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void StartMessageGUI(){
        ClientGUI clientGUI = new ClientGUI(oInput,oOutput,userName);
        clientGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try{
                    socket.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                System.exit(0);
            }
        });
        clientGUI.StartWindow();
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
