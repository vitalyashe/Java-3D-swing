import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello, world!");

        JFrame frame = new JFrame("Engine");
        frame.setBounds(200, 200, 1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Field field = new Field(1920, 1080);
        field.setBounds(0, 0, 1920, 1080);
        frame.add(field);

        Mesh[] objects = initObjects();

        for (Mesh obj : objects) {
            field.addObject(obj.getName(), obj);
            System.out.println("Loaded object " + obj.getName());
        }

        Mesh sphere = field.getObject("Sphere");

        //sphere.rotateY((45 * Math.PI) / 180);

        field.repaint();

        while (true) {
            field.repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Mesh[] initObjects()
    {
        ArrayList<Mesh> result = new ArrayList<>();
        //result.add(getObject("box.obj"));
        //result.add(getObject("monkey.obj"));
        result.add(getObject("sphere.obj"));

        return result.toArray(new Mesh[0]);
    }

    private static Mesh getObject(String pathToFile)
    {
        try {
            Path path = Paths.get(pathToFile);
            BufferedReader reader = Files.newBufferedReader(path);
            StringBuilder sb = new StringBuilder();
            String buffer;
            ArrayList<Vector> points = new ArrayList<>();
            ArrayList<Triangle> triangles = new ArrayList<>();
            String name = "";

            do {
                buffer = reader.readLine();
                if (buffer == null) {
                    break;
                }

                if (buffer.startsWith("#")) {
                    continue;
                } else if (buffer.startsWith("o")) {
                    name = buffer.substring(2);
                } else if (buffer.startsWith("v")) {
                    String vectorString = buffer.substring(2);
                    String cords[] = vectorString.split("\\s+");
                    points.add(new Vector(Double.parseDouble(cords[0]), Double.parseDouble(cords[1]), Double.parseDouble(cords[2])));



                    System.out.println("adding " + Double.parseDouble(cords[0]) + "; " + Double.parseDouble(cords[1]) + "; " + Double.parseDouble(cords[2]));

                } else if (buffer.startsWith("f")) {
                    String triangleString = buffer.substring(2);
                    String pointIndexesPlusOne[] = triangleString.split("\\s+");
                    triangles.add(new Triangle(new Vector(points.get(Integer.parseInt(pointIndexesPlusOne[0]) - 1)),
                            new Vector(points.get(Integer.parseInt(pointIndexesPlusOne[1]) - 1)),
                            new Vector(points.get(Integer.parseInt(pointIndexesPlusOne[2]) - 1))));
                }
            } while (true);

            System.out.println("Triangles count is " + triangles.size());

            Mesh mesh = new Mesh(triangles.toArray(new Triangle[0]), name);

            //mesh.plus(new Vector(0, 0, 50));

            return mesh;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
