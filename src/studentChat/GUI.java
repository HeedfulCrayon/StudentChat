package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Nate on 1/30/2017.
 */
public class GUI extends JFrame {
    private static final long serialVersionUID = 0;

    public GUI(){
        JPanel panel = new JPanel();
        add(panel);
        JButton ok = new JButton("OK");
        ok.addActionListener((e) -> ok.setText("Cancel")); // Changes text after ok button is clicked
        panel.add(ok);

        JTextField text = new JTextField("this is a new textfield");
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.getText().equals("this is a new textfield")) {
                    text.setText("this is a label");
                }
                else {
                    text.setText("this is a new textfield");
                }
            }
        });
        panel.add(text);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("leaving program");
            }
        });


        setPreferredSize(new Dimension(300,300));

        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
