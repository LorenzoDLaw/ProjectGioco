package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Mostro {
    private Rectangle mostro;
    private double maxWidth;
    private double maxHeight;
    private int tileSize; // Valore del tile
    private int camminaMostro; // Passo corrente nel percorso del mostro
    private double[] percorsoX; // Coordinate X del percorso
    private double[] percorsoY; // Coordinate Y del percorso
    ImageView spriteMostro;
    Image spriteImageMostro = new Image(getClass().getResourceAsStream("Immagini/SpritesMostro/idle1.png"));
    
    
    public Mostro(Rectangle mostro1, double maxWidth, double maxHeight, int tileSize, double[] percorsoX, double[] percorsoY) {
        this.mostro = mostro1;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tileSize = tileSize;
        this.camminaMostro = 0;
        this.percorsoX = percorsoX;
        this.percorsoY = percorsoY;
        
        mostro.setFill(Color.TRANSPARENT);
        mostro.setStroke(Color.TRANSPARENT);
        
        spriteMostro = new ImageView(spriteImageMostro);
    }

    public void muoviMostro() {
        // Se ci sono ancora passi nel percorso del mostro, muovi il mostro verso la prossima posizione nel percorso
        if (camminaMostro < percorsoX.length && camminaMostro < percorsoY.length) {
            double newX = percorsoX[camminaMostro]*tileSize;
            double newY = percorsoY[camminaMostro]*tileSize;

            // Controlla che il mostro rimanga all'interno dei limiti del gioco
            if (newX >= 0 && newX + mostro.getWidth() <= maxWidth && newY >= 0 && newY + mostro.getHeight() <= maxHeight) {
                mostro.setX(newX);
                mostro.setY(newY);
                camminaMostro++;
            }
            if(camminaMostro==percorsoX.length) {
            	camminaMostro=0;
            }
        }
    }
}
