package emulator;

/**
 * Created by Alexander on 2015-02-01.
 */
public class ProcessorRunner implements Runnable
{
    private final Processor processor;
    private volatile boolean stopped = false;

    public ProcessorRunner(Processor processor) {
	this.processor = processor;
    }

    public void run() {
	while (!processor.isHalted() && !stopped) {
	    processor.tick();
	}
    }

    public void stopExecution() {
	stopped = true;
    }
}
