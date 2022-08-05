import java.awt.*;

public class Triangle {
    private Vector[] vectors;
    private Color color;
    private Vector normal;

    public Triangle(Vector v1, Vector v2, Vector v3)
    {
        this.vectors = new Vector[]{v1, v2, v3};
        this.color = Color.BLACK;

        Vector vn1 = new Vector(new Vector(v2).minus(v1));
        Vector vn2 = new Vector(new Vector(v3).minus(v1));

        Vector cross = vn1.cross(vn2);

        normal = (cross.sqrAbs() > Double.MIN_VALUE) ? cross.normalized(): new Vector(0, 0, 0);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector[] getVectors() {
        return vectors;
    }

    public void translate(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.translate(vector);
        }
    }

    public void plus(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.plus(vector);
        }
    }

    public void minus(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.minus(vector);
        }
    }

    public void mul(double number) {
        for (Vector localVector : vectors) {
            localVector.mul(number);
        }
    }

    public void div(double number) {
        for (Vector localVector : vectors) {
            localVector.div(number);
        }
    }

    public Triangle mulToMatrix(Matrix matrix) {
        return new Triangle(matrix.mul4ToVector(vectors[0]), matrix.mul4ToVector(vectors[1]), matrix.mul4ToVector(vectors[2]));
    }

    public Vector getNormal()
    {
        return normal;
    }

    @Override
    public String toString()
    {
        return "Triangle {\n\t" +
                vectors[0] + "\n\t" +
                vectors[1] + "\n\t" +
                vectors[2] + "\n}";
    }
}
