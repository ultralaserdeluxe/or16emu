package emulator;

/**
 * Created by Alexander on 2015-02-01.
 */
public class CPURunner extends Thread {
    private final CPU cpu;
    private volatile boolean stopped = false;

    public CPURunner(CPU cpu) {
        this.cpu = cpu;
    }

    public void run() {
        while (!cpu.isHalted() && !stopped) {
            cpu.tick();
        }
    }

    public void stopExecution() {
        stopped = true;
    }
}
