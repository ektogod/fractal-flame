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
    private TransformationsPanel transformationsPanel;
    private SettingsPanel settingsPanel;
    private InfoPanel infoPanel;
    private AutoSavePanel autoSavePanel;

    private final static int WIDTH = 450;
    private final static int HEIGHT = 450;

    @Autowired
    public MainFrame(TransformationsPanel transformationsPanel, SettingsPanel settingsPanel, InfoPanel infoPanel, AutoSavePanel autoSavePanel){
        this.transformationsPanel = transformationsPanel;
        this.settingsPanel = settingsPanel;
        this.infoPanel = infoPanel;
        this.autoSavePanel = autoSavePanel;
    }

    @PostConstruct
    public void init() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JTabbedPane settingsPanels = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
                settingsPanels.add("image settings", settingsPanel);
                settingsPanels.add("transformation settings", transformationsPanel);
                settingsPanels.add("some info", infoPanel);
                settingsPanels.add("Auto save settings", autoSavePanel);

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
}
