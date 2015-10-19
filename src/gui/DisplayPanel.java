package gui;

import emulator.IObserver;
import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Alexander on 2014-11-13.
 */
public class DisplayPanel extends JPanel implements IObserver {
    private final List<JPanel> pixels;
    private final int columns;
    private final int rows;
    private final Memory memory;

    public DisplayPanel(Memory memory, int columns, int rows) {
        this.memory = memory;
        this.columns = columns;
        this.rows = rows;

        Dimension size = getPreferredSize();
        final int defaultWidth = 400;
        final int defaultHeight = 200;
        size.setSize(defaultWidth, defaultHeight);
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("DisplayPanel"));

        // Set layout
        LayoutManager layout = new GridLayout(rows, columns);
        setLayout(layout);

        // Create components
        pixels = new ArrayList<JPanel>();

        for (int i = 0; i < columns * rows; i++) {
            JPanel pixel = new JPanel();
            pixel.setBackground(Color.BLACK);
            pixels.add(i, pixel);
            add(pixel);
        }

        notifyObserver();
    }

    @Override public void notifyObserver() {
        final int redMask = 0xE0;
        final int greenMask = 0x1C;
        final int blueMask = 0x03;
        for (int i = 0; i < columns * rows; i++) {
            int red = (memory.read(i) & redMask);
            int green = (memory.read(i) & greenMask) << 3;
            int blue = (memory.read(i) & blueMask) << 6;
            pixels.get(i).setBackground(new Color(red, green, blue));
        }
    }
}
