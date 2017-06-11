/**
 * Created by koshachok on 27.05.2017.
 */
public abstract class Cell {
    protected int mass;
    protected double x;
    protected double y;
    protected double dX;
    protected double dY;
    protected double speed;
    protected long time;

    public Cell() {
        mass = Default.playerMassST;
        x = 0; y = 0;
        dX = 0; dY = 0;
        speed = 0; time = 0;
    }

    public Cell(int mass, double x, double y) {
        this.mass = mass;
        this.x = x; this.y = y;
        this.dX = 0; this.dY = 0;
        this.time = 0; this.speed = 0;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return (int) (Math.pow(mass, 0.5) * Math.PI);
    }

    public void update() {
    }

    public void setSpeed(double dx, double dy){

    }

}
