package it.edu.iisguiio;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
    Rectangle mostro1= new Rectangle(TilesSize,TilesSize);
    //percorso del mostro
    double[] percorsoX = {2, 3, 3, 4, 5,5,5}; 
    double[] percorsoY = {2, 2, 2, 2, 2,3,4};
    // ImageView per lo sprite del personaggio
    ImageView spritePersonaggio;
    Image spriteImage = new Image(getClass().getResourceAsStream("Immagini/SpriteCamminata/walk_down_1.png"));
    //sprite del mostro
    ImageView spriteMostro;
    Image spriteImageMostro = new Image(getClass().getResourceAsStream("Immagini/Monster/skeletonlord_down_2.png"));
    // classe 
    Personaggio richiamaPersonaggio; 
    Mostro richiamaMostro;
    int VitaPersonaggio;
    
    // creo i pane per il gioco e il munù
    Pane paneMenù= new Pane();
    Pane paneWord = new Pane();
    Pane paneComandi = new Pane();
    public static void main(String args[]) {
        launch(args);
    }

    Stage finestra;
    public void start(Stage primaryStage) throws Exception {
    	finestra=primaryStage;
    	VitaPersonaggio=3;
        // Creo la griglia
        paneMenù.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
    	paneWord.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
        Scene scene = new Scene(paneWord);

        primaryStage.setScene(scene);
        primaryStage.show();

        timelineGioco.setCycleCount(Timeline.INDEFINITE);

        // Carica l'immagine dello sprite del personaggio
        spritePersonaggio = new ImageView(spriteImage);
        spriteMostro = new ImageView(spriteImageMostro);
        // Imposta la posizione iniziale dello sprite
        spritePersonaggio.setX(Xpersonaggio);
        spritePersonaggio.setY(Ypersonaggio);
        // Imposta la dimensione dello sprite 
        spritePersonaggio.setFitWidth(TilesSize);
        spritePersonaggio.setFitHeight(TilesSize);
        spriteMostro.setFitWidth(TilesSize);
        spriteMostro.setFitHeight(TilesSize);
        // Aggiungi lo sprite al Pane del gioco
        paneWord.getChildren().addAll(personaggio, spritePersonaggio);
        
        // Inizializza il gestore del personaggio
        richiamaPersonaggio = new Personaggio(personaggio, WIDTH_GIOCO, HEIGHT_GIOCO, spritePersonaggio, TilesSize);
        richiamaMostro = new Mostro(mostro1, WIDTH_GIOCO, HEIGHT_GIOCO, TilesSize,percorsoX, percorsoY,spriteMostro);
        //posizione il mostro
        mostro1.setFill(Color.RED); // Colore rosso per il mostro (puoi cambiarlo a tuo piacimento)
        mostro1.setStroke(Color.TRANSPARENT);
        mostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        mostro1.setY(HEIGHT_GIOCO / 4);
        spriteMostro.setX(HEIGHT_GIOCO/4);
        spriteMostro.setY(HEIGHT_GIOCO / 4);
        // Aggiungi il mostro al gioco
        paneWord.getChildren().addAll(mostro1, spriteMostro);

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
        Shape intersect = Shape.intersect(personaggio, mostro1);
        // Verifica la collisione tra il personaggio e il mostro
        if(intersect.getBoundsInLocal().getWidth() != -1) {
        	if (richiamaPersonaggio.HoAttaccato==true) {
        		timelineGioco.stop();
        		paneWord.getChildren().remove(mostro1);
        	}else {
        		if (VitaPersonaggio==0) {
        			paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
        		}else {
        			VitaPersonaggio--;
        		}	
        	}
        		
        }
    }
    
    @FXML
	private void attivaGioco(ActionEvent e){
    	System.out.println("click!");
    }

}