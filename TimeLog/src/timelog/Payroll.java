package timelog;

public class Payroll {
	
	
	// Calcul le salaire brute de l'employ�
	
	
	double salaireBrute;
	public double netFrombrute(PayInfo info) {
		
		double SalaireBrute = ((c.lire_Heures_Travaillees_Base(null, null, null) * info.getTaux_Horaire_Base(SalaireBrute))  + (info.getTaux_Horaire_Supp(salaireBrute) * c.lire_Heures_Travaillees_Supp(null, null, null);
		
		
		return salaireBrute;
		
		
		
	}
	


	
	public double deduction() {
		
		
		double pensionPlanRate = 0.0505; // Taux du r�gime de rentes du Qu�bec (RRQ)
		double federalTaxRate = 0.15; // Taux d'imp�t f�d�ral
		double provincialTaxRate = 0.15; // Taux d'imp�t provincial (Qu�bec)
		

		
		// Calcul des d�ductions d'imp�ts
        double federalTaxDeduction = salaireBrute * federalTaxRate;
        double provincialTaxDeduction = salaireBrute * provincialTaxRate;
        double pensionRetraite = salaireBrute * pensionPlanRate;

        // Calcul total des d�ductions
        double totalDeductions =  + federalTaxDeduction +provincialTaxDeduction +pensionRetraite;

        return totalDeductions;
	
	// Le salaire Net
	


		double SalaireNet = (salaireBrute - totalDeductions);
	


		

		
}

	
	
	public Printpay(){
		//	System.out.println("DATE DU " +DateMinimum + " AU  " +DateMaximum);
			System.out.println("Nombre d'heure travaill� de Base :  " );
			System.out.println("Nombre d'heure travaill� supplementaire : "  );
			System.out.println("NSalaire Brute : " );
	}
		
		
	}
	 
	
	
