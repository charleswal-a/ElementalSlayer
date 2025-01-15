package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Fireball extends Entity {
    private int distanceTraveled;
    private BufferedImage flying1, flying2, flying3, flying4;
    private BufferedImage impact1, impact2, impact3, impact4;
    private BufferedImage disappearing1, disappearing2, disappearing3;

    public Fireball(int x, int y, int xSpeed, KeyHandler keyH, Player p) {
        super(x, y, xSpeed, 0, 10, 10, "flying", keyH, p);
        setFireballImages();
        setCurrImg(flying1);
        distanceTraveled = 0;
    }

    private void setFireballImages() {
        try {
            flying1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Flying_1.png"));
            flying2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Flying_2.png"));
            flying3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Flying_3.png"));
            flying4 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Flying_4.png"));

            impact1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Impact_1.png"));
            impact2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Impact_2.png"));
            impact3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Impact_3.png"));
            impact4 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Impact_4.png"));

            disappearing1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Disappearing_1.png"));
            disappearing2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Disappearing_2.png"));
            disappearing3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Fireball Sprites/Fireball_Disappearing_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX) {
        int newX = getX() + getxSpeed();
        if (!(Objects.equals("impacting", getState()))) {
            if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
                newX += getP().getxSpeed();
            }
            else if (getKeyH().isForwardPressed()) {
                newX -= getP().getxSpeed();
            }
            distanceTraveled += getxSpeed();
        }
        if (distanceTraveled == 65 * getxSpeed()) {
            setState("disappearing");
            setFramesSinceStateChange(0);
        }
        setX(newX);
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 30;
        setLastFrameState(getState());

        if (Objects.equals("flying", getState())) {
            if (frameMod <= 6) {
                setCurrImg(flying1);
            } else if (frameMod <= 15) {
                setCurrImg(flying2);
            } else if (frameMod <= 22) {
                setCurrImg(flying3);
            } else {
                setCurrImg(flying4);
            }
        }
        else if (Objects.equals("impacting", getState())) {
            if (frameMod <= 6) {
                setCurrImg(impact1);
            } else if (frameMod <= 15) {
                setCurrImg(impact2);
            } else if (frameMod <= 22) {
                setCurrImg(impact3);
            } else {
                setCurrImg(impact4);
            }

            if (getFramesSinceStateChange() == 29){
                setState("gone");
            }
        }
        else if (Objects.equals("disappearing", getState())) {
            if (frameMod <= 10) {
                setCurrImg(disappearing1);
            } else if (frameMod <= 20) {
                setCurrImg(disappearing2);
            } else {
                setCurrImg(disappearing3);
            }

            if (getFramesSinceStateChange() == 29) {
                setState("gone");
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }

    public void startImpact() {
        setState("impacting");
        setFramesSinceStateChange(0);
    }
}
