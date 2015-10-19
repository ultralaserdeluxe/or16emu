package gui;

import emulator.IObserver;
import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Alexander on 2014-11-13.
 */
public class MemoryPanel extends JPanel implements IObserver {
    private final DefaultListModel<String> listModel;
    private final Memory memory;

    public MemoryPanel(final Memory memory) {
        this.memory = memory;

        Dimension size = getPreferredSize();
        final int defaultWidth = 200;
        final int defaultHeight = 400;
        size.setSize(defaultWidth, defaultHeight);
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("MemoryPanel"));

        setLayout(new BorderLayout());

        // Create components
	listModel = new DefaultListModel<String>();
	final JList<String> memContents = new JList<String>(listModel);
	JScrollPane scrollPane = new JScrollPane(memContents);

        // Add components
        add(scrollPane, BorderLayout.CENTER);

        // Make memContents editable
        final JPanel parent = this;

        final MouseListener ma = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getSource().equals(memContents) && e.getClickCount() == 2) {
                    int index = memContents.getSelectedIndex();
		    String currentValue = listModel.getElementAt(index).split(":")[1].trim();
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
        notifyObserver();
    }

    @Override public void notifyObserver() {
        listModel.removeAllElements();
        for (int i = 0; i < memory.size(); i++) {
            listModel.add(i, i + ": " + memory.read(i));
        }
    }
}
