package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class Icicle extends Entity {
    private KeyHandler keyH;
    private Player p;
    private int framesSinceStateChange;
    private String lastFrameState;

    private BufferedImage currImg;
    private BufferedImage falling1, falling2, falling3;
    private BufferedImage breaking1, breaking2, breaking3, breaking4;

    public Icicle(int x, int y, int ySpeed, KeyHandler keyH, Player p) {
        super(x, y, 0, ySpeed, 10, 10, "falling");
        this.keyH = keyH;
        setIcicleImages();
        this.p = p;
        lastFrameState = "falling";
        framesSinceStateChange = 0;
        currImg = falling1;
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
        if (keyH.isBackwardPressed() && (backgroundX < 0)) {
            newX += p.getxSpeed();
        }
        else if (keyH.isForwardPressed()) {
            newX -= p.getxSpeed();
        }
        setX(newX);

        if (getY() + getySpeed() < p.getY()) {
            setY(getY() + getySpeed());
        }
        else {
            setState("breaking");
        }

        if (Objects.equals("breaking", getState()) && framesSinceStateChange == 29) {
            setState("gone");
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), lastFrameState))) {
            framesSinceStateChange = 0;
        }
        int frameMod = framesSinceStateChange % 30;
        lastFrameState = getState();

        if (Objects.equals("falling", getState())) {
            if (frameMod <= 10) {
                currImg = falling1;
            } else if (frameMod <= 20) {
                currImg = falling2;
            } else {
                currImg = falling3;
            }
        }
        if (Objects.equals("breaking", getState())) {
            if (frameMod <= 6) {
                currImg = breaking1;
            } else if (frameMod <= 15) {
                currImg = breaking2;
            } else if (frameMod <= 22) {
                currImg = breaking3;
            } else {
                currImg = breaking4;
            }
        }

        g2d.drawImage(currImg, getX(), getY(), tileSize, tileSize, null);

        framesSinceStateChange++;
    }
}
