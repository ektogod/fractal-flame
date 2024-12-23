package fractal_flame.gui;

import fractal_flame.spring.AppService;
import fractal_flame.spring.BackgroundService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.BoxLayout.*;

@Getter
@Component
public class TransformationsPanel extends JPanel {
    private JCheckBox sinusCheckbox = new JCheckBox("Sinus transformation");
    private JCheckBox sphereCheckbox = new JCheckBox("Spherical transformation");
    private JCheckBox polarCheckbox = new JCheckBox("Polar transformation");
    private JCheckBox heartCheckbox = new JCheckBox("Heart");
    private JCheckBox diskCheckbox = new JCheckBox("Disk");
    private JCheckBox hyperbolicCheckbox = new JCheckBox("Hyperbolic transformation");
    private JCheckBox logarithmicCheckBox = new JCheckBox("Logarithmic transformation");
    private JCheckBox spiralCheckBox = new JCheckBox("Spiral transformation");
    private JCheckBox wavesCheckBox = new JCheckBox("Waves transformation");
    private JCheckBox mobiusCheckBox = new JCheckBox("Mobius transformation");
    private JCheckBox collatzCheckBox = new JCheckBox("Collatz transformation");

    private Image backgroundImage;
    private AppService service;

    @Autowired
    public TransformationsPanel(AppService appService, BackgroundService backgroundService) throws IOException {
        this.service = appService;
        setLayout(new BoxLayout(this, Y_AXIS));

        backgroundImage = backgroundService.getBackgroundImage();

        initComponents();
        addListeners();
    }

    private void initComponents(){
        sinusCheckbox.setSelected(false);
        service.setSinusFlag(false);

        sphereCheckbox.setSelected(true);
        service.setSphereFlag(true);

        polarCheckbox.setSelected(false);
        service.setPolarFlag(false);

        heartCheckbox.setSelected(true);
        service.setHeartFlag(true);

        diskCheckbox.setSelected(true);
        service.setDiskFlag(true);

        hyperbolicCheckbox.setSelected(true);
        service.setHyperbolicFlag(true);

        logarithmicCheckBox.setSelected(false);
        service.setLogarithmicFlag(false);

        spiralCheckBox.setSelected(true);
        service.setSpiralFlag(true);

        wavesCheckBox.setSelected(false);
        service.setWavesFlag(false);

        mobiusCheckBox.setSelected(false);
        service.setMobiusFlag(false);

        collatzCheckBox.setSelected(false);
        service.setCollatzFlag(false);

        sinusCheckbox.setOpaque(false);
        sphereCheckbox.setOpaque(false);
        polarCheckbox.setOpaque(false);
        heartCheckbox.setOpaque(false);
        diskCheckbox.setOpaque(false);
        hyperbolicCheckbox.setOpaque(false);
        logarithmicCheckBox.setOpaque(false);
        spiralCheckBox.setOpaque(false);
        wavesCheckBox.setOpaque(false);
        mobiusCheckBox.setOpaque(false);
        collatzCheckBox.setOpaque(false);

        setAlignmentY(CENTER_ALIGNMENT);
        add(sinusCheckbox);
        add(sphereCheckbox);
        add(polarCheckbox);
        add(heartCheckbox);
        add(diskCheckbox);
        add(hyperbolicCheckbox);
        add(logarithmicCheckBox);
        add(spiralCheckBox);
        add(wavesCheckBox);
        add(mobiusCheckBox);
        add(collatzCheckBox);
    }

    private void addListeners(){
        sinusCheckbox.addActionListener(e -> service.setSinusFlag(sinusCheckbox.isSelected()));
        sphereCheckbox.addActionListener(e -> service.setSphereFlag(sphereCheckbox.isSelected()));
        polarCheckbox.addActionListener(e -> service.setPolarFlag(polarCheckbox.isSelected()));
        heartCheckbox.addActionListener(e -> service.setHeartFlag(heartCheckbox.isSelected()));
        diskCheckbox.addActionListener(e -> service.setDiskFlag(diskCheckbox.isSelected()));
        hyperbolicCheckbox.addActionListener(e -> service.setHyperbolicFlag(hyperbolicCheckbox.isSelected()));
        logarithmicCheckBox.addActionListener(e -> service.setLogarithmicFlag(logarithmicCheckBox.isSelected()));
        spiralCheckBox.addActionListener(e -> service.setSpiralFlag(spiralCheckBox.isSelected()));
        wavesCheckBox.addActionListener(e -> service.setWavesFlag(wavesCheckBox.isSelected()));
        mobiusCheckBox.addActionListener(e -> service.setMobiusFlag(mobiusCheckBox.isSelected()));
        collatzCheckBox.addActionListener(e -> service.setCollatzFlag(collatzCheckBox.isSelected()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
