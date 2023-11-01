package application.front;

import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FenChoixEnregistrer extends Stage {
	
	private final int widthBtn = 100;
	private final int heightBtn = 30;
	private Label titre = new Label("Quelle service désirez-vous enregistrer ? ");
	private Button bnPretsation = new Button("Prestation");
	private Button bnPetitDej = new Button("Petit Déjeuner");
	private Button bnConso = new Button("Consommation");
	private Button retour = new Button("Retour");
    private VBox root = new VBox();
    private GridPane lesBouttons = new GridPane();
     
     public FenChoixEnregistrer() {
    	 
    	 this.setHeight(400);
    	 this.setWidth(600);
    	 this.setResizable(false);
    	 this.setScene(new Scene(creerContenu()));
    	 String cssFile = getClass().getResource("application.css").toExternalForm();
    	 this.getScene().getStylesheets().add(cssFile);
     }
     
     
     public Parent creerContenu() {
    	
    	 /* Ajouts des bouttons */
    	 lesBouttons.add(bnPretsation, 0, 0);
    	 lesBouttons.add(bnPetitDej, 1, 0);
    	 lesBouttons.add(bnConso, 2, 0);
    	 lesBouttons.add(retour, 1, 1);
    	 
    	 
    	 lesBouttons.setPadding(new Insets(10));
    	 lesBouttons.setHgap(10);
    	 lesBouttons.setVgap(10);
    	 GridPane.setMargin(retour, new Insets(20,0,0,0));
    	 /* Positionnement des éléments */
    	
    	 bnPretsation.setPrefWidth(widthBtn);
    	 lesBouttons.setAlignment(Pos.CENTER);
    	 
    	 root.setAlignment(Pos.CENTER);
    	
    	 /* Taille des bouttons */
    	 
    	 bnPretsation.setPrefWidth(widthBtn);
    	 bnPetitDej.setPrefWidth(widthBtn);
    	 bnConso.setPrefWidth(widthBtn);
    	 retour.setPrefWidth(widthBtn);
    	 
    	 bnPretsation.setPrefHeight(heightBtn);
    	 bnPetitDej.setPrefHeight(heightBtn);
    	 bnConso.setPrefHeight(heightBtn);
    	 retour.setPrefHeight(heightBtn);
    	 
    	 
    	 
    	 /* Faire un hover sur les bouttons */
    	 
    	 for(Node element : lesBouttons.getChildren()) {
 			ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(0.5), element);
 	        scaleTransition1.setToX(1.1);
 	        scaleTransition1.setToY(1.1);

 	        // Créer une transition d'échelle vers l'échelle initiale
 	        ScaleTransition scaleTransitionReverse1 = new ScaleTransition(Duration.seconds(0.5), element);
 	        scaleTransitionReverse1.setToX(1.0);
 	        scaleTransitionReverse1.setToY(1.0);

 	        // Ajouter les gestionnaires d'événements pour démarrer les transitions lorsqu'on survole ou quitte le bouton
 	        element.setOnMouseEntered(event -> scaleTransition1.play());
 	        element.setOnMouseExited(event -> scaleTransitionReverse1.play());
 			element.setOnMouseEntered(event -> scaleTransition1.play());
 			element.setOnMouseExited(event -> scaleTransitionReverse1.play());
         	
         }
    	 
    	 /* Ajout de style cc */
    	 root.setStyle("-fx-background-color :  #FAEBD7;");
    	 titre.setStyle("-fx-font-size : 1.9em;");
    	 retour.getStyleClass().add("button");
    	 
    	 /* Configuration boutton */
    	 
    	 retour.setOnAction((e) -> {
    		 this.close();
    		 new FenPrincipale().show();
    	 });
    	 
    	 bnConso.setOnAction((e) -> {
    		 try {
				new FenEnregistrerConsommation().show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		 this.DisableButtons(true);
    	 });
    	 bnPetitDej.setOnAction((e) -> {
    		 try {
				new FenEnregistrerPetitDejeuner().show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		 this.DisableButtons(true);
    	 });
    	 bnPretsation.setOnAction((e) -> {
    		 try {
				new FenEnregistrerPrestation().show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		 this.DisableButtons(true);
    	 });
    	 
    	 /* Espace entre les éléments */
    	 
    	 root.setSpacing(20);
    	 root.getChildren().addAll(titre, lesBouttons);
    	 return root;
    	 
     }
     
     public void DisableButtons(boolean b) {
     	this.bnConso.setDisable(b);
     	this.bnPetitDej.setDisable(b);
     	this.bnPretsation.setDisable(b);
     	this.retour.setDisable(b);
  	 }
}
