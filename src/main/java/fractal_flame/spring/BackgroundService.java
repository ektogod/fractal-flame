package fractal_flame.spring;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@Getter
@Service
public class BackgroundService {
    private Image backgroundImage;
    private Image bootBackgroundImage;

    public BackgroundService() throws IOException {
        defineBackgroundImage();
        setBootBackgroundImage("/backgroundImages/bootBackground.png");
    }

    public void defineBackgroundImage() throws IOException {
        Random random = new Random();
        int num = random.nextInt(3);
        switch (num) {
            case 0 -> {
                setBackgroundImage("/backgroundImages/background1.png");
            }
            case 1 -> {
                setBackgroundImage("/backgroundImages/background2.jpg");
            }
            case 2 -> {
                setBackgroundImage("/backgroundImages/background3.png");
            }
        }
    }

    private void setBackgroundImage(String path) throws IOException {
        InputStream stream = getClass().getResourceAsStream(path);
        backgroundImage = ImageIO.read(stream);
    }

    private void setBootBackgroundImage(String path) throws IOException{
        InputStream stream = getClass().getResourceAsStream(path);
        bootBackgroundImage = ImageIO.read(stream);
    }
}
