package timelog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Projet {
	private String nom_Projet;
	private int id;
	private double nbre_Heures_Budgetes;
	private Date date_Debut;
	private Date date_Fin;
	private ArrayList<Employe> liste_Employes;
	private ArrayList<Discipline> liste_Disciplines;
	
	public Projet(String nom,int id,double heuresDesign1, double heuresDesign2, 
			double heuresImplementation, double heuresTest, double heuresDeploiement, Date debut, Date fin) {
		
		this.nbre_Heures_Budgetes = heuresDesign1+heuresDesign2+heuresImplementation+heuresTest+heuresDeploiement;
	
		
		this.nom_Projet = nom;
		this.id = id;
		this.date_Debut = debut;
		this.date_Fin = fin;
		
		this.liste_Employes = new ArrayList<>();
		this.liste_Disciplines = new ArrayList<>();
		this.liste_Disciplines.add(new Discipline("Design1", heuresDesign1));
		this.liste_Disciplines.add(new Discipline("Design2", heuresDesign2));
		this.liste_Disciplines.add(new Discipline("Implémentation", heuresImplementation));
		this.liste_Disciplines.add(new Discipline("Test", heuresTest));
		this.liste_Disciplines.add(new Discipline("Déploiement", heuresDeploiement));
	}
	
	public Projet(String nom,int id,ArrayList<Employe> liste_Employes,ArrayList<Discipline> liste_Discipline,double nbre_Heures_Budgetes,Date debut, Date fin) {
		
		this.nbre_Heures_Budgetes = nbre_Heures_Budgetes;
	
		
		this.nom_Projet = nom;
		this.id = id;
		this.date_Debut = debut;
		this.date_Fin = fin;
		
		this.liste_Employes = liste_Employes;
		this.liste_Disciplines = liste_Discipline;
	}
	
	public void ajouter_Employe(Employe p) {
		this.liste_Employes.add(p);
	}
	
	public void supprimer_Employe(Employe p) {
		this.liste_Employes.remove(p);
	}
	
	public double getNbre_Heures_Budgetes() {
		return nbre_Heures_Budgetes;
	}

	public void setNbre_Heures_Budgetes(double nbre_Heures_Budgetes) {
		this.nbre_Heures_Budgetes = nbre_Heures_Budgetes;
	}

	public ArrayList<Employe> getListe_Employes() {
		return liste_Employes;
	}

	public void setListe_Employes(ArrayList<Employe> liste_Employes) {
		this.liste_Employes = liste_Employes;
	}

	public ArrayList<Discipline> getListe_Disciplines() {
		return liste_Disciplines;
	}

	public void setListe_Disciplines(ArrayList<Discipline> liste_Disciplines) {
		this.liste_Disciplines = liste_Disciplines;
	}

	public void ajouter_Discipline(String nom, double heures) {
		this.liste_Disciplines.add(new Discipline(nom, heures));
	}
	
	public void supprimer_Discipline(String nom) {
		for (Discipline d : liste_Disciplines) {
			if(d.getNom_Discipline().equals(nom))
				this.liste_Disciplines.remove(d);
		}
	}
	
	public void modifier_Nom_Discipline(String nom_Discipline, String nouveau_Nom) {
		for (Discipline d : liste_Disciplines) {
			if(d.getNom_Discipline().equals(nom_Discipline))
				d.setNom_Discipline(nouveau_Nom);
		}
	}
	
	public void modifier_Heures_Discipline(String nom_Discipline, double heures) {
		for (Discipline d : liste_Disciplines) {
			if(d.getNom_Discipline().equals(nom_Discipline))
				d.setNbre_Heures_budgetes(heures);
		}
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

	public void setDate_Debut(String date) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8)));
		this.date_Debut = calendrier.getTime();
	}

	public Date getDate_Fin() {
		return date_Fin;
	}

	public void setDate_Fin(String date) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8)));
		this.date_Fin = calendrier.getTime();
	}
	
}
