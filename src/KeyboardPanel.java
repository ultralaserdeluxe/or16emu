import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexander on 2014-11-13.
 */
public class KeyboardPanel extends JPanel {
    JButton keys[];
    Memory memory;

    public KeyboardPanel(final Memory memory) {
        this.memory = memory;

        setBorder(BorderFactory.createTitledBorder("KeyboardPanel"));

        // Layout manager
        setLayout(new GridLayout(4, 4, 2, 2));

        // Create components
        String keyLabels[] = {
                "7", "8", "9", "A",
                "4", "5", "6", "B",
                "1", "2", "3", "C",
                "0", "D", "E", "F"};
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
                    if (e.getSource() == key) {
                        memory.write(0, keyToInt(key.getText()));
                    }
                }
            }
        };

        for (JButton key : keys) {
            key.addActionListener(al);
        }

    }

    private int keyToInt(String keyLabel) {
        if (keyLabel.equals("0")) {
            return 0;
        } else if (keyLabel.equals("1")) {
            return 1;
        } else if (keyLabel.equals("2")) {
            return 2;
        } else if (keyLabel.equals("3")) {
            return 3;
        } else if (keyLabel.equals("4")) {
            return 4;
        } else if (keyLabel.equals("5")) {
            return 5;
        } else if (keyLabel.equals("6")) {
            return 6;
        } else if (keyLabel.equals("7")) {
            return 7;
        } else if (keyLabel.equals("8")) {
            return 8;
        } else if (keyLabel.equals("9")) {
            return 9;
        } else if (keyLabel.equals("A")) {
            return 10;
        } else if (keyLabel.equals("B")) {
            return 11;
        } else if (keyLabel.equals("C")) {
            return 12;
        } else if (keyLabel.equals("D")) {
            return 13;
        } else if (keyLabel.equals("E")) {
            return 14;
        } else if (keyLabel.equals("F")) {
            return 15;
        } else {
            return 0xFFFF;
        }
    }
}
