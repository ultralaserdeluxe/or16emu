import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2015-01-31.
 */
public class KeyboardMemory implements Memory {
    private int character;
    List<Object> observers;

    public KeyboardMemory() {
        observers = new ArrayList<Object>();
    }

    @Override
    public int read(int address) {
        return character;
    }

    @Override
    public void write(int address, int value) {
        if (address == 0) {
            character = value;
            notifyObservers();
        }
    }

    @Override
    public int size() {
        return 1;
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
