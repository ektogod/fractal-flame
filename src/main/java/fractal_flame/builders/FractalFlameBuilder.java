package fractal_flame.builders;

import fractal_flame.pixel.Pixel;
import fractal_flame.transformation.Transformation;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Getter
@Setter
public abstract class FractalFlameBuilder {
    private ConcurrentHashMap<Transformation, Color> transformationColors = new ConcurrentHashMap<>();
    private double xMax = 1.777;
    private double xMin = -1.777;
    private double yMax = 1.0;
    private double yMin = -1.0;

    public void render(int xRes, int yRes, int it, int samples, int symmetryConst, FractalFlameImage image, List<Transformation> nonLinTrans, List<Transformation> linTrans) {
        int threadAmount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(threadAmount);

//        for (int i = 0; i < threadAmount; i++) {
//            service.execute(() -> renderWithThreads(xRes, yRes, it, Math.floorDiv(samples, threadAmount),symmetryConst, image, nonLinTrans, linTrans));
//        }
//
//        service.shutdown();
//        try {
//            if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
//                service.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            service.shutdownNow();
//            Thread.currentThread().interrupt();
//        }
//    }
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < threadAmount; i++) {
                futures.add(service.submit(() -> renderWithThreads(xRes, yRes, it, Math.floorDiv(samples, threadAmount), symmetryConst, image, nonLinTrans, linTrans)));
            }
            for (Future<?> future : futures) {
                future.get(); // Ждем завершения всех задач
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
            try {
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                service.shutdownNow();
            }
        }
    }

    public void gammaCorrection(FractalFlameImage image, double gamma) {
        var pixels = image.getPixels();
        double max = 0.0;
        for (int row = 0; row < image.getWidth(); row++) {
            for (int col = 0; col < image.getHeight(); col++) {
                if (pixels[row][col].getHitNumber() != 0) {
                    pixels[row][col].setNormal(Math.log10(pixels[row][col].getHitNumber()));
                    if (pixels[row][col].getNormal() > max) {
                        max = pixels[row][col].getNormal();
                    }
                }
            }
        }

        for (int row = 0; row < image.getWidth(); row++) {
            for (int col = 0; col < image.getHeight(); col++) {
                var curPix = pixels[row][col];
                pixels[row][col] = new Pixel(
                        (int) (curPix.getRed() * Math.pow(curPix.getNormal(), (1.0 / gamma))),
                        (int) (curPix.getGreen() * Math.pow(curPix.getNormal(), (1.0 / gamma))),
                        (int) (curPix.getBlue() * Math.pow(curPix.getNormal(), (1.0 / gamma))),
                        curPix.getHitNumber(),
                        curPix.getNormal() / max);
            }
        }
    }

    protected abstract void renderWithThreads(int xRes, int yRes, int it, int samples, int symmetryConst, FractalFlameImage image, List<Transformation> nonLinTrans, List<Transformation> linTransformations);
}
