package timelog;

public class Payroll {
	public double netFromBrute(PayInfo p) {
		return (p.getNbre_heure_base() * p.getTaux_Horaire_Base()+p.getNbre_heure_supp()*p.getTaux_Horaire_Supp());
	}
}
