package timelog;

import java.util.Date;

public class Discipline {
	private String nom_Discipline;
	private double nbre_Heures_budgetes;
	
	public Discipline(String nom, double heures) {
		this.nom_Discipline = nom;
		this.nbre_Heures_budgetes = heures;
	}
}
