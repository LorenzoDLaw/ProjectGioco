package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Mostro {
	public Rectangle personaggio;
    public Rectangle mostro1;
    public Rectangle mostro2;
    public Rectangle mostro3;
    public ImageView spriteMostro1;
    public ImageView spriteMostro2;
    public ImageView spriteMostro3;
    private double maxWidth;
    private double maxHeight;
    private int tileSize; // Valore del tile
    
    // booleane per la direzione del mostro
    boolean walkDownImg = false;
    boolean walkLeftImg = false;
    boolean walkRightImg = false;
    boolean walkUpImg = false;
    
    boolean walkUpM1 = false;
    boolean walkDownM1 = false;
    boolean walkLeftM1 = false;
    boolean walkRightM1 = false;
    boolean walkUpM2 = false;
    boolean walkDownM2 = false;
    boolean walkLeftM2 = false;
    boolean walkRightM2 = false;
    boolean walkUpM3 = false;
    boolean walkDownM3 = false;
    boolean walkLeftM3 = false;
    boolean walkRightM3 = false;
    
    boolean personaggioDavanti=false;

    // coordinate del mostro per calcolare il suo spostamento
    double xPrecedenteM1 = 0;
    double yPrecedenteM1 = 0;
    double xPrecedenteM2 = 0;
    double yPrecedenteM2 = 0;
    double xPrecedenteM3 = 0;
    double yPrecedenteM3 = 0;

    public Mostro(Rectangle rMostro1, Rectangle rMostro2, Rectangle rMostro3, double maxWidth, double maxHeight,
            int tileSize, ImageView spriteMostro1, ImageView spriteMostro2,
            ImageView spriteMostro3, Rectangle personaggio) {
        this.mostro1 = rMostro1;
        this.mostro2 = rMostro2;
        this.mostro3 = rMostro3;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tileSize = tileSize;
        this.spriteMostro1 = spriteMostro1;
        this.spriteMostro2 = spriteMostro2;
        this.spriteMostro3 = spriteMostro3;
        this.personaggio = personaggio;
    }

    // tutte le immagini che utilizzo per la camminata
    Image walk_up_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_up_1.png"));
    Image walk_up_2 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_up_2.png"));
    Image walk_down_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    Image walk_down_2 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    Image walk_left_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_left_1.png"));
    Image walk_left_2 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_left_2.png"));
    Image walk_right_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_right_1.png"));
    Image walk_right_2 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_right_2.png"));
    
    Image attacco_down_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_attack_down_2.png"));
    Image attacco_up_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_attack_up_2.png"));
    Image attacco_left_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_attack_left_2.png"));
    Image attacco_right_1 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_attack_right_2.png"));

    public void muoviMostro() {
        mostro1();
        mostro2();
        mostro3();
    }

	public void mostro1() {
        // Dentro il ciclo di aggiornamento del gioco
        int direction = (int) (Math.random() * 4) + 1; // Genera un numero casuale da 1 a 4

        double newX = mostro1.getX();
        double newY = mostro1.getY();

        // Determina la nuova posizione in base alla direzione casuale
        switch (direction) {
        case 1: // Su
            newY -= tileSize;
            break;
        case 2: // Giù
            newY += tileSize;
            break;
        case 3: // Destra
            newX += tileSize;
            break;
        case 4: // Sinistra
            newX -= tileSize;
            break;
        }

        // Controlla che il mostro rimanga all'interno dei limiti del gioco
        if (newX >= 0 && newX + mostro1.getWidth() <= maxWidth && newY >= 0
                && newY + mostro1.getHeight() <= maxHeight) {
            mostro1.setX(newX);
            mostro1.setY(newY);
            // Imposta la posizione dell'ImageView correttamente
            spriteMostro1.setX(newX);
            spriteMostro1.setY(newY);
        }

        double deltaX = mostro1.getX() - xPrecedenteM1;
        double deltaY = mostro1.getY() - yPrecedenteM1;
        // viene utilizzato per capire la direzione del mostro in base alla sua posizione
        // precedente
        // poi inserisco lo sprite in base alla direzione
        if (deltaY < 0) {
        	walkUpM1=true;
            walkDownM1 = false;
            walkLeftM1 = false;
            walkRightM1 = false;
            // Il mostro si muove verso l'alto
            if (!walkUpImg) {
                walkUpImg = true;
                spriteMostro1.setImage(walk_up_2);
            } else {
                walkUpImg = false;
                spriteMostro1.setImage(walk_up_1);
            }
        } 
        if (deltaY > 0) {
        	walkUpM1= false;
            walkDownM1 = true;
            walkLeftM1 = false;
            walkRightM1 = false;
            // Il mostro si muove verso il basso
            if (!walkDownImg) {
                walkDownImg = true;
                spriteMostro1.setImage(walk_down_2);
            } else {
                walkDownImg = false;
                spriteMostro1.setImage(walk_down_1);
            }
        } 
        if (deltaX < 0) {
        	walkUpM1 = false;
            walkDownM1 = false;
            walkLeftM1 = true;
            walkRightM1 = false;
            // Il mostro si muove verso sinistra
            if (!walkLeftImg) {
                walkLeftImg = true;
                spriteMostro1.setImage(walk_left_2);
            } else {
                walkLeftImg = false;
                spriteMostro1.setImage(walk_left_1);
            }
        } 
        if (deltaX > 0) {
        	walkUpM1 = false;
            walkDownM1 = false;
            walkLeftM1 = false;
            walkRightM1 = true;
            // Il mostro si muove verso destra
            if (!walkRightImg) {
                walkRightImg = true;
                spriteMostro1.setImage(walk_right_2);
            } else {
                walkRightImg = false;
                spriteMostro1.setImage(walk_right_1);
            }
        }
        // Aggiorna le coordinate precedenti
        xPrecedenteM1 = mostro1.getX();
        yPrecedenteM1 = mostro1.getY();
    }

    public void mostro2() {
        // Dentro il ciclo di aggiornamento del gioco
        int direction = (int) (Math.random() * 4) + 1; // Genera un numero casuale da 1 a 4

        double newX = mostro2.getX();
        double newY = mostro2.getY();

        // Determina la nuova posizione in base alla direzione casuale
        switch (direction) {
        case 1: // Su
            newY -= tileSize;
            break;
        case 2: // Giù
            newY += tileSize;
            break;
        case 3: // Destra
            newX += tileSize;
            break;
        case 4: // Sinistra
            newX -= tileSize;
            break;
        }

        // Controlla che il mostro rimanga all'interno dei limiti del gioco
        if (newX >= 0 && newX + mostro2.getWidth() <= maxWidth && newY >= 0
                && newY + mostro2.getHeight() <= maxHeight) {
            mostro2.setX(newX);
            mostro2.setY(newY);
            // Imposta la posizione dell'ImageView correttamente
            spriteMostro2.setX(newX);
            spriteMostro2.setY(newY);
        }
        double deltaX = mostro2.getX() - xPrecedenteM2;
        double deltaY = mostro2.getY() - yPrecedenteM2;
        // viene utilizzato per capire la direzione del mostro in base alla sua posizione
        // precedente
        // poi inserisco lo sprite in base alla direzione
        if (deltaY < 0) {
            // Il mostro si muove verso l'alto
            if (!walkUpImg) {
                walkUpImg = true;
                spriteMostro2.setImage(walk_up_2);
            } else {
                walkUpImg = false;
                spriteMostro2.setImage(walk_up_1);
            }
        } else if (deltaY > 0) {
            // Il mostro si muove verso il basso
            if (!walkDownImg) {
                walkDownImg = true;
                spriteMostro2.setImage(walk_down_2);
            } else {
                walkDownImg = false;
                spriteMostro2.setImage(walk_down_1);
            }
        } else if (deltaX < 0) {
            // Il mostro si muove verso sinistra
            if (!walkLeftImg) {
                walkLeftImg = true;
                spriteMostro2.setImage(walk_left_2);
            } else {
                walkLeftImg = false;
                spriteMostro2.setImage(walk_left_1);
            }
        } else if (deltaX > 0) {
            // Il mostro si muove verso destra
            if (!walkRightImg) {
                walkRightImg = true;
                spriteMostro2.setImage(walk_right_2);
            } else {
                walkRightImg = false;
                spriteMostro2.setImage(walk_right_1);
            }
        }
        // Aggiorna le coordinate precedenti
        xPrecedenteM2 = mostro2.getX();
        yPrecedenteM2 = mostro2.getY();
    }

    public void mostro3() {
        // Dentro il ciclo di aggiornamento del gioco
        int direction = (int) (Math.random() * 4) + 1; // Genera un numero casuale da 1 a 4

        double newX = mostro3.getX();
        double newY = mostro3.getY();

        // Determina la nuova posizione in base alla direzione casuale
        switch (direction) {
        case 1: // Su
            newY -= tileSize;
            break;
        case 2: // Giù
            newY += tileSize;
            break;
        case 3: // Destra
            newX += tileSize;
            break;
        case 4: // Sinistra
            newX -= tileSize;
            break;
        }

        // Controlla che il mostro rimanga all'interno dei limiti del gioco
        if (newX >= 0 && newX + mostro3.getWidth() <= maxWidth && newY >= 0
                && newY + mostro3.getHeight() <= maxHeight) {
            mostro3.setX(newX);
            mostro3.setY(newY);
            // Imposta la posizione dell'ImageView correttamente
            spriteMostro3.setX(newX);
            spriteMostro3.setY(newY);
        }

        double deltaX = mostro3.getX() - xPrecedenteM3;
        double deltaY = mostro3.getY() - yPrecedenteM3;
        // viene utilizzato per capire la direzione del mostro in base alla sua posizione
        // precedente
        // poi inserisco lo sprite in base alla direzione
        if (deltaY < 0) {
            // Il mostro si muove verso l'alto
            if (!walkUpImg) {
                walkUpImg = true;
                spriteMostro3.setImage(walk_up_2);
            } else {
                walkUpImg = false;
                spriteMostro3.setImage(walk_up_1);
            }
        } else if (deltaY > 0) {
            // Il mostro si muove verso il basso
            if (!walkDownImg) {
                walkDownImg = true;
                spriteMostro3.setImage(walk_down_2);
            } else {
                walkDownImg = false;
                spriteMostro3.setImage(walk_down_1);
            }
        } else if (deltaX < 0) {
            // Il mostro si muove verso sinistra
            if (!walkLeftImg) {
                walkLeftImg = true;
                spriteMostro3.setImage(walk_left_2);
            } else {
                walkLeftImg = false;
                spriteMostro3.setImage(walk_left_1);
            }
        } else if (deltaX > 0) {
            // Il mostro si muove verso destra
            if (!walkRightImg) {
                walkRightImg = true;
                spriteMostro3.setImage(walk_right_2);
            } else {
                walkRightImg = false;
                spriteMostro3.setImage(walk_right_1);
            }
        }
        // Aggiorna le coordinate precedenti
        xPrecedenteM3 = mostro3.getX();
        yPrecedenteM3 = mostro3.getY();
    }

}