package io;

import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by Alexander on 2015-02-01.
 */
public class FileReader {
    private Memory memory;
    private JFileChooser fileChooser;

    public FileReader(Memory memory) {
        this.memory = memory;
        this.fileChooser = new JFileChooser();
    }

    public void readFileToMemory(File file) {
        try {
            InputStream in = new FileInputStream(file);
            int i = 0;
            while (true) {
                int byte1 = in.read();
                int byte2 = in.read();
                if (byte1 != -1 && byte2 != -1) {
                    int value = (byte1 << 8) | byte2;
                    memory.write(i, value);
                    i++;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int showOpenDialog(Container c) {
        return fileChooser.showOpenDialog(c);
    }

    public File getSelectedFile() {
        return fileChooser.getSelectedFile();
    }
}
