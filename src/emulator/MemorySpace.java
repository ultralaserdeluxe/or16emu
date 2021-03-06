package emulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2014-10-18.
 */
public class MemorySpace implements Memory, IObserver {
    private final List<Memory> memoryRegions;
    private final List<IObserver> observers;

    public MemorySpace() {
        observers = new ArrayList<IObserver>();
        memoryRegions = new ArrayList<Memory>();
    }

    @Override
    public int read(int address) {
        int cumulativeSize = 0;

        for (Memory memory : memoryRegions) {
            // Check if address is within bounds of current memory region.
            if (isAddressInBounds(address, cumulativeSize, memory.size())) {
                return memory.read(address - cumulativeSize);
            } else {
                cumulativeSize += memory.size();
            }
        }

        return 0;
    }

    @Override
    public void write(int address, int value) {
        int cumulativeSize = 0;

        for (Memory memory : memoryRegions) {
            //Check if address is within bound of current memory region.
            if (isAddressInBounds(address, cumulativeSize, memory.size())) {
                memory.write(address - cumulativeSize, value);
                return;
            } else {
                cumulativeSize += memory.size();
            }
        }
    }

    @Override public void reset() {
        for (Memory memory : memoryRegions) memory.reset();
    }

    private boolean isAddressInBounds(int address, int cumulativeSize, int memorySize) {
        return cumulativeSize <= address && address < cumulativeSize + memorySize;
    }

    public void addMemoryRegion(Memory memory) {
        memoryRegions.add(memory);
        memory.addObserver(this);
    }

    public int size() {
        int size = 0;

        for (Memory memory : memoryRegions) {
            size += memory.size();
        }

        return size;
    }

    @Override public void addObserver(IObserver o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (IObserver o : observers) {
            o.notifyObserver();
        }
    }

    @Override public void notifyObserver() {
        notifyObservers();
    }
}
