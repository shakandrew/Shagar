/**
 * Created by koshachok on 27.05.2017.
 */
public class Cell {
    private int mass;
    private double x;
    private double y;

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
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

    public Cell(int mass, double x, double y) {

        this.mass = mass;
        this.x = x;
        this.y = y;
    }

    public int getRadius() {
        return (int) Math.pow(mass, 0.8);
    }
}
