import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class MemoryPanel extends JPanel {
    public MemoryPanel() {
        Dimension size = getPreferredSize();
        size.width = 200;
        size.height = 400;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("MemoryPanel"));
    }
}
