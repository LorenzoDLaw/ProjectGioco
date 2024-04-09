package it.edu.iisguiio;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;

public class Zelda extends Application {
    Timeline timelineGioco = new Timeline(new KeyFrame(
            Duration.seconds(0.16), // ogni quanto va chiamata la funzione
            x -> aggiornaGioco()));
    Timeline timelineMenù = new Timeline(new KeyFrame(
            Duration.seconds(0.16), // ogni quanto va chiamata la funzione
            x -> aggiornaMenù()));
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
    double[] percorsoX = {100, 150, 200, 250, 300}; 
    double[] percorsoY = {100, 120, 100, 80, 100};
    
    
    

    // ImageView per lo sprite del personaggio
    ImageView spritePersonaggio;
    Image spriteImage = new Image(getClass().getResourceAsStream("link.png"));
    // classe 
    Personaggio richiamaPersonaggio; 
    Mostro mostro = new Mostro(mostro1, WIDTH_GIOCO, HEIGHT_GIOCO, TilesSize,percorsoX, percorsoY);
    
    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
    	

        // Creo la griglia
        Pane gameWord = new Pane();
        gameWord.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
        Scene scene = new Scene(gameWord);

        primaryStage.setScene(scene);
        primaryStage.show();

        timelineGioco.setCycleCount(Timeline.INDEFINITE);
        timelineMenù.setCycleCount(Timeline.INDEFINITE);

        // Carica l'immagine dello sprite del personaggio
        spritePersonaggio = new ImageView(spriteImage); 
        // Imposta la posizione iniziale dello sprite
        spritePersonaggio.setX(Xpersonaggio);
        spritePersonaggio.setY(Ypersonaggio);
        // Imposta la dimensione dello sprite
        spritePersonaggio.setFitWidth(TilesSize);
        spritePersonaggio.setFitHeight(TilesSize);
        // Aggiungi lo sprite al Pane del gioco
        gameWord.getChildren().addAll(personaggio, spritePersonaggio);
        
        // Inizializza il gestore del personaggio
        richiamaPersonaggio = new Personaggio(personaggio, WIDTH_GIOCO, HEIGHT_GIOCO, spritePersonaggio, TilesSize);
        
        //posizione il mostro
        mostro1.setFill(Color.RED); // Colore rosso per il mostro (puoi cambiarlo a tuo piacimento)
        mostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        mostro1.setY(HEIGHT_GIOCO / 4);
        // Aggiungi il mostro al gioco
        gameWord.getChildren().add(mostro1);

        
        scene.setOnKeyPressed(this::premiTasto);
        scene.setOnMousePressed(this::premiMouse);

        timelineGioco.play();
        timelineMenù.play();
        
    	Path pathMostro = new Path();
    	pathMostro.getElements().add(new MoveTo(percorsoX[0], percorsoY[0])); // Posizione iniziale del percorso

    	// Aggiungi i segmenti del percorso
    	for (int i = 1; i < percorsoX.length; i++) {
    	    pathMostro.getElements().add(new LineTo(percorsoX[i], percorsoY[i]));
    	}

    	// Opzionale: chiudi il percorso
    	// pathMostro.getElements().add(new ClosePath());

    	// Imposta il percorso del mostro
    	mostro1.setX(percorsoX[0]); // Posizione iniziale del mostro
    	mostro1.setY(percorsoY[0]);

    	// Aggiungi il percorso al gioco
    	gameWord.getChildren().add(pathMostro);
    }
    
    public void premiTasto(KeyEvent e) {
        richiamaPersonaggio.muovi(e);
    }

    public void premiMouse(MouseEvent e) {
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
           
        }
    }

    public void aggiornaGioco() {
        mostro.muoviMostro();
    }

    public void aggiornaMenù() {
 
    }
}
