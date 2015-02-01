import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by Alexander on 2014-11-13.
 */
public class ControlPanel extends JPanel implements ObserverInterface {
    private final OR16 cpu;
    private HashMap<String, String> state;
    private HashMap<String, JLabel> values;
    private List<JLabel> labels;

    public ControlPanel(final OR16 cpu) {
        this.cpu = cpu;

        setBorder(BorderFactory.createTitledBorder("ControlPanel"));

        // Layout manager
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Get state information to display
        state = cpu.getState();
        int nfields = state.size();

        // Create components
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(nfields, 2, 2, 0));

        values = new HashMap<String, JLabel>();
        labels = new ArrayList<JLabel>();
        for (Map.Entry<String, String> entry : state.entrySet()) {
            JLabel label = new JLabel(entry.getKey() + ":", JLabel.RIGHT);
            JLabel value = new JLabel(entry.getValue(), JLabel.LEFT);
            labels.add(label);
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
                    cpu.run();
                } else if (e.getSource() == stopButton) {
                    // TODO: Implement stopbutton
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
