import java.util.*;

/**
 * Created by koshachok on 27.05.2017.
 */


public class GameModel {
    private FieldMap cells; // x - first key, y - second key
    private LinkedList<Cell> players;
    private int totalmass;

    public void addCell(int x, int y) {
        cells.setData(x, y);
    }

    public void delCell(int x, int y) {
        if (cells.checkData(x, y))
            cells.delData(x, y);
        totalmass -= Default.cellMassST;
    }

    public int getTotalmass() {
        return totalmass;
    }

    public FieldMap getCells() {
        return cells;
    }

    public LinkedList<Cell> getPlayers() {
        return players;
    }

    public void addPlayer() {
        Pair<Integer, Integer> coordinates = smartRandomGeneration();
        players.add(new Player(Default.playerMassST, coordinates.getFirst(), coordinates.getSecond()));
        totalmass += Default.playerMassST;
    }

    public void delPlayer(Cell player) {
        players.remove(player);
    }

    public GameModel() {
        new GameModel(1);
    }

    public GameModel(int amount) {
        totalmass = 0;
        for (int i = 0; i < amount; i++) {
            addPlayer();
        }
        cells = new FieldMap();
        generateCells(Default.totalMass - totalmass, cells, 0, 0, 0, 0);
    }

    private Pair<Integer, Integer> smartRandomGeneration() {
        Pair<Integer, Integer> result = new Pair<>(0, 0);
        int x, y;
        Random rand = new Random();
        boolean mark = false;
        do {
            x = rand.nextInt(Default.fieldWidth);
            y = rand.nextInt(Default.fieldHeight);
            for (Cell i :
                    players) {
                if (i.getY() - i.getRadius() < y && i.getY() + i.getRadius() > y
                        && i.getX() - i.getRadius() < x && i.getX() + i.getRadius() > x) {
                    mark = true;
                    break;
                }
            }
        } while (mark);

        result.setFirst(x);
        result.setSecond(y);
        return result;
    }

    // generate amount cells, generating isn't allowed inside (x1..x2,y1..y2)

    public static void generateCells(int amount, FieldMap cells, int x1, int x2, int y1, int y2) {
        Random rand = new Random();

        int x, y;
        for (int i = 0; i < amount; i++) {
            x = rand.nextInt(Default.fieldWidth);
            y = rand.nextInt(Default.fieldHeight);
            if (!cells.checkData(x, y) && !(x >= x1 && y >= y1 && x <= x2 && y <= y2))
                cells.setData(x, y);
        }
    }
}
