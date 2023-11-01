package application.front.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EnregistrerConsoController {
	
	javafx.scene.layout.BorderStroke border_stroke = new javafx.scene.layout.BorderStroke(
            Color.WHITE,                       // Couleur de la bordure
            javafx.scene.layout.BorderStrokeStyle.SOLID,  // Style de la bordure
            new javafx.scene.layout.CornerRadii(40),      // Coins arrondis
            new javafx.scene.layout.BorderWidths(2)        // Largeur de la bordure
    );
	
	@FXML
    private Label l_titre;

	@FXML
    private Button b_annuler;

    @FXML
    private Button b_enregistrer;

    @FXML
    private ComboBox<?> c_lieu;

    @FXML
    private TextField f_date;

    @FXML
    private TextField f_nom;

    @FXML
    private TextField f_numero;

    @FXML
    private TextField f_quantite;

    @FXML
    private AnchorPane main_container;
    
    @FXML
    void ClickButton(ActionEvent event) {
    	Button b = (Button) event.getSource();
    	Stage stage = (Stage) b.getScene().getWindow();
    	
    	if (b.equals(b_enregistrer)) {
    		
    	}
    	
    	else
    	stage.close();
    }

    @FXML
    void KeyTypedField(KeyEvent event) {

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
