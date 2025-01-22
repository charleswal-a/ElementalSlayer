package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private BufferedImage idle1, idle2;
    private BufferedImage run1, run2, run3;
    private BufferedImage swing1, swing2, swing3;
    private BufferedImage dying1, dying2, dying3, dying4, dead;
    private BufferedImage fireSwing1, fireSwing2, fireSwing3, fireSwing4;

    private int maxEnergy, currEnergy;

    public Player(int x, int y, int xSpeed, int ySpeed, int maxHealth, int maxEnergy, KeyHandler keyH) {
        super(x, y, xSpeed, ySpeed, maxHealth, "idle", keyH, null);
        this.maxEnergy = maxEnergy;
        currEnergy = maxEnergy;
        setPlayerImages();
        setCurrImg(idle1);
    }

    private void setPlayerImages() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Idle_2.png"));

            run1 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Run_1.png"));
            run2 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Run_2.png"));
            run3 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Run_3.png"));

            swing1 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Swing_1.png"));
            swing2 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Swing_2.png"));
            swing3 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Swing_3.png"));

            dying1 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Dying_1.png"));
            dying2 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Dying_2.png"));
            dying3 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Dying_3.png"));
            dying4 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Dying_4.png"));
            dead = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Dead.png"));

            fireSwing1 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Fire_Swing_1.png"));
            fireSwing2 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Fire_Swing_2.png"));
            fireSwing3 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Fire_Swing_3.png"));
            fireSwing4 = ImageIO.read(getClass().getResourceAsStream("/Player Sprites/Player_Fire_Swing_4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(int backgroundX) {
        if (!Objects.equals("dead", getState())) {
            if (getKeyH().isBackwardPressed() && (backgroundX < 0)) {
                setState("running");
            } else if (getKeyH().isForwardPressed()) {
                setState("running");
            } else if (getKeyH().isMeleePressed()) {
                setState("swing");
            } else if (getKeyH().isFireAbilityPressed()) {
                setState("casting fire spell");
            } else if (getFramesSinceStateChange() > 30){
                setState("idle");
            }
        }

        if (getCurrHealth() == 0) {
            setState("dead");
        }
    }

    public void draw(Graphics2D g2d, int tileSize, int framesSinceFireball, int framesSinceSpikes) {
        //this will keep the swing animation going after a key is released
        if (((Objects.equals(getCurrImg(), swing1)) || (Objects.equals(getCurrImg(), swing2)) || (Objects.equals(getCurrImg(), swing3))) && !(Objects.equals(getState(), "swing"))) {
            setState("swing");
        }
        if (((Objects.equals(getCurrImg(), fireSwing1)) || (Objects.equals(getCurrImg(), fireSwing2)) || (Objects.equals(getCurrImg(), fireSwing3)) || (Objects.equals(getCurrImg(), fireSwing4))) && !(Objects.equals(getState(), "casting fire spell"))) {
            setState("casting fire spell");
        }

        if (!(Objects.equals(getState(), getLastFrameState()))) {
            setFramesSinceStateChange(0);
        }
        int frameMod = getFramesSinceStateChange() % 30;
        setLastFrameState(getState());

        if (Objects.equals(getState(), "idle")) {
            if (frameMod <= 15) {
                setCurrImg(idle1);
            } else {
                setCurrImg(idle2);
            }
        }
        else if (Objects.equals(getState(), "running")) {
            if (frameMod <= 10) {
                setCurrImg(run1);
            } else if (frameMod <= 20) {
                setCurrImg(run2);
            } else {
                setCurrImg(run3);
            }
        }
        else if (Objects.equals(getState(), "swing")) {
            if (frameMod <= 10) {
                setCurrImg(swing1);
            } else if (frameMod <= 20) {
                setCurrImg(swing2);
            } else {
                setCurrImg(swing3);
            }

            if (getFramesSinceStateChange() == 30) {
                setCurrImg(idle1);
                setState("idle");
                setFramesSinceStateChange(0);
            }
        }
        else if (Objects.equals(getState(), "casting fire spell") && (framesSinceFireball < 30)) {
            if (frameMod <= 6) {
                setCurrImg(fireSwing1);
            } else if (frameMod <= 15) {
                setCurrImg(fireSwing2);
            } else if (frameMod <= 22) {
                setCurrImg(fireSwing3);
            } else {
                setCurrImg(fireSwing4);
            }

            if (getFramesSinceStateChange() == 30){
                setState("idle");
            }
        }
        else if (Objects.equals("dead", getState())) {
            if (frameMod <= 6) {
                setCurrImg(dying1);
            } else if (frameMod <= 15) {
                setCurrImg(dying2);
            } else if (frameMod <= 22) {
                setCurrImg(dying3);
            } else {
                setCurrImg(dying4);
            }

            if (getFramesSinceStateChange() > 29) {
                setCurrImg(dead);
            }
        }
        else {
            setCurrImg(idle1);
        }

        g2d.drawImage(getCurrImg(), getX(), getY(), tileSize, tileSize, null);

        setFramesSinceStateChange(getFramesSinceStateChange() + 1);
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getCurrEnergy() {
        return currEnergy;
    }

    public void setCurrEnergy(int currEnergy) {
        this.currEnergy = currEnergy;
    }
}
