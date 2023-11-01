package application.back;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Consommation extends Service{

    private int quantite;
    private String libelle;
    
    public Consommation() {
    	super();
    }

    public Consommation(String numero, String lieu, String libelle, int quantite, String date){
        super(numero, lieu, date);
        this.libelle = libelle;
        this.quantite = quantite;
    }
    
    public boolean equals(Consommation c) {
    	return (((Service) c).equals(((Service)this)) && (this.libelle.equals(c.getLibelle())));
    }
    
    public String toString() {
    	return super.toString() + " - " + this.quantite + " " + this.libelle + " en " + this.getLieu().toLowerCase() + " - " + this.getDate();
    }

    public int getQuantite(){return this.quantite;}
    public String getLibelle(){return this.libelle;}

    public void setQuantite(int q){this.quantite = q;}
    public void setLibelle(String l) {this.libelle = l;};
    
	public void modifier(String numero, String lieu, String libelle, int quantite, String date) {
		super.modifier(numero, lieu, date);
		this.setLibelle(libelle);
		this.setQuantite(quantite);
	}
	
	public void sauvegarder() {
		Service.sauvegarder("consommation.dat", this);
	}
	
	public static void ecraserTout(Collection<Service> contenu_fichier) {
		Service.ecraserTout("consommation.dat", contenu_fichier);
	}
	
	public static Collection<Service> chargerTout() {
		return Service.chargerTout("consommation.dat");
	}
}