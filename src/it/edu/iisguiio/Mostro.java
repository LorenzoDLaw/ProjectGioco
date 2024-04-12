package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Mostro {
    public Rectangle mostro;
    public ImageView spriteMostro;
    private double maxWidth;
    private double maxHeight;
    private int tileSize; // Valore del tile
    private int camminaMostro; // Passo corrente nel percorso del mostro
    private double[] percorsoX; // Coordinate X del percorso
    private double[] percorsoY; // Coordinate Y del percorso
    
    
    public Mostro(Rectangle mostro1, double maxWidth, double maxHeight, int tileSize, double[] percorsoX, double[] percorsoY,ImageView spriteMostro) {
        this.mostro = mostro1;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tileSize = tileSize;
        this.camminaMostro = 0;
        this.percorsoX = percorsoX;
        this.percorsoY = percorsoY;
        this.spriteMostro = spriteMostro;
        // Imposta il colore di riempimento trasparente
        mostro.setFill(Color.TRANSPARENT);
        // Imposta il colore del contorno trasparente
        
    }
    Image walk_up = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_up_1.png"));

    public void muoviMostro() {
        // Se ci sono ancora passi nel percorso del mostro, muovi il mostro verso la prossima posizione nel percorso
        spriteMostro.setImage(walk_up);
        if (camminaMostro < percorsoX.length && camminaMostro < percorsoY.length) {
            double newX = percorsoX[camminaMostro] * tileSize;
            double newY = percorsoY[camminaMostro] * tileSize;
            // Controlla che il mostro rimanga all'interno dei limiti del gioco
            if (newX >= 0 && newX + mostro.getWidth() <= maxWidth && newY >= 0 && newY + mostro.getHeight() <= maxHeight) {
                mostro.setX(newX);
                mostro.setY(newY);
                // Imposta la posizione dell'ImageView correttamente
                spriteMostro.setX(newX);
                spriteMostro.setY(newY);
                camminaMostro++;
            }
            if (camminaMostro == percorsoX.length) {
                camminaMostro = 0;
            }
        }
    }

}