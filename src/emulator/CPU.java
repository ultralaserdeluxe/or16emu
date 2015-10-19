package emulator;

import java.util.HashMap;

/**
 * Created by Alexander on 2014-10-17.
 */
public interface CPU extends IObservable {
    void tick();

    void reset();

    boolean isHalted();

    HashMap<String, String> getCPUState();
}
