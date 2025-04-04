package fractal_flame.aop;

import fractal_flame.config.ImageCounter;
import fractal_flame.gui.SettingsPanel;
import fractal_flame.spring.AppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class Logger {
    private final AppService service;
    private final ImageCounter counter = new ImageCounter();

    //@AfterReturning("execution(* fractal_flame.gui.SettingsPanel.addSaveButtonEvent(..)) && target(panel)")
    public void logImageSaving(SettingsPanel panel) {
        String mode;
        if (panel.getNormalModeRButton().isSelected()) {
            mode = "Normal";
        } else if (panel.getCosmicModeRButton().isSelected()) {
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
                panel.getResYField().getText(),
                panel.getResXField().getText(),
                panel.getSamplesAmountField().getText(),
                panel.getIterAmountField().getText(),
                panel.getTransAmountField().getText(),
                panel.getSymmetryField().getText(),
                panel.getGammaField().getText(),
                panel.getGammaCorrectionCheckBox().isSelected(),
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
}
