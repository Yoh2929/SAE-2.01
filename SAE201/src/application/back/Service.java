package application.back;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Service implements Serializable{
    
    private String lieu;
    private String numero;
    private String date;

    public Service(){}

    public Service(String numero, String lieu, String date){
    	this.numero = numero;
        this.lieu = lieu;
        this.date = date;
    }
    
    public String toString() {
    	return "Chambre " + this.numero;
    }

    public String getLieu(){return this.lieu;}
    public String getNumero() {return this.numero;}
    public String getDate(){return this.date;}
    
    public void setLieu(String l){this.lieu = l;}
    public void setNumero(String n) {this.numero = n;}
    public void setDate(String d){this.date = d;}

    public void modifier(String numero, String lieu, String date) {
    	this.setNumero(numero);
		this.setLieu(lieu);
		this.setDate(date);
    }
    
    public static void sauvegarder(String nom_fichier, Service service) {
		
		Collection<Service> contenu_fichier = Service.chargerTout(nom_fichier);
		
		try (ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(nom_fichier))) {
			for (Service c : contenu_fichier) {
				sortie.writeObject(c);
			}
			sortie.writeObject(service);
			sortie.close();
			System.out.println("Enregistrement reussi !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public boolean equals(Service s) {
    	return ((s.getDate().equals(this.getDate())) && (s.getLieu().equals(this.getLieu())) && (s.getNumero().equals(this.getNumero())));
    }

	public static void ecraserTout(String nom_fichier, Collection<Service> contenu_fichier) {
		
		try (ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(nom_fichier))) {
			for (Service c : contenu_fichier) {
				sortie.writeObject(c);
			}
			sortie.close();
			System.out.println("Enregistrement reussi !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static Collection<Service> chargerTout(String nom_fichier) {
    	
    	Collection<Service> contenu_fichier = new ArrayList<>();
		Service object_actuel;
		
		try (ObjectInputStream entree = new ObjectInputStream(new FileInputStream(nom_fichier))) {
			while ((object_actuel = (Service) entree.readObject()) != null) { //Tant que le fichier n'est pas vide
				contenu_fichier.add(object_actuel);
			}
			entree.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return contenu_fichier;
    }
}
