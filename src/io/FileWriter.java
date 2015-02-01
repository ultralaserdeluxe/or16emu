package io;

import emulator.Memory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alexander on 2015-02-01.
 */
public class FileWriter {
    private final Memory memory;
    private final JFileChooser fileChooser;
    private final int size;

    public FileWriter(Memory memory, int size) {
        this.memory = memory;
        this.size = size;
        this.fileChooser = new JFileChooser();
    }

    public void writeMemoryToFile(File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);

            for (int i = 0; i < size; i++) {
                out.write(memory.read(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int showSaveDialog(Container c) {
        return fileChooser.showSaveDialog(c);
    }

    public File getSelectedFile() {
        return fileChooser.getSelectedFile();
    }
}
