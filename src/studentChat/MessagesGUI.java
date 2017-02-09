package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

/**
 * Created by Nate on 1/30/2017.
 * Displays chat messages between students in the group
 */
public class MessagesGUI extends JDialog {
    private static final long serialVersionUID = 1545492493375167694L;

    private JTextArea messages;
    private JTextArea sendTxt;

    /**
     * Displays chat window of selected group
     * Populates the chat box with previous messages
     * @param sGroup Group name
     * @param group Group object
     */
    public MessagesGUI(String sGroup, Group group){
        String groupMessages = "";
        for (int i =0; i < group.getStudent1().getResponses().size();i++) {
            groupMessages += String.format("%s\n",group.getStudent1().getResponses().get(i));
            groupMessages += String.format("%s\n",group.getStudent2().getResponses().get(i));
        }

        JPanel panel = new JPanel();
        messages = new JTextArea(10, 40);
        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        messages.setText(groupMessages);
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
        cancel.addActionListener((e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING))));
        panel.add(cancel);

        setSize(new Dimension(500,300));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(sGroup + " Chat");
    }

    /**
     * Updates the message box to contain typed text
     * Erases the send box
     */
    private void sendMessage() {
        messages.setText(messages.getText() + "\n" + sendTxt.getText());
        sendTxt.setText("");
    }
}
