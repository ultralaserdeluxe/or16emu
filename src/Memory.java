/**
 * Created by Alexander on 2014-10-17.
 */
public interface Memory extends ObservableInterface {
    public int read(int address);

    public void write(int address, int value);

    public int size();
}
