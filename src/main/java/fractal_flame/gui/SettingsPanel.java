package fractal_flame.gui;

import fractal_flame.builders.*;
import fractal_flame.config.AppConfig;
import fractal_flame.config.ImageCounter;
import fractal_flame.spring.AppService;
import fractal_flame.spring.BackgroundService;
import fractal_flame.transformation.Transformation;
import fractal_flame.utilities.ImageUtils;
import fractal_flame.utilities.TransformationUtility;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsPanel extends JPanel {
    final JTextField resXField = new JTextField(15);
    final JTextField resYField = new JTextField(15);
    final JTextField samplesAmountField = new JTextField(15);
    final JTextField iterAmountField = new JTextField(15);
    final JTextField transAmountField = new JTextField(15);
    final JTextField symmetryField = new JTextField(15);
    final JTextField gammaField = new JTextField(15);

    final JCheckBox gammaCorrectionCheckBox = new JCheckBox("Gamma correction");

    final ButtonGroup radioButtonsGroup = new ButtonGroup();
    final JRadioButton normalModeRButton = new JRadioButton("Normal mode");
    final JRadioButton cosmicModeRButton = new JRadioButton("Cosmic mode");
    final JRadioButton zendalaModeRButton = new JRadioButton("Zendala mode");

    final JButton saveButton = new JButton("Save the image");
    final JButton generateButton = new JButton("Generate an image");

    FractalFlameImage curFractalFlameImage = null;
    Image backgroundImage;

    AppService service;
    AppConfig config;
    BackgroundService backgroundService;

    @Autowired
    public SettingsPanel(AppService appService, BackgroundService backgroundService, AppConfig config) {
        this.service = appService;
        this.config = config;
        this.backgroundService = backgroundService;

        backgroundImage = backgroundService.getBackgroundImage();

        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        radioButtonsGroup.add(normalModeRButton);
        radioButtonsGroup.add(cosmicModeRButton);
        radioButtonsGroup.add(zendalaModeRButton);

        this.add(new JLabel("Image width:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Image height:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Samples amount:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Iterations amount:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Affine transformation amount:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Symmetry const:"), gbc);
        gbc.gridy++;
        this.add(new JLabel("Gamma const:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(resXField, gbc);
        gbc.gridy++;
        this.add(resYField, gbc);
        gbc.gridy++;
        this.add(samplesAmountField, gbc);
        gbc.gridy++;
        this.add(iterAmountField, gbc);
        gbc.gridy++;
        this.add(transAmountField, gbc);
        gbc.gridy++;
        this.add(symmetryField, gbc);
        gbc.gridy++;
        this.add(gammaField, gbc);
        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(gammaCorrectionCheckBox, gbc);

        radioButtonsGroup.add(normalModeRButton);
        radioButtonsGroup.add(cosmicModeRButton);
        radioButtonsGroup.add(zendalaModeRButton);

        gbc.gridy++;
        this.add(normalModeRButton, gbc);
        gbc.gridy++;
        this.add(cosmicModeRButton, gbc);
        gbc.gridy++;
        this.add(zendalaModeRButton, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(generateButton, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(saveButton, gbc);

        configComponents();
        addGenerateButtonEvent();
        addSaveButtonEvent();
    }

    // initial settings
    private void configComponents() {
        saveButton.setEnabled(false);
        gammaCorrectionCheckBox.setSelected(true);
        normalModeRButton.setSelected(true);

        resXField.setText(config.getImageWidth() + "");
        resYField.setText(config.getImageHeight() + "");
        samplesAmountField.setText(config.getSamplesAmount() + "");
        iterAmountField.setText(config.getIterationsAmount() + "");
        transAmountField.setText(config.getTransformationsAmount() + "");
        gammaField.setText(config.getGammaConst() + "");
        symmetryField.setText(config.getSymmetryConst() + "");

        normalModeRButton.setOpaque(false);
        cosmicModeRButton.setOpaque(false);
        zendalaModeRButton.setOpaque(false);

        gammaCorrectionCheckBox.setOpaque(false);
    }

    private void addGenerateButtonEvent() {
        generateButton.addActionListener(e -> {
            try {
                // getting data from the frame
                int width = Integer.parseInt(resXField.getText());
                int height = Integer.parseInt(resYField.getText());
                int samples = Integer.parseInt(samplesAmountField.getText());
                int iter = Integer.parseInt(iterAmountField.getText());
                int transformationAmount = Integer.parseInt(transAmountField.getText());
                int symmetryConst = Integer.parseInt(symmetryField.getText());
                double gammaConst = Double.parseDouble(gammaField.getText());

                // case when data is wrong
                if (width < 0 || height < 0 || samples < 0 || iter < 0 || transformationAmount < 0) {
                    throw new NumberFormatException();
                }

                FractalFlameBuilder builder = createBuilder(width, height);
                FractalFlameImage image = new FractalFlameImage(width, height);

                List<Transformation> linTransformations = TransformationUtility.buildAffineTransformation(transformationAmount);
                List<Transformation> nonLinTransformations = TransformationUtility.getNonLinearTransformations(service);
                generateImage(builder, image, width, height, samples, iter, symmetryConst, gammaConst, linTransformations, nonLinTransformations);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Something wrong with input data");
            }
        });
    }

    private void addSaveButtonEvent(){
        saveButton.addActionListener(e -> {
            if (service.isFilePathFlag()) {
                try {
                    ImageUtils.save(ImageUtils.convertIntoBufImage(curFractalFlameImage), Paths.get(service.getPathToFolder()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Something went wrong with autosave! Check chosen folder.");
                }
            } else {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int fileChooserResult = fileChooser.showOpenDialog(null);
                if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageUtils.save(ImageUtils.convertIntoBufImage(curFractalFlameImage), fileChooser.getSelectedFile().toPath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Something went wrong with autosave! Check chosen folder.");
                    }
                }
            }

            log();
        });
    }

    private void generateImage(FractalFlameBuilder builder,
                               FractalFlameImage image,
                               int width,
                               int height,
                               int samples,
                               int iterations,
                               int symConst,
                               double gammaConst,
                               List<Transformation> linTransformations,
                               List<Transformation> nonLinTransformations) {

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int parentWidth = parentFrame.getWidth();
        int parentHeight = parentFrame.getHeight();
        Point topLeftCorner = parentFrame.getLocation();

        BootFrame bootFrame = new BootFrame(backgroundService, topLeftCorner, parentWidth, parentHeight);//new BootFrame(parentFrame.getLocation(), parentFrame.getWidth(), parentFrame.getHeight());
        bootFrame.startAnimation();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                builder.render(width, height, iterations, samples, symConst, image, nonLinTransformations, linTransformations);
                if (gammaCorrectionCheckBox.isSelected()) {
                    builder.gammaCorrection(image, gammaConst);
                }

                saveButton.setEnabled(true);
                curFractalFlameImage = image;
                showImage(image);

                return null;
            }

            @Override
            protected void done() {
                bootFrame.endAnimation();
                bootFrame.dispose();
                if (service.isAutoSaveFlag()) {
                    try {
                        ImageUtils.save(ImageUtils.convertIntoBufImage(curFractalFlameImage), Paths.get(service.getPathToFolder()));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Something went wrong with autosave! Check chosen folder.");
                    }
                }
            }
        };

        worker.execute();
    }

    private void showImage(FractalFlameImage fractalImage) {
        BufferedImage image = ImageUtils.convertIntoBufImage(fractalImage);

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon(image));

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                label.setIcon(null);
                image.flush();
                frame.dispose();
            }
        });
    }

    private FractalFlameBuilder createBuilder(int width, int height) {
        FractalFlameBuilder builder = null;
        if (normalModeRButton.isSelected()) {
            builder = new NormalBuilder(width, height);
        } else if (cosmicModeRButton.isSelected()) {
            builder = new CosmicBuilder(width, height);
        } else if (zendalaModeRButton.isSelected()) {
            builder = new ZendalaBuilder(width, height);
        }

        return builder;
    }

    private void log(){
        ImageCounter counter = new ImageCounter();
        String mode;
        if (getNormalModeRButton().isSelected()) {
            mode = "Normal";
        } else if (getCosmicModeRButton().isSelected()) {
            mode = "Cosmic";
        } else {
            mode = "Zendala";
        }

        log.info("fractalFlameImage{} was saved with parameters: height {}, " +
                        "width {}, " +
                        "samples amount {}, " +
                        "iterations amount {}, " +
                        "affine transformation amount {}, " +
                        "symmetry const {}, " +
                        "gamma const {}, " +
                        "gamma correction: {}, " +
                        "mode: {}, " +
                        "sinus: {}, " +
                        "sphere: {}, " +
                        "polar: {}, " +
                        "heart: {}, " +
                        "disk: {}, " +
                        "hyperbolic: {}, " +
                        "logarithmic: {}, " +
                        "spiral: {}, " +
                        "waves: {}, " +
                        "mobius: {}, " +
                        "collatz: {}",
                counter.get(),
                getResYField().getText(),
                getResXField().getText(),
                getSamplesAmountField().getText(),
                getIterAmountField().getText(),
                getTransAmountField().getText(),
                getSymmetryField().getText(),
                getGammaField().getText(),
                getGammaCorrectionCheckBox().isSelected(),
                mode,
                service.isSinusFlag(),
                service.isSphereFlag(),
                service.isPolarFlag(),
                service.isHeartFlag(),
                service.isDiskFlag(),
                service.isHyperbolicFlag(),
                service.isLogarithmicFlag(),
                service.isSpiralFlag(),
                service.isWavesFlag(),
                service.isMobiusFlag(),
                service.isCollatzFlag());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }


}
