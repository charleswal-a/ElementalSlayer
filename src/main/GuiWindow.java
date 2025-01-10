package main;

import javax.swing.JFrame;

public class GuiWindow {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("-----====| Dungeon Crawler |====-----");

        GuiPanel panel = new GuiPanel();
        frame.add(panel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.startGameThread();
    }
}