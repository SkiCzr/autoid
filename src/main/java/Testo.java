import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Testo extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int MAX_ITER = 1000;
    private static final double ZOOM_FACTOR = 1.05;

    private double zoom = 1.0;
    private double centerX = -0.5;
    private double centerY = 0.0;

    public Testo() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        Timer timer = new Timer(1000, e -> {
            zoom = ZOOM_FACTOR;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double zx, zy, cx, cy;
                int iter;
                zx = zy = 0;
                cx = (x - WIDTH / 2.0) * 4.0/(WIDTH * zoom) + centerX;
                cy = (y - HEIGHT / 2.0) * 4.0 / (HEIGHT * zoom) + centerY;
                for (iter = 0; iter < MAX_ITER && zx * zx + zy * zy < 4; iter++) {
                    double tmp = zx * zx - zy * zy + cx;
                    zy = 2.0 * zx * zy + cy;
                    zx = tmp;
                }
                int color = Color.HSBtoRGB(iter / (float) MAX_ITER, 1, 1);
                image.setRGB(x, y, color);
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mandelbrot Zoom Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Testo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
