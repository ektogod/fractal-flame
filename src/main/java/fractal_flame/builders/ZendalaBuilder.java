package fractal_flame.builders;

import fractal_flame.pixel.Point;
import fractal_flame.pixel.Pixel;
import fractal_flame.transformation.Transformation;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ZendalaBuilder extends FractalFlameBuilder {
    public ZendalaBuilder(int width, int height){
        setXMax((double) width / height);
        setXMin(-(double) width / height);
    }

    @Override
    protected void renderWithThreads(int xRes, int yRes, int it, int samples, int symmetryConst, FractalFlameImage image, List<Transformation> nonLinTrans, List<Transformation> linTransformations) {
        var pixels = image.getPixels();
        int newX, newY;
        for (int i = 0; i < samples; i++) {
            Point startPoint = new Point(
                    ThreadLocalRandom.current().nextDouble(getXMin(), getXMax()),
                    ThreadLocalRandom.current().nextDouble(getYMin(), getYMax()));

            for (int j = -20; j < it; ++j) {
                int randLin = ThreadLocalRandom.current().nextInt(0, linTransformations.size());
                int randNonLin = ThreadLocalRandom.current().nextInt(0, nonLinTrans.size());

                getTransformationColors().putIfAbsent(
                        linTransformations.get(randLin),
                        new Color(
                                ThreadLocalRandom.current().nextInt(0, 256),
                                ThreadLocalRandom.current().nextInt(0, 256),
                                ThreadLocalRandom.current().nextInt(0, 256)
                        ));

                Point point = nonLinTrans
                        .get(randNonLin)
                        .apply(linTransformations.get(randLin).apply(startPoint));

                startPoint = point;

                if (j >= 0 && point.x() >= getXMin() && point.x() <= getXMax() && point.y() >= getYMin() && point.y() <= getYMax()) {
                    double theta = 0.0;
                    for (int k = 1; k < symmetryConst; theta += Math.PI * 2 / k, ++k) {
                        var rotX = point.x() * Math.cos(theta) - point.y() * Math.sin(theta);
                        var rotY = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);

                        newX = xRes - (int) Math.floor((((getXMax() - rotX) / (getXMax() - getXMin())) * xRes));
                        newY = yRes - (int) Math.floor((((getYMax() - rotY) / (getYMax() - getYMin()))) * yRes);

                        if (newX >= 0 && newX <= xRes && newY >= 0 && newY <= yRes) {
                            Color curColor = getTransformationColors().get(linTransformations.get(randLin));
                            Pixel curPixel = pixels[newX][newY];
                            synchronized (curPixel) {
                                if (curPixel.getHitNumber() == 0) {
                                    pixels[newX][newY] = new Pixel(
                                            curColor.getRed(),
                                            curColor.getGreen(),
                                            curColor.getBlue(),
                                            1, 0);
                                } else {
                                    pixels[newX][newY] = new Pixel(
                                            (curColor.getRed() + curPixel.getRed()) / 2,
                                            (curColor.getGreen() + curPixel.getGreen()) / 2,
                                            (curColor.getBlue() + curPixel.getBlue()) / 2,
                                            curPixel.getHitNumber() + 1, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
