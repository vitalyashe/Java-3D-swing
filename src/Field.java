import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.LinkedHashMap;
import java.util.Map;

public class Field extends JPanel {
    private LinkedHashMap<String, Mesh> objects = new LinkedHashMap<>();
    private double screenWidth;
    private double screenHeight;

    public int frame = 0;

    public Field(double width, double height) {
        this.screenWidth = width;
        this.screenHeight = height;
        setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Matrix P = Matrix.projection(90, 16.0/9, 0.1, 1000);
        Matrix P = Matrix.identity(4);
        //Matrix S = Matrix.screenSpace(1920, 1080);
        Matrix S = Matrix.identity(4);
        Matrix SP = S.mul(P);

        for (Map.Entry<String, Mesh> entry : objects.entrySet()) {
            Mesh mesh = entry.getValue();
            Triangle[] triangles = mesh.getTriangles();
            mesh.rotate(new Vector(0, 1, 0), (1.0 * Math.PI) / 180);

            int counter = 0;

            for (Triangle triangle : triangles) {
                Path2D path = new Path2D.Double();
                boolean isPathStarted = false;

                Triangle projected = triangle.mulToMatrix(mesh.model()).mulToMatrix(SP);

                Triangle projectedNormalized = new Triangle(
                        new Vector(projected.getVectors()[0].div(projected.getVectors()[0].getW())),
                        new Vector(projected.getVectors()[1].div(projected.getVectors()[1].getW())),
                        new Vector(projected.getVectors()[2].div(projected.getVectors()[2].getW()))
                );

                for (Vector vector : projectedNormalized.getVectors()) {
                    double x = vector.getX();
                    double y = vector.getY();

                    if (!isPathStarted) {
                        path.moveTo(x + 1920 / 2, 1080 / 2 - y);
                        isPathStarted = true;
                    } else {
                        path.lineTo(x + 1920 / 2, 1080 / 2 - y);
                    }
                }

                path.closePath();
                g2.setColor(new Color(counter * 1398101));
                g2.fill(path);

                counter++;
            }
        }

        frame++;
    }

    public void addObject(String key, Mesh object) {
        objects.put(key, object);
    }

    public void removeObject(String key) {
        objects.remove(key);
    }

    public Mesh getObject(String key) {
        return this.objects.get(key);
    }
}
