package timelog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayInfo implements PayrollInterface {

    protected int id_Employe;
    public double nbre_heure_base;
    public double nbre_heure_supp;
    public double taux_Horaire_Base;
    public double taux_Horaire_Supp;
    public Date date_debut;
    public Date date_fin;

    public int getId_Employe() {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.id_Employe;
    }

    public void setId_Employe(int id_Employe) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        this.id_Employe = id_Employe;
    }

    public double getNbre_heure_base() {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.nbre_heure_base;
    }

    public double setNbre_heure_base(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.nbre_heure_base = value;

    }

    public double getNbre_heure_supp(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.nbre_heure_supp = value;
    }

    public double setNbre_heure_supp(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.nbre_heure_supp = value;
    }

    public double getTaux_Horaire_Base(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.taux_Horaire_Base = value;
    }

    public double setTaux_Horaire_Base(double value) {
        return this.taux_Horaire_Base = value;
    }

    public double getTaux_Horaire_Supp(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.taux_Horaire_Supp = value;
    }

    public double setTaux_Horaire_Supp(double value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.taux_Horaire_Supp = value;
    }

    public Date getDate_debut() {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.date_debut;
    }

    public Date setDate_debut(Date value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.date_debut = value;
    }

    public Date getDate_fin() {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.date_fin;
    }

    public Date setDate_fin(final Date value) {
        // Automatically generated method. Please delete this comment before entering
        // specific code.
        return this.date_fin = value;
    }

}
