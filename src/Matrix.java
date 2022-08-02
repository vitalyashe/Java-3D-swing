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
                {1.0, 0, 0},
                {0, 1.0, 0},
                {0, 0, 1.0}
        });
    }

    public static Matrix rotateX(double rx) {
        double cos = Math.cos(rx);
        double sin = Math.sin(rx);

        double[][] result = new double[3][3];

        result[0][0] = 1;
        result[1][1] = cos;
        result[1][2] = -sin;
        result[2][1] = sin;
        result[2][2] = cos;

        return new Matrix(result);
    }

    public static Matrix rotate(Vector vector, double rv) {
        double[][] result = new double[3][3];

        double cos = Math.cos(rv);
        double sin = Math.sin(rv);

        result[0][0] = cos + (1.0 - cos) * vector.getX() * vector.getX();
        result[0][1] = (1.0 - cos) * vector.getX() * vector.getY() - sin * vector.getZ();
        result[0][2] = (1.0 - cos) * vector.getX() * vector.getZ() + sin * vector.getY();

        result[1][0] = (1.0 - cos) * vector.getX() * vector.getY() + sin * vector.getZ();
        result[1][1] = cos + (1.0 - cos) * vector.getY() * vector.getY();
        result[1][2] = (1.0 - cos) * vector.getY() * vector.getZ() - sin * vector.getX();

        result[2][0] = (1.0 - cos) * vector.getZ() * vector.getX() - sin * vector.getY();
        result[2][1] = (1.0 - cos) * vector.getZ() * vector.getY() + sin * vector.getX();
        result[2][2] = cos + (1.0 - cos) * vector.getZ() * vector.getZ();

        return new Matrix(result);
    }

    public static Matrix rotateY(double ry, double r) {
        double[][] result = new double[3][3];

        double cos = Math.cos(ry);
        double sin = Math.sin(ry);

        result[1][1] = 1;
        result[0][0] = r * cos;
        result[0][2] = r * sin;
        result[2][0] = r * -sin;
        result[2][2] = r * cos;

        return new Matrix(result);
    }
}
