package gui;

import emulator.*;
import io.FileReader;
import io.FileWriter;

import javax.swing.*;

/**
 * Created by Alexander on 2014-10-24.
 */
class GUI implements Runnable {
    @Override
    public void run() {
        // Create main memory
        final int mainMemorySize = 1000;
        Memory mainMemory = new MainMemory(mainMemorySize);

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

        // Create file reader and writer
        FileReader fileReader = new FileReader(memorySpace);
        FileWriter fileWriter = new FileWriter(memorySpace, mainMemorySize);

        // Create the main window
        String title = "or16emu";
        JFrame frame = new EmulatorFrame(title, ctrlPanel, memPanel, keyPanel, displayPanel, fileReader, fileWriter);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // To avoid bug with Gridlayout and JPanels (in gui.DisplayPanel.java)
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        GUI emuWindow = new GUI();
        SwingUtilities.invokeLater(emuWindow);
    }
}
