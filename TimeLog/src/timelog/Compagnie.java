package timelog;

import java.util.ArrayList;

public class Compagnie {
	private ArrayList<Employe> liste_Employes;
	private Admin admin;
	private ArrayList<Projet> listeProjets;
	private int npe;
	
	public Compagnie(Admin a) {
		liste_Employes = new ArrayList<>();
		listeProjets = new ArrayList<>();
		admin = a;
		npe = 2;
	}
	
	public int getNpe() {
		return npe;
	}

	public void setNpe(int npe) {
		this.npe = npe;
	}

	public ArrayList<Projet> getListeProjets() {
		return listeProjets;
	}

	public void setListeProjets(ArrayList<Projet> listeProjets) {
		this.listeProjets = listeProjets;
	}

	public void ajouter_Employe(Employe e){
		liste_Employes.add(e);
	}
	
	public void enlever_Employe(Employe e){
		liste_Employes.remove(e);
	}
	
	public ArrayList<Employe> getListe_Employes() {
		return liste_Employes;
	}

	public void setListe_Employes(ArrayList<Employe> liste_Employes) {
		this.liste_Employes = liste_Employes;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void ajouterProjet(Projet p){
		listeProjets.add(p);
	}
	
	public void supprimerProjet(Projet p){
		listeProjets.remove(p);
	}
	
	public Personne trouverPersonne(String nom) {
		if(nom.equals("admin"))
			return this.admin;
		for (Personne p : liste_Employes) {
			if(p.getNom().equals(nom))
				return p;
		}
		return null;
	}
	
	public Projet trouverProjet(String nom) {
		for (Projet p : listeProjets) {
			if(p.getNom_Projet().equals(nom))
				return p;
		}
		return null;
	}
}
