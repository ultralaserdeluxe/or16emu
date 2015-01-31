import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2014-10-17.
 */
public class MainMemory implements Memory {
    private int[] memory;
    List<Object> observers;

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
        memory[address] = value;
        notifyObservers();
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
            ((ObserverInterface) o).hasChanged();
        }
    }
}
