package it.edu.iisguiio;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Zelda extends Application {
    Timeline timelineGioco = new Timeline(new KeyFrame(
            Duration.seconds(0.008), // ogni quanto va chiamata la funzione
            x -> aggiornaGioco()));
    Timeline timelineMenù = new Timeline(new KeyFrame(
            Duration.seconds(0.008), // ogni quanto va chiamata la funzione
            x -> aggiornaMenù()));
    int defaultTileSize = 16;
    int scale = 3;
    int TilesSize = defaultTileSize * scale;
    int gameColoum = 18;
    int gameRow = 12;
    Rectangle personaggio = new Rectangle(TilesSize,TilesSize);
    final int WIDTH_GIOCO = gameColoum * TilesSize;
    final int HEIGHT_GIOCO = gameRow * TilesSize;

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
        
        gameWord.getChildren().add(personaggio);
        scene.setOnKeyPressed(e -> premiTasto(e));
        scene.setOnMousePressed(e -> premiMouse(e));
        
    }

    public void premiTasto(KeyEvent e) {
    	if (e.getCode()==KeyCode.SPACE) {
    		
    	}
		if (e.getCode()==KeyCode.UP) {
		    		
		    	}
		if (e.getCode()==KeyCode.DOWN) {
			
		}
		if (e.getCode()==KeyCode.LEFT) {
			
		}
		if (e.getCode()==KeyCode.RIGHT) {
			
		}
        // Handle key press event here
    }
    public void premiMouse(MouseEvent e) {
    	if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            // Handle mouse click event
        }
        // Handle key press event here
    }

    public void aggiornaGioco() {
        // Update game logic here
    }
    public void aggiornaMenù() {
        // Update menù logic
    }
}
