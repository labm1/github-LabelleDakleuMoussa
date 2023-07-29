package timelog;

import java.util.ArrayList;

public class Compagnie {
	private ArrayList<Personne> listePersonnes;
	private ArrayList<Projet> listeProjets;
	private int npe;
	
	public int getNpe() {
		return npe;
	}

	public void setNpe(int npe) {
		this.npe = npe;
	}

	public void ajouterPersonne(Personne p){
		listePersonnes.add(p);
	}
	
	public void enleverPersonne(Personne p){
		listePersonnes.add(p);
	}
	
	public void ajouterProjet(Projet p){
		listeProjets.add(p);
	}
	
	public void supprimerProjet(Projet p){
		listeProjets.remove(p);
	}
	
	public Personne trouverPersonne(String nom) {
		for (Personne p : listePersonnes) {
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
