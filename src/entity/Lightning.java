package entity;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Lightning extends Entity {
    private BufferedImage striking1, striking2, striking3, striking4, striking5, striking6;

    public Lightning(int x, int y, KeyHandler keyH, Player p) {
        super(x, y, 0, 0, 10, "striking", keyH, p);
        setLightningImages();
        setCurrImg(striking1);
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
        if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
            newX += getP().getxSpeed();
        }
        else if (getKeyH().isForwardPressed()) {
            newX -= getP().getxSpeed();
        }
        setX(newX);

        if (getFramesSinceStateChange() == 29) {
            setState("gone");
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 30;
        setLastFrameState(getState());

        if (Objects.equals("striking", getState())) {
            if (frameMod < 5) {
                setCurrImg(striking1);
            } else if (frameMod < 10) {
                setCurrImg(striking2);
            } else if (frameMod < 15) {
                setCurrImg(striking3);
            } else if (frameMod < 20) {
                setCurrImg(striking4);
            } else if (frameMod < 25) {
                setCurrImg(striking5);
            } else {
                setCurrImg(striking6);
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize * 4, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
