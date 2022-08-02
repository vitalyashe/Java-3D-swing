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
//        g2.setColor(Color.RED);
//        g2.fillRect(10, 10, 100, 100);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //double s = 1.0 / Math.tan((alpha * Math.PI * 0.5) / 180);
        double s = 0.04;//Math.atan(alpha);
        double aspect = 1;

//        System.out.println("S is: " + s);
//
//        g2.setColor(Color.BLACK);
//
//        Path2D p = new Path2D.Double();
//        p.moveTo(100, 100);
//        p.lineTo(200, 200);
//        p.lineTo(200, 100);
//        p.closePath();
//
//        g2.fill(p);

        double S = 1;

        double tanA = 1;

        //entry.getValue().plus(new Vector(0, 0, 0.05));



        for (Map.Entry<String, Mesh> entry : objects.entrySet()) {
            String key = entry.getKey();

            //entry.getValue().rotateY((5.0 * Math.PI) / 180);

            //entry.getValue().plus(new Vector(0, 0, 0.05));


            Triangle[] triangles = entry.getValue().getTriangles();


            //System.out.println("Starting to paint " + entry.getValue().getName());

            int counter = 0;

            //int
            for (Triangle triangle : triangles) {

                Path2D path = new Path2D.Double();
                boolean isPathStarted = false;

                for (Vector vector: triangle.getVectors()) {

                    Matrix projection = new Matrix(new double[][]{
                            {aspect * tanA, 0, 0 },
                            {0, tanA, 0},
                            {0, 0, tanA}
                    });

                    //Matrix result = vector.getMatrix().mul(projection);
                    Matrix result = vector.getMatrix().mul(Matrix.rotateY((frame * Math.PI) / 180, vector.abs()));
                    result = result.mul(projection);

                    //System.out.println(result.get(0, 0) + "/" + result.get(2, 2) + "; " + result.get(1, 1) + "/" + result.get(2, 2));


                    double x = result.get(0, 0);// / result.get(2, 2);
                    double y = result.get(1, 1);// / result.get(2, 2);
                    //double x = vector.getX();
                    //double y = vector.getY();




                    //System.out.println("X: " + vector.getX() + "; Y: " + vector.getY() + "; Z: " + vector.getZ());

                    //double x = (vector.getX() * S) / (vector.getZ());
                    //double y = vector.getY() / (vector.getZ() * s);
                    //double x = vector.getX();
                    //double y = vector.getY();


                    //double y = (vector.getY() * S) / vector.getZ();
                    //double y = Math.tan((60 * Math.PI) / 180);

//                    double cosX = vector.getX() / Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2));
//                    double angleX = Math.acos(cosX);
//                    double tgX = Math.sin(angleX) / Math.cos(angleX);
//
//                    //double x = 16.0 / 9 * S * tgX;
//                    double x = vector.getX() / (16.0 / 9 * tgX);
//                    //double x =
//
//                    double cosY = vector.getY() / Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2) + Math.pow(vector.getZ(), 2));
//                    double angleY = Math.acos(cosY);
//                    double tgY = Math.sin(angleY) / Math.cos(angleY);
//
//                    //double y = S * tgY;
//                    //double y = vector.getY() / (vector.getZ() * tgY);
//                    double y = vector.getY() / (vector.getZ() * tgY);





                    //double x = vector.getX() / (vector.getZ() * tgX);

                    //double x = vector.getX();
                    //double y = vector.getY();



                    if (!isPathStarted) {
                        path.moveTo(x + 1920/2, 1080/2 - y);
                        isPathStarted = true;
                    } else {
                        path.lineTo(x + 1920/2, 1080/2 - y);
                    }

                    //System.out.println(Math.round(x) + " " + Math.round(y));
                }

                path.closePath();
                //g2.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
                //g2.setColor(triangle.getColor());
                g2.setColor(new Color(counter * 1398101));
                g2.draw(path);

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

    public Mesh getObject(String key)
    {
        return this.objects.get(key);
    }
}
