import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

public class Field extends JPanel {
    private LinkedHashMap<String, Mesh> objects = new LinkedHashMap<>();

    private double screenWidth;
    private double screenHeight;
    private volatile Vector position = new Vector(0, 0, 10);
    private volatile Matrix transformMatrix = Matrix.identity(4);

    public int frame = 0;

    public Field(double width, double height) {
        this.screenWidth = width;
        this.screenHeight = height;
        setBackground(Color.WHITE);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //position = position.plus(new Vector(-1, 0, 0));

        Matrix P = Matrix.projection(90, 16.0 / 9, 0.1, 1000);
        //Matrix P = Matrix.identity(4);
        Matrix S = Matrix.screenSpace(1920, 1080);
        //Matrix S = Matrix.identity(4);
        Matrix SP = S.mul(P);
        Matrix V = Matrix.view(transformMatrix.x().normalized(), transformMatrix.y().normalized(), transformMatrix.z().normalized(), position);

        boolean isFirstPointPainted = false;

        //System.out.println("Matrix V " + V);


        //System.out.println(position);

        for (Map.Entry<String, Mesh> entry : objects.entrySet()) {
            Mesh mesh = entry.getValue();
            Triangle[] triangles = mesh.getTriangles();
            //mesh.rotate(new Vector(0, 1, 0), (Math.PI) / 180);

            int counter = 0;

            for (Triangle triangle : triangles) {
                String pathLog = "Path started\n";
                Path2D path = new Path2D.Double();
                boolean isPathStarted = false;

                Triangle modelTriangle = triangle.mulToMatrix(mesh.model());

                if (!isFirstPointPainted) {
                    //System.out.println("Model triangle " + modelTriangle);
                }

                Triangle modelViewTriangle = modelTriangle.mulToMatrix(V);


                if (!isFirstPointPainted) {
                    //System.out.println("Model view triangle " + modelViewTriangle);
                }

                Triangle projected = modelViewTriangle.mulToMatrix(SP);

                if (!isFirstPointPainted) {
                    //System.out.println("Projected " + projected);
                }

                double dot = projected.getNormal().dot(new Vector(position).minus(mesh.getPosition()).normalized());

                if (dot < 0) {
                    continue;
                }

                if (!isFirstPointPainted) {
                    //System.out.println("WE PASSED DOT CHECK");
                }

                Triangle projectedNormalized = new Triangle(
                        new Vector(projected.getVectors()[0].div(projected.getVectors()[0].getW())),
                        new Vector(projected.getVectors()[1].div(projected.getVectors()[1].getW())),
                        new Vector(projected.getVectors()[2].div(projected.getVectors()[2].getW()))
                );

                if (!isFirstPointPainted) {
                    //System.out.println("Projected normalized " + modelTriangle);
                }

                for (Vector vector : projected.getVectors()) {
                    double x = vector.getX();
                    double y = vector.getY();

                    if (!isFirstPointPainted) {
                        //System.out.println("Paint in " + x + ", " + y);
                        isFirstPointPainted = true;

                        //g2.fillOval((int)Math.round(x - 10), (int)Math.round(y - 10), 20, 20);
                    }


//                    if (!isPathStarted) {
//                        path.moveTo(x + 1920 / 2, 1080 / 2 - y);
//                        isPathStarted = true;
//                    } else {
//                        path.lineTo(x + 1920 / 2, 1080 / 2 - y);
//                    }

                    pathLog += "\t(" + x + ", " + y + ")\n";

                    if (!isPathStarted) {
                        path.moveTo(x, y);

                        isPathStarted = true;
                    } else {
                        path.lineTo(x, y);
                    }
                }

                path.closePath();
                g2.setColor(new Color(counter * 1398101));
                //g2.setColor(new Color(0xFF4500));
                g2.fill(path);

                //System.out.println(pathLog);

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

    public void addToPosition(Vector v) {
        position.plus(v);
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void mulTransformToMatrix(Matrix matrix) {
        transformMatrix = transformMatrix.mul(matrix);
    }
}
