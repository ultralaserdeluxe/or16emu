package emulator;

import java.util.HashMap;

/**
 * Created by Alexander on 2014-10-17.
 */
public interface CPU extends IObservable {
    public void tick();

    public void reset();

    public boolean isHalted();

    public HashMap<String, String> getCPUState();
}
