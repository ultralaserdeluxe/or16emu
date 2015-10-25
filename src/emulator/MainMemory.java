package emulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2014-10-17.
 */
public class MainMemory implements Memory {
    private final int[] memory;
    private final List<Object> observers;

    public MainMemory(int size) {
        memory = new int[size];
        observers = new ArrayList<Object>();
    }

    @Override
    public int read(int address) {
        return memory[address];
    }

    @Override
    public void write(int address, int value) {
        final int memoryMask = 0xFFFF;
        memory[address] = value & memoryMask;
        notifyObservers();
    }

    public void reset() {
        for (int i = 0; i < memory.length; i++) memory[i] = 0;
    }

    @Override
    public int size() {
        return memory.length;
    }

    @Override
    public void addObserver(Object o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (Object o : observers) {
            ((IObserver) o).notifyObserver();
        }
    }
}
