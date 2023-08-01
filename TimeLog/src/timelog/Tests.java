package timelog;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void test6() {
		Date d = new Date();
		
		Projet pro = new Projet("Projet1",1,10,10,10,10,10,d,d);
		Projet pro2 = new Projet("Projet2",1,10,10,10,10,10,d,d);
		Employe e = new Employe("Employe1",1,"Développeur sénior",15,17,d,d,123456789);
		Employe e2 = new Employe("Employe2",1,"Développeur junior",15,17,d,d,123456789);
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		
		c.ajouterProjet(pro);
		c.ajouterProjet(pro2);
		c.ajouter_Employe(e);
		pro.ajouter_Employe(e);
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e2);
		
		c.setNpe(3);
		a.ajouter_Employe("test", 0, null, 0, 0, "2023/02/02", "2023/02/02", 0, c);
		a.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02", c);
		a.assigner_Projet(e2, pro2, c);
		
		assert(c.getNpe()==3 && c.trouverProjet("test") != null && c.trouverPersonne("test") != null && pro2.getListe_Employes().contains(e2));
	}
	
	@Test
	void test7() {
		Date d = new Date();
		
		Projet pro = new Projet("Projet1",1,10,10,10,10,10,d,d);
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		
		c.ajouterProjet(pro);
		
		boolean disciplines = true;
		
		ArrayList<Discipline> liste = pro.getListe_Disciplines();
		
		if() {
			
		}
		
		assert(liste.);
		
	}

}
