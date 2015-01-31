import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class EmulatorFrame extends JFrame {
    public EmulatorFrame(String title, OR16 cpu, Memory memorySpace, Memory peripherals, Memory graphicsMemory, int columns, int rows) {
        super(title);

        Container c = getContentPane();

        // Layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Create components
        JPanel memPanel = new MemoryPanel(memorySpace);
        memorySpace.addObserver(memPanel);
        JPanel displayPanel = new DisplayPanel(graphicsMemory, columns, rows);
        graphicsMemory.addObserver(displayPanel);
        JPanel ctrlPanel = new ControlPanel(cpu);
        cpu.addObserver(ctrlPanel);
        JPanel keyPanel = new KeyboardPanel(peripherals);

        // Add components
        // First column
        gc.weightx = gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        gc.fill = GridBagConstraints.BOTH;
        c.add(memPanel, gc);

        // Second Column
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        c.add(displayPanel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.NONE;
        c.add(ctrlPanel, gc);

        // Third column
        gc.gridx = 2;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        c.add(keyPanel, gc);

        pack();
    }
}
