package timelog;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Employe extends Personne {

	/**
	 * Constructeur
	 */
	public Employe(String nom, int id_personne,String poste, double taux_horaire_base, double taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		super(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas);
	}
	
	
	/**
	 * Débute une activité dans une discipline d'un projet
	 * @param p projet dont une activité est débutée
	 * @param d discipline dont une activité est débutée
	 */
	public void connecter_Activite(Projet p,Discipline d){
		Compagnie c = Compagnie.getInstance();
		Date date = new Date();
		c.sauvegarder_date_debut(date, p, d, this);
		//enregistrer la date de début d'activité, et le projet et discipline
	}
	
	
	/**
	 * Termine une activité commencée
	 */
	public void terminer_Activite() {
		Compagnie c = Compagnie.getInstance();
		Date date = new Date();
		c.sauvegarder_date_fin(date, this);
		//enregistrer la date de fin d'activité, proche de la date de début d'activité
	}
	
	
	/**
	 * Afficher un talon de paye selon une date spécifiée
	 * @param dateString une date String en format AAAA/MM/JJ
	 */
	public void demander_Periode(String dateString) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7))-1, Integer.parseInt(dateString.substring(8)));
		
		Date fin = calendrier.getTime();
		int semaine = calendrier.get(Calendar.WEEK_OF_YEAR);
		calendrier.add(Calendar.DATE, -14);
		if(semaine % 2 == 0) {
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine+1);
			 fin = calendrier.getTime();
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine-1);
		}
		Date debut = calendrier.getTime();
		
		System.out.println("Voici le talon de paye à partir du " + debut + " au "+fin);
		Payroll p = new Payroll();
		
		p.salaire(this, debut, fin);
		
		//imprimer à l'écran en json le salaire brut et net total depuis la date
	}
	
	/**
	 * Afficher un talon de paye depuis la dernière période de paye
	 */
	public void demander_Talon() {
		//imprimer à l'écran en json le salaire brut et net depuis la dernière période de paye
		Calendar calendrier = Calendar.getInstance();
		Date fin = calendrier.getTime();
		
		
		calendrier.add(Calendar.DATE, -14);
		int semaine = calendrier.get(Calendar.WEEK_OF_YEAR);
		
		if(semaine % 2 == 0) {
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine+1);
		}
		
		Date debut = calendrier.getTime();
		
		
		System.out.println("Voici le talon de paye à partir du " + debut + " au "+fin);
		Payroll p = new Payroll();
		
		p.salaire(this, debut, fin);
		
	}
	
	/**
	 * Affiche le nombre d'heures travaillées de base dans une intervalle de temps
	 * @param dateDebut borne minimale de l'intervale de temps AAAA/MM/JJ
	 * @param dateFin borne maximale de l'intervale de temps
	 */
	public void demander_Nbre_Heure_Travaille(String dateDebut, String dateFin) {
		DecimalFormat df = new DecimalFormat("#.00");
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7))-1, Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7))-1, Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		try {
			double heures = c.lire_Heures_Travaillees_Base(getNom(),debut ,fin);
			
			JSONObject heuresTravaillees = new JSONObject();
			
			heuresTravaillees.put("Heures travaillées de base", df.format(heures));
			System.out.println(heuresTravaillees.toString(4));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//imprimer le nombre d'heures travaillées entre les deux dates
	}
	
	
	/**
	 * Affiche le nombre d'heures travaillées supplémentaires dans une intervalle de temps
	 * @param dateDebut borne minimale de l'intervale de temps
	 * @param dateFin borne maximale de l'intervale de temps
	 */
	public void demander_Nbre_Heure_Supp_Travaille(String dateDebut, String dateFin) {
		DecimalFormat df = new DecimalFormat("#.00");
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7))-1, Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7))-1, Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		//imprimer le nombre d'heures travaillées supplémentaires entre les deux dates
		
		double heuresSupp;
		try {
			heuresSupp = c.lire_Heures_Travaillees_Supp(getNom(),debut ,fin);
		
		
		JSONObject heuresTravaillees = new JSONObject();
		
		heuresTravaillees.put("Heures travaillées supplémentaire", df.format(heuresSupp));
		System.out.println(heuresTravaillees.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Affiche un rapport pour le salaire brut pour chaque projet dont l'employé a travaillé
	 * @param dateString date format AAAA/MM/JJ
	 */
	public void rapport(String dateString) {
		DecimalFormat df = new DecimalFormat("#.00");
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		Date fin = calendrier.getTime();
		Date debut;
		
		if(!dateString.equals("non")) {
	    	calendrier.set(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7))-1, Integer.parseInt(dateString.substring(8)));
	    	debut = calendrier.getTime();
		}else {
				calendrier.add(Calendar.DATE, -14);
			int semaine = calendrier.get(Calendar.WEEK_OF_YEAR);
			if(semaine % 2 == 0) {
				calendrier.set(Calendar.WEEK_OF_YEAR, semaine+1);
			}
			debut = calendrier.getTime();
		}
	    
	    try {
		    JSONArray array = new JSONArray();
		    
		    for (Projet projet : c.getListeProjets()) {
		    	JSONObject objet = new JSONObject();
		        double resultat;
			
				resultat = c.lire_Heures_Travaillees_Base(getNom(),projet.getNom_Projet(),debut, fin)*getTaux_horaire_base()+
				        c.lire_Heures_Travaillees_Supp(getNom(),projet.getNom_Projet(),debut, fin)*getTaux_horaire_supp();
			
				if(resultat == 0)
					continue;
				
		        objet.put(projet.getNom_Projet(),df.format(resultat)+"$");
		        array.put(objet);
		    }
	
		    System.out.println(array.toString(4));
	} catch (IOException e) {
				e.printStackTrace();
			}
	}
}




