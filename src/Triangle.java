import java.awt.*;

public class Triangle implements Calculable {
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

    @Override
    public void translate(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.translate(vector);
        }
    }

    @Override
    public void plus(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.plus(vector);
        }
    }

    @Override
    public void minus(Vector vector) {
        for (Vector localVector : vectors) {
            localVector.minus(vector);
        }
    }

    @Override
    public void mul(double number) {
        for (Vector localVector : vectors) {
            localVector.mul(number);
        }
    }

    @Override
    public void div(double number) {
        for (Vector localVector : vectors) {
            localVector.div(number);
        }
    }

    @Override
    public void rotateX(double rx) {
//        for (Vector localVector : vectors) {
//            //localVector.rotateX(rx);
//
//        }
//
//        for (int i = 0; i < this.vectors.length; i++) {
//            this.vectors[i] = new Vector(this.vectors[i].getX(), this.vectors[i].getY(), this.vectors[i].getZ());
//        }
    }
}
