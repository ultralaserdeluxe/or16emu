package gui;

import io.FileReader;
import io.FileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Alexander on 2014-11-13.
 */
class EmulatorFrame extends JFrame {
    EmulatorFrame(String title, JPanel ctrlPanel, JPanel memPanel, JPanel keyPanel, JPanel displayPanel,
                  final FileReader fileReader, final FileWriter fileWriter)
    {
        super(title);

        final Container contentPane = getContentPane();

        // Layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Add components
        // First column
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 2;
        gc.fill = GridBagConstraints.BOTH;
        contentPane.add(memPanel, gc);

        // Second Column
        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        contentPane.add(displayPanel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.NONE;
        contentPane.add(ctrlPanel, gc);

        // Third column
        gc.gridx = 2;
        gc.gridy = 1;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        contentPane.add(keyPanel, gc);

        // Add menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        final JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(loadItem);
        final JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        final JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        setJMenuBar(menuBar);

        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(loadItem)) {
                    int returnVal = fileReader.showOpenDialog(contentPane);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileReader.getSelectedFile();
                        System.out.println("Loading file:" + file.getAbsolutePath());
                        fileReader.readFileToMemory(file);
                    }
                } else if (e.getSource().equals(saveItem)) {
                    int returnVal = fileWriter.showSaveDialog(contentPane);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileWriter.getSelectedFile();
                        System.out.println("Saving to file:" + file.getAbsolutePath());
                        fileWriter.writeMemoryToFile(file);
                    }
                } else if (e.getSource().equals(exitItem)) {
                    System.exit(0);
                }
            }
        };

        loadItem.addActionListener(al);
        saveItem.addActionListener(al);
        exitItem.addActionListener(al);

        pack();
    }
}
