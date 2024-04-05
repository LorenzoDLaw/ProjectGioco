package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Personaggio {
    private Rectangle personaggio;
    public ImageView spritePersonaggio;
    private double maxWidth;
    private double maxHeight;
    private double TilesSize;
    boolean walkUp = true;
    boolean walkDown = true;
    boolean walkLeft = true;
    boolean walkRigth = true;
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
    Image walk_down_1 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_down_1.png"));
    Image walk_down_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_down_2.png"));
    Image walk_up_1 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_up_1.png"));
    Image walk_up_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_up_2.png"));
    Image walk_left_1 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_left_1.png"));
    Image walk_left_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_left_2.png"));
    Image walk_right_1 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_right_1.png"));
    Image walk_right_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_right_2.png"));
    public void muovi(KeyEvent e) {
        double deltaX = 0;
        double deltaY = 0;
        double Xplayer = personaggio.getX();
        double Yplayer = personaggio.getY();
        if (e.getText().equals("w")) {
        	Yplayer +=  -TilesSize;
        	if(walkUp==false) {
        		spritePersonaggio.setImage(walk_up_2);
        		walkUp=true;
        	}else {
        		spritePersonaggio.setImage(walk_up_1);
        		walkUp=false;
        	}
        }
        if (e.getText().equals("s")) {
            Yplayer += TilesSize;
            if(walkDown==false) {
            	walkDown=true;
        		spritePersonaggio.setImage(walk_down_2);
        	}else {
        		walkDown=false;
        		spritePersonaggio.setImage(walk_down_1);
        	}
            
        }
        if (e.getText().equals("a")) {
        	Xplayer += -TilesSize;
        	if(walkLeft==false) {
        		spritePersonaggio.setImage(walk_left_2);
        		walkLeft=true;
        	}else {
        		walkLeft=false;
        		spritePersonaggio.setImage(walk_left_1);
        	}
        }
        if (e.getText().equals("d")) {
            Xplayer += TilesSize;
            if(walkRigth==false) {
        		spritePersonaggio.setImage(walk_right_2);
        		walkRigth=true;
        	}else {
        		spritePersonaggio.setImage(walk_right_1);
        		walkRigth=false;
        	}
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
    public void attacca(MouseEvent e) {
        double deltaX = 0;
        double deltaY = 0;
        double Xplayer = personaggio.getX();
        double Yplayer = personaggio.getY();
        if (e.getText().equals("w")) {
        	Yplayer +=  -TilesSize;
        	if(walkUp==false) {
        		spritePersonaggio.setImage(walk_up_2);
        		walkUp=true;
        	}else {
        		spritePersonaggio.setImage(walk_up_1);
        		walkUp=false;
        	}
        }
        if (e.getText().equals("s")) {
            Yplayer += TilesSize;
            if(walkDown==false) {
            	walkDown=true;
        		spritePersonaggio.setImage(walk_down_2);
        	}else {
        		walkDown=false;
        		spritePersonaggio.setImage(walk_down_1);
        	}
            
        }
        if (e.getText().equals("a")) {
        	Xplayer += -TilesSize;
        	if(walkLeft==false) {
        		spritePersonaggio.setImage(walk_left_2);
        		walkLeft=true;
        	}else {
        		walkLeft=false;
        		spritePersonaggio.setImage(walk_left_1);
        	}
        }
        if (e.getText().equals("d")) {
            Xplayer += TilesSize;
            if(walkRigth==false) {
        		spritePersonaggio.setImage(walk_right_2);
        		walkRigth=true;
        	}else {
        		spritePersonaggio.setImage(walk_right_1);
        		walkRigth=false;
        	}
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
