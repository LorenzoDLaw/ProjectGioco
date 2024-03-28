package it.edu.iisguiio;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Movimento {
    private Rectangle personaggio;
    private ImageView spritePersonaggio;
    private double maxWidth;
    private double maxHeight;
    private double TilesSize;

    public Movimento(Rectangle personaggio, double maxWidth, double maxHeight, ImageView spritePersonaggio, double TilesSize) {
        this.personaggio = personaggio;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.spritePersonaggio = spritePersonaggio;
        this.TilesSize = TilesSize;
    }

    public void premiTasto(KeyEvent e) {
        double deltaX = 0;
        double deltaY = 0;
        double Xplayer = personaggio.getX();
        double Yplayer = personaggio.getY();

        if (e.getText().equals("w")) {
            deltaY = -TilesSize; // Sposta verso l'alto
            Yplayer += deltaY;
        } else if (e.getText().equals("s")) {
            deltaY = TilesSize; // Sposta verso il basso
            Yplayer += deltaY;
        } else if (e.getText().equals("a")) {
            deltaX = -TilesSize; // Sposta verso sinistra
            Xplayer += deltaX;
        } else if (e.getText().equals("d")) {
            deltaX = TilesSize; // Sposta verso destra
            Xplayer += deltaX;
        }

        // Controlla i bordi e limita il movimento all'interno dell'area di gioco
        if (Xplayer >= 0 && Xplayer + personaggio.getWidth() <= maxWidth) {
            personaggio.setX(Xplayer);
            //spritePersonaggio.setX(Xplayer);
        }
        if (Yplayer >= 0 && Yplayer + personaggio.getHeight() <= maxHeight) {
            personaggio.setY(Yplayer);
            //spritePersonaggio.setY(Yplayer);
        }
    }
}
