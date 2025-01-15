package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Icicle extends Entity {
    private BufferedImage falling1, falling2, falling3;
    private BufferedImage breaking1, breaking2, breaking3, breaking4;

    public Icicle(int x, int y, int ySpeed, KeyHandler keyH, Player p) {
        super(x, y, 0, ySpeed, 10, 10, "falling", keyH, p);
        setIcicleImages();
        setCurrImg(falling1);;
    }

    public void setIcicleImages() {
        try {
            falling1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Falling_1.png"));
            falling2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Falling_2.png"));
            falling3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Falling_3.png"));

            breaking1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Breaking_1.png"));
            breaking2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Breaking_2.png"));
            breaking3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Breaking_3.png"));
            breaking4 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Icicle Sprites/Icicle_Breaking_4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX) {
        int newX = getX();
        if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
            newX += getP().getxSpeed();
        }
        else if (getKeyH().isForwardPressed()) {
            newX -= getP().getxSpeed();
        }
        setX(newX);

        if (getY() + getySpeed() < getP().getY()) {
            setY(getY() + getySpeed());
        }
        else {
            setState("breaking");
        }

        if (Objects.equals("breaking", getState()) && getFramesSinceStateChange() == 29) {
            setState("gone");
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 30;
        setLastFrameState(getState());

        if (Objects.equals("falling", getState())) {
            if (frameMod <= 10) {
                setCurrImg(falling1);
            } else if (frameMod <= 20) {
                setCurrImg(falling2);
            } else {
                setCurrImg(falling3);
            }
        }
        if (Objects.equals("breaking", getState())) {
            if (frameMod <= 6) {
                setCurrImg(breaking1);
            } else if (frameMod <= 15) {
                setCurrImg(breaking2);
            } else if (frameMod <= 22) {
                setCurrImg(breaking3);
            } else {
                setCurrImg(breaking4);
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
