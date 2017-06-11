/**
 * Created by koshachok on 10.06.2017.
 */
public class AI extends Cell {

    public AI() {
        super();
    }

    public AI(int mass, int x, int y) {
        super(mass, x, y);
    }

    @Override
    public void update() {
        targetPoint();

        double new_x = x + speed * dX;
        double new_y = y + speed * dY;

        x = Math.min(Default.fieldWidth, Math.max(new_x, 0));
        y = Math.min(Default.fieldWidth, Math.max(new_y, 0));

        if (System.currentTimeMillis() - time > Default.timeToBeSmaller) {
            if (mass > Default.playerMassST) {
                mass = (int) (mass - Math.pow((double) mass / 100, 1.5));
            }
            time = System.currentTimeMillis();
        }
    }

    private void targetPoint() {

    }

}
