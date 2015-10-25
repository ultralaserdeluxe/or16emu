package gui;

import emulator.*;
import io.FileReader;
import io.FileWriter;

import javax.swing.*;

/**
 * Created by Alexander on 2014-10-24.
 */
class GUI implements Runnable {
    private FileReader fileReader = null;
    private FileWriter fileWriter = null;
    private MemorySpace memorySpace = null;
    private Memory graphicsMemory = null;
    private Memory keyboardMemory = null;
    private OR16 cpu = null;
    private static final int COLUMNS = 80;
    private static final int ROWS = 40;
    private static final int MAIN_MEMORY_SIZE = 1000;

    @Override
    public void run() {
        // Create computer
        createComputer();

        // Create all panels
        MemoryPanel memPanel = new MemoryPanel(memorySpace);
        memorySpace.addObserver(memPanel);

        DisplayPanel displayPanel = new DisplayPanel(graphicsMemory, COLUMNS, ROWS);
        graphicsMemory.addObserver(displayPanel);

        ControlPanel ctrlPanel = new ControlPanel(cpu);
        cpu.addObserver(ctrlPanel);

        KeyboardPanel keyPanel = new KeyboardPanel(keyboardMemory);

        // Create the file reader and writer
        createFileHandlers(memorySpace, MAIN_MEMORY_SIZE);

        // Create the main window
        String title = "or16emu";
        JFrame frame = new EmulatorFrame(title, ctrlPanel, memPanel, keyPanel, displayPanel, fileReader, fileWriter);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void createComputer() {
        // Create main memory
        Memory mainMemory = new MainMemory(MAIN_MEMORY_SIZE);

        // Create graphics memory
        graphicsMemory = new MainMemory(COLUMNS * ROWS);

        // Create keyboard memory
        keyboardMemory = new KeyboardMemory();

        // Create a memory space and add all memory regions to it
        memorySpace = new MemorySpace();
        memorySpace.addMemoryRegion(mainMemory);
        mainMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(keyboardMemory);
        keyboardMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(graphicsMemory);
        graphicsMemory.addObserver(memorySpace);

        // Create a emulator.Processor and give it a memory space to work on
        cpu = new OR16(memorySpace);
    }

    public void createFileHandlers(MemorySpace memorySpace, int mainMemorySize) {
        fileReader = new FileReader(memorySpace);
        fileWriter = new FileWriter(memorySpace, mainMemorySize);
    }

    public static void main(String[] args) {
        // To avoid bug with Gridlayout and JPanels (in gui.DisplayPanel.java)
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        Runnable emuWindow = new GUI();
        SwingUtilities.invokeLater(emuWindow);
    }
}
