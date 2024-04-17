
package it.edu.iisguiio;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
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
    Rectangle rMostro1= new Rectangle(tilesSize-10,tilesSize-5);
    Rectangle rMostro2 = new Rectangle(tilesSize-10,tilesSize-5);
    Rectangle rMostro3 = new Rectangle(tilesSize-10,tilesSize-5);
    
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
    //
    int vitaPersonaggio=3;
    int countMostriEliminati=0;
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
    //label punteggio
    Label lPunteggio = new Label("Mosstri eliminati "+countMostriEliminati);
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
        rMostro1.setFill(Color.BLACK); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro1.setStroke(Color.TRANSPARENT);
        rMostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        rMostro1.setY(HEIGHT_GIOCO / 4);
        spriteMostro.setX(HEIGHT_GIOCO / 4);
        spriteMostro.setY(HEIGHT_GIOCO / 4);
        
        //mostro 2 
        rMostro2.setFill(Color.RED); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro2.setStroke(Color.TRANSPARENT);
        //mostro 3
        rMostro3.setFill(Color.BLUE); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro3.setStroke(Color.TRANSPARENT);
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
    	bComandi.setPrefSize(4*tilesSize, tilesSize);
    	bComandi.setLayoutX(WIDTH_GIOCO/2-tilesSize*2);
    	bComandi.setLayoutY(HEIGHT_GIOCO/2+tilesSize);
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

    	ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("Immagini/mappa.jpg")));
    	imageView.setFitWidth(911.0);
    	imageView.setFitHeight(825.0);
    	imageView.setLayoutX(-49.0);
    	imageView.setLayoutY(-171.0);
    	imageView.setPickOnBounds(true);
    	imageView.setPreserveRatio(true);

    	Button buttonIniziaAvventura = new Button("Inizia la tua avventura");
    	buttonIniziaAvventura.setLayoutX(303.0);
    	buttonIniziaAvventura.setLayoutY(379.0);
    	buttonIniziaAvventura.setOnAction(e -> iniziaAvventura());
    	buttonIniziaAvventura.setPrefWidth(204.0);
    	buttonIniziaAvventura.setPrefHeight(25.0);

    	Button buttonEnter = new Button("ENTER");
    	buttonEnter.setLayoutX(303.0);
    	buttonEnter.setLayoutY(335.0);

    	Button buttonD = new Button("D");
    	buttonD.setLayoutX(303.0);
    	buttonD.setLayoutY(288.0);
    	buttonD.setPrefWidth(51.0);
    	buttonD.setPrefHeight(25.0);

    	Button buttonA = new Button("A");
    	buttonA.setLayoutX(303.0);
    	buttonA.setLayoutY(237.0);
    	buttonA.setPrefWidth(51.0);
    	buttonA.setPrefHeight(25.0);

    	Button buttonS = new Button("S");
    	buttonS.setLayoutX(303.0);
    	buttonS.setLayoutY(191.0);
    	buttonS.setPrefWidth(51.0);
    	buttonS.setPrefHeight(25.0);

    	Button buttonW = new Button("W");
    	buttonW.setLayoutX(303.0);
    	buttonW.setLayoutY(153.0);
    	buttonW.setPrefWidth(51.0);
    	buttonW.setPrefHeight(25.0);

    	Label labelMuoviAlto = new Label("Muovi verso l'alto");
    	labelMuoviAlto.setLayoutX(370.0);
    	labelMuoviAlto.setLayoutY(153.0);
    	labelMuoviAlto.setPrefWidth(140.0);
    	labelMuoviAlto.setPrefHeight(25.0);
    	labelMuoviAlto.setFont(new Font(14.0));

    	Label labelMuoviBasso = new Label("Muovi verso il basso");
    	labelMuoviBasso.setLayoutX(370.0);
    	labelMuoviBasso.setLayoutY(191.0);
    	labelMuoviBasso.setPrefWidth(140.0);
    	labelMuoviBasso.setPrefHeight(25.0);
    	labelMuoviBasso.setFont(new Font(14.0));

    	Label labelMuoviSinistra = new Label("Muovi a sinistra");
    	labelMuoviSinistra.setLayoutX(370.0);
    	labelMuoviSinistra.setLayoutY(237.0);
    	labelMuoviSinistra.setPrefWidth(140.0);
    	labelMuoviSinistra.setPrefHeight(25.0);
    	labelMuoviSinistra.setFont(new Font(14.0));

    	Label labelMuoviDestra = new Label("Muovi a destra");
    	labelMuoviDestra.setLayoutX(370.0);
    	labelMuoviDestra.setLayoutY(288.0);
    	labelMuoviDestra.setPrefWidth(140.0);
    	labelMuoviDestra.setPrefHeight(25.0);
    	labelMuoviDestra.setFont(new Font(14.0));

    	Label labelAttacca = new Label("Attacca");
    	labelAttacca.setLayoutX(375.0);
    	labelAttacca.setLayoutY(335.0);
    	labelAttacca.setPrefWidth(140.0);
    	labelAttacca.setPrefHeight(25.0);
    	labelAttacca.setFont(new Font(14.0));

    	paneComandi.getChildren().addAll(imageView, buttonIniziaAvventura, buttonEnter, buttonD, buttonA, buttonS, buttonW,
    	        labelMuoviAlto, labelMuoviBasso, labelMuoviSinistra, labelMuoviDestra, labelAttacca);
    	paneWord.getChildren().add(paneComandi);
    }
    public void aggiornaGioco() {
        // Muovi il mostro
        richiamaMostro.muoviMostro();

        // Verifica la collisione con i mostri
        Shape intersect1 = Shape.intersect(personaggio, rMostro1);
        Shape intersect2 = Shape.intersect(personaggio, rMostro2);
        Shape intersect3 = Shape.intersect(personaggio, rMostro3);

        // Verifica la collisione tra il personaggio e il primo mostro
        if (intersect1.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.HoAttaccato == true) {
                paneWord.getChildren().removeAll(spriteMostro, rMostro1);
                mostro1Eliminato = true;
            } else {
                if (vitaPersonaggio == 0) {
                    // Gestisci la fine del gioco
                } else {
                    vitaPersonaggio--;
                }
            }
        }
        if (intersect2.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.HoAttaccato == true) {
                paneWord.getChildren().removeAll(spriteMostro2, rMostro2);
                mostro2Eliminato = true;
            } else {
                if (vitaPersonaggio == 0) {
                    // Gestisci la fine del gioco
                } else {
                    vitaPersonaggio--;
                }
            }
        }
        if (intersect3.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.HoAttaccato == true) {
                paneWord.getChildren().removeAll(spriteMostro3, rMostro3);
                mostro3Eliminato = true;
            } else {
                if (vitaPersonaggio == 0) {
                    // Gestisci la fine del gioco
                } else {
                    vitaPersonaggio--;
                }
            }
        }
        // Controllo se devo passare alla fase successiva
        if (fase1 && mostro1Eliminato) {
            fase1 = false;
            fase2 = true;
            aggiungiMostro();
        }
        if (fase2 && mostro1Eliminato && mostro2Eliminato) {
            fase2 = false;
            fase3 = true;
            aggiungiMostro();
        }
        if (fase3 && mostro1Eliminato && mostro2Eliminato && mostro3Eliminato) {
            aggiungiMostro();
        }
    }

    // Funzione per aggiungere un nuovo mostro se necessario
    private void aggiungiMostro() {
        mostro1Eliminato = false;
        mostro2Eliminato = false;
        mostro3Eliminato = false;

        if (fase2) {
            int posXM1 = (int) (Math.random() * gameColoum) * tilesSize;
            int posYM1 = (int) (Math.random() * gameRow) * tilesSize;
            int posXM2 = (int) (Math.random() * gameColoum) * tilesSize;
            int posYM2 = (int) (Math.random() * gameRow) * tilesSize;

            // Posiziona il primo mostro
            rMostro1.setX(posXM1);
            rMostro1.setY(posYM1);
            spriteMostro.setX(posXM1);
            spriteMostro.setY(posYM1);

            // Posiziona il secondo mostro
            rMostro2.setX(posXM2);
            rMostro2.setY(posYM2);
            spriteMostro2.setX(posXM2);
            spriteMostro2.setY(posYM2);

            // Aggiungi i mostri al gioco
            paneWord.getChildren().addAll(rMostro1, spriteMostro, rMostro2, spriteMostro2);

            // Muovi i mostri
            richiamaMostro.muoviMostro();
        } else {
            if (fase3) {
                int posXM1 = (int) (Math.random() * gameColoum) * tilesSize;
                int posYM1 = (int) (Math.random() * gameRow) * tilesSize;
                int posXM2 = (int) (Math.random() * gameColoum) * tilesSize;
                int posYM2 = (int) (Math.random() * gameRow) * tilesSize;
                int posXM3 = (int) (Math.random() * gameColoum) * tilesSize;
                int posYM3 = (int) (Math.random() * gameRow) * tilesSize;

                // Posiziona il primo mostro
                rMostro1.setX(posXM1);
                rMostro1.setY(posYM1);
                spriteMostro.setX(posXM1);
                spriteMostro.setY(posYM1);

                // Posiziona il secondo mostro
                rMostro2.setX(posXM2);
                rMostro2.setY(posYM2);
                spriteMostro2.setX(posXM2);
                spriteMostro2.setY(posYM2);

                // Posiziona il terzo mostro
                rMostro3.setX(posXM3);
                rMostro3.setY(posYM3);
                spriteMostro3.setX(posXM3);
                spriteMostro3.setY(posYM3);

                // Aggiungi i mostri al gioco
                paneWord.getChildren().addAll(rMostro1, spriteMostro, rMostro2, spriteMostro2, rMostro3, spriteMostro3);

                // Muovi i mostri
                richiamaMostro.muoviMostro();
            }
        }
    }
}
