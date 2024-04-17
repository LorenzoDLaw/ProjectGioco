package it.edu.iisguiio;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    char lastDirection = 'w';
    boolean HoAttaccato = false;
    public Personaggio(Rectangle personaggio, double maxWidth, double maxHeight, ImageView spritePersonaggio, double TilesSize) {
        this.personaggio = personaggio;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.spritePersonaggio = spritePersonaggio;
        this.TilesSize = TilesSize;;
        // Imposta il colore di riempimento trasparente
        personaggio.setFill(Color.YELLOW);
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
    Image attack_down_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteAttacco/attacco_down_2.png"));
    Image attack_up_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteAttacco/attacco_up_2.png"));
    Image attack_left_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteAttacco/attacco_left_2.png"));
    Image attack_right_2 = new Image(getClass().getResourceAsStream("Immagini/SpriteAttacco/attacco_right_2.png"));
    
    public void muovi(KeyEvent e) {
        double Xplayer = personaggio.getX();
        double Yplayer = personaggio.getY();
        if (e.getText().equals("w")) {
        	lastDirection = 'w';
        	if(HoAttaccato==true) {
        		personaggio.setWidth(TilesSize);
            	spritePersonaggio.setFitWidth(TilesSize);
            	personaggio.setHeight(TilesSize);
                spritePersonaggio.setFitHeight(TilesSize);
                Yplayer += TilesSize;
        	}
            Yplayer -= TilesSize;
        	if(walkUp==false) {
        		spritePersonaggio.setImage(walk_up_2);
        		walkUp=true;
        	}else {
        		spritePersonaggio.setImage(walk_up_1);
        		walkUp=false;
        	}
        	HoAttaccato=false;
        }
        if (e.getText().equals("s")) {
        	if(HoAttaccato==true) {
	        	personaggio.setWidth(TilesSize);
	        	spritePersonaggio.setFitWidth(TilesSize);
	        	personaggio.setHeight(TilesSize);
	            spritePersonaggio.setFitHeight(TilesSize);
	            if(lastDirection=='a') {
	            	Xplayer += TilesSize;
	            }
        	}
        	lastDirection = 's';
        	Yplayer += TilesSize;
            if(walkDown==false) {
            	walkDown=true;
        		spritePersonaggio.setImage(walk_down_2);
        	}else {
        		walkDown=false;
        		spritePersonaggio.setImage(walk_down_1);
        	}
            HoAttaccato=false;
        }
        if (e.getText().equals("a")) {
        	if (HoAttaccato==true) {
            	personaggio.setWidth(TilesSize);
            	spritePersonaggio.setFitWidth(TilesSize);
            	personaggio.setHeight(TilesSize);
                spritePersonaggio.setFitHeight(TilesSize);
                Xplayer += TilesSize;
                if(lastDirection=='w') {
                	Yplayer +=TilesSize;
                }
        	}
        	lastDirection = 'a';
        	Xplayer -= TilesSize;
        	if(walkLeft==false) {
        		spritePersonaggio.setImage(walk_left_2);
        		walkLeft=true;
        	}else {
        		walkLeft=false;
        		spritePersonaggio.setImage(walk_left_1);
        	}
        	HoAttaccato=false;
        }
        if (e.getText().equals("d")) {
        	if (HoAttaccato==true) {
        		personaggio.setWidth(TilesSize);
            	spritePersonaggio.setFitWidth(TilesSize);
            	personaggio.setHeight(TilesSize);
                spritePersonaggio.setFitHeight(TilesSize);
                if(lastDirection=='w') {
                	Yplayer += TilesSize;
                }
        	}
        	lastDirection = 'd';
            Xplayer += TilesSize;
            if(walkRigth==false) {
        		spritePersonaggio.setImage(walk_right_2);
        		walkRigth=true;
        	}else {
        		spritePersonaggio.setImage(walk_right_1);
        		walkRigth=false;
        	}
            HoAttaccato=false;
            
        }
        if (e.getCode() == KeyCode.ENTER){
        	HoAttaccato=true;
        	if(lastDirection == 'w') {
        		Yplayer -=TilesSize;
        		personaggio.setHeight(TilesSize*2);
        		spritePersonaggio.setFitHeight(TilesSize*2);
        		spritePersonaggio.setImage(attack_up_2);
        	}
        	if(lastDirection == 's') {
        		personaggio.setHeight(TilesSize*2);
        		spritePersonaggio.setFitHeight(TilesSize*2);
        		spritePersonaggio.setImage(attack_down_2);
        	}
        	if(lastDirection == 'a') {
        		Xplayer -= TilesSize;
        		personaggio.setWidth(TilesSize*2);
        		spritePersonaggio.setFitWidth(TilesSize*2);
        		spritePersonaggio.setImage(attack_left_2);
        	}
        	if(lastDirection == 'd') {
        		
        		personaggio.setWidth(TilesSize*2);
        		spritePersonaggio.setFitWidth(TilesSize*2);
        		spritePersonaggio.setImage(attack_right_2);
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