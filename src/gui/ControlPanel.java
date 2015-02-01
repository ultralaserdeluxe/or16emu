package gui;

import emulator.CPU;
import emulator.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 2014-11-13.
 */
public class ControlPanel extends JPanel implements IObserver {
    private final CPU cpu;
    private HashMap<String, String> state;
    private final HashMap<String, JLabel> values;

    public ControlPanel(final CPU cpu) {
        this.cpu = cpu;

        setBorder(BorderFactory.createTitledBorder("gui.ControlPanel"));

        // Layout manager
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Get state information to display
        state = cpu.getState();
        int nFields = state.size();

        // Create components
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(nFields, 2, 2, 0));

        values = new HashMap<String, JLabel>();
        for (Map.Entry<String, String> entry : state.entrySet()) {
            JLabel label = new JLabel(entry.getKey() + ":", JLabel.RIGHT);
            JLabel value = new JLabel(entry.getValue(), JLabel.LEFT);
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
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        // Add components
        add(statusPanel);
        add(buttonPanel);

        // Add actions to buttons
        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tickButton) {
                    cpu.tick();
                } else if (e.getSource() == runButton) {
                    // TODO: Implement runButton
                    //cpu.run();
                } else if (e.getSource() == stopButton) {
                    // TODO: Implement stopButton
                } else if (e.getSource() == resetButton) {
                    cpu.reset();
                }
            }
        };

        tickButton.addActionListener(al);
        runButton.addActionListener(al);
        stopButton.addActionListener(al);
        resetButton.addActionListener(al);

        hasChanged();
    }

    @Override
    public void hasChanged() {
        state = cpu.getState();

        for (String label : state.keySet()) {
            JLabel valueLabel = values.get(label);
            String value = state.get(label);
            valueLabel.setText(value);
        }
    }
}
