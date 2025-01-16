package main;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import entity.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.util.Objects;

public class GuiPanel extends JPanel implements Runnable {
    // Variables that determine the GUI display dimensions
    private final int ORIGINAL_TILE_SIZE = 16;
    private final int SCALE = 9;
    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private final int MAX_SCREEN_COL = 8;
    private final int MAX_SCREEN_ROW = 4;

    // Variables that handle user input and running game
    private final KeyHandler KEY_H = new KeyHandler();
    private Thread gameThread;

    // Variables that are used in UI and environment
    private BufferedImage foreground, background1, background2, background3, background4;
    private BufferedImage emptyBar, heartSym, energySym;

    // Variables that keep track of entities
    private ArrayList<Entity> enemies;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Spikes> spikes;
    private ArrayList<Icicle> icicles;
    private ArrayList<Lightning> bolts;
    private Player p;

    // Variables that handle and count frames
    private final int FPS = 60;
    private int frameCount;
    private int framesSinceFireball, framesSinceSpikes, framesSinceIcicles, framesSinceLightning;

    // Variables that handle environment scrolling
    private int foregroundX, background1X, background2X, background3X, background4X;

    public GuiPanel() {
        // Sets basic characteristics of the panel
        this.setPreferredSize(new Dimension(TILE_SIZE * MAX_SCREEN_COL, TILE_SIZE * MAX_SCREEN_ROW));
        this.setDoubleBuffered(true);
        this.addKeyListener(KEY_H);
        this.setFocusable(true);
        this.setBackground(new Color(125, 184, 228));
        setValues();
    }

