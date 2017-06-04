import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by koshachok on 02.06.2017.
 */
public class Sprite {
    private Image img;

    public Sprite(String name) {
        loadImage(name);
    }

    private void loadImage(String name) {
        try {
            img = ImageIO.read(getClass().getResource(name));
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    public int getWidth() {
        return img.getWidth(null);
    }

    public int getHeight() {
        return img.getHeight(null);
    }

    public Image getImg() {
        return img;
    }

    public void setImage(Image img) {
        this.img = img;
    }


}
