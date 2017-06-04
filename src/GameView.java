import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Created by koshachok on 28.05.2017.
 */
public class GameView {
    private MenuView menu;
    private PlayingView game;
    private JFrame frame;
    private double scale;

    public GameView() {

    }

    public void initMenu() {
        menu = new MenuView();
        frame = new JFrame("Shagar");
        game = new PlayingView();
    }

    public void initGame() {
        //menu
        game.setPreferredSize(new Dimension(Default.gameScreenWidth, Default.gameScreenHeight));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public MenuView getMenu() {
        return menu;
    }

    public PlayingView getGame() {
        return game;
    }

    public Pair<Double, Double> moveVector(int x, int y) {
        double x_res, y_res, width, height;
        width = Default.gameScreenWidth - x;
        height = Default.gameScreenHeight - y;
        x_res = (-width) / Math.sqrt(width * width + height * height);
        y_res = (-height) / Math.sqrt(width * width + height * height);
        return new Pair<>(x_res, y_res);
    }

    public double mouseVScenter(int x, int y) {
        return Math.sqrt(Default.sqr(Default.gameScreenWidth / 2 - x) + Default.sqr(Default.gameScreenHeight / 2 - y));
    }

    public class MenuView extends JFrame {
        private JButton start_button;
        private JButton exit_button;

        public MenuView() {
            setSize(Default.screenWidth, Default.screenHeight);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setLayout(null);
            start_button = new JButton();
            exit_button = new JButton();

            initButton(start_button, Default.start_button_active_path, Default.start_button_inactive_path);
            initButton(exit_button, Default.exit_button_active_path, Default.exit_button_inactive_path);
            //button location -----------------
            start_button.setBounds((Default.screenWidth - start_button.getIcon().getIconWidth()) / 2,
                    (Default.screenHeight - start_button.getIcon().getIconHeight()) / 2,
                    start_button.getIcon().getIconWidth(), start_button.getIcon().getIconHeight() / 2);

            exit_button.setBounds((Default.screenWidth - start_button.getIcon().getIconWidth()) / 2,
                    (int) (start_button.getBounds().getY() + start_button.getBounds().getHeight()),
                    exit_button.getIcon().getIconWidth(), exit_button.getIcon().getIconHeight() / 2);
            // add buttons --------------------
            add(start_button);
            add(exit_button);
        }

        void initButton(JButton button, String name1, String name2) {
            button.setIcon(new ImageIcon(new Sprite(name1).getImg()));
            button.setRolloverIcon(new ImageIcon(new Sprite(name2).getImg()));
            button.setSize(button.getIcon().getIconWidth() / 2, button.getIcon().getIconHeight() / 2);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
        }

        public void addStartListener(ActionListener listener) {
            start_button.addActionListener(listener);
        }

        public void addExitListener(ActionListener listener) {
            exit_button.addActionListener(listener);
        }
    }

    public class PlayingView extends JPanel {
        private GameModel model;

        public PlayingView() {

        }

        public void render(GameModel model) {
            this.model = model;
            revalidate();
            repaint();
        }

        public void paint(Graphics graphics) {
            Cell player = model.getPlayer();
            Cell bot = model.getBot();
            int y_view = (2 * player.getMass() / Default.field_scale + 1) * Default.field_scale;
            int x_view = y_view * Default.gameScreenWidth / Default.gameScreenHeight;
            int x_pos = (int) Math.round(player.getX());
            int y_pos = (int) Math.round(player.getY());
            ArrayList<Pair<Integer, Integer>> list = model.getCells().getDataFromRange(
                    Math.max(x_pos - x_view / 2, 0), Math.min(x_pos + x_view / 2, Default.fieldWidth),
                    Math.max(y_pos - y_view / 2, 0), Math.min(y_pos + y_view / 2, Default.fieldHeight));

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, Default.gameScreenWidth, Default.gameScreenHeight);

            scale = y_view / Default.gameScreenHeight;

            drawCell(graphics, Default.gameScreenWidth / 2, Default.gameScreenHeight / 2, (int) (player.getRadius() / scale), Color.RED);

            for (Pair<Integer, Integer> i : list) {
                int x = (int) (Default.gameScreenWidth / 2 - (x_pos - i.getFirst() * (1 / scale)));
                int y = (int) (Default.gameScreenHeight / 2 - (y_pos - i.getSecond() * (1 / scale)));
                drawCell(graphics, x, y, Default.cellRadius, randomColor(i.getFirst(), i.getSecond()));
            }

            graphics.dispose();
        }

        private void drawCell(Graphics graphics, int x, int y, int radius, Color color) {
            graphics.setColor(color);
            graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }

        private Color randomColor(int first, int second) {
            return new Color(first % 31 * 8 + 1,
                    second % 31 * 8 + 1, (second + first) % 31 * 8 + 1);
        }

        public void addMML(MouseMotionListener listener) {
            addMouseMotionListener(listener);
        }

    }

}
