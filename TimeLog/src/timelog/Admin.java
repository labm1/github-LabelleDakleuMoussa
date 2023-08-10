package timelog;
import java.util.Calendar;
import java.util.Date;

public class Admin extends Personne{

	/**
	 * Constructeur
	 */
	public Admin(String nom, int id_personne,String poste, double taux_horaire_base, double taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		super(nom, id_personne,poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas);	
	}
	
	/**
	 * Cr�� un nouveau objet projet et l'ajoute � la liste de projets dans Compagnie
	 * @param nom nom du projet
	 * @param id id du projet
	 * @param heuresDesign1 nombre d'heures budg�t�s dans Design1
	 * @param heuresDesign2 nombre d'heures budg�t�s dans Design2
	 * @param heuresImplementation nombre d'heures budg�t�s dans Impl�mentation
	 * @param heuresTest nombre d'heures budg�t�s dans test
	 * @param heuresDeploiement nombre d'heures budg�t�s dans D�ploiement
	 * @param dateDebut date de d�but du projet
	 * @param dateFin date de fin du projet
	 */
	public void ajouter_Projet(String nom,int id,double heuresDesign1, double heuresDesign2, 
			double heuresImplementation, double heuresTest, double heuresDeploiement, String dateDebut, String dateFin) {
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7))-1, Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7))-1, Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		c.ajouterProjet(new Projet(nom, id, heuresDesign1, heuresDesign2, heuresImplementation, heuresTest, heuresDeploiement, debut, fin));
		c.sauvegarder_Projets();
	}
	
	
	/**
	 * supprime un projet de la Compagnie
	 * @param p projet � supprimer
	 */
	public void supprimer_Projet(Projet p) {
		Compagnie c = Compagnie.getInstance();
		c.supprimerProjet(p);
		c.sauvegarder_Projets();
	}
	
	/**
	 * enl�ve un employ� de la compagnie
	 * @param e employ� � enlever
	 */
	public void supprimer_Employe(Employe e) {
		Compagnie c = Compagnie.getInstance();
		c.enlever_Employe(e);
		c.sauvegarder_Personnes();
	}
	

	/**
	 * Cr�� un nouveau objet Employe et l'ajoute � la liste des employ�s dans Compagnie
	 * @param nom nom de l'employ�
	 * @param id_personne id de l'employ�
	 * @param poste poste de l'employ�
	 * @param taux_horaire_base taux horaire de base de l'employ�
	 * @param taux_horaire_supp taux horaire suppl�mentaire de l'employ�
	 * @param date_embauche date d'embauche de l'employ�
	 * @param date_depart date de d�part de l'employ�
	 * @param numero_nas num�ro d'assurance social de l'employ�
	 */
	public void ajouter_Employe(String nom, int id_personne, String poste, int taux_horaire_base, int taux_horaire_supp, String date_embauche,

			String date_depart, int numero_nas) {
		Compagnie c = Compagnie.getInstance();
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(date_embauche.substring(0, 4)), Integer.parseInt(date_embauche.substring(5, 7))-1, Integer.parseInt(date_embauche.substring(8)));
		Date embauche = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(date_depart.substring(0, 4)), Integer.parseInt(date_depart.substring(5, 7))-1, Integer.parseInt(date_depart.substring(8)));
		Date depart = calendrier.getTime();
		c.ajouter_Employe(new Employe (nom, id_personne,poste, taux_horaire_base, taux_horaire_supp, embauche, depart ,numero_nas ));
		c.sauvegarder_Personnes();
	}
	
	
	/**
	 * assigne un employ� � un projet
	 * @param e employ� � assigner
	 * @param p projet
	 */
	public void assigner_Projet(Employe e, Projet p) {
		Compagnie c = Compagnie.getInstance();
		int i = 0;
		for (Projet projet : c.getListeProjets()) {
			if(projet.getListe_Employes().contains(e))
				i++;
		}
		if(i>=c.getNpe()) {
			System.out.println("Impossible d'ajouter l'employ�, car le nombre de projet par personne maximal est atteint pour cet employ�");
			return;
		}
		p.ajouter_Employe(e);
		System.out.println("Employ� "+ e.getNom() + " a �t� ajout� au projet "+ p.getNom_Projet());
		c.sauvegarder_Projets();
	}
	
	
	/**
	 * Donne un rapport des salaires bruts et net totaux des employ�s depuis la
	 * derni�re p�riode de paye
	 */
	public void rapport_Salaire() {
		Calendar calendrier = Calendar.getInstance();
		Date fin = calendrier.getTime();
		
		calendrier.add(Calendar.DATE, -14);
		int semaine = calendrier.get(Calendar.WEEK_OF_YEAR);
		if(semaine % 2 == 0) {
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine+1);
		}
		Date debut = calendrier.getTime();

		System.out.println("Voici le talon de paye � partir du " + debut + " au "+fin);
		
		Payroll p = new Payroll();
		p.salaireTous(debut, fin);
	}
	
	
	/**
	 * Donne un rapport des salaires bruts et net totaux des employ�s pour une date � sp�cifi�e
	 * @param dateString une date sous format AAAA/MM/JJ
	 */
	public void rapport_Salaire(String dateString) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateString.substring(0, 4)), Integer.parseInt(dateString.substring(5, 7))-1, Integer.parseInt(dateString.substring(8)));
		
		Date fin = calendrier.getTime();
		int semaine = calendrier.get(Calendar.WEEK_OF_YEAR);
		calendrier.add(Calendar.DATE, -14);
		System.out.println(semaine);
		if(semaine % 2 == 0) {
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine+1);
			 fin = calendrier.getTime();
			calendrier.set(Calendar.WEEK_OF_YEAR, semaine-1);
		}
		Date debut = calendrier.getTime();
		
		System.out.println("Voici le talon de paye � partir du " + debut + " au "+fin);
		
		Payroll p = new Payroll();
		p.salaireTous(debut, fin);
	}
	
	/**
	 * modifie le npe
	 * @param npe nouvelle valeur
	 */
	public void modifier_Npe(int npe) {
		Compagnie c = Compagnie.getInstance();
		c.setNpe(npe);
		c.sauvegarder_Projets();
	}
}
