package timelog;



	import java.util.ArrayList;
	import java.sql.Date;

	public class Personne {

	String nom;

	int id_personne;

	int taux_horaire_base;

	int taux_horaire_supp;

	Date date_embauche;

	Date date_depart;

	int numero_nas;

	ArrayList <Employe> l1;

	public Personne(String nom, int id_personne, int taux_horaire_base, int taux_horaire_supp, Date date_embauche,
			Date date_depart, int numero_nas) {
		this.nom = nom;
		this.id_personne = id_personne;
		this.taux_horaire_base = taux_horaire_base;
		this.taux_horaire_supp = taux_horaire_supp;
		this.date_embauche = date_embauche;
		this.date_depart = date_depart;
		this.numero_nas = numero_nas;
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

	public int getTaux_horaire_base() {
		return taux_horaire_base;
	}

	public void setTaux_horaire_base(int taux_horaire_base) {
		this.taux_horaire_base = taux_horaire_base;
	}

	public int getTaux_horaire_supp() {
		return taux_horaire_supp;
	}

	public void setTaux_horaire_supp(int taux_horaire_supp) {
		this.taux_horaire_supp = taux_horaire_supp;
	}

	public Date getDate_embauche() {
		return date_embauche;
	}

	public void setDate_embauche(Date date_embauche) {
		this.date_embauche = date_embauche;
	}

	public Date getDate_depart() {
		return date_depart;
	}

	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}

	public int getNumero_nas() {
		return numero_nas;
	}

	public void setNumero_nas(int numero_nas) {
		this.numero_nas = numero_nas;
	}

<<<<<<< HEAD
	public boolean seConnecter(int id) {
		
	}
=======
	public boolean seconnecter(int id) {
		
		if (id==this.id_personne  )
			return true;
		else return false;

		}
>>>>>>> 35e18aa8838b3ed6403a26135a4b4865cf75fa55



		
		
	}
	

