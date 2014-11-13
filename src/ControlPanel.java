import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexander on 2014-11-13.
 */
public class ControlPanel extends JPanel {
    public ControlPanel() {
        setBorder(BorderFactory.createTitledBorder("ControlPanel"));

        // Layout manager
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Create components
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(6, 2, 2, 0));
        JLabel tickLabel = new JLabel("Ticks:", JLabel.RIGHT);
        JLabel ipReg = new JLabel("IP:", JLabel.RIGHT);
        JLabel pcReg = new JLabel("PC:", JLabel.RIGHT);
        JLabel xrReg = new JLabel("XR:", JLabel.RIGHT);
        JLabel spReg = new JLabel("SP:", JLabel.RIGHT);
        JLabel aReg = new JLabel("A:", JLabel.RIGHT);
        statusPanel.add(tickLabel);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));
        statusPanel.add(ipReg);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));
        statusPanel.add(pcReg);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));
        statusPanel.add(xrReg);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));
        statusPanel.add(spReg);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));
        statusPanel.add(aReg);
        statusPanel.add(new JLabel("#VALUE#", JLabel.LEFT));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton tickButton = new JButton("Tick");
        JButton runButton = new JButton("Run");
        JButton stopButton = new JButton("Stop");
        buttonPanel.add(tickButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);

        // Add components
        add(statusPanel);
        add(buttonPanel);
    }
}
