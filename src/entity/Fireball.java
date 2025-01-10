package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Fireball extends Entity {
    private KeyHandler keyH;
    private Player p;
    private int framesSinceStateChange;
    private String lastFrameState;
    private int distanceTraveled;

    private BufferedImage currImg;
    private BufferedImage flying1, flying2, flying3, flying4;
    private BufferedImage impact1, impact2, impact3, impact4;
    private BufferedImage disappearing1, disappearing2, disappearing3;

    public Fireball(int x, int y, int xSpeed, KeyHandler keyH, Player p) {
        super(x, y, xSpeed, 0, 10, 10, "flying");
        setFireballImages();
        this.keyH = keyH;
        this.p = p;
        currImg = flying1;
        this.lastFrameState = "flying";
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
            if (keyH.isBackwardPressed() && (backgroundX < 0)) {
                newX += p.getxSpeed();
            }
            else if (keyH.isForwardPressed()) {
                newX -= p.getxSpeed();
            }
            distanceTraveled += getxSpeed();
        }
        if (distanceTraveled == 65 * getxSpeed()) {
            setState("disappearing");
            framesSinceStateChange = 0;
        }
        setX(newX);
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), lastFrameState))) {
            framesSinceStateChange = 0;
        }
        int frameMod = framesSinceStateChange % 30;
        lastFrameState = getState();

        if (Objects.equals("flying", getState())) {
            if (frameMod <= 6) {
                currImg = flying1;
            } else if (frameMod <= 15) {
                currImg = flying2;
            } else if (frameMod <= 22) {
                currImg = flying3;
            } else {
                currImg = flying4;
            }
        }
        else if (Objects.equals("impacting", getState())) {
            if (frameMod <= 6) {
                currImg = impact1;
            } else if (frameMod <= 15) {
                currImg = impact2;
            } else if (frameMod <= 22) {
                currImg = impact3;
            } else {
                currImg = impact4;
            }

            if (framesSinceStateChange == 29){
                setState("gone");
            }
        }
        else if (Objects.equals("disappearing", getState())) {
            if (frameMod <= 10) {
                currImg = disappearing1;
            } else if (frameMod <= 20) {
                currImg = disappearing2;
            } else {
                currImg = disappearing3;
            }

            if (framesSinceStateChange == 29) {
                setState("gone");
            }
        }

        g2d.drawImage(currImg, getX(), getY(), tileSize, tileSize, null);

        framesSinceStateChange++;
    }

    public void startImpact() {
        setState("impacting");
        framesSinceStateChange = 0;
    }
}
