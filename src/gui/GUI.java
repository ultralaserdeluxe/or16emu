package gui;

import emulator.*;
import io.FileReader;

import javax.swing.*;

/**
 * Created by Alexander on 2014-10-24.
 */
public class GUI implements Runnable {
    @Override
    public void run() {
        // Create main memory
        Memory mainMemory = new MainMemory(1000);

        // Create graphics memory
        int columns = 80;
        int rows = 40;
        Memory graphicsMemory = new MainMemory(columns * rows);

        // Create keyboard memory
        Memory keyboardMemory = new KeyboardMemory();

        // Create a memory space and add all memory regions to it
        MemorySpace memorySpace = new MemorySpace();
        memorySpace.addMemoryRegion(mainMemory);
        mainMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(keyboardMemory);
        keyboardMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(graphicsMemory);
        graphicsMemory.addObserver(memorySpace);

        // Create a emulator.CPU and give it a memory space to work on
        OR16 cpu = new OR16(memorySpace);

        // Create all panels
        JPanel memPanel = new MemoryPanel(memorySpace);
        memorySpace.addObserver(memPanel);

        JPanel displayPanel = new DisplayPanel(graphicsMemory, columns, rows);
        graphicsMemory.addObserver(displayPanel);

        JPanel ctrlPanel = new ControlPanel(cpu);
        cpu.addObserver(ctrlPanel);

        JPanel keyPanel = new KeyboardPanel(keyboardMemory);

        // Create file reader
        FileReader fileReader = new FileReader(memorySpace);

        // Create the main window
        JFrame frame = new EmulatorFrame("or16emu", ctrlPanel, memPanel, keyPanel, displayPanel, fileReader);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        // To avoid bug with Gridlayout and JPanels (in gui.DisplayPanel.java)
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        GUI emuWindow = new GUI();
        SwingUtilities.invokeLater(emuWindow);
    }
}
