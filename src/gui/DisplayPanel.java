package gui;

import emulator.IObserver;
import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Alexander on 2014-11-13.
 */
public class DisplayPanel extends JPanel implements IObserver {
    private final ArrayList<JPanel> pixels;
    private final int columns;
    private final int rows;
    private final Memory memory;

    public DisplayPanel(Memory memory, int columns, int rows) {
        this.memory = memory;
        this.columns = columns;
        this.rows = rows;

        Dimension size = getPreferredSize();
        size.width = 400;
        size.height = 200;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("gui.DisplayPanel"));

        // Set layout
        GridLayout layout = new GridLayout(rows, columns);
        setLayout(layout);

        // Create components
        pixels = new ArrayList<JPanel>();

        for (int i = 0; i < columns * rows; i++) {
            JPanel pixel = new JPanel();
            pixel.setBackground(Color.BLACK);
            pixels.add(i, pixel);
            add(pixel);
        }

        hasChanged();
    }

    @Override
    public void hasChanged() {
        for (int i = 0; i < columns * rows; i++) {
            int red = (memory.read(i) & 0xE0);
            int green = (memory.read(i) & 0x1C) << 3;
            int blue = (memory.read(i) & 0x03) << 6;
            pixels.get(i).setBackground(new Color(red, green, blue));
        }
    }
}