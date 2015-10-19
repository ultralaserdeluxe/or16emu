package gui;

import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexander on 2014-11-13.
 */
class KeyboardPanel extends JPanel {
    private final JButton[] keys;

    KeyboardPanel(final Memory memory) {

        setBorder(BorderFactory.createTitledBorder("KeyboardPanel"));

        // Layout manager
        setLayout(new GridLayout(4, 4, 2, 2));

        // Create components
        String[] keyLabels = { "7", "8", "9", "A", "4", "5", "6", "B", "1", "2", "3", "C", "0", "D", "E", "F" };
        keys = new JButton[keyLabels.length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new JButton(keyLabels[i]);
        }

        // Add components
        for (JButton key : keys) {
            add(key);
        }

        // Add actions to buttons
        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JButton key : keys) {
                    if (e.getSource().equals(key)) {
                        final int base = 16;
                        memory.write(0, Integer.parseInt(key.getText(), base));
                    }
                }
            }
        };

        for (JButton key : keys) {
            key.addActionListener(al);
        }

    }
}
