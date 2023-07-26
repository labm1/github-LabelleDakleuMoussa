package timelog;

public class Discipline {
	private String nom_Discipline;
	private double nbre_Heures_budgetes;
	
	public Discipline(String nom, double heures) {
		this.nom_Discipline = nom;
		this.nbre_Heures_budgetes = heures;
	}
	
	public String getNom_Discipline() {
		return nom_Discipline;
	}

	public void setNom_Discipline(String nom_Discipline) {
		this.nom_Discipline = nom_Discipline;
	}

	public double getNbre_Heures_budgetes() {
		return nbre_Heures_budgetes;
	}

	public void setNbre_Heures_budgetes(double nbre_Heures_budgetes) {
		this.nbre_Heures_budgetes = nbre_Heures_budgetes;
	}
}
