/**
 * Created by Alexander on 2014-10-17.
 */
public class MainMemory implements Memory {
    private int[] memory;

    public MainMemory(int size) {
        memory = new int[size];
    }

    @Override
    public int read(int address) {
        return memory[address];
    }

    @Override
    public void write(int address, int value) {
        memory[address] = value;
    }

    @Override
    public int size() {
        return memory.length;
    }
}
