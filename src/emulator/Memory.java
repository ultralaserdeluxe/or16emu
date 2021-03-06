package emulator;

/**
 * Created by Alexander on 2014-10-17.
 */
public interface Memory extends IObservable {
    int read(int address);

    void write(int address, int value);

    void reset();

    int size();
}
