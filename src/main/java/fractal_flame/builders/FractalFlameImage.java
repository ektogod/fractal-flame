package fractal_flame.builders;

import fractal_flame.pixel.Pixel;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class FractalFlameImage {
    private Pixel[][] pixels;
    private int width;
    private int height;

    public FractalFlameImage(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Pixel[width + 1][height + 1];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = new Pixel( 0, 0, 0, 0, 0);
            }
        }
    }

    public void clearPixels() {
        for (int i = 0; i < pixels.length; i++) {
            Arrays.fill(pixels[i], null);
        }
        pixels = null;
    }
}
