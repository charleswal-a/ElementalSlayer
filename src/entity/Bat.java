package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bat extends Entity{
    private BufferedImage idle1, idle2;
    private BufferedImage attacking1, attacking2, attacking3;

    public Bat(int x, int y, KeyHandler keyH, Player p) {
        super(x, y, 0, 0, 25, "idle", keyH, p);
        setBatImages();
        setCurrImg(idle1);
    }

    private void setBatImages() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Bat Sprites/Bat_Idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Bat Sprites/Bat_Idle_2.png"));

            attacking1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Bat Sprites/Bat_Attacking_1.png"));
            attacking2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Bat Sprites/Bat_Attacking_2.png"));
            attacking3 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Bat Sprites/Bat_Attacking_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX, int tileSize) {
        int newX = moveCamera(backgroundX);

        if (!Objects.equals(getState(), "dead") && (newX < getP().getX() + tileSize * 5)) {
            boolean xInRange = false;
            boolean yInRange = false;

            if (newX < getP().getX() - tileSize * 0.4) {
                setxSpeed(4);
                setState("flying");
            } else if (newX > getP().getX() + tileSize * 0.4) {
                setxSpeed(-4);
                setState("flying");
            } else {
                setxSpeed(0);
                xInRange = true;
            }

            if (getY() < getP().getY() - tileSize * 0.25) {
                setySpeed(1);
                setState("flying");
            } else if (newX > getP().getY() + tileSize * 0.25) {
                setySpeed(-1);
                setState("flying");
            } else {
                setySpeed(0);
                yInRange = true;
            }

            if (xInRange && yInRange) {
                setState("attacking");
            }
        }
        else {
            setxSpeed(0);
            setySpeed(0);
        }

        if (getCurrHealth() == 0) {
            setState("dead");
        }

        setX(newX + getxSpeed());
        setY(getY() + getySpeed());
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 60;
        setLastFrameState(getState());

        if (Objects.equals(getState(), "idle")) {
            if (frameMod <= 30) {
                setCurrImg(idle1);
            } else {
                setCurrImg(idle2);
            }
        }
        else if (Objects.equals(getState(), "flying")) {
            if (frameMod <= 15) {
                setCurrImg(idle1);
            } else if (frameMod <= 30) {
                setCurrImg(idle2);
            } else if (frameMod <= 45) {
                setCurrImg(idle1);
            } else {
                setCurrImg(idle2);
            }
        }
        else if (Objects.equals(getState(), "attacking")) {
            if (frameMod <= 15) {
                setCurrImg(attacking1);
            } else if (frameMod <= 30) {
                setCurrImg(attacking2);
            } else if (frameMod <= 45) {
                setCurrImg(attacking3);
            } else {
                setCurrImg(idle1);
            }

            if (frameMod == 50) {
                getP().setCurrHealth(getP().getCurrHealth() - 10);
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        if (!Objects.equals(getState(), "dead")) {
            drawHealthBar(g2d, tileSize);
        }

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
