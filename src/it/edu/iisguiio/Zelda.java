package it.edu.iisguiio;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    // classe 
    Personaggio richiamaPersonaggio; 
    boolean StoAttaccando=false;
    Mostro mostro = new Mostro(mostro1, WIDTH_GIOCO, HEIGHT_GIOCO, TilesSize,percorsoX, percorsoY);
    // creo i pane per il gioco e il munù
    Pane gameMenù= new Pane();
    Pane gameWord = new Pane();
    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        // Creo la griglia
        gameMenù.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
    	gameWord.setPrefSize(WIDTH_GIOCO, HEIGHT_GIOCO);
        Scene scene = new Scene(gameWord);

        primaryStage.setScene(scene);
        primaryStage.show();

        timelineGioco.setCycleCount(Timeline.INDEFINITE);

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
        richiamaPersonaggio = new Personaggio(personaggio, WIDTH_GIOCO, HEIGHT_GIOCO, spritePersonaggio, TilesSize,StoAttaccando);
        
        //posizione il mostro
        mostro1.setFill(Color.RED); // Colore rosso per il mostro (puoi cambiarlo a tuo piacimento)
        mostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        mostro1.setY(HEIGHT_GIOCO / 4);
        // Aggiungi il mostro al gioco
        gameWord.getChildren().add(mostro1);

        
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
    public void AvviaMenù() {
    	Button bStart = new Button("inizia la tua avventura");
    	bStart.setPrefSize(4*TilesSize, TilesSize);
    	bStart.setLayoutX(WIDTH_GIOCO/2-TilesSize*2);
    	bStart.setLayoutY(HEIGHT_GIOCO/2-TilesSize);
    	Button bComandi = new Button("comandi");
    	bComandi.setPrefSize(4*TilesSize, TilesSize);
    	bComandi.setLayoutX(WIDTH_GIOCO/2-TilesSize*2);
    	bComandi.setLayoutY(HEIGHT_GIOCO/2);
    	gameMenù.getChildren().addAll(bComandi , bStart);
    	gameWord.getChildren().add(gameMenù);
    	gameMenù.setBackground(null);
    	
    	bStart.setOnAction(start ->iniziaAvventura());
    	bComandi.setOnAction(e-> mostraComandi());
    }
    public void iniziaAvventura() {
    	timelineGioco.play();
    	gameMenù.getChildren().clear();
    }
    public void mostraComandi() {
    	Pane paneComandi = new Pane();
    	paneComandi.getChildren().clear();
    }
    public void aggiornaGioco() {
        // Muovi il mostro
        mostro.muoviMostro();
        // Verifica la collisione tra il personaggio e il mostro
        if (personaggio.getBoundsInParent().intersects(mostro1.getBoundsInParent())) {
        	if (StoAttaccando==true);
        		timelineGioco.stop();
        		gameWord.getChildren().remove(mostro);
        }
    }

}