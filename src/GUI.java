import javax.swing.*;

/**
 * Created by Alexander on 2014-10-24.
 */
public class GUI implements Runnable {
    @Override
    public void run() {
        JFrame frame = new EmulatorFrame("or16emu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        GUI emuWindow = new GUI();
        SwingUtilities.invokeLater(emuWindow);
    }
}
