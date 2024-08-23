package fractal_flame;

import fractal_flame.gui.MainFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = "fractal_flame")
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class).headless(false).run(args);

        MainFrame frame = context.getBean(MainFrame.class);
        frame.init();
    }
}
