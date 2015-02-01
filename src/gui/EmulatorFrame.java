package gui;

import io.FileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Alexander on 2014-11-13.
 */
public class EmulatorFrame extends JFrame {
    public EmulatorFrame(String title, JPanel ctrlPanel, JPanel memPanel, JPanel keyPanel, JPanel displayPanel, final FileReader fileReader) {
        super(title);

        final Container c = getContentPane();

        // Layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

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

        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadItem) {
                    int returnVal = fileReader.showOpenDialog(c);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileReader.getSelectedFile();
                        System.out.println("Loading file:" + file.getAbsolutePath());
                        fileReader.readFileToMemory(file);
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
