package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Goblin extends Entity {
    private BufferedImage idle1, idle2;
    private BufferedImage walking1, walking2, walking3, walking4;

    public Goblin(int x, int y, int xSpeed, KeyHandler keyH, Player p) {
        super(x, y, xSpeed, 0, 25, 25, "idle", keyH, p);
        setGoblinImages();
        setCurrImg(idle1);
    }

    private void setGoblinImages() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_idle_2.png"));

            walking1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_1.png"));
            walking2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_2.png"));
            walking3 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_3.png"));
            walking4 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX, int tileSize) {
        int newX = getX();
        if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
            newX += getP().getxSpeed();
        }
        else if (getKeyH().isForwardPressed()) {
            newX -= getP().getxSpeed();
        }

        if (newX < getP().getX() - tileSize) {
            setxSpeed(3);
            setState("walking");
        }
        else if (newX > getP().getX() + tileSize) {
            setxSpeed(-3);
            setState("walking");
        }
        else {
            setxSpeed(0);
            setState("idle");
        }

        setX(newX + getxSpeed());
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 40;
        setLastFrameState(getState());

        if (Objects.equals(getState(), "idle")) {
            if (frameMod <= 20) {
                setCurrImg(idle1);
            } else {
                setCurrImg(idle2);
            }
        }
        else if (Objects.equals(getState(), "walking")) {
            if (frameMod <= 10) {
                setCurrImg(walking1);
            } else if (frameMod <= 20) {
                setCurrImg(walking2);
            } else if (frameMod <= 30) {
                setCurrImg(walking3);
            } else {
                setCurrImg(walking4);
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
