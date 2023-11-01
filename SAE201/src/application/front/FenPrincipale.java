package application.front;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FenPrincipale extends Stage{

	
	public FenPrincipale(){
		this.setTitle("Le cheval blanc"); 
		
		try {
			this.setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/accueil.fxml"))));
			this.setResizable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getScene().getStylesheets().add(getClass().getResource("controllers/style.css").toExternalForm());
	}
}
