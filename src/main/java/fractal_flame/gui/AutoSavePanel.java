package fractal_flame.gui;

import fractal_flame.spring.AppService;
import fractal_flame.spring.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

@Component
public class AutoSavePanel extends JPanel {
    private final JCheckBox autoSaveCheckBox = new JCheckBox("Enable auto saving");
    private final JButton setFolderButton = new JButton("Set folder for saving");
    private JTextField folderField = new JTextField(30);

    private BackgroundService backgroundService;
    private AppService service;
    private Image backgroundImage;

    @Autowired
    public AutoSavePanel(BackgroundService backgroundService, AppService service) {
        this.backgroundService = backgroundService;
        this.service = service;
        backgroundImage = backgroundService.getBackgroundImage();

        init();
    }

    private void init() {
        addListeners();

        folderField.setHorizontalAlignment(JTextField.CENTER);
        autoSaveCheckBox.setOpaque(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.add(new JLabel("Folder for saving"), gbc);
        gbc.gridy++;

        this.add(folderField, gbc);
        gbc.gridy++;

        this.add(setFolderButton, gbc);
        gbc.gridy++;

        this.add(autoSaveCheckBox, gbc);
        gbc.gridy++;
    }

    private void addListeners() {
        setFolderButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                folderField.setText(fileChooser.getSelectedFile().getPath());
                service.setPathToFolder(fileChooser.getSelectedFile().getPath());
            }
        });

        autoSaveCheckBox.addActionListener(e -> {
            service.setAutoSaveFlag(autoSaveCheckBox.isSelected());
        });

        folderField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePath();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePath();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePath();
            }

            private void updatePath() {
                service.setPathToFolder(folderField.getText());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
