import java.awt.event.*;
import java.util.*;

/**
 * Created by koshachok on 27.05.2017.
 */
public class GameController {
    private static boolean gameRunning;
    private static GameModel model;
    private static GameView view;
    public static int end; // 0 - menu | 1 - game | 2 - player won | 3 - player lost

    public GameController() {

        view = new GameView();
        view.initMenu();

        gameRunning = false;

        view.getMenu().addStartListener(new StartButtonListener());
        view.getMenu().addExitListener(new ExitButtonListener());
        view.getMenu().setVisible(true);
    }


    private void checkCellEating(Cell active_cell) {
        int x = (int) Math.round(active_cell.getX()), y = (int) Math.round(active_cell.getY()), radius = active_cell.getRadius();
        //load cells from range(x1..x2,y1..y2) to result
        ArrayList<Pair<Integer, Integer>> result = model.getCells().getDataFromRange(Math.max(x - radius, 0),
                Math.min(x + radius, Default.fieldWidth), Math.max(y - radius, 0), Math.min(y + radius, Default.fieldHeight));

        if (result.size() == 0) return;

        for (Pair<Integer, Integer> i : result) {
            if (Default.sqr(i.getFirst() - x) + Default.sqr(i.getSecond() - y) < Default.sqr(radius)) {
                model.delCell(i.getFirst(), i.getSecond());
                active_cell.setMass(active_cell.getMass() + Default.cellMassST);
            }
        }
        //TODO
       /*result = food_cells.getDataFromRange(Math.max(x - radius, 0),
                Math.min(x + radius, Default.fieldWidth), Math.max(y - radius, 0), Math.min(y + radius, Default.fieldHeight));
        *//*
        if (model.getTotalmass() < Default.totalMass)
            GameModel.generateCells(Default.totalMass - model.getTotalmass(),
                    food_cells, Math.max(x - radius, 0), Math.min(x + radius, Default.fieldWidth),
                    Math.max(y - radius, 0), Math.min(y + radius, Default.fieldHeight));*/
    }

    private void checkPlayerEating() {
        for (Cell first : model.getPlayers())
            for (Cell second : model.getPlayers())
                if (first != second) {
                    if (canEat(first, second))
                        first.setMass(first.getMass() + second.getMass());
                    model.delPlayer(second);
                }
    }

    //first tries to eat second
    private boolean canEat(Cell first, Cell second) {
        if (first.getMass() < second.getMass() * Default.eatingRatio) return false;
        double dist_bet_rad = Math.sqrt(Default.sqr(first.getX() - second.getX()) +
                Default.sqr(first.getY() - second.getY()));
        return (first.getRadius() / 2 - dist_bet_rad > Default.eatingDistance * second.getRadius() / 2);
    }


    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameRunning = true;
            end = 1;
            new Thread(new RunGame()).start();
        }
    }

    class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class RunGame implements Runnable {
        public void run() {
            model = new GameModel(2);

            //view.getGame().addMML(new MouseAct());
            view.initGame();
            end = 0;
            while (gameRunning) {
                for( Cell player : model.getPlayers()) {
                    checkCellEating(player);
                }
                checkPlayerEating();
                view.getGame().render(model);
                for (Cell i : model.getPlayers()) {
                    i.update();
                }
            }
        }
    }
/*
    private void setSpeed(int x, int y) {
        if (model.getPlayer().getRadius() - view.mouseVScenter(x, y) > 0)
            model.getPlayer().setSpeed(Default.maxSpeed - (model.getPlayer().getRadius() - view.mouseVScenter(x, y))
                    / model.getPlayer().getRadius() * Default.maxSpeed);
        else
            model.getPlayer().setSpeed(Default.maxSpeed);
    }


    class MouseAct implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Pair<Double, Double> vector = view.moveVector(e.getX(), e.getY());
            setSpeed(e.getX(), e.getY());
            model.getPlayer().setdX(vector.getFirst());
            model.getPlayer().setdY(vector.getSecond());
        }
    }*/
}



