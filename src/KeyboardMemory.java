/**
 * Created by Alexander on 2015-01-31.
 */
public class KeyboardMemory implements Memory {
    private int character;

    @Override
    public int read(int address) {
        int value = character;
        character = 0xFFFF;
        return character;
    }

    @Override
    public void write(int address, int value) {
        if (address == 0) {
            character = value;
        }
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public void addObserver(Object o) {

    }
}
