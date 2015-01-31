import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexander on 2014-11-13.
 */
public class ControlPanel extends JPanel implements ObserverInterface {
    private JLabel tickValue;
    private JLabel ipVal;
    private JLabel pcVal;
    private JLabel xrVal;
    private JLabel spVal;
    private JLabel aVal;
    private JLabel srVal;
    private final OR16 cpu;

    public ControlPanel(final OR16 cpu) {
        this.cpu = cpu;

        setBorder(BorderFactory.createTitledBorder("ControlPanel"));

        // Layout manager
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Create components
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(7, 2, 2, 0));
        JLabel tickLabel = new JLabel("Ticks:", JLabel.RIGHT);
        JLabel ipReg = new JLabel("IP:", JLabel.RIGHT);
        JLabel pcReg = new JLabel("PC:", JLabel.RIGHT);
        JLabel xrReg = new JLabel("XR:", JLabel.RIGHT);
        JLabel spReg = new JLabel("SP:", JLabel.RIGHT);
        JLabel aReg = new JLabel("A:", JLabel.RIGHT);
        JLabel srReg = new JLabel("SR:", JLabel.RIGHT);
        tickValue = new JLabel("#VALUE#", JLabel.LEFT);
        ipVal = new JLabel("#VALUE#", JLabel.LEFT);
        pcVal = new JLabel("#VALUE#", JLabel.LEFT);
        xrVal = new JLabel("#VALUE#", JLabel.LEFT);
        spVal = new JLabel("#VALUE#", JLabel.LEFT);
        aVal = new JLabel("#VALUE#", JLabel.LEFT);
        srVal = new JLabel("#VALUE#", JLabel.LEFT);
        statusPanel.add(tickLabel);
        statusPanel.add(tickValue);
        statusPanel.add(ipReg);
        statusPanel.add(ipVal);
        statusPanel.add(pcReg);
        statusPanel.add(pcVal);
        statusPanel.add(xrReg);
        statusPanel.add(xrVal);
        statusPanel.add(spReg);
        statusPanel.add(spVal);
        statusPanel.add(aReg);
        statusPanel.add(aVal);
        statusPanel.add(srReg);
        statusPanel.add(srVal);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        final JButton tickButton = new JButton("Tick");
        final JButton runButton = new JButton("Run");
        final JButton stopButton = new JButton("Stop");
        buttonPanel.add(tickButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);

        // Add components
        add(statusPanel);
        add(buttonPanel);

        // Add actions to buttons
        final ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tickButton) {
                    cpu.tick();
                } else if (e.getSource() == runButton) {

                } else if (e.getSource() == stopButton) {

                }
            }
        };

        tickButton.addActionListener(al);
        runButton.addActionListener(al);
        stopButton.addActionListener(al);

        hasChanged();
    }

    @Override
    public void hasChanged() {
        tickValue.setText(Integer.toString(cpu.getTicks()));
        ipVal.setText(Integer.toString(cpu.getIp()));
        pcVal.setText(Integer.toString(cpu.getPc()));
        xrVal.setText(Integer.toString(cpu.getXr()));
        spVal.setText(Integer.toString(cpu.getSp()));
        aVal.setText(Integer.toString(cpu.getAcc()));
        srVal.setText(Integer.toString(cpu.getSr()));
    }
}
