package fractal_flame.spring;

import fractal_flame.gui.BootFrame;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.awt.*;


@Getter
@Setter
@Configuration
@ComponentScan(basePackages = "fractal_flame")
public class AppConfig {
    @Value("${image.width}")
    private int imageWidth;

    @Value("${image.height}")
    private int imageHeight;

    @Value("${samples}")
    private int samplesAmount;

    @Value("${iterations}")
    private int iterationsAmount;

    @Value("${transformations}")
    private int transformationsAmount;

    @Value("${symmetryConst}")
    private int symmetryConst;

    @Value("${gammaConst}")
    private double gammaConst;
}

