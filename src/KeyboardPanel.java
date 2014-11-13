import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class KeyboardPanel extends JPanel {
    public KeyboardPanel() {
        setBorder(BorderFactory.createTitledBorder("KeyboardPanel"));

        // Layout manager
        setLayout(new GridLayout(4, 4, 2, 2));

        // Create components
        String keyLabels[] = {
                "7", "8", "9", "A",
                "4", "5", "6", "B",
                "1", "2", "3", "C",
                "0", "D", "E", "F"};
        JButton keys[] = new JButton[keyLabels.length];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new JButton(keyLabels[i]);
        }

        // Add components
        for (JButton key : keys) {
            add(key);
        }
    }
}
