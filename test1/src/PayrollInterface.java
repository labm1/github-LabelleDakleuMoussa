

public interface PayrollInterface {
	
	
	

	// Fonction qui calcule les deductions 
	   
	public double deduction() {
		double socialSecurityRate = 0.0525; // Taux d'assurance-emploi (AE)
		double pensionPlanRate = 0.0505; // Taux du régime de rentes du Québec (RRQ)
		double federalTaxRate = 0.15; // Taux d'impôt fédéral
		double provincialTaxRate = 0.15; // Taux d'impôt provincial (Québec)
		double dependentsDeduction = 150.0; // Déduction pour chaque personne à charge

		
		// Calcul des déductions d'impôts
        double federalTaxDeduction = employee.getBaseSalary() * federalTaxRate;
        double provincialTaxDeduction = employee.getBaseSalary() * provincialTaxRate;
        double dependentsDeductionTotal = dependentsDeduction * employee.getDependents();

        // Calcul total des déductions
        double totalDeductions = socialSecurityDeduction + pensionPlanDeduction + federalTaxDeduction +
                                provincialTaxDeduction - dependentsDeductionTotal;

        return totalDeductions;
	}
	
	
	// Calcul le salaire brute de l'employé
	
	public double netFrombrute() {
		double SalaireBrute = ((get.taux_horaire_base*h1) + (get.taux_horaire_supp * h2));
		
		
		return salaireBrute;
		
	}
	
	
	
	public Printpay(){
		
		
	}
	 
	
	
	// Le salaire Net
	
	double SalaireNet = (SalaireBrute - totalDeductions);
	
	

	     
	


}
