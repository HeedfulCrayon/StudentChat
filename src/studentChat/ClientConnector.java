package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nate on 3/11/2017.
 */
public class ClientConnector extends JFrame{
    private static final long serialVersionUID = -922753426746135029L;
    private JTextField ip_textfield;
    private JButton connect_button;
    private InetAddress ip;
    private Socket socket;
    private int port = 8080;
    private String user = "Nate\r\n";
    private InetAddress local;
    private Thread localServer;
    protected Boolean connected;

    public ClientConnector () {
        try {
            initialize();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void initialize() throws UnknownHostException, IOException {
        connected = false;
        JPanel panel = new JPanel();
        Dimension spacing = new Dimension(10,10);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(spacing));
        panel.add(new JLabel("Enter a server IP address:"));
        panel.add(Box.createRigidArea(spacing));
        ip_textfield = new JTextField();
        panel.add(ip_textfield);
        connect_button = new JButton("Connect");
        connect_button.addActionListener((e) -> {
            try {
                connect_button.setText("Connecting");
                String ipStr = ip_textfield.getText();
                ip = InetAddress.getByName(ipStr);
                Connect(ip);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        });
        panel.add(Box.createRigidArea(spacing));
        panel.add(connect_button);
        setSize(new Dimension(300,300));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Connect to server");
        add(panel);
    }

//    private void StartLocal(){
//        try {
//            new Thread(new Server()).start();
//            if (Reachable("127.0.0.1")) {
//                local = InetAddress.getByName("127.0.0.1");
//                if(!Connect(local)){
//                    System.out.println("Unable to connect");
//
//                }
//            }
//        } catch (IOException ioe){
//            ioe.printStackTrace();
//        }
//    }

//    private Boolean Reachable(String ip){
//        try {
//
//            InetAddress address = InetAddress.getByName(ip);
//
//            if(address == null)
//            {
//                return false;
//            }
//
//        } catch (UnknownHostException e) {
//            return false;
//        }
//        return true;
//    }

    private void Connect(InetAddress ip) {
        try {
            socket = new Socket(ip, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new Reader(socket,input)).start();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.write(user);
            output.flush();
            MessagesGUI messagesGUI = new MessagesGUI(socket,input,output);
            messagesGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        ClientConnector.super.setVisible(false);
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        System.exit(0);
                    }
                });
            messagesGUI.Start();
//            if (connected) {
//                MessagesGUI client = new MessagesGUI(socket, in, out);
//                client.addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowOpened(WindowEvent e) {
//                        ClientConnector.super.setVisible(false);
//                    }
//
//                    @Override
//                    public void windowClosed(WindowEvent e) {
//                        ClientConnector.super.setVisible(true);
//                    }
//                });
//                new Thread(client).start();
//            }
//            return connected;
        } catch (IOException e){
            try {
                new Thread(new Server()).start();
                Connect(InetAddress.getByName("127.0.0.1"));
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    private class Reader implements Runnable {
        private BufferedReader input;
        private Socket socket;
        private Boolean running;

        public Reader(Socket s, BufferedReader in) throws IOException {
            socket = s;
            input = in;
            running = true;
        }

        @Override
        public void run() {
            System.out.println("CLIENT:Reader thread started");
            String reply;
            try {
                System.out.println("CLIENT:Starting loop");
                while(running){
                    System.out.println("CLIENT:Entered Loop");
                    if((reply = input.readLine()) != null) {
                        if (reply.equals("ACK")) {
                            System.out.println("CLIENT:Ack received");
                            connected = true;
                            running = false;
                        } else if (reply.equals("DENY")) {
                            throw new IOException();
                        }
                    }
                }

                System.out.println("CLIENT:Exited Loop");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
