package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Personaggio {
    private Rectangle personaggio;
    private ImageView spritePersonaggio;
    private double maxWidth;
    private double maxHeight;
    private double TilesSize;

    public Personaggio(Rectangle personaggio, double maxWidth, double maxHeight, ImageView spritePersonaggio, double TilesSize) {
        this.personaggio = personaggio;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.spritePersonaggio = spritePersonaggio;
        this.TilesSize = TilesSize;
     // Imposta il colore di riempimento trasparente
        personaggio.setFill(Color.TRANSPARENT);
        // Imposta il colore del contorno trasparente
        personaggio.setStroke(Color.TRANSPARENT);

    }

    public void muovi(KeyEvent e) {
        double deltaX = 0;
        double deltaY = 0;
        double Xplayer = personaggio.getX();
        double Yplayer = personaggio.getY();

        if (e.getText().equals("w")) {
            Yplayer +=  -TilesSize;
        }
        if (e.getText().equals("s")) {
            Yplayer += TilesSize;
        }
        if (e.getText().equals("a")) {
        	Xplayer += -TilesSize;
        }
        if (e.getText().equals("d")) {
            Xplayer += TilesSize;
        }

        // Controlla i bordi e limita il movimento all'interno dell'area di gioco
        if (Xplayer >= 0 && Xplayer + personaggio.getWidth() <= maxWidth) {
            personaggio.setX(Xplayer);
            spritePersonaggio.setX(Xplayer);
        }
        if (Yplayer >= 0 && Yplayer + personaggio.getHeight() <= maxHeight) {
            personaggio.setY(Yplayer);
            spritePersonaggio.setY(Yplayer);
        }
    }
}
