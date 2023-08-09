package timelog;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class Employe extends Personne {

	public Employe(String nom, int id_personne,String poste, double taux_horaire_base, double taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		super(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas);
	}
		// TODO Auto-generated constructor stub
	
	public void connecter_Activite(Projet p,Discipline d){
		Compagnie c = Compagnie.getInstance();
		Date date = new Date();
		c.sauvegarder_date_debut(date, p, d, this);
		//enregistrer la date de début d'activité, et le projet et discipline
	}
	
	public void terminer_Activite() {
		Compagnie c = Compagnie.getInstance();
		Date date = new Date();
		c.sauvegarder_date_fin(date, this);
		//enregistrer la date de fin d'activité, proche de la date de début d'activité
	}
	
	public void demander_Periode(String dateString) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7)), Integer.parseInt(dateString.substring(8)));
		Date date = calendrier.getTime();
		//imprimer à l'écran en json le salaire brut et net total depuis la date
	}
	
	public void demander_Talon() {
		//imprimer à l'écran en json le salaire brut et net depuis la dernière période de paye
	}
	
	public void demander_Nbre_Heure_Travaille(String dateDebut, String dateFin) {
		DecimalFormat df = new DecimalFormat("#.00");
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7)), Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7)), Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		try {
			double heures = c.lire_Heures_Travaillees_Base(getNom(),debut ,fin);
			
			JSONObject heuresTravaillees = new JSONObject();
			
			heuresTravaillees.put("Heures travaillées de base", df.format(heures));
			System.out.println(heuresTravaillees.toString(4));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//imprimer le nombre d'heures travaillées entre les deux dates
	}
	
	public void demander_Nbre_Heure_Supp_Travaille(String dateDebut, String dateFin) {
		DecimalFormat df = new DecimalFormat("#.00");
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7)), Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7)), Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		//imprimer le nombre d'heures travaillées supplémentaires entre les deux dates
		
		double heuresSupp;
		try {
			heuresSupp = c.lire_Heures_Travaillees_Supp(getNom(),debut ,fin);
		
		
		JSONObject heuresTravaillees = new JSONObject();
		
		heuresTravaillees.put("Heures travaillées supplémentaire", df.format(heuresSupp));
		System.out.println(heuresTravaillees.toString(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}




