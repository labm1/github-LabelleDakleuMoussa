package timelog;

public class StockageRegistre {
	private Compagnie compagnie;
	
	public StockageRegistre(Compagnie c) {
		this.compagnie = c;
	}
	
	public void supprimer_Projet(Projet p) {
		this.compagnie.supprimerProjet(p);
	}
	
	public void ajouter_Projet(Projet p) {
		this.compagnie.ajouterProjet(p);
	}

	//modifier projet
	//supprimer projet
	//ajouter projet
}
