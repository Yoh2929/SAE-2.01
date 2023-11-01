package application.back;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class Prestation extends Service{

    private String libelle;
    private float prixHT;
    
    public Prestation() {
    	super();
    }

    public Prestation(String numero, String lieu, String libelle, float prixHT, String date){
        super(numero, lieu, date);
        this.libelle = libelle;
        this.prixHT = prixHT;
    }
    
    public boolean equals(Prestation c) {
    	return (((Service) c).equals(((Service)this)) && (this.libelle.equals(c.getLibelle())));
    }

    public String getLibelle(){return this.libelle;}
    public float getPrixHT() {return this.prixHT;}

    public void setLibelle(String l) {this.libelle = l;}
    public void setPrixHT(float p) {this.prixHT = p;}

	public void modifier(String numero, String lieu, String libelle, float prixHT, String date) {
		super.modifier(numero, lieu, date);
		this.setLibelle(libelle);
		this.setPrixHT(prixHT);
	}
	
	public void sauvegarder() {
		Service.sauvegarder("prestation.dat", this);
	}
	
	public static Collection<Service> chargerTout() {
		return Service.chargerTout("prestation.dat");
	}
}
