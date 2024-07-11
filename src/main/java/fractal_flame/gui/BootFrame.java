package fractal_flame.gui;

import fractal_flame.spring.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class BootFrame extends JFrame {
    private JLabel notification;
    private Timer timer;
    private int index = 0;

    private final static int WIDTH = 175;
    private final static int HEIGHT = 125;

    private String firstVar = new String("Image generation     ");
    private String secondVar = new String("Image generation.    ");
    private String thirdVar = new String("Image generation..   ");
    private String fourthVar = new String("Image generation...  ");


    public BootFrame(BackgroundService backgroundService, Point topLeftCorner, int width, int height) throws HeadlessException {
        setBackgroundImage(backgroundService);
        notification.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //setLayout(new BorderLayout());
        add(notification);
        setSize(WIDTH, HEIGHT);

        int x = topLeftCorner.x + (width - this.getWidth()) / 2;
        int y = topLeftCorner.y + (height - this.getHeight()) / 2;

        setVisible(true);
        setLocation(x, y);
    }

    public void startAnimation() {
        String[] texts = new String[]{firstVar, secondVar, thirdVar, fourthVar};
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notification.setText(texts[index % texts.length]);
                ++index;
            }
        }, 0, 400);
    }

    public void endAnimation() {
        timer.cancel();
    }

    private void setBackgroundImage(BackgroundService backgroundService) {
        notification = new JLabel(firstVar, new ImageIcon(backgroundService.getBackgroundImage()), JLabel.CENTER);

        notification.setHorizontalTextPosition(JLabel.CENTER);
        notification.setVerticalTextPosition(JLabel.CENTER);
        notification.setHorizontalAlignment(JLabel.CENTER);
        notification.setVerticalAlignment(JLabel.CENTER);
    }
}
