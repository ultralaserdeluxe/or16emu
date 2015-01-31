import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class MemoryPanel extends JPanel implements ObserverInterface {
    private final DefaultListModel listModel;
    private Memory memory;

    public MemoryPanel(Memory memory) {
        this.memory = memory;

        Dimension size = getPreferredSize();
        size.width = 200;
        size.height = 400;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("MemoryPanel"));

        setLayout(new BorderLayout());

        // Create components
        listModel = new DefaultListModel();
        JList memContents = new JList(listModel);
        JScrollPane scrollpane = new JScrollPane(memContents);

        // Add components
        add(scrollpane, BorderLayout.CENTER);

        // Update components
        hasChanged();
    }

    @Override
    public void hasChanged() {
        listModel.removeAllElements();
        for (int i = 0; i < memory.size(); i++) {
            listModel.add(i, Integer.toString(i) + ": " + memory.read(i));
        }
    }
}
