import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Alexander on 2014-11-13.
 */
public class EmulatorFrame extends JFrame {
    public EmulatorFrame(String title, OR16 cpu, final Memory memorySpace, Memory peripherals, Memory graphicsMemory, int columns, int rows) {
        super(title);

        final Container c = getContentPane();

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

        // Add menubar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        final JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(loadItem);
        final JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        setJMenuBar(menuBar);

        // Add menu actions
        final JFileChooser fc = new JFileChooser();

        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadItem) {
                    int returnVal = fc.showOpenDialog(c);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        System.out.println("Loading file:" + file.getAbsolutePath());
                        FileReader.readFileToMemory(file, memorySpace);
                    }
                } else if (e.getSource() == exitItem) {
                    System.exit(0);
                }
            }
        };

        loadItem.addActionListener(al);
        exitItem.addActionListener(al);

        pack();
    }
}
