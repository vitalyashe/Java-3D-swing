import java.util.Arrays;

public class Matrix {
    private double[][] matrix;

    public Matrix(int width, int height) {
        this.matrix = new double[width][height];
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix plus(Matrix another) {
        double[][] result = new double[this.matrix.length][this.matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] + another.matrix[i][j];
            }
        }

        return new Matrix(result);
    }

    public Vector mulToVector(Vector vector) {

        double x = matrix[0][0] * vector.getX() + matrix[0][1] * vector.getY() + matrix[0][2] * vector.getZ();
        double y = matrix[1][0] * vector.getX() + matrix[1][1] * vector.getY() + matrix[1][2] * vector.getZ();
        double z = matrix[2][0] * vector.getX() + matrix[2][1] * vector.getY() + matrix[2][2] * vector.getZ();

        return new Vector(x, y, z);
    }

    public Vector mul4ToVector(Vector vector) {
        double x = matrix[0][0] * vector.getX() + matrix[0][1] * vector.getY() + matrix[0][2] * vector.getZ() + matrix[0][3] * vector.getW();
        double y = matrix[1][0] * vector.getX() + matrix[1][1] * vector.getY() + matrix[1][2] * vector.getZ() + matrix[1][3] * vector.getW();
        double z = matrix[2][0] * vector.getX() + matrix[2][1] * vector.getY() + matrix[2][2] * vector.getZ() + matrix[2][3] * vector.getW();
        double w = matrix[3][0] * vector.getX() + matrix[3][1] * vector.getY() + matrix[3][2] * vector.getZ() + matrix[3][3] * vector.getW();

        return new Vector(x, y, z, w);
    }

    public Matrix mul(Matrix another) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = 0; k < another.matrix.length; k++) {
                    result[i][j] += matrix[i][k] * another.matrix[k][j];
                }
            }
        }

        return new Matrix(result);
    }

    public double get(int i, int j) {
        return matrix[i][j];
    }

    public static Matrix identity() {
        return new Matrix(new double[][]{
                {1.0, 0, 0, 0},
                {0, 1.0, 0, 0},
                {0, 0, 1.0, 0},
                {0, 0, 0, 1.0}
        });
    }

    public static Matrix identity(int size) {
        double[][] result = new double[size][size];

        for (int i = 0; i < size; i++) {
            result[i][i] = 1.0;
        }

        return new Matrix(result);
    }

    public static Matrix rotateX(double rx) {
        double cos = Math.cos(rx);
        double sin = Math.sin(rx);

        double[][] result = new double[4][4];

        result[0][0] = 1;
        result[1][1] = cos;
        result[1][2] = -sin;
        result[2][1] = sin;
        result[2][2] = cos;
        result[3][3] = 1;

        return new Matrix(result);
    }

    public static Matrix rotate(Vector vector, double rv) {
        double[][] result = new double[4][4];

        double cos = Math.cos(rv);
        double sin = Math.sin(rv);

        vector = vector.normalized();

        result[0][0] = cos + (1.0 - cos) * vector.getX() * vector.getX();
        result[0][1] = (1.0 - cos) * vector.getX() * vector.getY() - sin * vector.getZ();
        result[0][2] = (1.0 - cos) * vector.getX() * vector.getZ() + sin * vector.getY();

        result[1][0] = (1.0 - cos) * vector.getX() * vector.getY() + sin * vector.getZ();
        result[1][1] = cos + (1.0 - cos) * vector.getY() * vector.getY();
        result[1][2] = (1.0 - cos) * vector.getY() * vector.getZ() - sin * vector.getX();

        result[2][0] = (1.0 - cos) * vector.getZ() * vector.getX() - sin * vector.getY();
        result[2][1] = (1.0 - cos) * vector.getZ() * vector.getY() + sin * vector.getX();
        result[2][2] = cos + (1.0 - cos) * vector.getZ() * vector.getZ();

        result[3][3] = 1;

        return new Matrix(result);
    }

    public static Matrix rotateY(double ry) {
        double[][] result = new double[4][4];

        double cos = Math.cos(ry);
        double sin = Math.sin(ry);

        result[1][1] = 1;
        result[0][0] = cos;
        result[0][2] = sin;
        result[2][0] = -sin;
        result[2][2] = cos;
        result[3][3] = 1;

        return new Matrix(result);
    }

    public static Matrix rotateZ(double rz) {
        double[][] result = new double[4][4];

        double cos = Math.cos(rz);
        double sin = Math.sin(rz);

        result[2][2] = 1;
        result[0][0] = cos;
        result[0][1] = -sin;
        result[1][0] = sin;
        result[1][1] = cos;
        result[3][3] = 1;

        return new Matrix(result);
    }

    public static Matrix translate(Vector v, int size) {
        Matrix identity = identity(size);

        identity.matrix[0][3] = v.getX();
        identity.matrix[1][3] = v.getY();
        identity.matrix[2][3] = v.getZ();

        return identity;
    }

    public static Matrix rotation(Vector v) {
        return rotateX(v.getX()).mul(rotateY(v.getY())).mul(rotateZ(v.getZ()));
    }

    public Vector w() {
        return new Vector(matrix[0][3], matrix[1][3], matrix[2][3]);
    }

    public static Matrix projection(double fov, double aspect, double ZNear, double ZFar) {
        double[][] result = new double[][]{
                {1.0 / (Math.tan(Math.PI * fov * 0.5 / 180) * aspect), 0, 0, 0},
                {0, 1.0 / (Math.tan(Math.PI * fov * 0.5 / 180)), 0, 0},
                {0, 0, ZFar / (ZFar - ZNear), (-ZFar * ZNear) / (ZFar - ZNear), 0},
                {0, 0, 1, 0}
        };

        return new Matrix(result);
    }

    public static Matrix screenSpace(int width, int height) {
        double[][] result = new double[][]{
                {-0.5 * width, 0, 0, 0.5 * width},
                {0, -0.5 * height, 0, 0.5 * height},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        return new Matrix(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");

        result.append("{\n");

        for (int i = 0; i < matrix.length; i++) {
            result.append("\t[");
            for (int j = 0; j < matrix[i].length; j++) {
                result.append(matrix[i][j]);
                result.append("\t");
            }

            result.append("]\n");
        }

        result.append("}");

        return result.toString();
    }

    public static Matrix view(Vector left, Vector up, Vector lookAt, Vector eye)
    {
//        double[][] result = new double[][] {
//                {left.getX(), left.getY(), left.getZ(), new Vector(-eye.getX(), -eye.getY(), -eye.getZ()).dot(left)},
//                {up.getX(), up.getY(), up.getZ(), new Vector(-eye.getX(), -eye.getY(), -eye.getZ()).dot(up)},
//                {lookAt.getX(), lookAt.getY(), lookAt.getZ(), new Vector(-eye.getX(), -eye.getY(), -eye.getZ()).dot(lookAt)},
//                {0, 0, 0, 1}
//        };

        double[][] result = new double[4][4];

        result[0][0] = left.getX();
        result[0][1] = left.getY();
        result[0][2] = left.getZ();
        result[0][3] = -eye.dot(left);

        result[1][0] = up.getX();
        result[1][1] = up.getY();
        result[1][2] = up.getZ();
        result[1][3] = -eye.dot(up);

        result[2][0] = lookAt.getX();
        result[2][1] = lookAt.getY();
        result[2][2] = lookAt.getZ();
        result[2][3] = -eye.dot(lookAt);

        result[3][3] = 1.0;

        return new Matrix(result);
    }

    public Vector x()
    {
        return new Vector(matrix[0][0], matrix[1][0], matrix[2][0]);
    }

    public Vector y()
    {
        return new Vector(matrix[0][1], matrix[1][1], matrix[2][1]);
    }

    public Vector z()
    {
        return new Vector(matrix[0][2], matrix[1][2], matrix[2][2]);
    }
}
