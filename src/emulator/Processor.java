package emulator;

import java.util.Map;

/**
 * Created by Alexander on 2014-10-17.
 */
public interface Processor extends IObservable
{
    void tick();

    void reset();

    boolean isHalted();

    Map<String, String> getCPUState();
}
