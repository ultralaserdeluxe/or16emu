package emulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2014-10-17.
 */
public class MainMemory implements Memory {
    private final int[] memory;
    private final List<IObserver> observers;

    public MainMemory(int size) {
        memory = new int[size];
        observers = new ArrayList<IObserver>();
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

    @Override public void addObserver(IObserver o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (IObserver o : observers) {
            o.notifyObserver();
        }
    }
}
