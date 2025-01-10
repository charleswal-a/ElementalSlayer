package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spikes extends Entity {
    private KeyHandler keyH;
    private Player p;
    private int framesSinceStateChange;
    private String lastFrameState;

    private BufferedImage currImg;
    private BufferedImage idle;
    private BufferedImage appearing1, appearing2, appearing3;
    private BufferedImage disappearing1, disappearing2, disappearing3, disappearing4, disappearing5;

    public Spikes(int x, int y, KeyHandler keyH, Player p) {
        super(x, y, 0, 0, 10, 10, "appearing");
        this.keyH = keyH;
        setSpikesImages();
        this.p = p;
        this.lastFrameState = "appearing";
        framesSinceStateChange = 0;
        currImg = appearing1;
    }

    private void setSpikesImages() {
        try {
            idle = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Idle.png"));

            appearing1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Appearing_1.png"));
            appearing2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Appearing_2.png"));
            appearing3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Appearing_3.png"));

            disappearing1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Disappearing_1.png"));
            disappearing2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Disappearing_2.png"));
            disappearing3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Disappearing_3.png"));
            disappearing4 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Disappearing_4.png"));
            disappearing5 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Spikes Sprites/Spikes_Disappearing_5.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX) {
        int newX = getX() + getxSpeed();
        if (keyH.isBackwardPressed() && (backgroundX < 0)) {
            newX += p.getxSpeed();
        }
        else if (keyH.isForwardPressed()) {
            newX -= p.getxSpeed();
        }
        setX(newX);
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), lastFrameState))) {
            framesSinceStateChange = 0;
        }
        int frameMod = framesSinceStateChange % 30;
        lastFrameState = getState();

        if (Objects.equals("appearing", getState())) {
            if (frameMod <= 3) {
                currImg = appearing1;
            } else if (frameMod <= 8) {
                currImg = appearing2;
            } else {
                currImg = appearing3;
            }

            if (frameMod == 12) {
                setState("still");
            }
        }
        else if (Objects.equals("still", getState())) {
            currImg = idle;

            if (framesSinceStateChange == 30) {
                setState("disappearing");
            }
        }
        else if (Objects.equals("disappearing", getState())) {
            if (frameMod <= 6) {
                currImg = disappearing1;
            } else if (frameMod <= 15) {
                currImg = disappearing2;
            } else if (frameMod <= 22) {
                currImg = disappearing3;
            } else {
                currImg = disappearing4;
            }

            if (framesSinceStateChange == 29) {
                setState("gone");
            }
        }

        g2d.drawImage(currImg, getX(), getY(), tileSize * 3, tileSize, null);

        framesSinceStateChange++;
    }
}
