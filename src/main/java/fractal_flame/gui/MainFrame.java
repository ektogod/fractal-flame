package fractal_flame.gui;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Getter
@Component
public class MainFrame extends JFrame {
    @Autowired
    private TransformationsPanel transformationsPanel;

    @Autowired
    private SettingsPanel settingsPanel;

    @Autowired
    private InfoPanel infoPanel;

    private final static int WIDTH = 450;
    private final static int HEIGHT = 450;

    @PostConstruct
    public void init() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JTabbedPane settingsPanels = new JTabbedPane();
                settingsPanels.add("image settings", settingsPanel);
                settingsPanels.add("transformation settings", transformationsPanel);
                settingsPanels.add("some info", infoPanel);

                add(settingsPanels);

                return null;
            }

            @Override
            protected void done() {
                setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(WIDTH, HEIGHT);

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                setLocation((screenSize.width - WIDTH) / 2, (screenSize.height - HEIGHT) / 3);
            }
        };

        worker.execute();
    }


    public MainFrame() throws IOException {
    }
}
