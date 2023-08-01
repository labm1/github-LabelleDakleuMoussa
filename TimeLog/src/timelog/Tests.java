package timelog;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void test1() {
		
	}
	@Test
	void test2() {
		
	}
	@Test
	void test3() {
		
	}
	@Test
	void test4() {
		
	}
	@Test
	void test5() {
		
	}
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
				
		ArrayList<Discipline> liste = pro.getListe_Disciplines();
		
		assert(liste.get(0).getNom_Discipline().equals("Design1") && liste.get(1).getNom_Discipline().equals("Design2") 
				&& liste.get(2).getNom_Discipline().equals("Implémentation") && liste.get(3).getNom_Discipline().equals("Test") 
				&& liste.get(4).getNom_Discipline().equals("Déploiement"));
	}

	@Test
	void test8() {
		
	}
	
	@Test
	void test9() {
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
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e);
		
		a.assigner_Projet(e2, pro2, c);
		
		c.setNpe(1);
		a.assigner_Projet(e, pro2, c);
		assert(!pro2.getListe_Employes().contains(e) && pro2.getListe_Employes().contains(e2));
	}
	
	@Test
	void test10() {
		Date d = new Date();
		
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		
		a.setId_personne(0);
		
		assert(a.seconnecter(0));
	}
	
	@Test
	void test11() {
		Date d = new Date();
		

		Employe e = new Employe("Employe1",1,"Développeur sénior",15,17,d,d,123456789);
		Employe e2 = new Employe("Employe2",1,"Développeur junior",15,17,d,d,123456789);
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		
		c.ajouter_Employe(e);
		c.ajouter_Employe(e2);

		e.setNom("Nouveau");
		e.setId_personne(0);
		a.setId_personne(0);
		
		assert(e.getNom().equals("Nouveau") && e.getId_personne()==0 && a.getId_personne() == 0);
	}
	
	@Test
	void test12() {
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
		
		a.ajouter_Employe("test", 0, null, 0, 0, "2023/02/02", "2023/02/02", 0, c);
		c.trouverPersonne("test").setTaux_horaire_base(10);
		
		a.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02", c);
		c.trouverProjet("test").setNbre_Heures_Budgetes(10);
		
		a.assigner_Projet(e2, pro2, c);
		
		assert(c.trouverPersonne("test") != null && c.trouverProjet("test") != null &&c.trouverPersonne("test").getTaux_horaire_base() == 10 && c.trouverProjet("test").getNbre_Heures_Budgetes() == 10);
	}

	@Test
	void test13() {
		Date d = new Date();
		
		Employe e = new Employe("Employe1",1,"Développeur sénior",15,17,d,d,123456789);		
		
		assert(e.seconnecter(1));
	}
	@Test
	void test14() {
		
	}
	@Test
	void test15() {
		
	}
}
