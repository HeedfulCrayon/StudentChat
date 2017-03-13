package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Nate on 1/30/2017.
 * Displays chat messages between students in the group
 */
public class MessagesGUI extends JDialog {//implements Runnable {
    private static final long serialVersionUID = 1545492493375167694L;

    private InetAddress ip;
    private Socket socket;
    private int port = 8080;
    private String user = "Nate";
    protected BufferedReader input;
    protected PrintWriter output;
    protected Boolean connected;
    protected Boolean handshake;
    private JTextArea messages;
    private JTextArea sendTxt;

    /**
     * Displays chat window of selected group
     * Populates the chat box with previous messages
     */
    public MessagesGUI(Socket s,BufferedReader in,PrintWriter out){
        String groupMessages = "";
        socket = s;
        input = in;
        output = out;
    }

    public void Start(){
        System.out.println("GUI:Starting reader thread");
        new Thread(new Reader(input)).start();
        JPanel panel = new JPanel();
        messages = new JTextArea(10, 40);
        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        messages.setText("");
        messages.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(messages);
        panel.add(messageScrollPane);

        sendTxt = new JTextArea(4,20);
        sendTxt.setLineWrap(true);
        sendTxt.setWrapStyleWord(true);
        sendTxt.setEditable(true);
        sendTxt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int codeKey = e.getKeyCode();
                int modifierKey = e.getModifiers();
                if (codeKey == KeyEvent.VK_ENTER && modifierKey == KeyEvent.CTRL_MASK){
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        JScrollPane sendScrollPane = new JScrollPane(sendTxt);
        panel.add(sendScrollPane);
        add(panel);

        JButton send = new JButton("Send");
        send.addActionListener((e) -> {
            sendMessage();
        });
        panel.add(send);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((e) -> {
            output.write("quit\r\n");
            output.flush();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(cancel);

        setSize(new Dimension(500,300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(socket.getInetAddress().getHostName() + " Chat");
    }

    /**
     * Updates the message box to contain typed text
     * Erases the send box
     */
    private void sendMessage() {
        String toSend = sendTxt.getText();
        output.write(toSend + "\r\n");
        output.flush();
        System.out.println("GUI:Message Sent - " + toSend);
        sendTxt.setText("");
    }

    private class Reader implements Runnable {
        private BufferedReader input;
        private Boolean running;

        public Reader(BufferedReader is) {
            input = is;
            running = true;
        }

        @Override
        public void run() {
            System.out.println("GUI:Reader thread started");
            String reply = "";
            try {
                while (running) {
                    if((reply = input.readLine()) != null) {
                        System.out.println("GUI: Reply received: " + reply);
                        if (reply.equals("quit")) {
                            running = false;
                        }else {
                            messages.append(reply + "\r\n");
                        }
                    }
                }
            } catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

//    public Boolean getConnectionStatus(){
//        return connected;
//    }
//
//    public Boolean Connect(InetAddress ip) throws IOException {
//        socket = new Socket(ip, port);
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(),true);
//
//        new Thread(new Reader(in)).start();
//        out.println(user);
//        out.flush();
//        return connected;
//    }
//
//    private class Reader implements Runnable {
//        private BufferedReader input;
//
//        public Reader(BufferedReader is) {
//            input = is;
//        }
//
//        @Override
//        public void run() {
//            String reply;
//            Boolean replyRec = false;
//            connected = false;
//            try {
//                while((!replyRec) && (!connected)){
//                    System.out.println("Test before");
//                    reply = input.readLine();
//                    if (reply.equals("ACK")) {
//                        System.out.println("Ack received");
//                        replyRec = true;
//                        connected = true;
//                    } else {
//                        replyRec = true;
//                        connected = false;
//                    }
//                    System.out.println("Test after");
//                }
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
