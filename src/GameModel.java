import java.util.*;

/**
 * Created by koshachok on 27.05.2017.
 */


public class GameModel {
    private FieldMap cells; // x - first key, y - second key
    private Cell Bot;
    private Cell Player;

    public FieldMap getCells() {
        return cells;
    }

    public void setCells(FieldMap cells) {
        this.cells = cells;
    }

    public Cell getBot() {
        return Bot;
    }

    public void setBot(Cell bot) {
        Bot = bot;
    }

    public Cell getPlayer() {
        return Player;
    }

    public void setPlayer(Cell player) {
        Player = player;
    }

    public GameModel(int xPlayerPos, int yPlayerPoss, int xBotPoss, int yBotPoss) {
        Player = new Cell(Default.playerMassST, xPlayerPos, yPlayerPoss);
        Bot = new Cell(Default.botMassST, xBotPoss, yBotPoss);
        cells = new FieldMap();
        generateCells(Default.totalMass, cells, 0, 0, 0, 0);
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
