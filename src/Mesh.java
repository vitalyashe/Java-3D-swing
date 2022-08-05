import java.util.Arrays;

public class Mesh implements Object3D {
    private Triangle[] triangles;
    private String name;
    private Vector angle = new Vector();
    private Vector position = new Vector(0, 0, 0);
    private Matrix transformMatrix = Matrix.identity(4);

    public Mesh(Triangle[] triangles) throws Exception {
        this.triangles = triangles;
    }

    public Mesh(Triangle[] triangles, String name) throws Exception {
        this.triangles = triangles;
        this.name = name;
    }

    public Triangle[] getTriangles() {
        return triangles;
    }

    public String getName() {
        return name;
    }

    public void translate(Vector vector) {
        position.plus(vector);

        for (Triangle triangle : triangles) {
            triangle.translate(vector);
        }
    }

    public void plus(Vector vector) {
        for (Triangle triangle : triangles) {
            triangle.plus(vector);
        }
    }

    public void minus(Vector vector) {
        for (Triangle triangle : triangles) {
            triangle.minus(vector);
        }
    }

    public void mul(double number) {
        for (Triangle triangle : triangles) {
            triangle.mul(number);
        }
    }

    public void div(double number) {
        for (Triangle triangle : triangles) {
            triangle.div(number);
        }
    }

    public void rotateX(double rx) {
        for (Triangle triangle : triangles) {
            for (Vector vector : triangle.getVectors()) {
                Matrix matrix = vector.getMatrix().mul(Matrix.rotateX(rx));
                vector.set(matrix.get(0, 0), matrix.get(1, 1), matrix.get(2, 2));
            }
        }
    }

    public void rotateY(double ry) {
        for (Triangle triangle : triangles) {
            for (Vector vector : triangle.getVectors()) {
                Matrix matrix = vector.getMatrix().mul(Matrix.rotateY(ry));
                vector.set(matrix.get(0, 0), matrix.get(1, 1), matrix.get(2, 2));
            }
        }
    }

    public void transform(Matrix t) {
        transformMatrix = transformMatrix.mul(t);
    }

    public void rotate(Vector v) {
        angle.plus(v);
        transform(Matrix.rotation(v));
    }

    public void rotate(Vector v, double rv) {
        transform(Matrix.rotate(v, rv));
    }

    public void rotateRelativePoint(Vector s, Vector r) {
        angle.plus(r);

        transformRelativePoint(s, Matrix.rotation(r));
    }

    public void rotateRelativePoint(Vector s, Vector v, double r) {
        transformRelativePoint(s, Matrix.rotate(v, r));
    }

    public void transformRelativePoint(Vector point, Matrix transform) {
        position.minus(point);
        transformMatrix = Matrix.translate(position, 4);
        transformMatrix = transform.mul(transformMatrix);
        position = transformMatrix.w().plus(point);

        transformMatrix = Matrix.translate(transformMatrix.w().mul(-1), 4).mul(transformMatrix);
    }

    public void translateToPoint(Vector point) {
        translate(point.minus(position));
    }

    public Matrix model() {
        return Matrix.translate(position, 4).mul(transformMatrix);
    }

    public Vector getPosition()
    {
        return position;
    }
}
