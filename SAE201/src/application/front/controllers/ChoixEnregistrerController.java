package application.front.controllers;

import java.io.IOException;

import application.front.FenChoixEnregistrer;
import application.front.FenEnregistrerConsommation;
import application.front.FenEnregistrerPetitDejeuner;
import application.front.FenEnregistrerPrestation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChoixEnregistrerController {
	
	javafx.scene.layout.BorderStroke border_stroke = new javafx.scene.layout.BorderStroke(
            Color.WHITE,                       // Couleur de la bordure
            javafx.scene.layout.BorderStrokeStyle.SOLID,  // Style de la bordure
            new javafx.scene.layout.CornerRadii(40),      // Coins arrondis
            new javafx.scene.layout.BorderWidths(2)        // Largeur de la bordure
    );
	
	@FXML
    private AnchorPane main_container;

    @FXML
    private Button b_conso;

    @FXML
    private Button b_petitdej;

    @FXML
    private Button b_prestation;

    @FXML
    private Button b_retour;

    @FXML
    void ClickButton(ActionEvent event) throws IOException {
    	
    	Button b = (Button) event.getSource();
    	Stage stage = (Stage) b.getScene().getWindow();
    	
    	/*Click Boutton Conso*/
    	if (b.equals(this.b_conso)) {
    		
    	    Stage conso = new FenEnregistrerConsommation();
    	    
    	    conso.show();
    	}
    	
    	/*Click Boutton PetitDej*/
    	else if (b.equals(this.b_petitdej)) {
    		;
    		Stage dej = new FenEnregistrerPetitDejeuner();
    		dej.show();
    	} 
    	
    	/*Click Boutton Prestation*/
    	else if (b.equals(this.b_prestation)) {
    		Stage presta = new FenEnregistrerPrestation();
    		presta.show();
    	} 
    	
    	/*Click Boutton Retour*/
    	else{
    	    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("accueil.fxml"))));
    	    stage.show();
    	}
    }

    @FXML
    void MouseInButton(MouseEvent event) {
    	Button b = (Button) event.getSource();
    	b.setTranslateY(-1);
    	b.setBorder(new Border(border_stroke));
    }
    
    @FXML
    void MouseQuitButton(MouseEvent event) {
    	Button b = (Button) event.getSource();
    	b.setTranslateY(0);
    	b.setBorder(null);
    }

}
