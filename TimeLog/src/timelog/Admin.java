package timelog;
import java.util.Calendar;
import java.util.Date;

public class Admin extends Personne{

	public Admin(String nom, int id_personne,String poste, double taux_horaire_base, double taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		super(nom, id_personne,poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas);	
	}
	
	public void ajouter_Projet(String nom,int id,double heuresDesign1, double heuresDesign2, 
			double heuresImplementation, double heuresTest, double heuresDeploiement, String dateDebut, String dateFin, Compagnie c) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7)), Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7)), Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		c.ajouterProjet(new Projet(nom, id, heuresDesign1, heuresDesign2, heuresImplementation, heuresTest, heuresDeploiement, debut, fin));
	}
	
	public void supprimer_Projet(Projet p, Compagnie c) {
		c.supprimerProjet(p);
	}
	
	public void supprimer_Employe(Employe p, Compagnie c) {
		c.enlever_Employe(p);
	}
	

	public void ajouter_Employe(String nom, int id_personne, String poste, int taux_horaire_base, int taux_horaire_supp, String date_embauche,

			String date_depart, int numero_nas , Compagnie c) {
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(date_embauche.substring(0, 4)), Integer.parseInt(date_embauche.substring(5, 7)), Integer.parseInt(date_embauche.substring(8)));
		Date embauche = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(date_depart.substring(0, 4)), Integer.parseInt(date_depart.substring(5, 7)), Integer.parseInt(date_depart.substring(8)));
		Date depart = calendrier.getTime();
		c.ajouter_Employe(new Employe (nom, id_personne,poste, taux_horaire_base, taux_horaire_supp, embauche, depart ,numero_nas ));
		c.sauvegarder_Employes();

	}
	public void assigner_Projet(Employe e, Projet p, Compagnie c) {
		int i = 0;
		for (Projet projet : c.getListeProjets()) {
			if(projet.getListe_Employes().contains(e))
				i++;
		}
		if(i>=c.getNpe()) {
			System.out.println("Impossible d'ajouter l'employé, car le nombre de projet par personne maximal est atteint pour cet employé");
			return;
		}
		p.ajouter_Employe(e);
		System.out.println("Employé "+ e.getNom() + " a été ajouté au projet "+ p.getNom_Projet());
	}
	
	public void rapport_Salaire() {
		
	}
}
