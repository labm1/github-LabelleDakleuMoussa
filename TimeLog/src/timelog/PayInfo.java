package timelog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Georges
 *
 */
public class PayInfo implements PayrollInterface {

    protected int id_Employe;
    public double nbre_heure_base;
    public double nbre_heure_supp;
    public double taux_Horaire_Base;
    public double taux_Horaire_Supp;
    public Date date_debut;
    public Date date_fin;
	public int getId_Employe() {
		return id_Employe;
	}
	public void setId_Employe(int id_Employe) {
		this.id_Employe = id_Employe;
	}
	public double getNbre_heure_base() {
		return nbre_heure_base;
	}
	public void setNbre_heure_base(double nbre_heure_base) {
		this.nbre_heure_base = nbre_heure_base;
	}
	public double getNbre_heure_supp() {
		return nbre_heure_supp;
	}
	public void setNbre_heure_supp(double nbre_heure_supp) {
		this.nbre_heure_supp = nbre_heure_supp;
	}
	public double getTaux_Horaire_Base() {
		return taux_Horaire_Base;
	}
	public void setTaux_Horaire_Base(double taux_Horaire_Base) {
		this.taux_Horaire_Base = taux_Horaire_Base;
	}
	public double getTaux_Horaire_Supp() {
		return taux_Horaire_Supp;
	}
	public void setTaux_Horaire_Supp(double taux_Horaire_Supp) {
		this.taux_Horaire_Supp = taux_Horaire_Supp;
	}
	public Date getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}
	public Date getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

  

}
