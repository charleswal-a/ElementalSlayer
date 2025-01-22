package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Goblin extends Entity {
    private BufferedImage idle1, idle2;
    private BufferedImage walking1, walking2, walking3, walking4;
    private BufferedImage attacking1, attacking2, attacking3;
    private BufferedImage dying1, dying2, dying3, dead;

    public Goblin(int x, int y, KeyHandler keyH, Player p) {
        super(x, y, 0, 0, 50, "idle", keyH, p);
        setGoblinImages();
        setCurrImg(idle1);
    }

    private void setGoblinImages() {
        // Assigns all image sprite files to correct variables
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Idle_2.png"));

            walking1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_1.png"));
            walking2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_2.png"));
            walking3 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_3.png"));
            walking4 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Walking_4.png"));

            attacking1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Attacking_1.png"));
            attacking2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Attacking_2.png"));
            attacking3 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Attacking_3.png"));

            dying1 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Dying_1.png"));
            dying2 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Dying_2.png"));
            dying3 = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Dying_3.png"));
            dead = ImageIO.read(getClass().getResourceAsStream("/Enemy Sprites/Goblin Sprites/Goblin_Dead.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX, int tileSize) {
        int newX = moveCamera(backgroundX);

        // Changes speed and direction based on relation to player position
        if (!Objects.equals(getState(), "dead") && (newX < getP().getX() + tileSize * 4)) {
            if (newX < getP().getX() - tileSize * 0.6) {
                setxSpeed(3);
                setState("walking");
            } else if (newX > getP().getX() + tileSize * 0.6) {
                setxSpeed(-3);
                setState("walking");
            } else {
                setxSpeed(0);
                setState("attacking");
            }
        }
        else {
            setxSpeed(0);
        }

        // Updates state if monster has no more health
        if (getCurrHealth() == 0) {
            setState("dead");
        }

        setX(newX + getxSpeed());
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 50;
        setLastFrameState(getState());

        if (Objects.equals(getState(), "idle")) {
            if (frameMod <= 25) {
                setCurrImg(idle1);
            } else {
                setCurrImg(idle2);
            }
        }
        else if (Objects.equals(getState(), "walking")) {
            if (frameMod <= 12) {
                setCurrImg(walking1);
            } else if (frameMod <= 25) {
                setCurrImg(walking2);
            } else if (frameMod <= 37) {
                setCurrImg(walking3);
            } else {
                setCurrImg(walking4);
            }
        }
        else if (Objects.equals(getState(), "attacking")) {
            if (frameMod <= 12) {
                setCurrImg(attacking1);
            } else if (frameMod <= 25) {
                setCurrImg(attacking2);
            } else if (frameMod <= 37) {
                setCurrImg(attacking3);
            } else {
                setCurrImg(idle1);
            }

            if (frameMod == 25) {
                getP().setCurrHealth(getP().getCurrHealth() - 15);
            }
        }
        else if (Objects.equals(getState(), "dead")) {
            if (frameMod <= 15) {
                setCurrImg(dying1);
            } else if (frameMod <= 30) {
                setCurrImg(dying2);
            } else {
                setCurrImg(dying3);
            }
            if (getFramesSinceStateChange() >= 50) {
                setCurrImg(dead);
            }
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        if (!Objects.equals(getState(), "dead")) {
            drawHealthBar(g2d, tileSize);
        }

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }
}
