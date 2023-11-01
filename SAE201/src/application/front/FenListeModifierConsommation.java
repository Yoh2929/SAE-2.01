package application.front;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.*;

import application.Main;
import application.back.Consommation;
import application.back.Service;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class FenListeModifierConsommation extends Stage{
	
	private TextField field_recherche = new TextField();
	
	private Button b_modifier = new Button();
	private Button b_supprimer = new Button();
	private Button b_supprimer_tout = new Button();
	private Button b_retour = new Button();

	private ListView l_conso = new ListView<Consommation>();
	
	public FenListeModifierConsommation() throws IOException {
		this.setResizable(false);
		this.setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/modifierListeConso.fxml"))));
		this.lierContenu();
		this.contenu();
	}
	
	public ListView getLConso() {
		return this.l_conso;
	}
	
	private void lierContenu() {
		for (Node n : Main.getAllNodes(this.getScene().getRoot())) {
			if (n.getId() != null){
				if (n.getId().equals("b_retour")) {
					this.b_retour = (Button) n;
				} else if (n.getId().equals("b_modifier")) {
					this.b_modifier = (Button) n;
				} else if (n.getId().equals("b_supp")) {
					this.b_supprimer = (Button) n;
				} else if (n.getId().equals("b_supptout")) {
					this.b_supprimer_tout = (Button) n;
				} else if (n.getId().equals("l_conso")) {
					this.l_conso  = (ListView) n;
				} else if (n.getId().equals("f_recherche")) {
					this.field_recherche = (TextField) n;
				}
			}
		}
	}

	private void contenu() {
		
		BooleanBinding is_selection_empty = Bindings.equal(this.l_conso.getSelectionModel().selectedIndexProperty(), -1);
		
		for (Service c : Consommation.chargerTout()) {
			System.out.println(c.getNumero());
		}
		this.l_conso.getItems().addAll(Consommation.chargerTout());
		
		this.b_modifier.disableProperty().bind(is_selection_empty);
		this.b_supprimer.disableProperty().bind(is_selection_empty);
		
		this.b_supprimer.setOnAction(e -> ClickButtonSupprimer());
		this.b_supprimer_tout.setOnAction(e -> ClickButtonSupprimerTout());
		this.b_modifier.setOnAction(e -> ClickButtonModifier());
		this.b_retour.setOnAction(e -> {
			try {
				((Stage) this.b_retour.getScene().getWindow()).setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/choixModifier.fxml"))));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	private void ClickButtonModifier() {
		try {
			new FenModifierConsommation(this,(Consommation) this.l_conso.getSelectionModel().getSelectedItem()).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ClickButtonSupprimer() {
		
		Consommation selected_item = (Consommation) this.l_conso.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Attention !!!!");
		alert.setContentText("Vous êtes sûr de vouloir supprimer \"" + selected_item.getLibelle() + "\" pour la chambre n°" + selected_item.getNumero() + " pour le " + selected_item.getDate());
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			Collection<Service> consos = Consommation.chargerTout();
			ObservableList<Service> new_consos = FXCollections.observableArrayList();
			
			for (Service c : consos) {	
				if (!c.toString().equals(this.l_conso.getSelectionModel().getSelectedItem().toString())) {
					new_consos.add(c);
				}
			}
			
			Consommation.ecraserTout(new_consos);
			this.l_conso.setItems(new_consos);
			this.l_conso.refresh();
		}
	}

	private void ClickButtonSupprimerTout() {
		if (!this.l_conso.getItems().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Attention !!!!");
			alert.setContentText("Vous êtes sûr de vouloir tout supprimer ?!");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				new File("consommation.dat").delete();
				this.l_conso.setItems(null);
				this.l_conso.refresh();
			}
		}
		
	}
}
