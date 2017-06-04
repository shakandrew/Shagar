import java.awt.event.*;
import java.util.*;

/**
 * Created by koshachok on 27.05.2017.
 */
public class GameController {
    private static boolean gameRunning;
    private int totalMass;
    private static GameModel model;
    private static GameView view;
    private double dX;
    private double dY;
    private double speed;


    public GameController() {
        model = new GameModel((Default.fieldWidth / 100), (Default.fieldHeight / 100),
                (Default.fieldWidth / 10), (Default.fieldHeight / 10));
        view = new GameView();
        view.initMenu();

        gameRunning = false;

        view.getGame().addMML(new MouseAct());
        view.getMenu().addStartListener(new StartButtonListener());
        view.getMenu().addExitListener(new ExitButtonListener());
        view.getMenu().setVisible(true);
    }


    private void checkCellEating(Cell active_cell) {
        int x = (int) Math.round(active_cell.getX()), y = (int) Math.round(active_cell.getY()), radius = active_cell.getRadius();
        FieldMap food_cells = model.getCells();
        //load cells from range(x1..x2,y1..y2) to result
        ArrayList<Pair<Integer, Integer>> result = food_cells.getDataFromRange(Math.max(x - radius, 0),
                Math.min(x + radius, Default.fieldWidth), Math.max(y - radius, 0), Math.min(y + radius, Default.fieldHeight));
        if (result.size() == 0) return;
        for (Pair<Integer, Integer> i : result) {
            if (Default.sqr(i.getFirst() - x) + Default.sqr(i.getSecond() - y) < Default.sqr(radius)) {
                food_cells.delData(i.getFirst(), i.getSecond());
                //active_cell.setMass(active_cell.getMass() + Default.cellMassST);
                totalMass--;
            }
        }
        /*if (totalMass != Default.totalMass)
            GameModel.generateCells(Default.totalMass - totalMass, food_cells, Math.max(x - radius, 0),
                    Math.min(x + radius, Default.fieldWidth), Math.max(y - radius, 0), Math.min(y + radius, Default.fieldHeight));*/
    }

    private Cell checkPlayerEating() {
        Cell result = null, player = model.getPlayer(), bot = model.getBot();
        if (canEat(player, bot)) result = player;
        if (canEat(bot, player)) result = bot;
        return result;
    }

    //first tries to eat second
    private boolean canEat(Cell first, Cell second) {
        if (first.getMass() < second.getMass() * Default.eatingRatio) return false;
        double dist_bet_rad = Math.sqrt(Default.sqr(first.getX() - second.getX()) +
                Default.sqr(first.getY() - second.getY()));
        return first.getRadius() / 2 - dist_bet_rad > Default.eatingDistance * second.getRadius() / 2;
    }


    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameRunning = true;
            run();
        }
    }

    class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private void run() {

        view.initGame();
        while (gameRunning) {
            checkCellEating(model.getPlayer());
            checkCellEating(model.getBot());
            if (checkPlayerEating() != null) {
                gameRunning = false; // check for winner
            }
            view.getGame().render(model);
            update();
        }
    }

    private void update() {
        double x = model.getPlayer().getX() * speed * dX;
        double y = model.getPlayer().getY() * speed * dY;
        model.getPlayer().setX(x);
        model.getPlayer().setY(y);
    }

    private void setSpeed(int x, int y) {
        if (model.getPlayer().getRadius() - view.mouseVScenter(x, y) > 0)
            speed = Default.maxSpeed - (model.getPlayer().getRadius() - view.mouseVScenter(x, y)
                    / model.getPlayer().getRadius())*Default.maxSpeed;
        else
            speed = Default.maxSpeed;
    }


    class MouseAct implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Pair<Double, Double> vector = view.moveVector(e.getX(), e.getY());
            setSpeed(e.getX(), e.getY());
            dX = vector.getFirst();
            dY = vector.getSecond();
        }
    }
}



