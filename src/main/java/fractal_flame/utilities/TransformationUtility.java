package fractal_flame.utilities;

import fractal_flame.spring.AppService;
import fractal_flame.pixel.Point;
import fractal_flame.transformation.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TransformationUtility {
//    private static double a;
//    private static double b;
//    private static double c;
//    private static double d;
//    private static double e;
//    private static double f;

    public static List<Transformation> buildAffineTransformation(int num) {
        List<Transformation> transformations = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            double c = ThreadLocalRandom.current().nextDouble(-1, 1);
            double f = ThreadLocalRandom.current().nextDouble(-1, 1);

            while (true) {
                double a = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
                double b = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
                double d = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
                double e = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
                if ((a * a) + (d * d) < 1
                        && (b * b) + (e * e) < 1
                        && (a * a) + (b * b) + (c * c) + (d * d) < 1 + (a * e - b * d)) {
                    transformations.add(p -> new Point(a * p.x() + b * p.y() + c, d * p.x() + e * p.y() + f));

                    break;
                }
            }
        }

        return transformations;
    }

    public static List<Transformation> getNonLinearTransformations(AppService service) {
        List<Transformation> transformations = new ArrayList<>();

        double p1 = ThreadLocalRandom.current().nextDouble(-2, 2);
        double p2 = ThreadLocalRandom.current().nextDouble(-2, 2);
        double p3 = ThreadLocalRandom.current().nextDouble(-2, 2);
        double p4 = ThreadLocalRandom.current().nextDouble(-2, 2);

        if (service.isSinusFlag()) {
            transformations.add(p -> new Point(Math.sin(p.x()), Math.sin(p.y())));
        }
        if (service.isSphereFlag()) {
            transformations.add(p -> new Point(
                    p.x() / (p.x() * p.x() + p.y() * p.y()),
                    p.y() / (p.x() * p.x() + p.y() * p.y())));
        }
        if (service.isPolarFlag()) {
            transformations.add(p -> new Point(
                    Math.atan(p.y() / p.x()) / Math.PI,
                    Math.sqrt(p.x() * p.x() + p.y() * p.y()) - 1));
        }
        if (service.isHeartFlag()) {
            transformations.add(p -> new Point(
                    Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.sin(Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.atan(p.y() / p.x())),
                    -Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.cos(Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.atan(p.y() / p.x()))));
        }
        if (service.isDiskFlag()) {
            transformations.add(p -> new Point(
                    1 / Math.PI * Math.atan(p.y() / p.x()) * Math.sin(Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.PI),
                    1 / Math.PI * Math.atan(p.y() / p.x()) * Math.cos(Math.sqrt(p.x() * p.x() + p.y() * p.y()) * Math.PI)));
        }
        if (service.isHyperbolicFlag()) {
            transformations.add(p -> new Point(
                    p.x() / ((p.x() * p.x()) - (p.y() * p.y())),
                    p.y() / ((p.x() * p.x()) - (p.y() * p.y()))));
        }
        if (service.isLogarithmicFlag()) {
            transformations.add(p -> new Point(
                    Math.log(p.x() * p.x() + 1),
                    Math.log(p.y() * p.y() + 1)));
        }
        if (service.isSpiralFlag()) {
            transformations.add(p -> new Point(
                    (1 / Math.sqrt(p.x() * p.x() + p.y() * p.y())) * (Math.cos(Math.atan2(p.y(), p.x())) + Math.sin(Math.sqrt(p.x() * p.x() + p.y() * p.y()))),
                    (1 / Math.sqrt(p.x() * p.x() + p.y() * p.y())) * (Math.sin(Math.atan2(p.y(), p.x())) - Math.cos(Math.sqrt(p.x() * p.x() + p.y() * p.y())))
            ));

        }
        if (service.isWavesFlag()) {
            transformations.add(p -> new Point(
                    p.x() + p1 * Math.sin(p.y() / (p2 * p2)),
                    p.y() + p3 * Math.sin(p.x() / (p4 * p4))
            ));
        }
        if (service.isMobiusFlag()) {
            transformations.add(p -> {
                double t = (p3 * p.x() + p4) * (p3 * p.x() + p4) + p3 * p.y() * p3 * p.y();
                return new Point(
                        ((p1 * p.x() + p2) * (p3 * p.x() + p4) + p1 * p3 * p.y() * p.y()) / t,
                        (p1 * p.y() * (p3 * p.x() + p4) - p3 * p.y() * (p1 * p.x() + p2)) / t
                );
            });

        }
        if (service.isCollatzFlag()) {
            transformations.add(p -> new Point(
                    .4 * (1.0 + 4.0 * p.x() - (1.0 + 2.0 * p.x()) * Math.cos(Math.PI * p.x())),
                    .4 * (1.0 + 4.0 * p.y() - (1.0 + 2.0 * p.y()) * Math.cos(Math.PI * p.y()))
            ));
        }


        return transformations;
    }
}
