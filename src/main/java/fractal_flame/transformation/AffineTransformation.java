package fractal_flame.transformation;

import lombok.Getter;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class AffineTransformation {
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;

    private int Red;
    private int Green;
    private int Blue;

    public AffineTransformation() {
        Red = ThreadLocalRandom.current().nextInt(0, 256);
        Green = ThreadLocalRandom.current().nextInt(0, 256);
        Blue = ThreadLocalRandom.current().nextInt(0, 256);

        c = ThreadLocalRandom.current().nextDouble(-1, 1);
        f = ThreadLocalRandom.current().nextDouble(-1, 1);
        while (true){
            a = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            b = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            d = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            e = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            if ((a * a) + (d * d) < 1
                    && (b * b) + (e * e) < 1
                    && (a * a) + (b * b) + (c * c) + (d * d) < 1 + (a * e - b * d)){
                break;
            }
        }
    }
}
