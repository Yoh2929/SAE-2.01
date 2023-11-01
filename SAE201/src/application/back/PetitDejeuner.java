package application.back;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class PetitDejeuner extends Service{
    
	private int quantite;
    
    public PetitDejeuner() {
    	super();
    }

    public PetitDejeuner(String numero, String lieu, int quantite, String date){
        super(numero, lieu, date);
        this.quantite = quantite;
    }
    
    public String toString() {
    	return super.toString() + " - " + this.quantite + " en " + this.getLieu().toLowerCase() + " - " + this.getDate();
    }

    public int getQuantite(){return this.quantite;}

    public void setQuantite(int q){this.quantite = q;}

	public void modifier(String numero, String lieu, String libelle, int quantite, String date) {
		super.modifier(numero, lieu, date);
		this.setQuantite(quantite);
	}
	
    public void sauvegarder() {
		Service.sauvegarder("petitdej.dat", this);
	}
    
    public static void ecraserTout(Collection<Service> contenu_fichier) {
		Service.ecraserTout("petitdej.dat", contenu_fichier);
	}
    
    public static Collection<Service> chargerTout() {
		return Service.chargerTout("petitdej.dat");
	}
}
