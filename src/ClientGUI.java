import blackjack.message.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Nate on 3/22/2017.
 */
public class ClientGUI extends JDialog {
    private static final long serialVersionUID = 683679158276206671L;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private JTextArea messages;
    private JTextArea sendTxt;
    private String userName;

    public ClientGUI(ObjectInputStream in, ObjectOutputStream out,String uName){
        input = in;
        output = out;
        userName = uName;
    }

    public void StartWindow(){
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
            closeConnection();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        panel.add(cancel);

        JButton startGame = new JButton("Start Game");
        startGame.addActionListener((e -> {
            try {
                output.writeObject(MessageFactory.getStartMessage());
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }));
        panel.add(startGame);

        JTextField card = new JTextField(13);
        card.setEditable(false);
        panel.add(card);

        setSize(new Dimension(500,300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("BlackJack");
        new Thread(new ChatReader(input)).start();
    }

    private void sendMessage() {
        String toSend = sendTxt.getText();
        try {
            output.writeObject(MessageFactory.getChatMessage(toSend,userName));
            output.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("GUI:Message Sent - " + toSend);
        sendTxt.setText("");
    }

    private void updateMessage(ChatMessage msg){
        String user = msg.getUsername();
        String message = msg.getText();
        messages.append(user + ": " + message + "\r\n");
    }

    private void updateCard(CardMessage card){
        card.getCard();
    }

    private void closeConnection(){
        try {
            input.close();
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private class ChatReader implements Runnable {
        private ObjectInputStream input;
        private Boolean running;

        public ChatReader(ObjectInputStream is) {
            input = is;
            running = true;
        }

        @Override
        public void run() {
            System.out.println("GUI:Reader thread started");
            Message reply;
            try {
                while (running) {
                    reply = (Message)input.readObject();
                    if(reply.getType() == Message.MessageType.CHAT) {
                        System.out.println(reply.getType() + " : " + reply.toString());
                        updateMessage((ChatMessage)reply);
                    }else if(reply.getType() == Message.MessageType.ACK) {
                        System.out.println(reply.getType() + " : " + reply.toString());
                        joinGame();
                    }else if(reply.getType() == Message.MessageType.GAME_STATE){
                        System.out.println(((GameStateMessage)reply).getRequestedState());
                    }else{
                        System.out.println(reply.getType() + " : " + reply.toString());
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        private void joinGame(){
            try {
                output.writeObject(MessageFactory.getJoinMessage());
                output.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
