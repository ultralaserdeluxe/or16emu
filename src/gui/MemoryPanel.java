package gui;

import emulator.IObserver;
import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Alexander on 2014-11-13.
 */
public class MemoryPanel extends JPanel implements IObserver {
    private final DefaultListModel listModel;
    private final Memory memory;

    public MemoryPanel(final Memory memory) {
        this.memory = memory;

        Dimension size = getPreferredSize();
        size.width = 200;
        size.height = 400;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("MemoryPanel"));

        setLayout(new BorderLayout());

        // Create components
        listModel = new DefaultListModel();
        final JList memContents = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(memContents);

        // Add components
        add(scrollPane, BorderLayout.CENTER);

        // Make memContents editable
        final JPanel parent = this;

        final MouseAdapter ma = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == memContents && e.getClickCount() == 2) {
                    int index = memContents.getSelectedIndex();
                    String currentValue = ((String) listModel.getElementAt(index)).split(":")[1].trim();
                    String newValue = JOptionPane.showInputDialog(parent, "New value for memory cell at address " + index + ":", currentValue);
                    try {
                        memory.write(index, Integer.parseInt(newValue));
                        listModel.removeElementAt(index);
                        listModel.insertElementAt(index + ": " + newValue, index);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                }
            }
        };

        memContents.addMouseListener(ma);

        // Update components
        hasChanged();
    }

    @Override
    public void hasChanged() {
        listModel.removeAllElements();
        for (int i = 0; i < memory.size(); i++) {
            listModel.add(i, i + ": " + memory.read(i));
        }
    }
}
