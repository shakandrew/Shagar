/**
 * Created by koshachok on 27.05.2017.
 */
public abstract class Default {
    //ST - starting
    public final static int playerMassST = 20;
    public final static int botMassST = 10;
    public final static int screenWidth = 800;
    public final static int screenHeight = 600;
    public final static int gameScreenWidth = 1920;
    public final static int gameScreenHeight = 1080;
    public final static int cellMassST = 1;
    public final static int fieldWidth = 5000; // Field is squa
    public final static int fieldHeight = 5000;
    public final static double eatingRatio = 1.25;
    public final static double eatingDistance = 0.25;
    public final static int cellRadius = 5;
    public final static int totalMass = 20000;
    public final static String start_button_active_path = "data/sprites/main_menu/main_menu_start_button_active.png";
    public final static String start_button_inactive_path = "data/sprites/main_menu/main_menu_start_button_inactive.png";
    public final static String exit_button_active_path = "data/sprites/main_menu/main_menu_exit_button_active.png";
    public final static String exit_button_inactive_path = "data/sprites/main_menu/main_menu_exit_button_inactive.png";
    public final static int field_scale = 500;
    public final static double maxSpeed = 0.0004;
    public final static long timeToBeSmaller = 8000;


    public static int sqr(int first) {
        return first*first;
    }
    public static double sqr(double first) { return first*first;}
}
