public class Mesh implements Object3D, Calculable {
    private Triangle[] triangles;
    private String name;
    private Vector angle = new Vector();

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void translate(Vector vector) {
        for (Triangle triangle : triangles) {
            triangle.translate(vector);
        }
    }

    @Override
    public void plus(Vector vector) {
        for (Triangle triangle : triangles) {
            triangle.plus(vector);
        }
    }

    @Override
    public void minus(Vector vector) {
        for (Triangle triangle : triangles) {
            triangle.minus(vector);
        }
    }

    @Override
    public void mul(double number) {
        for (Triangle triangle : triangles) {
            triangle.mul(number);
        }
    }

    @Override
    public void div(double number) {
        for (Triangle triangle : triangles) {
            triangle.div(number);
        }
    }

    @Override
    public void rotateX(double rx) {
        for (Triangle triangle : triangles) {
            triangle.rotateX(rx);
        }
    }

    public void rotateY(double ry) {
        for (Triangle triangle : triangles) {
            for (Vector vector : triangle.getVectors()) {

                System.out.println("ABS IS " + vector.abs());

                Matrix matrix = vector.getMatrix().mul(Matrix.rotateY(ry, vector.abs()));
                vector.set(matrix.get(0, 0), matrix.get(1, 1), matrix.get(2, 2));
            }
        }
    }
}
