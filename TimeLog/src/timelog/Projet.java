package timelog;

import java.util.ArrayList;
import java.util.Date;

public class Projet {
	private String nom_Projet;
	private int id;
	private double nbre_Heures_Budgetes;
	private Date date_Debut;
	private Date date_Fin;
	private ArrayList<Personne> liste_Personnes;
	private ArrayList<Discipline> liste_Disciplines;
	
	public Projet(String nom,int id,double nbHeuresBudgetes,double heuresDesign1, double heuresDesign2, 
			double heuresImplementation, double heuresTest, double heuresDeploiement, Date debut, Date fin) {
		
		try {
		
			if(nbHeuresBudgetes != heuresDesign1+heuresDesign2+heuresImplementation+heuresTest+heuresDeploiement)
				throw new ArithmeticException("Les heures budgétées du projet et celle des disciplines ne correspondent pas");
		} catch(ArithmeticException e) {
			System.out.println(e.getMessage());
		}
		
		this.nom_Projet = nom;
		this.id = id;
		this.nbre_Heures_Budgetes = nbHeuresBudgetes;
		this.date_Debut = debut;
		this.date_Fin = fin;
		
		this.liste_Personnes = new ArrayList<>();
		this.liste_Disciplines = new ArrayList<>();
		this.liste_Disciplines.add(new Discipline("Design1", heuresDesign1));
		this.liste_Disciplines.add(new Discipline("Design2", heuresDesign2));
		this.liste_Disciplines.add(new Discipline("Implémentation", heuresImplementation));
		this.liste_Disciplines.add(new Discipline("Test", heuresTest));
		this.liste_Disciplines.add(new Discipline("Déploiement", heuresDeploiement));
	}
	
	public void addPersonne(Personne p) {
		this.liste_Personnes.add(p);
	}
	
	public void removePersonne(Personne p) {
		this.liste_Personnes.remove(p);
	}

	public String getNom_Projet() {
		return nom_Projet;
	}

	public void setNom_Projet(String nom_Projet) {
		this.nom_Projet = nom_Projet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate_Debut() {
		return date_Debut;
	}

	public void setDate_Debut(Date date_Debut) {
		this.date_Debut = date_Debut;
	}

	public Date getDate_Fin() {
		return date_Fin;
	}

	public void setDate_Fin(Date date_Fin) {
		this.date_Fin = date_Fin;
	}
	
}
