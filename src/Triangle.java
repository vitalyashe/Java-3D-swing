import java.awt.*;

public class Triangle {
    private Vector[] vectors;
    private Color color;

    public Triangle(Vector[] vectors) {
        this.vectors = vectors;
        this.color = Color.BLACK;
    }

    public Triangle(Vector[] vectors, Color color) {
        this.vectors = vectors;
        this.color = color;
    }

    public Triangle(Vector v1, Vector v2, Vector v3)
    {
        this.vectors = new Vector[]{v1, v2, v3};
        this.color = Color.BLACK;
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
        return new Triangle(matrix.mulToVector(vectors[0]), matrix.mulToVector(vectors[1]), matrix.mulToVector(vectors[2]));
    }
}
