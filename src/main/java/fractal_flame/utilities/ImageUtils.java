package fractal_flame.utilities;

import fractal_flame.builders.FractalFlameImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class ImageUtils {
    public static void save(BufferedImage image, Path path) throws IOException {
        path = Paths.get(path.toString() + String.format("\\fractalFlameImage%d.png", ThreadLocalRandom.current().nextInt(0, 10000)));
        ImageIO.write(image, "png", path.toFile());
    }

    public static BufferedImage convertIntoBufImage(FractalFlameImage fractalImage) {
        BufferedImage image = new BufferedImage(fractalImage.getWidth(), fractalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < fractalImage.getPixels().length - 1; ++i) {
            for (int j = 0; j < fractalImage.getPixels()[i].length - 1; ++j) {
                var cur = fractalImage.getPixels()[i][j];
                image.setRGB(i, j, cur.getRed() * cur.getGreen() * cur.getBlue() * 1);
            }
        }

        return image;
    }

}
