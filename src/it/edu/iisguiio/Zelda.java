
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
            Duration.seconds(0.5), // ogni quanto va chiamata la funzione
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
    Rectangle rMostro1= new Rectangle(tilesSize-5,tilesSize-5);
    Rectangle rMostro2 = new Rectangle(tilesSize-5,tilesSize-5);
    Rectangle rMostro3 = new Rectangle(tilesSize-5,tilesSize-5);
    
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
    Pane paneHaiPerso = new Pane();
    
    //fasi del gioco
    boolean fase1=true;
    boolean fase2=false;
    boolean fase3=false;
    boolean inizioGioco=true;
    //mostri eliminati
    boolean mostro1Eliminato=false;
    boolean mostro2Eliminato=false;
    boolean mostro3Eliminato=false;
    boolean haiPerso=false;
    //label punteggio
    Label lPunteggio = new Label("Mostri eliminati "+countMostriEliminati);
    Image healtImg = new Image(getClass().getResourceAsStream("Immagini/cuori/heartfull.png"));
    ImageView healtImgWiew = new ImageView(healtImg);
    //bottoni per iniziare il gioco e i comandi
    Button bStart = new Button("inizia la tua avventura");
    Button bComandi= new Button("comandi");
    public static void main(String args[]) {
        launch(args);
    }

    Stage finestra;
    public void start(Stage primaryStage) throws Exception {
    	
    	//Font font = new Font(getClass().getResource("SuperPixel-m2L8j.ttf").toString(), 30);
    	
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
        richiamaMostro = new Mostro(rMostro1, rMostro2, rMostro3, WIDTH_GIOCO, HEIGHT_GIOCO, tilesSize, spriteMostro, spriteMostro2, spriteMostro3, personaggio);
        //posizione il mostro e do un colore al rettangolo
        rMostro1.setFill(Color.BROWN); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro1.setStroke(Color.TRANSPARENT);
        /*rMostro1.setX(WIDTH_GIOCO / 4); // Posizione iniziale del mostro
        rMostro1.setY(HEIGHT_GIOCO / 4);
        spriteMostro.setX(HEIGHT_GIOCO / 4);
        spriteMostro.setY(HEIGHT_GIOCO / 4);*/
        
        //mostro 2 
        rMostro2.setFill(Color.BLUE); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro2.setStroke(Color.TRANSPARENT);
        //mostro 3
        rMostro3.setFill(Color.RED); // lo rendo trasparente cosi non sara visibile ma sara usato per le collisioni
        rMostro3.setStroke(Color.TRANSPARENT);
       
        //posiziono il punteggio e i cuori
        lPunteggio.setLayoutX(0);
        lPunteggio.setLayoutY(0);
        lPunteggio.setPrefSize(tilesSize*5.5, tilesSize);
        lPunteggio.setFont(Font.loadFont(getClass().getResourceAsStream("font/SuperPixel-m2L8j.ttf"), 12));
        
        healtImgWiew.setLayoutX(0);
        healtImgWiew.setLayoutY(tilesSize);
        healtImgWiew.setFitWidth(tilesSize*2);
        healtImgWiew.setFitHeight(tilesSize);
        
        scene.setOnKeyPressed(this::premiTasto);
        
        // Aggiungi tutto al gioco
        paneWord.getChildren().addAll(lPunteggio,healtImgWiew);
        
        //permette di richiamare la funzione all'inizio
        AvviaMenù();
        
        scene.getStylesheets().add(getClass().getResource("Stile.css").toExternalForm());
        
    }
    
    public void premiTasto(KeyEvent e) {
        	richiamaPersonaggio.muovi(e);
            if(e.getCode()==KeyCode.ESCAPE) {
            	timelineGioco.stop();
            	AvviaMenù();
            }   	
    } 
    
    
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
    	if(haiPerso) {
    		timelineGioco.play();
    		vitaPersonaggio=3;
    		Image fullVita = new Image(getClass().getResourceAsStream("Immagini/cuori/heartfull.png"));
    		healtImgWiew.setImage(fullVita);
    	}
    	paneHaiPerso.getChildren().clear();
    	paneComandi.getChildren().clear();
    	paneMenù.getChildren().clear();
    }
    public void mostraComandi() {

    	ImageView mappaImgWiew = new ImageView(new Image(getClass().getResourceAsStream("Immagini/mappa.jpg")));
    	mappaImgWiew.setFitWidth(911.0);
    	mappaImgWiew.setFitHeight(825.0);
    	mappaImgWiew.setLayoutX(-49.0);
    	mappaImgWiew.setLayoutY(-171.0);
    	mappaImgWiew.setPickOnBounds(true);
    	mappaImgWiew.setPreserveRatio(true);

    	Button bIniziaAvventura = new Button("Inizia la tua avventura");
    	bIniziaAvventura.setLayoutX(303.0);
    	bIniziaAvventura.setLayoutY(379.0);
    	bIniziaAvventura.setOnAction(e -> iniziaAvventura());
    	bIniziaAvventura.setPrefWidth(204.0);
    	bIniziaAvventura.setPrefHeight(25.0);

    	Button bEnter = new Button("ENTER");
    	bEnter.setLayoutX(303.0);
    	bEnter.setLayoutY(335.0);

    	Button bDestra = new Button("D");
    	bDestra.setLayoutX(303.0);
    	bDestra.setLayoutY(288.0);
    	bDestra.setPrefWidth(51.0);
    	bDestra.setPrefHeight(25.0);

    	Button bASinistra = new Button("A");
    	bASinistra.setLayoutX(303.0);
    	bASinistra.setLayoutY(237.0);
    	bASinistra.setPrefWidth(51.0);
    	bASinistra.setPrefHeight(25.0);

    	Button bSDown = new Button("S");
    	bSDown.setLayoutX(303.0);
    	bSDown.setLayoutY(191.0);
    	bSDown.setPrefWidth(51.0);
    	bSDown.setPrefHeight(25.0);

    	Button bWUp = new Button("W");
    	bWUp.setLayoutX(303.0);
    	bWUp.setLayoutY(153.0);
    	bWUp.setPrefWidth(51.0);
    	bWUp.setPrefHeight(25.0);

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

    	paneComandi.getChildren().addAll(mappaImgWiew, bIniziaAvventura, bEnter, bDestra, bASinistra, bSDown, bWUp,
    	        labelMuoviAlto, labelMuoviBasso, labelMuoviSinistra, labelMuoviDestra, labelAttacca);
    	paneWord.getChildren().add(paneComandi);
    }
    public void haiPersoMenu() {
    	timelineGioco.stop();
    	bStart.setPrefSize(4*tilesSize, tilesSize);
    	bStart.setLayoutX(WIDTH_GIOCO/2-tilesSize*2);
    	bStart.setLayoutY(HEIGHT_GIOCO/2+tilesSize);
    	Label lPerso = new Label("RINIZIA");
    	lPerso.setFont((Font.loadFont(getClass().getResourceAsStream("font/SuperPixel-m2L8j.ttf"), 38)));
    	lPerso.setPrefSize(4*tilesSize, tilesSize);
    	lPerso.setLayoutX(WIDTH_GIOCO/2-tilesSize*2);
    	lPerso.setLayoutY(HEIGHT_GIOCO/2-tilesSize);
    	paneHaiPerso.getChildren().addAll(lPerso , bStart);
    	paneWord.getChildren().add(paneHaiPerso);
    	bStart.setOnAction(start ->iniziaAvventura());
    }
    
    public void aggiornaGioco() {
        // Muovi il mostro
        richiamaMostro.muoviMostro();
        lPunteggio.setText("Mostri eliminati: " + countMostriEliminati);
        
        // Verifica la collisione con i mostri
        Shape intersect1 = Shape.intersect(personaggio, rMostro1);
        Shape intersect2 = Shape.intersect(personaggio, rMostro2);
        Shape intersect3 = Shape.intersect(personaggio, rMostro3);
        if(fase1==true && inizioGioco==true) {
        	inizioGioco=false;
        	aggiungiMostro();
        }
        // Verifica la collisione tra il personaggio e il primo mostro
        if (intersect1.getBoundsInLocal().getWidth() != -1 ) {
            System.out.println("collide");
        	if (richiamaPersonaggio.hoAttaccato == true) {
                timelineGioco.stop();
        		paneWord.getChildren().removeAll(spriteMostro, rMostro1);
                mostro1Eliminato = true;
                countMostriEliminati++;
            } else {
                // Perdita di vita solo se non è stato eliminato il mostro
                if (vitaPersonaggio > 0) {
                    vitaPersonaggio--;
                    System.out.println("cuori");
                    AggiornaVita();
                }
            }
        	timelineGioco.play();
        }
        // Verifica la collisione con il secondo mostro
        if (intersect2.getBoundsInLocal().getWidth() != -1 ) {
            if (richiamaPersonaggio.hoAttaccato == true) {
            	timelineGioco.stop();
            	paneWord.getChildren().removeAll(spriteMostro2, rMostro2);
                mostro2Eliminato = true;
                countMostriEliminati++;
            } else {
                // Perdita di vita solo se non è stato eliminato il mostro
                if (vitaPersonaggio > 0) {
                    vitaPersonaggio--;
                    System.out.println("cuori");
                    AggiornaVita();
                }
            }
            timelineGioco.play();
        }
        // Verifica la collisione con il terzo mostro
        if (intersect3.getBoundsInLocal().getWidth() != -1) {
            if (richiamaPersonaggio.hoAttaccato == true) {
            	timelineGioco.stop();
            	paneWord.getChildren().removeAll(spriteMostro3, rMostro3);
                mostro3Eliminato = true;
                countMostriEliminati++;
            } else {
                // Perdita di vita solo se non è stato eliminato il mostro
                if (vitaPersonaggio > 0) {
                    vitaPersonaggio--;
                    System.out.println("cuori");
                    AggiornaVita();
                }
            }
            timelineGioco.play();
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

    private void AggiornaVita() {
		if (vitaPersonaggio==2) {
			Image heart2 = new Image(getClass().getResourceAsStream("Immagini/cuori/heart2.png"));
		    healtImgWiew.setImage(heart2);
		}
		if(vitaPersonaggio==1) {
			Image heart1 = new Image(getClass().getResourceAsStream("Immagini/cuori/heart1.png"));
		    healtImgWiew.setImage(heart1);
		}
		if (vitaPersonaggio==0) {
			Image heart0 = new Image(getClass().getResourceAsStream("Immagini/cuori/zerolife.png"));
		    healtImgWiew.setImage(heart0);
		    timelineGioco.stop();
		    //paneWord.getChildren().removeAll(personaggio, spritePersonaggio);
		    haiPerso=true;
		    fase1=true;
		    mostro1Eliminato = false;
	        mostro2Eliminato = false;
	        mostro3Eliminato = false;
	        paneWord.getChildren().removeAll(rMostro1, spriteMostro, rMostro2, spriteMostro2, rMostro3, spriteMostro3);
		    fase1=true;
		    inizioGioco=true;
	        haiPersoMenu();
		}
		
	}

	// Funzione per aggiungere un nuovo mostro se necessario
    private void aggiungiMostro() {
        mostro1Eliminato = false;
        mostro2Eliminato = false;
        mostro3Eliminato = false;
        if (fase1) {
            int posXM1 = (int) (Math.random() * gameColoum) * tilesSize;
            int posYM1 = (int) (Math.random() * gameRow) * tilesSize;

            // Posiziona il primo mostro
            rMostro1.setX(posXM1);
            rMostro1.setY(posYM1);
            spriteMostro.setX(posXM1);
            spriteMostro.setY(posYM1);

            // Aggiungi i mostri al gioco
            paneWord.getChildren().addAll(rMostro1, spriteMostro);

            // Muovi i mostri
            richiamaMostro.muoviMostro();
        }
        
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
