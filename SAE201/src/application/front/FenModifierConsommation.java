package application.front;

import java.io.IOException;

import application.Main;
import application.back.Consommation;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FenModifierConsommation extends Stage{
	
	private Button b_modifier;
	private Button b_annuler;
	private TextField field_numero;
	private ComboBox<String> combo_lieu;
	private TextField field_nom;
	private TextField field_quantite;
	private TextField field_date;
	private Label label_titre;
	
	private Consommation conso;
	private FenListeModifierConsommation parent;
	
	private String error_style = "-fx-background-color: #fff; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String field_style = "-fx-background-color: #fff; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String locked_field_style = "-fx-background-color: #f2f2f2; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	
	private boolean pas_erreur = true;
	private boolean erreur_date = false;
	private boolean erreur_nom = false;
	private boolean erreur_numero = false;
	private boolean erreur_quantite = false;

	public FenModifierConsommation(FenListeModifierConsommation parent, Consommation conso) throws IOException {
		this.conso = conso;
		this.parent = parent;
		
		this.setTitle("Modifier une consommation"); 
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/enregistrerConso.fxml"))));
		this.lierContenu();
	}
	
	
	private void lierContenu() {
		for (Node n : Main.getAllNodes(this.getScene().getRoot())) {
			if (n.getId() != null){
				
				if (n instanceof TextField) {
					n.setStyle(field_style);
					
					n.setOnMousePressed((e) -> {
						n.setStyle(this.field_style);
						((TextField) n).selectAll();
					});
				}
				
				if (n.getId().equals("b_enregistrer")) {
					this.b_modifier = (Button) n;
					
					this.b_modifier.setText("Modifier");
					this.b_modifier.setOnAction(e -> ClickButtonModifier());
				} else if (n.getId().equals("b_annuler")) {
					this.b_annuler = (Button) n;
					
				} else if (n.getId().equals("f_numero")) {
					this.field_numero = (TextField) n;
					
					this.field_numero.setOnKeyTyped((e) -> FieldNumeroKeyTyped(e));
					this.field_numero.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNumeroUnfocused(obs, oldVal, newVal));
					this.field_numero.setStyle(this.locked_field_style);
					this.field_numero.setEditable(false);
					this.field_numero.setDisable(true);
					this.field_numero.setText(this.conso.getNumero());
				} else if (n.getId().equals("c_lieu")) {
					this.combo_lieu = ((ComboBox<String>) n);
					
					this.combo_lieu.setItems(FXCollections.observableArrayList("Bar", "Chambre"));
					
					for (int i = 0; i < this.combo_lieu.getItems().size(); i++) {
						if (this.combo_lieu.getItems().get(i).equals(this.conso.getLieu())) {
							this.combo_lieu.getSelectionModel().select(i);
						}
					}
				} else if (n.getId().equals("f_nom")) {
					this.field_nom = (TextField) n;
					
					this.field_nom.setOnKeyTyped((e) -> FieldNomKeyTyped(e));
					this.field_nom.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNomUnfocused(obs, oldVal, newVal));
					this.field_nom.setText(this.conso.getLibelle());
				} else if (n.getId().equals("f_quantite")) {
					this.field_quantite = (TextField) n;
					
					this.field_quantite.setOnKeyTyped((e) -> FieldQuantiteKeyTyped(e));
					this.field_quantite.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldQuantiteUnfocused(obs, oldVal, newVal));
					this.field_quantite.setText(String.valueOf(this.conso.getQuantite()));
				} else if (n.getId().equals("f_date")) {
					this.field_date = (TextField) n;
					
					this.field_date.setOnKeyTyped(e -> FieldDateKeyTyped(e));
					this.field_date.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldDateUnfocused(obs, oldVal, newVal));
					this.field_date.setText(this.conso.getDate());
				} else if (n.getId().equals("l_titre")) {
					this.label_titre = (Label) n;
					
					this.label_titre.setText("Modifier une consommation");
				}
			}
		}
	}
	
	private void ClickButtonModifier() {
		Alert a;
		
		if (!(((this.erreur_nom) || (this.erreur_numero) || (this.erreur_quantite) || (this.erreur_date)))) {
			Consommation c = new Consommation(
					((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).getNumero(),
					this.combo_lieu.getSelectionModel().getSelectedItem(),
					this.field_nom.getText(),
					Integer.parseInt(this.field_quantite.getText()),
					this.field_date.getText()
					);
			
			boolean en_double = false;
			for (Object c_actuel : this.parent.getLConso().getItems()) {
				if (((Consommation)c_actuel).equals(c)) {
					en_double = true;
					break;
				}
			}
			
			if (en_double) {
				a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Erreur");
				a.setContentText("Cette consommation existe déjà !");
				a.showAndWait();
			} else {
				((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).setNumero(c.getNumero());
				((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).setLieu(c.getLieu());
				((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).setLibelle(c.getLibelle());
				((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).setQuantite(c.getQuantite());
				((Consommation) this.parent.getLConso().getSelectionModel().getSelectedItem()).setDate(c.getDate());
				
				Consommation.ecraserTout(this.parent.getLConso().getItems());
				
				this.parent.getLConso().getItems().setAll(Consommation.chargerTout());
				this.parent.getLConso().refresh();
				this.close();
			}
			
			
		} else {
			a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Erreur");
			a.setContentText("Champ(s) incorrect(s) !");
			a.showAndWait();
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
		}
	}
	
	private void isFieldDateUnfocused(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
		if ((!newVal) && (this.erreur_date)) {
			if (this.field_date.getText().equals("") || (this.field_date.getText().equals("Champ vide !"))) {
				/*Verifie la date*/
				
				this.field_date.setStyle(error_style);
				this.field_date.setText("Champ vide !");
			} else if (!Main.isDateValid(this.field_nom.getText())) {
				
				this.field_date.setStyle(error_style);
				this.field_date.setText("Date non valide !");
			}
		} 
	}
	
	private void FieldDateKeyTyped(KeyEvent e) {
		/*Verifie la date*/
		
		this.erreur_date = true;
		if ((this.field_date.getText().equals("")) || (this.field_date.getText().equals("Champ vide !"))) {
			this.pas_erreur = false;
		} else if (!Main.isDateValid(this.field_date.getText())) {
			this.pas_erreur = false;
		} else {
			this.erreur_date = false;
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
		}
	}
	
	private void isFieldQuantiteUnfocused(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
		if (!newVal) {
			
			if ((this.field_quantite.getText().equals("")) || (this.field_quantite.getText().equals("Champ vide !"))) {
				this.field_quantite.setStyle(error_style);
				this.field_quantite.setText("Champ vide !");
			} else if ((!Main.isNumericInt(this.field_quantite.getText())) || (this.field_quantite.getText().contains("."))) {
				this.field_quantite.setStyle(error_style);
				this.field_quantite.setText("La quantité doit être un entier !");
			}
		}
	}
	
	private void FieldQuantiteKeyTyped(KeyEvent e) {
		
		Main.replaceStringTextField(this.field_quantite, ",", "");
		Main.replaceStringTextField(this.field_quantite, ".", "");
		Main.replaceStringTextField(this.field_quantite, "-", "");
		
		this.erreur_quantite = true;
		if ((this.field_quantite.getText().equals("")) || (this.field_quantite.getText().equals("Champ vide !"))) {
			pas_erreur = false;
		} else if (!Main.isNumericInt(this.field_quantite.getText())) {
			pas_erreur = false;
		}
		else {
			this.erreur_quantite = false;
		}
	}
}
