package application.front;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.Main;
import application.back.Consommation;
import application.back.Service;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FenEnregistrerConsommation extends Stage{
	
	private TextField field_numero = new TextField();
	private ComboBox<String> combo_lieu;
	private TextField field_nom = new TextField();
	private TextField field_quantite = new TextField();
	private TextField field_date = new TextField();
	
	private Button b_enregistrer = new Button("Enregistrer");
	private Button b_annuler = new Button("Annuler");
	
	private boolean pas_erreur = true;
	private boolean erreur_nom = true;
	private boolean erreur_numero = true;
	private boolean erreur_quantite = true;
	
	private String error_style = "-fx-background-color: #fff; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String field_style = "-fx-background-color: #fff; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	private String locked_field_style = "-fx-background-color: #f2f2f2; -fx-border-color: #a0a0a0; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;";
	
	public FenEnregistrerConsommation() throws IOException {
		this.setTitle("Enregistrer une nouvelle consommation"); 
		this.setResizable(false);
		this.initStyle(StageStyle.UNDECORATED);
		this.setScene(new Scene(FXMLLoader.load(getClass().getResource("controllers/enregistrerConso.fxml"))));
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
					c.setItems(FXCollections.observableArrayList("Bar", "Chambre"));
					this.combo_lieu = c;
				} else if (n.getId().equals("f_nom")) {
					this.field_nom = (TextField) n;
				} else if (n.getId().equals("f_quantite")) {
					this.field_quantite = (TextField) n;
				} else if (n.getId().equals("f_date")) {
					this.field_date = (TextField) n;
				}
			}
		}
	}

	private void contenu() {
		
		ArrayList<Object> fields = new ArrayList<>(); 
		fields.add(this.field_nom);
		fields.add(this.field_numero);
		fields.add(this.combo_lieu);
		fields.add(this.field_quantite);
		fields.add(this.field_date);
		
		this.field_nom.setOnKeyTyped((e) -> FieldNomKeyTyped(e));
		this.field_numero.setOnKeyTyped((e) -> FieldNumeroKeyTyped(e));
		this.field_quantite.setOnKeyTyped((e) -> FieldQuantiteKeyTyped(e));
		
		this.field_nom.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNomUnfocused(obs, oldVal, newVal));
		this.field_numero.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldNumeroUnfocused(obs, oldVal, newVal));
		this.field_quantite.focusedProperty().addListener((obs, oldVal, newVal) -> isFieldQuantiteUnfocused(obs, oldVal, newVal));
		
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
		
		Consommation conso = new Consommation(this.field_numero.getText(), this.combo_lieu.getValue(), this.field_nom.getText(), Integer.parseInt(this.field_quantite.getText()), this.field_date.getText());
		boolean en_double = false;
		Alert a;
		
		for (Service c : Consommation.chargerTout()) {
			if (((Consommation)c).equals(conso)) en_double = true;
		}
		
		if (en_double) {
			a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Erreur");
			a.setContentText("Cette consommation existe déjà !");
			a.showAndWait();
		} else {
			a = new Alert(Alert.AlertType.CONFIRMATION);
			a.setTitle("Enregistrement");
			a.setContentText("Etes-vous sûr de vouloir enregistrer pour la chambre " + conso.getNumero() + ", " + conso.getQuantite() + " " + conso.getLibelle());
			a.showAndWait();
			if (a.getResult() == ButtonType.OK) {
				System.out.println(conso.getNumero());
				System.out.println(conso.getLieu());
				System.out.println(conso.getLibelle());
				System.out.println(conso.getQuantite());
				System.out.println(conso.getDate());
				
				conso.sauvegarder();
				
				for (Service s : Consommation.chargerTout()) {
					System.out.println(s.getNumero() + " " + ((Consommation) s).getLibelle());
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
		} else if((this.field_nom.getText().toLowerCase().compareTo("beurre doux")==0)) {
			this.field_nom.setStyle(error_style);
			this.field_nom.setText("Pas de beurre doux !!!!!!!!!!!!");
			Alert a = new Alert(Alert.AlertType.WARNING);
			a.setTitle("Beurre doux !!!!!");
			a.setContentText("INTERDIT !!");
			a.showAndWait();
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
			this.testErreurField();
		}
	}
	
	private void testErreurField() {
		System.out.println(this.erreur_quantite);
		if (((this.erreur_nom) || (this.erreur_numero) || (this.erreur_quantite))) {
			this.b_enregistrer.setDisable(true);
		} else {
			this.b_enregistrer.setDisable(false);
		}
	}
}
