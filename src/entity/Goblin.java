package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Goblin extends Entity {
    private BufferedImage idle1;

    public Goblin(int x, int y, int xSpeed, KeyHandler keyH, Player p) {
        super(x, y, xSpeed, 0, 25, 25, "idle", keyH, p);
    }

    private void setGoblinImages() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Player Spirtes/Player_Idle_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX) {
        int newX = getX() + getxSpeed();
        if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
            newX += getP().getxSpeed();
        }
        else if (getKeyH().isForwardPressed()) {
            newX -= getP().getxSpeed();
        }
        setX(newX);
    }

    public void draw(Graphics2D g2d, int tileSize) {
        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
