import javax.swing.*;

/**
 * Created by Alexander on 2014-10-24.
 */
public class GUI implements Runnable {
    @Override
    public void run() {
        // Create some components.
        Memory mainMemory = new MainMemory(1000);

        int columns = 80;
        int rows = 40;
        Memory graphicsMemory = new MainMemory(columns * rows);

        Memory keyboardMemory = new KeyboardMemory();

        // Create a memory space and add components to it.
        MemorySpace memorySpace = new MemorySpace();
        memorySpace.addMemoryRegion(mainMemory);
        mainMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(keyboardMemory);
        keyboardMemory.addObserver(memorySpace);
        memorySpace.addMemoryRegion(graphicsMemory);
        graphicsMemory.addObserver(memorySpace);

        // Create a CPU and give it a memory space to work on.
        OR16 cpu = new OR16(memorySpace);

        JFrame frame = new EmulatorFrame("or16emu", cpu, memorySpace, keyboardMemory, graphicsMemory, columns, rows);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        // To avoid bug with Gridlayout and JPanels (in DisplayPanel.java)
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        GUI emuWindow = new GUI();
        SwingUtilities.invokeLater(emuWindow);
    }
}
