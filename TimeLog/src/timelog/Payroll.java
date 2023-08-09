package timelog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Payroll {
	
	double salaireBrute=0;
	double salaireNet=0;
	
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
	
	public double netFrombrute(PayInfo info) {
		salaireBrute = (info.getNbre_heure_base() * info.getTaux_Horaire_Base()+info.getNbre_heure_supp()*info.getTaux_Horaire_Supp());
		return salaireBrute;
	}
	


	
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
	

	
	
	public void printPay(double brut, double net){
		//	System.out.println("DATE DU " +DateMinimum + " AU  " +DateMaximum);
			System.out.println("Nombre d'heure travaillé de Base :  " );
			System.out.println("Nombre d'heure travaillé supplementaire : "  );
			System.out.println("NSalaire Brute : " );
	}
		
		
}