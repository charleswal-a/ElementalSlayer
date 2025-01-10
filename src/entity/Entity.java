package entity;

import java.awt.*;

public class Entity {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int currHealth;
    private int maxHealth;
    private String state;

    public Entity(int x, int y, int xSpeed, int ySpeed, int currHealth, int maxHealth, String state) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.currHealth = currHealth;
        this.maxHealth = maxHealth;
        this.state = state;
    }

    public void update(int backgroundX) {

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
}
