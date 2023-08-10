package timelog;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Payroll {
	
	double salaireBrute=0;
	double salaireNet=0;
	
	/**
	 * Permet d'afficher un rapport du salaire total de tous les employés dans une
	 * intervalle de temps
	 * @param debut date minimale de l'intervalle
	 * @param fin date maximale de l'intervalle
	 */
	public void salaireTous(Date debut, Date fin) {
		Compagnie c = Compagnie.getInstance();
		try {
			double salaireBrut=0;
			double salaireNet=0;
			ArrayList<Employe> liste = c.getListe_Employes();
		for(int i = 0; i<liste.size();i++) {
			PayInfo info = new PayInfo(liste.get(i).getId_personne(),
					c.lire_Heures_Travaillees_Base(liste.get(i).getNom(), debut, fin),
					c.lire_Heures_Travaillees_Supp(liste.get(i).getNom(),debut,fin),
					liste.get(i).getTaux_horaire_base(),liste.get(i).getTaux_horaire_supp(),debut,fin);
			salaireBrut += netFrombrute(info);
			salaireNet += deduction();
		}
		printPay(salaireBrut,salaireNet);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Permet d'afficher un rapport du salaire d'un employé sur une intervalle de temps
	 * @param debut date minimale de l'intervalle
	 * @param fin date maximale de l'intervalle
	 * @param e employé
	 */
	public void salaire(Employe e,Date debut,Date fin) {
		Compagnie c = Compagnie.getInstance();
		try {
			double salaireBrut=0;
			double salaireNet=0;
			PayInfo info = new PayInfo(e.getId_personne(),
					c.lire_Heures_Travaillees_Base(e.getNom(), debut, fin),
					c.lire_Heures_Travaillees_Supp(e.getNom(),debut,fin),
					e.getTaux_horaire_base(),e.getTaux_horaire_supp(),debut,fin);
			
			salaireBrut += netFrombrute(info);
			salaireNet += deduction();
			printPay(salaireBrut,salaireNet);
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	
	/**
	 * Calcule le salaire brute
	 * @param info toutes les infos nécéssaires pour effectuer le calcul
	 * @return le salaire brut
	 */
	public double netFrombrute(PayInfo info) {
		salaireBrute = (info.getNbre_heure_base() * info.getTaux_Horaire_Base()+info.getNbre_heure_supp()*info.getTaux_Horaire_Supp());
		return salaireBrute;
	}
	


	/**
	 * Calcule les déductions et le salaire net
	 * @return le salaire net
	 */
	public double deduction() {
		double pensionPlanRate = 0.0505; // Taux du régime de rentes du Québec (RRQ)
		double federalTaxRate = 0.15; // Taux d'impôt fédéral
		double provincialTaxRate = 0.15; // Taux d'impôt provincial (Québec)

		
		// Calcul des déductions d'impôts
        double federalTaxDeduction = salaireBrute * federalTaxRate;
        double provincialTaxDeduction = salaireBrute * provincialTaxRate;
        double pensionRetraite = salaireBrute * pensionPlanRate;

        // Calcul total des déductions
        double totalDeductions =  + federalTaxDeduction +provincialTaxDeduction +pensionRetraite;
        
	
        // Le salaire Net
		salaireNet = (salaireBrute - totalDeductions);
		return salaireNet;
	}
	

	
	/**
	 * Imprime le talon de paie
	 * @param brut salaire brut
	 * @param net salaire net
	 */
	public void printPay(double brut, double net){
		//	System.out.println("DATE DU " +DateMinimum + " AU  " +DateMaximum);
		DecimalFormat df = new DecimalFormat("#.00");
			JSONArray talon = new JSONArray();
			JSONObject contenu = new JSONObject();
			contenu.put("Salaire brute", df.format(brut));
			contenu.put("Salaire net", df.format(net));
			talon.put(contenu);
			System.out.println(talon.toString(4));
	}	
}