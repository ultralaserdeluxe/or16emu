import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class DisplayPanel extends JPanel {
    public DisplayPanel() {
        Dimension size = getPreferredSize();
        size.width = 400;
        size.height = 200;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("DisplayPanel"));
    }
}
