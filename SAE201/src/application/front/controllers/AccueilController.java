package application.front.controllers;

import java.io.IOException;

import application.Main;
import application.front.FenChoixEnregistrer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class AccueilController {

	javafx.scene.layout.BorderStroke border_stroke = new javafx.scene.layout.BorderStroke(
            Color.WHITE,                       // Couleur de la bordure
            javafx.scene.layout.BorderStrokeStyle.SOLID,  // Style de la bordure
            new javafx.scene.layout.CornerRadii(40),      // Coins arrondis
            new javafx.scene.layout.BorderWidths(2)        // Largeur de la bordure
    );
	
	@FXML
    private AnchorPane main_container;
	
    @FXML
    private Button b_enregistrer;

    @FXML
    private Button b_modifier;

    @FXML
    private Button b_quitter;
    
    Stage stage;
    
    public void initialize(){
    	
    }

    @FXML
    void ClickButton(ActionEvent event) throws IOException {
    	Button b = (Button) event.getSource();
    	
    	this.stage = (Stage) b_quitter.getScene().getWindow();
    	
    	/*Click Boutton Enregistrer*/
    	if (b.equals(this.b_enregistrer)) {
    		System.out.println("Enregistrer");
    	    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("choixEnregistrer.fxml"))));
    	    stage.show();
    	}
    	
    	/*Click Boutton Modifier*/
    	else if (b.equals(this.b_modifier)) {
    		System.out.println("Modifier");
    		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("choixModifier.fxml"))));
    	    stage.show();
    	} 
    	
    	/*Click Boutton Quitter*/
    	else
    	Platform.exit();
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
