package StopWatch;

import javax.swing.*;
import java.awt.*;


public class TestStopWatch {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Collage");
        StopWatchLabel swLabel = new StopWatchLabel();
        frame.add(swLabel);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2,
                (screenSize.height - frame.getHeight()) / 2);
        frame.setVisible(true);
    }
}