    public void setValues() {
        // Calls method to reset the game
        resetGame();

        // Sets all images that will be used in the environment and UI
        try {
            foreground = ImageIO.read(getClass().getResourceAsStream("/Scenery Sprites/Foreground.png"));
            background1 = ImageIO.read(getClass().getResourceAsStream("/Scenery Sprites/Background_Layer_1.png"));
            background2 = ImageIO.read(getClass().getResourceAsStream("/Scenery Sprites/Background_Layer_2.png"));
            background3 = ImageIO.read(getClass().getResourceAsStream("/Scenery Sprites/Background_Layer_3.png"));
            background4 = ImageIO.read(getClass().getResourceAsStream("/Scenery Sprites/Background_Layer_4.png"));
            emptyBar = ImageIO.read(getClass().getResourceAsStream("/UI Sprites/Empty_Bar.png"));
            heartSym = ImageIO.read(getClass().getResourceAsStream("/UI Sprites/Heart_Symbol.png"));
            energySym = ImageIO.read(getClass().getResourceAsStream("/UI Sprites/Energy_Symbol.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        // Instantiates new player object and entity object lists to empty arraylists
        p = new Player(100, (int) (2.75 * TILE_SIZE), 5, 0, 100, 100, 100, KEY_H);
        enemies = new ArrayList<Entity>();
        fireballs = new ArrayList<Fireball>();
        spikes = new ArrayList<Spikes>();
        icicles = new ArrayList<Icicle>();
        bolts = new ArrayList<Lightning>();

        Goblin g = new Goblin(700, p.getY(), 3, KEY_H, p);
        enemies.add(g);

        // Resets frame counter variables
        frameCount = 0;
        framesSinceFireball = 500;
        framesSinceSpikes = 500;
        framesSinceIcicles = 500;
        framesSinceLightning = 500;

        // Resets the locations of the background image locations
        foregroundX = 0;
        background1X = 0;
        background2X = 0;
        background3X = 0;
        background4X = 0;
    }

    public void startGameThread() {
        // Assigns new game thread and starts it
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draws all background image layers
        g2d.drawImage(background4, background4X, 0, TILE_SIZE * MAX_SCREEN_COL * 4, TILE_SIZE * MAX_SCREEN_ROW, null);
        g2d.drawImage(background3, background3X, 0, TILE_SIZE * MAX_SCREEN_COL * 4, TILE_SIZE * MAX_SCREEN_ROW, null);
        g2d.drawImage(background2, background2X, 0, TILE_SIZE * MAX_SCREEN_COL * 4, TILE_SIZE * MAX_SCREEN_ROW, null);
        g2d.drawImage(background1, background1X, 0, TILE_SIZE * MAX_SCREEN_COL * 4, TILE_SIZE * MAX_SCREEN_ROW, null);

        // Draws the player
        p.draw(g2d, TILE_SIZE, framesSinceFireball, framesSinceSpikes);

        // Draws every spell in the spell array lists
        for (Fireball f : fireballs) {
            f.draw(g2d, TILE_SIZE);
        }
        for (Spikes s : spikes) {
            s.draw(g2d, TILE_SIZE);
        }
        for (Icicle i : icicles) {
            i.draw(g2d, TILE_SIZE);
        }
        for (Lightning l : bolts) {
            l.draw(g2d, TILE_SIZE);
        }

        for (Entity e : enemies) {
            e.draw(g2d, TILE_SIZE);
        }

        // Draws the foreground image layer
        g2d.drawImage(foreground, foregroundX, 0, TILE_SIZE * MAX_SCREEN_COL * 8, TILE_SIZE * MAX_SCREEN_ROW, null);

        // Draws the player UI
        g2d.drawImage(emptyBar, 20, 20, 320, 64, null);
        g2d.setColor(new Color(148, 1, 7));
        g2d.fill(new Rectangle2D.Double(76, 48, ((double) p.getCurrHealth())/p.getMaxHealth() * 236, 8));

        g2d.drawImage(emptyBar, 20, 80, 320, 64, null);
        g2d.setColor(new Color(255, 219, 49));
        g2d.fill(new Rectangle2D.Double(76, 108, ((double) p.getCurrEnergy())/p.getMaxEnergy() * 236, 8));

        g2d.drawImage(heartSym, 20, 20, 320, 64, null);
        g2d.drawImage(energySym, 20, 80, 320, 64, null);

        g2d.dispose();
    }

    public void update() {
        // Updates the player object
        p.update(background1X);

        // Increments each frame counter variable
        frameCount++;
        framesSinceFireball++;
        framesSinceSpikes++;
        framesSinceIcicles++;
        framesSinceLightning++;

        // Moves the background/foreground images based on key input and in-game bounds
        if (KEY_H.isBackwardPressed() && (foregroundX != 0)) {
            foregroundX += 8;
            background1X += 5;
            background2X += 3;
            background3X += 2;
            background4X += 1;
        }
        else if (KEY_H.isForwardPressed()) {
            foregroundX -= 8;
            background1X -= 5;
            background2X -= 3;
            background3X -= 2;
            background4X -= 1;
        }

        // Handles each spell that was cast if energy allows
        else if (p.getCurrEnergy() - 30 >= 0) {
            if (KEY_H.isFireAbilityPressed() && (framesSinceFireball >= 100)) {
                fireballs.add(new Fireball(p.getX(), p.getY(), 7, KEY_H, p));
                framesSinceFireball = 0;
                p.setCurrEnergy(p.getCurrEnergy() - 30);
            }
            else if (KEY_H.isEarthAbilityPressed() && (framesSinceSpikes >= 100)) {
                spikes.add(new Spikes(p.getX() + TILE_SIZE, p.getY(), KEY_H, p));
                framesSinceSpikes = 0;
                p.setCurrEnergy(p.getCurrEnergy() - 30);
            }
            else if (KEY_H.isIceAbilityPressed() && (framesSinceIcicles >= 100)) {
                for (int i = 1; i < 7; i++) {
                    icicles.add(new Icicle(p.getX() + (i * 96), 50, 8, KEY_H, p));
                }
                framesSinceIcicles = 0;
                p.setCurrEnergy(p.getCurrEnergy() - 30);
            }
            else if (KEY_H.isSkyAbilityPressed() && (framesSinceLightning >= 100)) {
                // !!!!! Change this to auto target enemies !!!!
                bolts.add(new Lightning(p.getX() + 200, 0,  KEY_H, p));
                framesSinceLightning = 0;
                p.setCurrEnergy(p.getCurrEnergy() - 30);
            }
        }

        for (int i = 0; i < fireballs.size(); i++) {
            if (Objects.equals("gone", fireballs.get(i).getState())) {
                fireballs.remove(fireballs.get(i));
                i--;
            } else {
                fireballs.get(i).update(background1X);
            }
        }
        for (int i = 0; i < spikes.size(); i++) {
            if (Objects.equals("gone", spikes.get(i).getState())) {
                spikes.remove(spikes.get(i));
                i--;
            } else {
                spikes.get(i).update(background1X);
            }
        }
        for (int i = 0; i < icicles.size(); i++) {
            if (Objects.equals("gone", icicles.get(i).getState())) {
                icicles.remove(icicles.get(i));
                i--;
            } else {
                icicles.get(i).update(background1X);
            }
        }
        for (int i = 0; i < bolts.size(); i++) {
            if (Objects.equals("gone", bolts.get(i).getState())) {
                bolts.remove(bolts.get(i));
                i--;
            } else {
                bolts.get(i).update(background1X);
            }
        }

        for (Entity e : enemies) {
            e.update(background1X, TILE_SIZE);
        }

        if ((frameCount % 20 == 0) && (p.getCurrEnergy() + 2 <= p.getMaxEnergy())) {
            p.setCurrEnergy(p.getCurrEnergy() + 2);
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
