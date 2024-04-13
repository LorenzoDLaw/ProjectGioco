package it.edu.iisguiio;

import java.io.IOException;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Zelda extends Application {
    Timeline timelineGioco = new Timeline(new KeyFrame(
            Duration.seconds(0.35), // ogni quanto va chiamata la funzione
            x -> aggiornaGioco()));
    //dimensione schermo i tiles del gioco
    int defaultTileSize = 16;
    int scale = 3;
    int TilesSize = defaultTileSize * scale;
    int gameColoum = 16;
    int gameRow = 12;
    final int WIDTH_GIOCO = gameColoum * TilesSize;
    final int HEIGHT_GIOCO = gameRow * TilesSize;
  //cordinate del personaggio
    double Xpersonaggio=WIDTH_GIOCO/2;
    double Ypersonaggio=HEIGHT_GIOCO-TilesSize;
    //personaggio e nemici come rettangoli
    Rectangle personaggio = new Rectangle(Xpersonaggio,Ypersonaggio,TilesSize,TilesSize);
    Rectangle rMostro1= new Rectangle(TilesSize,TilesSize);
    Rectangle rMostro2 = new Rectangle(TilesSize,TilesSize);
    Rectangle rMostro3 = new Rectangle(TilesSize,TilesSize);
    
    // ImageView per lo sprite del personaggio
    Image spriteImage = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_down_1.png"));
    ImageView spritePersonaggio = new ImageView(spriteImage);
    
    //sprite del mostro
    Image spriteImageMostro = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    ImageView spriteMostro= new ImageView(spriteImageMostro);
    Image spriteImageMostro2 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    ImageView spriteMostro2= new ImageView(spriteImageMostro2);
    Image spriteImageMostro3 = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    ImageView spriteMostro3= new ImageView(spriteImageMostro3);
    
    // classe 
    Personaggio richiamaPersonaggio; 
    Mostro richiamaMostro;
    
    int VitaPersonaggio=3;
    int countMostri=1;
    // creo i pane per il gioco e il munù
    Pane paneMenù= new Pane();
    Pane paneWord = new Pane();
    Pane paneComandi = new Pane();
    public static void main(String args[]) {
        launch(args);
    }

    Stage finestra;
    public void start(Stage primaryStage) throws Exception {
    	finestra=primaryStage;;
        // Creo la griglia
        paneMenù.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
    	paneWord.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
        Scene scene = new Scene(paneWord);
        
        primaryStage.setScene(scene);
        primaryStage.show();

        //sfondo 
        // Carica l'immagine di sfondo
        Image backgroundImage = new Image(getClass().getResourceAsStream("Immagini/mappa.jpg"));

        // Crea un ImageView per l'immagine di sfondo
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Imposta la dimensione dell'ImageView come la dimensione della scena
        backgroundImageView.setFitWidth(WIDTH_GIOCO);
        backgroundImageView.setFitHeight(HEIGHT_GIOCO);

        // Aggiungi l'ImageView di sfondo al paneWord in modo che sia dietro agli altri elementi
        paneWord.getChildren().add(0, backgroundImageView);

        timelineGioco.setCycleCount(Timeline.INDEFINITE);

        // Imposta la posizione iniziale dello sprite
        spritePersonaggio.setX(Xpersonaggio);
        spritePersonaggio.setY(Ypersonaggio);
        
        // Imposta la dimensione degli sprite 
        spritePersonaggio.setFitWidth(TilesSize);
        spritePersonaggio.setFitHeight(TilesSize);
        spriteMostro.setFitWidth(TilesSize);
        spriteMostro.setFitHeight(TilesSize);
        spriteMostro2.setFitWidth(TilesSize);
        spriteMostro2.setFitHeight(TilesSize);
        spriteMostro3.setFitWidth(TilesSize);
        spriteMostro3.setFitHeight(TilesSize);
        // Aggiungi lo sprite al Pane del gioco
        paneWord.getChildren().addAll(personaggio, spritePersonaggio);
        
        // Inizializza il gestore del personaggio
        richiamaPersonaggio = new Personaggio(personaggio, WIDTH_GIOCO, HEIGHT_GIOCO, spritePersonaggio, TilesSize);
        richiamaMostro = new Mostro(rMostro1, rMostro2, rMostro3, WIDTH_GIOCO, HEIGHT_GIOCO, TilesSize, spriteMostro, spriteMostro2, spriteMostro3);
        //posizione il mostro e do un colore al rettangolo
        rMostro1.setFill(Color.TRANSPARENT); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro1.setStroke(Color.TRANSPARENT);
        rMostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        rMostro1.setY(HEIGHT_GIOCO / 4);
        spriteMostro.setX(HEIGHT_GIOCO / 4);
        spriteMostro.setY(HEIGHT_GIOCO / 4);
        // Aggiungi il mostro al gioco
        paneWord.getChildren().addAll(rMostro1, spriteMostro);
        
        scene.setOnKeyPressed(this::premiTasto);
        
        //permette di richiamare la funzione all'inizio
        AvviaMenù();
    }
    
    public void premiTasto(KeyEvent e) {
        //if(timelineGioco.getStatus()==Status.RUNNING) {
        	richiamaPersonaggio.muovi(e);
            if(e.getCode()==KeyCode.ESCAPE) {
            	timelineGioco.stop();
            	AvviaMenù();
            }
        //}    	
    } 
    Button bStart = new Button("inizia la tua avventura");
    Button bComandi= new Button("comandi");
    
    public void AvviaMenù() {
    	bStart.setPrefSize(4*TilesSize, TilesSize);
    	bStart.setLayoutX(WIDTH_GIOCO/2-TilesSize*2);
    	bStart.setLayoutY(HEIGHT_GIOCO/2-TilesSize);
    	//Button bComandi = new Button("comandi");
    	bComandi.setPrefSize(4*TilesSize, TilesSize);
    	bComandi.setLayoutX(WIDTH_GIOCO/2-TilesSize*2);
    	bComandi.setLayoutY(HEIGHT_GIOCO/2);
    	paneMenù.getChildren().addAll(bComandi , bStart);
    	paneWord.getChildren().add(paneMenù);
    	paneMenù.setBackground(null);
    	
    	bStart.setOnAction(start ->iniziaAvventura());
    	bComandi.setOnAction(e-> mostraComandi());
    }
    public void iniziaAvventura() {
    	timelineGioco.play();
    	paneComandi.getChildren().clear();
    	paneMenù.getChildren().clear();
    }
    public void mostraComandi() {
    	try {
			Scene scenaComadi = new Scene( FXMLLoader.load(Zelda.class.getResource("menuComandi.fxml")) );
			 finestra.setScene(scenaComadi);
		     finestra.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void aggiornaGioco(){
        // Muovi il mostro
        richiamaMostro.muoviMostro();
        
        // Verifica la collisione con i mostri
        Shape intersect1 = Shape.intersect(personaggio, rMostro1);
        Shape intersect2 = Shape.intersect(personaggio, rMostro2);
        Shape intersect3 = Shape.intersect(personaggio, rMostro2);
        boolean mostro1Eliminato=false;
        boolean mostro2Eliminato=false;
        boolean mostro3Eliminato=false;
        // Verifica la collisione tra il personaggio e il primo mostro
        if(intersect1.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro,rMostro1);
                mostro1Eliminato=true;
                countMostri++;
            } else {
                if (VitaPersonaggio == 0) {
                    paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    VitaPersonaggio--;
                }   
            }      
        }
        if(intersect2.getBoundsInLocal().getWidth() != -1) {
        	if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro2,rMostro2);
                countMostri++;
                mostro2Eliminato=true;
            } else {
                if (VitaPersonaggio == 0) {
                    paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    VitaPersonaggio--;
                }   
            }
        }
        if(intersect3.getBoundsInLocal().getWidth() != -1) {
        	if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro3,rMostro3);
                mostro3Eliminato=true;
            } else {
                if (VitaPersonaggio == 0) {
                    paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    VitaPersonaggio--;
                }   
            }
        }
        if(countMostri==2 && mostro1Eliminato==true) {
        	mostro1Eliminato=false;
        	aggiungiMostro();
        }
        if(countMostri==3 && mostro1Eliminato==true && mostro2Eliminato==true) {
        	mostro1Eliminato=false; 
        	mostro2Eliminato=false;
        	aggiungiMostro();
        } 
        if(countMostri>=4 && mostro1Eliminato==true && mostro2Eliminato==true && mostro3Eliminato==true) {
        	mostro1Eliminato=false; 
        	mostro2Eliminato=false;
        	mostro3Eliminato=false; 
        	aggiungiMostro();
        } 
    }

    // Funzione per aggiungere un nuovo mostro se necessario
    private void aggiungiMostro() {
        if (countMostri ==2) {
        	int posX = (int) Math.random()*gameColoum;
        	int posY = (int) Math.random()*gameRow;
        	//posizione il mostro e do un colore al rettangolo
            rMostro2.setFill(Color.RED); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
            rMostro2.setStroke(Color.RED);
            rMostro2.setX(posX*TilesSize); // Posizione iniziale del mostro
            rMostro2.setY(posY*TilesSize);
            spriteMostro2.setX(posX*TilesSize);
            spriteMostro2.setY(posY*TilesSize);
        	paneWord.getChildren().addAll(spriteMostro, rMostro1, spriteMostro2, rMostro2);
            countMostri++;
            richiamaMostro.muoviMostro();
            //timelineGioco.play();
        }
        if(countMostri==3) {
        	int posX = (int) Math.random()*gameColoum;
        	int posY = (int) Math.random()*gameRow;
        	//posizione il mostro e do un colore al rettangolo
            rMostro3.setFill(Color.RED); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
            rMostro3.setStroke(Color.RED);
            rMostro3.setX(posX*TilesSize); // Posizione iniziale del mostro
            rMostro3.setY(posY*TilesSize);
            spriteMostro3.setX(posX*TilesSize);
            spriteMostro3.setY(posY*TilesSize);
        	paneWord.getChildren().addAll(spriteMostro,rMostro1,spriteMostro2, rMostro2,spriteMostro3, rMostro3);
        	richiamaMostro.muoviMostro();
        	//timelineGioco.play();
        }
    }
    
    @FXML
	private void attivaGioco(ActionEvent e){
    	System.out.println("click!");
    }

}