package fractal_flame.gui;

import fractal_flame.spring.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class InfoPanel extends JPanel {
    private final JTextPane infolabel = new JTextPane();
    private Image backgroundImage;

    @Autowired
    public InfoPanel(BackgroundService backgroundService) {
        String text = "• Normal mode: it's just a mode where the program works\n" +
                "as it's supposed to.\n" +
                "• Cosmic mode: to be honest, this mode can hardly be\n" +
                "called \"cosmic\". I just noticed the program in this mode\n" +
                "can generate structures that look like a galaxy. So that's\n" +
                "the main reason why I decided to give it this name.\n" +
                "Generally this mode can't generate anything really\n" +
                "beautiful.\n" +
                "• Zendala mode: this mode is called exactly like that\n" +
                "because it can generate really amazing images similar to\n" +
                "zendalas. It's funny, but I found this mode accidentally\n" +
                "when I had tried to change a variable value from 0 to 1.\n" +
                "Here is piece of advice: use fewer affine\n" +
                "transformations and iterations when generating images\n" +
                "in this mode, otherwise they will be overloaded by\n" +
                "colored pixels.";
        infolabel.setText(text);
        infolabel.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infolabel);

        backgroundImage = backgroundService.getBackgroundImage();
        add(scrollPane);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
