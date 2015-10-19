package gui;

import emulator.Processor;
import emulator.ProcessorRunner;
import emulator.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Alexander on 2014-11-13.
 */
public class ControlPanel extends JPanel implements IObserver {
    private final Processor processor;
    private ProcessorRunner processorRunner = null;
    private Thread cpuThread = null;
    private boolean threadExecuting = false;
    private Map<String, String> state;
    private final Map<String, JLabel> values;

    public ControlPanel(final Processor processor) {
        this.processor = processor;

        setBorder(BorderFactory.createTitledBorder("ControlPanel"));

        // Layout manager
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Get state information to display
        state = processor.getCPUState();
        int nFields = state.size();

        // Create components
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(nFields, 2, 2, 0));

        values = new HashMap<String, JLabel>();
        for (Entry<String, String> entry : state.entrySet()) {
            JLabel label = new JLabel(entry.getKey() + ":", SwingConstants.RIGHT);
            JLabel value = new JLabel(entry.getValue(), SwingConstants.LEFT);
            values.put(entry.getKey(), value);
            statusPanel.add(label);
            statusPanel.add(value);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        final JButton tickButton = new JButton("Tick");
        final JButton runButton = new JButton("Run");
        final JButton stopButton = new JButton("Stop");
        final JButton resetButton = new JButton("Reset");
        buttonPanel.add(tickButton);
        buttonPanel.add(runButton);
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        // Add components
        add(statusPanel);
        add(buttonPanel);

        // Add actions to buttons
        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(tickButton)) {
                    processor.tick();
                } else if (e.getSource().equals(runButton)) {
                    if (!threadExecuting) {
                        processorRunner = new ProcessorRunner(processor);
                        cpuThread = new Thread(processorRunner);
                        cpuThread.start();
                        threadExecuting = true;
                        runButton.setEnabled(false);
                        stopButton.setEnabled(true);
                        resetButton.setEnabled(false);
                        tickButton.setEnabled(false);
                    }
                } else if (e.getSource().equals(stopButton)) {
                    if (threadExecuting) {
                        try {
                            processorRunner.stopExecution();
                            cpuThread.join();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        threadExecuting = false;
                        runButton.setEnabled(true);
                        stopButton.setEnabled(false);
                        resetButton.setEnabled(true);
                        tickButton.setEnabled(true);
                    }
                } else if (e.getSource().equals(resetButton)) {
                    processor.reset();
                }
            }
        };

        tickButton.addActionListener(al);
        runButton.addActionListener(al);
        stopButton.addActionListener(al);
        resetButton.addActionListener(al);

        notifyObserver();
    }

    @Override public void notifyObserver() {
        state = processor.getCPUState();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (final Entry<String, String> stringStringEntry : state.entrySet()) {
                    JLabel valueLabel = values.get(stringStringEntry.getKey());
                    String value = stringStringEntry.getValue();
                    valueLabel.setText(value);
                }
            }
        });
    }
}
