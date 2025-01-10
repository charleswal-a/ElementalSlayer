package main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    // variables that store condition for each action
    private boolean upPressed, forwardPressed, backwardPressed;
    private boolean meleePressed;
    private boolean earthAbilityPressed, fireAbilityPressed, iceAbilityPressed, skyAbilityPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // function exists only for override
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            backwardPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            forwardPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            meleePressed = true;
        }
        if (code == KeyEvent.VK_A) {
            earthAbilityPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            fireAbilityPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            iceAbilityPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            skyAbilityPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            backwardPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            forwardPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            meleePressed = false;
        }
        if (code == KeyEvent.VK_A) {
            earthAbilityPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            fireAbilityPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            iceAbilityPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            skyAbilityPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isForwardPressed() {
        return forwardPressed;
    }

    public boolean isBackwardPressed() {
        return backwardPressed;
    }

    public boolean isMeleePressed() {
        return meleePressed;
    }

    public boolean isEarthAbilityPressed() {
        return earthAbilityPressed;
    }

    public boolean isFireAbilityPressed() {
        return fireAbilityPressed;
    }

    public boolean isSkyAbilityPressed() {
        return skyAbilityPressed;
    }

    public boolean isIceAbilityPressed() {
        return iceAbilityPressed;
    }
}
