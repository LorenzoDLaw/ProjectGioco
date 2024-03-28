package it.edu.iisguiio;

import javafx.scene.shape.Rectangle;

public class Mostro {
    private Rectangle mostro;
    private double maxWidth;
    private double maxHeight;
    private int tileSize; // Valore del tile
    private int passoCorrente; // Passo corrente nel percorso del mostro
    private double[] percorsoX; // Coordinate X del percorso
    private double[] percorsoY; // Coordinate Y del percorso

    public Mostro(Rectangle mostro, double maxWidth, double maxHeight, int tileSize, double[] percorsoX, double[] percorsoY) {
        this.mostro = mostro;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tileSize = tileSize;
        this.passoCorrente = 0;
        this.percorsoX = percorsoX;
        this.percorsoY = percorsoY;
    }

    public void muoviMostro() {
        // Se ci sono ancora passi nel percorso del mostro, muovi il mostro verso la prossima posizione nel percorso
        if (passoCorrente < percorsoX.length && passoCorrente < percorsoY.length) {
            double newX = percorsoX[passoCorrente];
            double newY = percorsoY[passoCorrente];

            // Controlla che il mostro rimanga all'interno dei limiti del gioco
            if (newX >= 0 && newX + mostro.getWidth() <= maxWidth && newY >= 0 && newY + mostro.getHeight() <= maxHeight) {
                mostro.setX(newX);
                mostro.setY(newY);
                passoCorrente++;
            }
        }
    }
}
