public class Vector {
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double w = 1;

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public Vector() {
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translate(Vector vector) {

    }

    public Vector plus(Vector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());

        return new Vector(getX(), getY(), getZ());
    }

    public Vector minus(Vector vector) {
        this.setX(this.getX() - vector.getX());
        this.setY(this.getY() - vector.getY());
        this.setZ(this.getZ() - vector.getZ());
        return new Vector(getX(), getY(), getZ());
    }

    public Vector mul(double number) {
        this.setX(this.getX() * number);
        this.setY(this.getY() * number);
        this.setZ(this.getZ() * number);

        return new Vector(getX(), getY(), getZ());
    }


    public Vector div(double number) throws IllegalArgumentException {
        if (Math.abs(number) < Double.MIN_VALUE) {
            throw new IllegalArgumentException("Division by zero");
        }

        this.setX(this.getX() / number);
        this.setY(this.getY() / number);
        this.setZ(this.getZ() / number);

        return new Vector(getX(), getY(), getZ());
    }


    public void rotateX(double rx) {
        this.x *= Math.cos(rx);
    }

    public double sqrAbs() {
        return getX() * getX() + getY() * getY() + getZ() * getZ();
    }

    public double abs() {
        return Math.sqrt(sqrAbs());
    }

    public Vector normalized() throws IllegalArgumentException {
        Vector result = new Vector(this);

        if (abs() < Double.MIN_VALUE) {
            throw new IllegalArgumentException("Division by zero");
        }

        result.div(abs());
        return result;
    }

    public double dot(Vector vector) {
        return getX() * vector.getY() + getY() * vector.getY() + getZ() * vector.getZ();
    }

    public Vector cross(Vector vector) {
        return new Vector(
                getY() * vector.getZ() - vector.getY() * getZ(),
                getZ() * vector.getX() - vector.getZ() * getX(),
                getX() * vector.getY() - vector.getX() * getY()
        );
    }

    public Matrix getMatrix() {
        return new Matrix(new double[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        });
    }

    public double[] getAsArray()
    {
        return new double[]{getX(), getY(), getZ()};
    }
}
