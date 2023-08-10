package timelog;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.text.DecimalFormat;

public class Personne {

	private String nom;
	private int id_personne;
	private String poste;
	private double taux_horaire_base;
	private double taux_horaire_supp;
	private Date date_embauche;
	private Date date_depart;
	private int numero_nas;
	
	/**
	 * Constructeur
	 */
	public Personne(String nom, int id_personne, String poste, double taux_horaire_base, double taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		this.nom = nom;
		this.id_personne = id_personne;
		this.taux_horaire_base = taux_horaire_base;
		this.taux_horaire_supp = taux_horaire_supp;
		this.date_embauche = date_embauche;
		this.date_depart = date_depart;
		this.numero_nas = numero_nas;
		this.poste = poste;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getId_personne() {
		return id_personne;
	}
	
	public void setId_personne(int id_personne) {
		this.id_personne = id_personne;
	}
	
	public double getTaux_horaire_base() {
		return taux_horaire_base;
	}
	
	public void setTaux_horaire_base(double taux_horaire_base) {
		this.taux_horaire_base = taux_horaire_base;
	}
	
	public double getTaux_horaire_supp() {
		return taux_horaire_supp;
	}
	
	public void setTaux_horaire_supp(double taux_horaire_supp) {
		this.taux_horaire_supp = taux_horaire_supp;
	}
	
	public Date getDate_embauche() {
		return date_embauche;
	}
	
	/**
	 * remplace la date d'embauche avec une string transformé en date
	 * @param date une String représentant une date sous format AAAA/MM/JJ
	 */
	public void setDate_embauche(String date) {
		Calendar calendrier = Calendar.getInstance();
			calendrier.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7))-1, Integer.parseInt(date.substring(8)));
			this.date_embauche =  calendrier.getTime();
		}
	
	public Date getDate_depart() {
		return date_depart;
	}
	
	/**
	 * remplace la date de départ avec une string transformé en date
	 * @param date une String représentant une date sous format AAAA/MM/JJ
	 */
	public void setDate_depart(String date) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7))-1, Integer.parseInt(date.substring(8)));
		this.date_depart =  calendrier.getTime();
	}
	
	public int getNumero_nas() {
		return numero_nas;
	}
	
	public void setNumero_nas(int numero_nas) {
		this.numero_nas = numero_nas;
	}
	
	/**
	 * Permet de vérifier si le id correspond à la personne
	 * @param id identification
	 * @return vrai si le id est celui de la personne, faux sinon
	 */
	public boolean seconnecter(int id) {
		if (id==this.id_personne  )
			return true;
		else return false;
	}
	
	/**
	 * Produit un rapport de progression sur un projet et l'imprime à l'écran
	 * @param p un projet
	 */
	public void rapport_Etat_Projet(Projet p) {
		try {
			JSONArray rapportTotal = new JSONArray();
			Compagnie c = Compagnie.getInstance();
			DecimalFormat df = new DecimalFormat("#.00");
			double heures_projet = 0;
				for(Discipline d : p.getListe_Disciplines()) {
					JSONObject rapport1 = new JSONObject();
					JSONObject rapport2 = new JSONObject();
					JSONArray rapportSousTotal = new JSONArray();
						double heures;
						
							heures = c.lire_Heures_Travaillees(p.getNom_Projet(),d.getNom_Discipline());
						
						rapport1.put(d.getNom_Discipline()+": heures travaillées",df.format(heures));
						rapport2.put(d.getNom_Discipline()+": pourcentage accompli",df.format(heures/d.getNbre_Heures_budgetes()*100)+"%");
						heures_projet+=heures;
					rapportSousTotal.put(rapport1);
					rapportSousTotal.put(rapport2);
					rapportTotal.put(rapportSousTotal);
				}
				JSONObject rapport = new JSONObject();
			rapport.put(p.getNom_Projet()+": heures travaillées", df.format(heures_projet));
			rapport.put(p.getNom_Projet()+": pourcentage accompli", df.format(heures_projet/p.getNbre_Heures_Budgetes()*100)+"%");
			rapportTotal.put(rapport);
			System.out.println(rapportTotal.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Produit un rapport de progression sur tous les projets et l'imprime à l'écran
	 */
	public void rapport_Total_Projet() {
		try {
			JSONArray rapportTotal = new JSONArray();
			Compagnie c = Compagnie.getInstance();
			DecimalFormat df = new DecimalFormat("#.00");
			double heuresTotal = 0;
			double heuresBudgetees = 0;
				for(Projet p : c.getListeProjets()) {
					JSONObject rapport1 = new JSONObject();
					JSONObject rapport2 = new JSONObject();
					JSONArray rapportSousTotal = new JSONArray();
					
					double heures;
					
					heures = c.lire_Heures_Travaillees(p.getNom_Projet());
					
					rapport1.put(p.getNom_Projet()+": heures travaillées",df.format(heures));
					rapport2.put(p.getNom_Projet()+": pourcentage accompli",df.format(heures/p.getNbre_Heures_Budgetes()*100)+"%");
					heuresTotal+=heures;
					heuresBudgetees +=p.getNbre_Heures_Budgetes();
					rapportSousTotal.put(rapport1);
					rapportSousTotal.put(rapport2);
					rapportTotal.put(rapportSousTotal);
				}
				JSONObject rapport = new JSONObject();
				JSONArray rapportSousTotal = new JSONArray();
			rapport.put("Total: heures travaillées", df.format(heuresTotal));
			rapport.put("Total: pourcentage accompli", df.format(heuresTotal/heuresBudgetees*100)+"%");
			rapportSousTotal.put(rapport);
			System.out.println(rapportTotal.toString(4));
		} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}
		
	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}
}
	

