package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Nate on 3/11/2017.
 */
public class ClientConnector extends JFrame{
    private static final long serialVersionUID = -922753426746135029L;
    private JTextField ip_textfield;
    private JTextField port_textfield;
    private JButton connect_button;
    private InetAddress ip;
    private Socket socket;
    private int port;
    private String user = "Nate\r\n";
    private BufferedReader input;
    private PrintWriter output;

    public ClientConnector () {
        try {
            initialize();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initialize() throws IOException {
        JPanel panel = new JPanel();
        Dimension spacing = new Dimension(10,10);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(spacing));
        panel.add(new JLabel("Enter a server port:"));
        panel.add(Box.createRigidArea(spacing));
        port_textfield = new JTextField();
        panel.add(port_textfield);
        panel.add(Box.createRigidArea(spacing));
        panel.add(new JLabel("Enter a server IP address:"));
        panel.add(Box.createRigidArea(spacing));
        ip_textfield = new JTextField();
        panel.add(ip_textfield);
        connect_button = new JButton("Connect");
        connect_button.addActionListener((e) -> {
            try {
                connect_button.setText("Connecting");
                port = Integer.valueOf(port_textfield.getText());
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

    private void checkDenial(Boolean ACK){
        if(ACK == true){
            StartMessageGUI();
        }else if(ACK == false) {
            StartLocal();
        }
    }

    private void StartLocal(){
        try {
            new Thread(new Server()).start();
            Connect(InetAddress.getByName("127.0.0.1"));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void Connect(InetAddress ip) {
        try {
            socket = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(new Reader(socket,input,output,this)).start();
        } catch (IOException e){
            System.out.println("CLIENT:Connection failed, starting local server");
            StartLocal();
        }
    }

    private void StartMessageGUI(){
        MessagesGUI messagesGUI = new MessagesGUI(socket, input, output);
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
    }

    private class Reader implements Runnable {
        private BufferedReader input;
        private PrintWriter output;
        private Socket socket;
        private Boolean running;
        private ClientConnector clientConnector;

        public Reader(Socket s, BufferedReader in, PrintWriter out, ClientConnector cc) throws IOException {
            socket = s;
            input = in;
            output = out;
            running = true;
            clientConnector = cc;
        }

        @Override
        public void run(){
            System.out.println("CLIENT:Reader thread started");
            String reply;
            try {
                int count = 0;
                System.out.println("CLIENT:Starting loop");
                while(running){
                    System.out.println("CLIENT:Entered Loop");
                    if (count == 0){
                        output.write(user);
                        output.flush();
                    }
                    if((reply = input.readLine()) != null) {
                        if (reply.equals("ACK")) {
                            System.out.println("CLIENT:Ack received");
                            clientConnector.checkDenial(true);
                            running = false;
                        } else if (reply.equals("DENY")) {
                            System.out.println("CLIENT:Deny received");
                            clientConnector.checkDenial(false);
                            running = false;
                        }
                    }
                    count++;
                }

                System.out.println("CLIENT:Exited Loop");
            } catch (IOException e){
                System.out.println("Unable to connect");
            }
        }
    }
}
