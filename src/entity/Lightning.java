package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Lightning extends Entity {
    private KeyHandler keyH;
    private Player p;
    private int framesSinceStateChange;
    private String lastFrameState;

    private BufferedImage currImg;
    private BufferedImage striking1, striking2, striking3, striking4, striking5, striking6;

    public Lightning(int x, int y, KeyHandler keyH, Player p) {
        super(x, y, 0, 0, 10, 10, "striking");
        this.keyH = keyH;
        this.p = p;
        framesSinceStateChange = 0;
        setLightningImages();
        currImg = striking1;
    }

    private void setLightningImages() {
        try {
            striking1 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_1.png"));
            striking2 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_2.png"));
            striking3 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_3.png"));
            striking4 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_4.png"));
            striking5 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_5.png"));
            striking6 = ImageIO.read(getClass().getResourceAsStream("/Spell Sprites/Lightning Sprites/Lightning_Strike_6.png"));

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

        if (framesSinceStateChange == 29) {
            setState("gone");
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), lastFrameState))) {
            framesSinceStateChange = 0;
        }
        int frameMod = framesSinceStateChange % 30;
        lastFrameState = getState();

        if (Objects.equals("striking", getState())) {
            if (frameMod < 5) {
                currImg = striking1;
            } else if (frameMod < 10) {
                currImg = striking2;
            } else if (frameMod < 15) {
                currImg = striking3;
            } else if (frameMod < 20) {
                currImg = striking4;
            } else if (frameMod < 25) {
                currImg = striking5;
            } else {
                currImg = striking6;
            }
        }

        g2d.drawImage(currImg, getX(), getY(), tileSize, tileSize * 4, null);

        framesSinceStateChange++;
    }
}
