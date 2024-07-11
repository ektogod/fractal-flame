import fractal_flame.pixel.Pixel;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;

@Getter
public class DrawingCanvas extends Canvas {
    private Pixel[][] points;
    private int x;
    private int y;
    public DrawingCanvas(int width, int height) {
        this.setSize(width, height);
        points = new Pixel[width + 1][height + 1];
        for (Pixel[] p: points){
            Arrays.fill(p, new Pixel(0, 0, 0, 0, 0));
        }
    }

    public void drawPoint(int x, int y, Color color) {
        Graphics g = getGraphics();
        g.setColor(color);
        this.x = x;
        this.y = y;
        paint(g);
    }

    @Override
    public void paint(Graphics g){
        g.drawLine(x, y, x, y);
        g.dispose();
    }
}
