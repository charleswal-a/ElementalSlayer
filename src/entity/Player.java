package entity;

import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private KeyHandler keyH;
    private int framesSinceStateChange;
    private String lastFrameState;

    private BufferedImage currImg;
    private BufferedImage idle1, idle2;
    private BufferedImage run1, run2, run3;
    private BufferedImage swing1, swing2, swing3;
    private BufferedImage dying1, dying2, dying3, dying4, dead;
    private BufferedImage fireSwing1, fireSwing2, fireSwing3, fireSwing4;

    private int maxEnergy, currEnergy;

    public Player(int x, int y, int xSpeed, int ySpeed, int currHealth, int maxHealth, int maxEnergy, KeyHandler keyH) {
        super(x, y, xSpeed, ySpeed, currHealth, maxHealth, "idle");
        this.keyH = keyH;
        this.framesSinceStateChange = 0;
        this.lastFrameState = "idle";
        this.maxEnergy = maxEnergy;
        currEnergy = maxEnergy;
        setPlayerImages();
        currImg = idle1;
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
            if (keyH.isBackwardPressed() && (backgroundX < 0)) {
                setState("running");
            } else if (keyH.isForwardPressed()) {
                setState("running");
            } else if (keyH.isMeleePressed()) {
                setState("swing");
            } else if (keyH.isFireAbilityPressed()) {
                setState("casting fire spell");
            } else {
                setState("idle");
            }
        }

        if (getCurrHealth() == 0) {
            setState("dead");
        }
    }

    public void draw(Graphics2D g2d, int tileSize, int framesSinceFireball, int framesSinceSpikes) {
        //this will keep the swing animation going after a key is released
        if (((Objects.equals(currImg, swing1)) || (Objects.equals(currImg, swing2)) || (Objects.equals(currImg, swing3))) && !(Objects.equals(getState(), "swing"))) {
            setState("swing");
        }
        if (((Objects.equals(currImg, fireSwing1)) || (Objects.equals(currImg, fireSwing2)) || (Objects.equals(currImg, fireSwing3)) || (Objects.equals(currImg, fireSwing4))) && !(Objects.equals(getState(), "casting fire spell"))) {
            setState("casting fire spell");
        }

        if (!(Objects.equals(getState(), lastFrameState))) {
            framesSinceStateChange = 0;
        }
        int frameMod = framesSinceStateChange % 30;

        if (Objects.equals(getState(), "idle")) {
            if (frameMod <= 15) {
                currImg = idle1;
            } else {
                currImg = idle2;
            }
        }
        else if (Objects.equals(getState(), "running")) {
            if (frameMod <= 10) {
                currImg = run1;
            } else if (frameMod <= 20) {
                currImg = run2;
            } else {
                currImg = run3;
            }
        }
        else if (Objects.equals(getState(), "swing")) {
            if (frameMod <= 10) {
                currImg = swing1;
            } else if (frameMod <= 20) {
                currImg = swing2;
            } else {
                currImg = swing3;
            }

            if (framesSinceStateChange == 30) {
                currImg = idle1;
                setState("idle");
            }
        }
        else if (Objects.equals(getState(), "casting fire spell") && (framesSinceFireball < 30)) {
            if (frameMod <= 6) {
                currImg = fireSwing1;
            } else if (frameMod <= 15) {
                currImg = fireSwing2;
            } else if (frameMod <= 22) {
                currImg = fireSwing3;
            } else {
                currImg = fireSwing4;
            }

            if (framesSinceStateChange == 30){
                setState("idle");
            }
        }
        else if (Objects.equals("dead", getState())) {
            if (frameMod <= 6) {
                currImg = dying1;
            } else if (frameMod <= 15) {
                currImg = dying2;
            } else if (frameMod <= 22) {
                currImg = dying3;
            } else {
                currImg = dying4;
            }

            if (framesSinceStateChange > 29) {
                currImg = dead;
            }
        }
        else {
            currImg = idle1;
        }

        g2d.drawImage(currImg, getX(), getY(), tileSize, tileSize, null);

        lastFrameState = getState();
        framesSinceStateChange++;
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
