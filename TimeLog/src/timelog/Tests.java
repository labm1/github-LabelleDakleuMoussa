package timelog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tests {

	
	 Date d = new Date();
		
		Projet pro = new Projet("Projet1",1,10,10,10,10,10,d,d);
		Projet pro2 = new Projet("Projet2",1,10,10,10,10,10,d,d);
		Employe e = new Employe("Employe1",1,"Développeur sénior",15,17,d,d,123456789);
		Employe e2 = new Employe("Employe2",1,"Développeur junior",15,17,d,d,123456789);
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		Compagnie c = Compagnie.getInstance();

	//test 1: rapport d'état pour un projet choisi
	@Test
	void test1() {
	
	}
	//test 2: rapport d'état global
	@Test
	void test2() {
	
	}
	
	//test 3: fournir un rapport des valeurs depuis une date
	@Test
	void test3() {
	
	}
	
	//test 4: avoir un talon de paye; il y en a un à chaque 2 semaines
	@Test
	void test4() {
		
	}
	
	//test 5: avoir un talon de paye avec le total des salaires
	@Test
	void test5() {
		
	}
	
	//test 6: Modification de données par l'admin
	@Test
	void test6() {
		
		c.setAdmin(a);
		
		c.ajouterProjet(pro);
		c.ajouterProjet(pro2);
		c.ajouter_Employe(e);
		pro.ajouter_Employe(e);
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e2);
		
		a.modifier_Npe(3);
		a.ajouter_Employe("test", 0, "testeur", 0, 0, "2023/02/02", "2023/02/02", 0);
		a.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02");
		a.assigner_Projet(e2, pro2);
		
		assert(c.getNpe()==3 && c.trouverProjet("test") != null && c.trouverPersonne("test") != null && pro2.getListe_Employes().contains(e2));
	}
	
	//test 7: la liste de disciplines par défaut est design1, design2, implémentation, test et déploiement.
	@Test
	void test7() {
		
		c.ajouterProjet(pro);
				
		ArrayList<Discipline> liste = pro.getListe_Disciplines();
		
		assert(liste.get(0).getNom_Discipline().equals("Design1") && liste.get(1).getNom_Discipline().equals("Design2") 
				&& liste.get(2).getNom_Discipline().equals("Implémentation") && liste.get(3).getNom_Discipline().equals("Test") 
				&& liste.get(4).getNom_Discipline().equals("Déploiement"));
	}

	//test 8: le système doit persister les informations au format JSON
	@Test
	void test8() {
		try {
			c.lire_Heures_Travaillees(pro.getNom_Projet());
		} catch (IOException e) {
			assert(false);
		}
		assert(true);
	}
	
	//test 9: assigner des employés aux projets, pas dépasser le NPE
	@Test
	void test9() {
		
		
		c.ajouterProjet(pro);
		c.ajouterProjet(pro2);
		c.ajouter_Employe(e);
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e);
		
		a.assigner_Projet(e2, pro2);
		
		c.setNpe(1);
		a.assigner_Projet(e, pro2);
		assert(!pro2.getListe_Employes().contains(e) && pro2.getListe_Employes().contains(e2));
	}
	
	//test 10: l'administrateur se connecte avec mot de passe et nom usager
	@Test
	void test10() {
		
		
		a.setId_personne(0);
		
		assert(a.seconnecter(0));
	}
	
	//test 11: le compte admin peut modifier les nom et id de tous les employés et les siens
	@Test
	void test11() {
		
		
		c.ajouter_Employe(e);
		c.ajouter_Employe(e2);

		e.setNom("Nouveau");
		e.setId_personne(0);
		a.setId_personne(0);
		
		assert(e.getNom().equals("Nouveau") && e.getId_personne()==0 && a.getId_personne() == 0);
	}
	
	//test 12: admin peut modifier la liste de projets et leurs caractéristiques,la liste des employés, leurs caractéristiques et leurs assignations aux différents projets.
	@Test
	void test12() {
Date d = new Date();
		
		c.setAdmin(a);
		
		c.ajouterProjet(pro);
		c.ajouterProjet(pro2);
		c.ajouter_Employe(e);
		pro.ajouter_Employe(e);
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e2);
		
		a.ajouter_Employe("test", 0, "testeur", 0, 0, "2023/02/02", "2023/02/02", 0);
		c.trouverPersonne("test").setTaux_horaire_base(10);
		
		a.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02");
		c.trouverProjet("test").setNbre_Heures_Budgetes(10);
		
		a.assigner_Projet(e2, pro2);
		
		assert(c.trouverPersonne("test") != null && c.trouverProjet("test") != null &&c.trouverPersonne("test").getTaux_horaire_base() == 10 && c.trouverProjet("test").getNbre_Heures_Budgetes() == 10);
	}
	
	//test 13: un employé se connecte avec nom usager et ID
	@Test
	void test13() {
		Date d = new Date();
		
		Employe e = new Employe("Employe1",1,"Développeur sénior",15,17,d,d,123456789);		
		
		assert(e.seconnecter(1));
	}
	
	//test 14: signaler début et fin d'activité
	@Test
	void test14() {
		
		c.setAdmin(a);
		
		c.ajouterProjet(pro);
		c.ajouterProjet(pro2);
		c.ajouter_Employe(e);
		pro.ajouter_Employe(e);
		c.ajouter_Employe(e2);
		pro.ajouter_Employe(e2);
		
		e.connecter_Activite(pro, pro.getListe_Disciplines().get(0));
		e.terminer_Activite();
	}
	
	//test 15: demander le nombre d'heures travaillées de base et supplémentaire
	@Test
	void test15() {
		
	}
}
