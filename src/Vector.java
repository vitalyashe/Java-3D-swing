public class Vector implements Calculable {
    private double x = 0;
    private double y = 0;
    private double z = 0;

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

    @Override
    public void translate(Vector vector) {

    }

    @Override
    public void plus(Vector vector) {
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
        this.setZ(this.getZ() + vector.getZ());
    }

    @Override
    public void minus(Vector vector) {
        this.setX(this.getX() - vector.getX());
        this.setY(this.getY() - vector.getY());
        this.setZ(this.getZ() - vector.getZ());
    }

    @Override
    public void mul(double number) {
        this.setX(this.getX() * number);
        this.setY(this.getY() * number);
        this.setZ(this.getZ() * number);
    }

    @Override
    public void div(double number) {
        if (Math.abs(number) < Double.MIN_VALUE) {
            this.set(0, 0, 0);
            return;
        }

        this.setX(this.getX() / number);
        this.setY(this.getY() / number);
        this.setZ(this.getZ() / number);
    }

    @Override
    public void rotateX(double rx) {
        this.x *= Math.cos(rx);
    }

    public double sqrAbs() {
        return getX() * getX() + getY() * getY() + getZ() * getZ();
    }

    public double abs() {
        return Math.sqrt(sqrAbs());
    }

    public Vector normalized() {
        Vector result = new Vector(this);
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
                {x, 0, 0},
                {0, y, 0},
                {0, 0, z}
        });
    }
}
