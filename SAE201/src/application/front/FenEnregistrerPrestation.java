package application.front;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.Main;
import application.back.Consommation;
import application.back.Prestation;
import application.back.Service;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FenEnregistrerPrestation extends Stage{
	
	private TextField field_numero = new TextField();
	private ComboBox<String> combo_lieu;
	private TextField field_nom = new TextField();
	private TextField field_prix = new TextField();
	private TextField field_date = new TextField();
	
	private Button b_enregistrer = new Button("Enregistrer");
	private Button b_annuler = new Button("Annuler");
	
	private boolean pas_erreur = true;
	private boolean erreur_nom = true;
	private boolean erreur_numero = true;
	private boolean erreur_prix = true;
	
	private String error_style = "-fx-background-color: #fff; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String field_style = "-fx-background-color: #fff; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String locked_field_style = "-fx-background-color: #f2f2f2; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	
	public FenEnregistrerPrestation() throws IOException {
		this.setTitle("Enregistrer une nouvelle consommation"); 
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/enregistrerPrestation.fxml"))));
		this.lierContenu();
		this.contenu();
	}
	
	
	
	private void lierContenu() {
		for (Node n : Main.getAllNodes(this.getScene().getRoot())) {
			if (n.getId() != null){
				if (n.getId().equals("b_enregistrer")) {
					this.b_enregistrer = (Button) n;
				} else if (n.getId().equals("b_annuler")) {
					this.b_annuler = (Button) n;
					
				} else if (n.getId().equals("f_numero")) {
					this.field_numero = (TextField) n;
				} else if (n.getId().equals("c_lieu")) {
					ComboBox<String> c = ((ComboBox<String>) n);
					c.setItems(FXCollections.observableArrayList("Pressing", "Navette"));
					this.combo_lieu = c;
					this.combo_lieu.setOnAction(e -> ClickComboLieu());
				} else if (n.getId().equals("f_nom")) {
					this.field_nom = (TextField) n;
				} else if (n.getId().equals("f_prixht")) {
					this.field_prix = (TextField) n;
				} else if (n.getId().equals("f_date")) {
					this.field_date = (TextField) n;
				}
			}
		}
	}

	private void ClickComboLieu() {
		if (this.combo_lieu.getSelectionModel().getSelectedItem().equals("Navette")) {
			this.field_nom.setDisable(true);
			this.field_nom.setStyle(locked_field_style);
			
			this.field_nom.setText("");
		} else {
			this.field_nom.setDisable(false);
			this.field_nom.setStyle(field_style);
		}
	}

	private void contenu() {
		
		ArrayList<Object> fields = new ArrayList<>(); 
		fields.add(this.field_nom);
		fields.add(this.field_numero);
		fields.add(this.combo_lieu);
		fields.add(this.field_prix);
		fields.add(this.field_date);
		
		this.field_nom.setOnKeyTyped((e) -> FieldNomKeyTyped(e));
		this.field_numero.setOnKeyTyped((e) -> FieldNumeroKeyTyped(e));
		this.field_prix.setOnKeyTyped((e) -> FieldPrixKeyTyped(e));
		
		this.field_nom.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNomUnfocused(obs, oldVal, newVal));
		this.field_numero.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNumeroUnfocused(obs, oldVal, newVal));
		this.field_prix.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldPrixUnfocused(obs, oldVal, newVal));
		
		this.field_date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		this.field_date.setEditable(false);
		
		this.combo_lieu.getSelectionModel().select(0);
		
		this.b_enregistrer.setDisable(true);
		
		int i;
		for (i = 0; i < fields.size(); i++) {
			Object o = ((Node) fields.get(i));
			if (i == fields.size()-1) {
				((Node) o).setStyle(this.locked_field_style);
			} else {
				((Node) o).setStyle(this.field_style);
				((Node) o).setOnMousePressed((e) -> {
					((Node) o).setStyle(this.field_style);
					if (o instanceof TextField) {
						((TextField) o).selectAll();
					}
				});
			}
		}
		
		this.b_enregistrer.setOnAction(e -> ClickButtonEnregistrer());
		this.b_annuler.setOnAction((e) -> {
			this.close();
		});
	}
	
	private void ClickButtonEnregistrer() {
		
		Prestation presta = new Prestation(this.field_numero.getText(), this.combo_lieu.getValue(), this.field_nom.getText(), Float.parseFloat(this.field_prix.getText()), this.field_date.getText());
		boolean en_double = false;
		Alert a;
		
		for (Service c : Prestation.chargerTout()) {
			if (((Prestation)c).equals(presta)) en_double = true;
		}
		
		if (en_double) {
			a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Erreur");
			a.setContentText("Cette prestation existe déjà !");
			a.showAndWait();
		} else {
			a = new Alert(Alert.AlertType.CONFIRMATION);
			a.setTitle("Enregistrement");
			if (this.combo_lieu.getSelectionModel().getSelectedItem().equals("Pressing")) {
				a.setContentText("Etes-vous sûr de vouloir enregistrer pour la chambre " + presta.getNumero() + ", un pressing: " + presta.getLibelle() + " pour " + presta.getPrixHT() + "€ HT");
			} else {
				a.setContentText("Etes-vous sûr de vouloir enregistrer pour la chambre " + presta.getNumero() + ", une navette pour " + presta.getPrixHT() + "€ HT");
			}
			
			a.showAndWait();
			if (a.getResult() == ButtonType.OK) {
				System.out.println(presta.getNumero());
				System.out.println(presta.getLieu());
				System.out.println(presta.getLibelle());
				System.out.println(presta.getPrixHT());
				System.out.println(presta.getDate());
				
				presta.sauvegarder();
				
				for (Service s : Prestation.chargerTout()) {
					System.out.println(s.getNumero() + " " + ((Prestation) s).getLibelle());
				}
				
				this.close();
			}
		}
	}
	
	private void isFieldNomUnfocused(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
		if ((!newVal) && (this.erreur_nom)) {
			/*Verifie le nom*/
			
			this.field_nom.setStyle(error_style);
			this.field_nom.setText("Champ vide !");
		}
	}
	
	private void FieldNomKeyTyped(KeyEvent e) {
		/*Verifie le nom*/
		
		this.erreur_nom = true;
		if ((this.field_nom.getText().equals("")) || (this.field_nom.getText().equals("Champ vide !"))) {
			this.pas_erreur = false;
		} else {
			this.erreur_nom = false;
			this.testErreurField();
		}
	}
	
	private void isFieldNumeroUnfocused(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
		if (!newVal) {
			/*Verifie le numero de chambre*/
			
			Integer[] numero_un = {0,1,2,3};
			
			if ((this.field_numero.getText().equals("")) || (this.field_numero.getText().equals("Champ vide !"))) {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("Champ vide !");
			} else if (!Main.isNumericInt(this.field_numero.getText())) {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("Le numero doit être un entier !");
			} else if (this.field_numero.getText().charAt(0) == '-') {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("Numéro de chambre négatif !");
			} else if (this.field_numero.getText().length() != 3) {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("Numéro de chambre à 3 chiffres !");
			} else if (!Main.contains(numero_un, Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(0))))) {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("Etages entre 0 et 3 !");
			} else if ((Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(1)) + String.valueOf(this.field_numero.getText().charAt(2))) > 20) || (Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(1)) + String.valueOf(this.field_numero.getText().charAt(2))) < 1)) {
				this.field_numero.setStyle(error_style);
				this.field_numero.setText("20 chambres par étages !");
			}
		}
	}
	
	private void FieldNumeroKeyTyped(KeyEvent e) {
		/*Verifie le numero de chambre*/
		
		Integer[] numero_un = {0,1,2,3};
		
		Main.replaceStringTextField(this.field_numero, ",", "");
		Main.replaceStringTextField(this.field_numero, ".", "");
		Main.replaceStringTextField(this.field_numero, "-", "");
		
		this.erreur_numero = true;
		if (!((this.field_numero.getText().equals("")) || (this.field_numero.getText().equals("Champ vide !"))
		|| (!Main.isNumericInt(this.field_numero.getText()))
		|| (this.field_numero.getText().length() != 3)
		|| (!Main.contains(numero_un, Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(0)))))
		|| ((Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(1)) + String.valueOf(this.field_numero.getText().charAt(2))) > 20) || (Integer.parseInt(String.valueOf(this.field_numero.getText().charAt(1)) + String.valueOf(this.field_numero.getText().charAt(2))) < 1)))) {
			this.erreur_numero = false;
			this.testErreurField();
		}
	}
	
	private void isFieldPrixUnfocused(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
		if (!newVal) {
			
			if ((this.field_prix.getText().equals("")) || (this.field_prix.getText().equals("Champ vide !"))) {
				this.field_prix.setStyle(error_style);
				this.field_prix.setText("Champ vide !");
			} else if (!Main.isNumeric(this.field_prix.getText())) {
				this.field_prix.setStyle(error_style);
				this.field_prix.setText("La quantité doit être un nombre !");
			}
		}
	}
	
	private void FieldPrixKeyTyped(KeyEvent e) {
		
		Main.replaceStringTextField(this.field_prix, ",", ".");
		Main.replaceStringTextField(this.field_prix, "-", "");
		
		this.erreur_prix = true;
		if ((this.field_prix.getText().equals("")) || (this.field_prix.getText().equals("Champ vide !"))) {
			pas_erreur = false;
		} else if (!Main.isNumeric(this.field_prix.getText())) {
			pas_erreur = false;
		}
		else {
			this.erreur_prix = false;
			this.testErreurField();
		}
	}
	
	private void testErreurField() {
		System.out.println(this.erreur_prix);
		if ((((this.erreur_nom) && (this.combo_lieu.getSelectionModel().getSelectedItem().equals("Pressing"))) || (this.erreur_numero) || (this.erreur_prix))) {
			this.b_enregistrer.setDisable(true);
		} else {
			this.b_enregistrer.setDisable(false);
		}
	}
}
