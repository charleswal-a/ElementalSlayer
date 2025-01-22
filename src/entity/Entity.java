package entity;

import main.KeyHandler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Entity {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int currHealth;
    private int maxHealth;
    private String state;
    private KeyHandler keyH;
    private Player p;
    private int framesSinceStateChange;
    private String lastFrameState;
    private BufferedImage currImg;

    public Entity(int x, int y, int xSpeed, int ySpeed, int maxHealth, String state, KeyHandler keyH, Player p) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.currHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.state = state;
        this.keyH = keyH;
        this.p = p;
        this.lastFrameState = state;
        framesSinceStateChange = 0;
    }

    public void update(int backgroundX) {

    }

    public void update(int backgroundX, int tileSize) {
        // This is the update method overriden to be used for path finding
    }

    public void draw(Graphics2D g2d, int tileSize) {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        if (currHealth < 0) {
            currHealth = 0;
        }
        this.currHealth = currHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getFramesSinceStateChange() {
        return framesSinceStateChange;
    }

    public void setFramesSinceStateChange(int framesSinceStateChange) {
        this.framesSinceStateChange = framesSinceStateChange;
    }

    public String getLastFrameState() {
        return lastFrameState;
    }

    public void setLastFrameState(String lastFrameState) {
        this.lastFrameState = lastFrameState;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public Player getP() {
        return p;
    }

    public BufferedImage getCurrImg() {
        return currImg;
    }

    public void setCurrImg(BufferedImage currImg) {
        this.currImg = currImg;
    }

    public void drawHealthBar(Graphics2D g2d, int tileSize) {
        g2d.setColor(new Color(74, 6, 20));
        g2d.fill(new Rectangle2D.Double(x + tileSize * 0.25, y - tileSize * 0.1, tileSize * 0.5, tileSize / 24));
        g2d.setColor(new Color(0, 91, 17));
        g2d.fill(new Rectangle2D.Double(x + tileSize * 0.25, y - tileSize * 0.1, tileSize * 0.5 * getCurrHealth() / getMaxHealth(), tileSize / 24));
    }

    public int moveCamera(int backgroundX) {
        int newX = x;
        if (keyH.isBackwardPressed() && (backgroundX < 0)) {
            newX += p.getxSpeed();
        }
        else if (keyH.isForwardPressed()) {
            newX -= p.getxSpeed();
        }
        return newX;
    }
}
