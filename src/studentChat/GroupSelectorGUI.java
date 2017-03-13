package studentChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by Nate on 2/6/2017.
 * Allows selection for which group to display
 */
public class GroupSelectorGUI extends JFrame {
    private static final long serialVersionUID = -8775987053801658369L;

    /**
     * Displays window with buttons to select which group to view
     * @param groups List of groups to display
     */
    public GroupSelectorGUI(List<studentChat.Group> groups) {
        Dimension spacing = new Dimension(10,10);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Please select the group you want to see:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(spacing));
        panel.add(label);
        // Creates the same number of buttons as there is groups
        // Adds actionListener to each button to launche MessagesGUI
//        for (int i = 1;i <= groups.size();i++)
//        {
//            JButton button = new JButton("Group " + i);
//            button.addActionListener((e) -> {
//                MessagesGUI messagesGui = new MessagesGUI(
//                        button.getText(),
//                        groups.get(Character.getNumericValue(button.getText().charAt(6)) - 1)
//                );
//                messagesGui.addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowOpened(WindowEvent e) {
//                        GroupSelectorGUI.super.setVisible(false);
//                    }
//
//                    @Override
//                    public void windowClosed(WindowEvent e) {
//                        GroupSelectorGUI.super.setVisible(true);
//                    }
//                });
//            });
//            button.setAlignmentX(Component.CENTER_ALIGNMENT);
//            panel.add(Box.createRigidArea(spacing));
//            panel.add(button);
//        }
        add(panel);

        setSize(new Dimension(300,300));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Group Selection");

    }
}
