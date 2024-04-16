
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
    int tilesSize = defaultTileSize * scale;
    int gameColoum = 16;
    int gameRow = 12;
    final int WIDTH_GIOCO = gameColoum * tilesSize;
    final int HEIGHT_GIOCO = gameRow * tilesSize;
    //cordinate del personaggio
    double Xpersonaggio=WIDTH_GIOCO/2;
    double Ypersonaggio=HEIGHT_GIOCO-tilesSize;
    //personaggio e nemici come rettangoli
    Rectangle personaggio = new Rectangle(Xpersonaggio,Ypersonaggio,tilesSize,tilesSize);
    Rectangle rMostro1= new Rectangle(tilesSize,tilesSize);
    Rectangle rMostro2 = new Rectangle(tilesSize,tilesSize);
    Rectangle rMostro3 = new Rectangle(tilesSize,tilesSize);
    
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
    
    int vitaPersonaggio=3;
    // creo i pane per il gioco e il munù
    Pane paneMenù= new Pane();
    Pane paneWord = new Pane();
    Pane paneComandi = new Pane();
    
    //fasi del gioco
    boolean fase1=true;
    boolean fase2=false;
    boolean fase3=false;
    //mostri eliminati
    boolean mostro1Eliminato=false;
    boolean mostro2Eliminato=false;
    boolean mostro3Eliminato=false;
    public static void main(String args[]) {
        launch(args);
    }

    Stage finestra;
    public void start(Stage primaryStage) throws Exception {
    	System.out.println("withd"+WIDTH_GIOCO);
    	System.out.println("he"+HEIGHT_GIOCO);
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
        spritePersonaggio.setFitWidth(tilesSize);
        spritePersonaggio.setFitHeight(tilesSize);
        spriteMostro.setFitWidth(tilesSize);
        spriteMostro.setFitHeight(tilesSize);
        spriteMostro2.setFitWidth(tilesSize);
        spriteMostro2.setFitHeight(tilesSize);
        spriteMostro3.setFitWidth(tilesSize);
        spriteMostro3.setFitHeight(tilesSize);
        // Aggiungi lo sprite al Pane del gioco
        paneWord.getChildren().addAll(personaggio, spritePersonaggio);
        
        // Inizializza il gestore del personaggio
        richiamaPersonaggio = new Personaggio(personaggio, WIDTH_GIOCO, HEIGHT_GIOCO, spritePersonaggio, tilesSize);
        richiamaMostro = new Mostro(rMostro1, rMostro2, rMostro3, WIDTH_GIOCO, HEIGHT_GIOCO, tilesSize, spriteMostro, spriteMostro2, spriteMostro3);
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
        	richiamaPersonaggio.muovi(e);
            if(e.getCode()==KeyCode.ESCAPE) {
            	timelineGioco.stop();
            	AvviaMenù();
            }   	
    } 
    Button bStart = new Button("inizia la tua avventura");
    Button bComandi= new Button("comandi");
    
    public void AvviaMenù() {
    	bStart.setPrefSize(4*tilesSize, tilesSize);
    	bStart.setLayoutX(WIDTH_GIOCO/2-tilesSize*2);
    	bStart.setLayoutY(HEIGHT_GIOCO/2-tilesSize);
    	//Button bComandi = new Button("comandi");
    	bComandi.setPrefSize(4*tilesSize, tilesSize);
    	bComandi.setLayoutX(WIDTH_GIOCO/2-tilesSize*2);
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
       
       
       
        // Verifica la collisione tra il personaggio e il primo mostro
        if(intersect1.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro,rMostro1);
                //System.out.println("tolti spriteMostro,rMostro1");
                mostro1Eliminato=true;
            } else {
                if (vitaPersonaggio == 0) {
                    //paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    vitaPersonaggio--;
                }   
            }      
        }
        if(intersect2.getBoundsInLocal().getWidth() != -1) {
        	if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro2,rMostro2);
                mostro2Eliminato=true;
            } else {
                if (vitaPersonaggio == 0) {
                   // paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    vitaPersonaggio--;
                }   
            }
        }
        if(intersect3.getBoundsInLocal().getWidth() != -1) {
        	if (richiamaPersonaggio.HoAttaccato==true) {
                paneWord.getChildren().removeAll(spriteMostro3,rMostro3);
                mostro3Eliminato=true;
            } else {
                if (vitaPersonaggio == 0) {
                    //paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
                } else {
                    vitaPersonaggio--;
                }   
            }
        }
        if( fase1==true && mostro1Eliminato==true) {
        	System.out.println("fase1=true");
        	fase1=false;
        	fase2=true;
        	aggiungiMostro();
        }
        if( fase2==true && mostro1Eliminato==true && mostro2Eliminato==true) {
        	System.out.println("fase2");
        	fase2=false;
        	System.out.println("fase2=false");
        	fase3=true;
        	System.out.println("fase3=true");
        	aggiungiMostro();
        } 
        if(fase3==true && mostro1Eliminato==true && mostro2Eliminato==true && mostro3Eliminato==true) {
        	aggiungiMostro();
        } 
    }

    // Funzione per aggiungere un nuovo mostro se necessario
    private void aggiungiMostro() {
    	mostro1Eliminato=false; 
    	mostro2Eliminato=false;
    	mostro3Eliminato=false;
    	if (fase2==true) {
        	//System.out.println("fase 2");        	
        	int posXM1 = (int) (Math.random()*(gameColoum+1))*tilesSize;
        	System.out.println(posXM1);
        	int posYM1 = (int) (Math.random()*(gameRow+1))*tilesSize;
        	System.out.println(posYM1);
        	int posXM2 = (int) (Math.random()*(gameColoum+1))*tilesSize;
        	System.out.println(posXM2);
        	int posYM2 = (int) (Math.random()*(gameRow+1))*tilesSize;
        	System.out.println(posYM2);
            //genero il mostro uno in una posizione casuale
            rMostro1.setX(posXM1); 
            rMostro1.setY(posYM1);
            spriteMostro.setX(posYM1);
            spriteMostro.setY(posYM1);
            
            //posizione il mostro e do un colore al rettangolo
            rMostro2.setFill(Color.TRANSPARENT); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
            rMostro2.setStroke(Color.TRANSPARENT);
            rMostro2.setX(posXM2*tilesSize); // Posizione iniziale del mostro
            rMostro2.setY(posYM2*tilesSize);
            spriteMostro2.setX(posYM2*tilesSize);
            spriteMostro2.setY(posYM2*tilesSize);
        	//paneWord.getChildren().addAll(spriteMostro, rMostro1, spriteMostro2, rMostro2);
            paneWord.getChildren().addAll(rMostro1);
        	paneWord.getChildren().addAll(spriteMostro);
        	paneWord.getChildren().addAll(rMostro2);
        	paneWord.getChildren().addAll(spriteMostro2);
            richiamaMostro.muoviMostro();
        }else {
        	if(fase3==true) {        		
            	int posXM1 = (int) (Math.random()*(gameColoum+1))*tilesSize;
            	int posYM1 = (int) (Math.random()*(gameRow+1))*tilesSize;
            	int posXM2 = (int) (Math.random()*(gameColoum+1))*tilesSize;
            	int posYM2 = (int) (Math.random()*(gameRow+1))*tilesSize;
            	int posXM3 = (int) (Math.random()*(gameColoum+1))*tilesSize;
            	int posYM3 = (int) (Math.random()*(gameRow+1))*tilesSize;
            	//posizione il mostro 1
            	rMostro1.setX(posXM1);
                rMostro1.setY(posYM1);
                spriteMostro.setX(posYM1);
                spriteMostro.setY(posYM1);

                //posiziono il mostro 2
            	rMostro2.setX(posXM2); 
                rMostro2.setY(posYM2);
                spriteMostro2.setX(posYM2);
                spriteMostro2.setY(posYM2);
            	
                //posizione il mostro 3
            	rMostro3.setFill(Color.TRANSPARENT); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
                rMostro3.setStroke(Color.TRANSPARENT);
                rMostro3.setX(posXM3); // Posizione iniziale del mostro
                rMostro3.setY(posYM3);
                spriteMostro3.setX(posXM3);
                spriteMostro3.setY(posYM3);
            	
                paneWord.getChildren().addAll(rMostro1);
            	paneWord.getChildren().addAll(spriteMostro);
            	paneWord.getChildren().addAll(rMostro2);
            	paneWord.getChildren().addAll(spriteMostro2);
            	paneWord.getChildren().addAll(rMostro3);
            	paneWord.getChildren().addAll(spriteMostro3);
            	richiamaMostro.muoviMostro();
            }
        }
    }
    
    @FXML
	private void attivaGioco(ActionEvent e){
    	System.out.println("click!");
    }

}
