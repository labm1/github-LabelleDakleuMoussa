package timelog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class Tests {
	/************************************************************************************************
	 * 
	 * Tous les tests sont automatisés, il suffit de faire jouer les tests pour obtenir les résultats
	 * 
	 * ATTENTION: Supprimer les fichier json avant de faire jouer le main 
	 * 				ou avant de faire jouer les test
	 * 
	 * Comme les tests sont initialisés de manière différente que le programme à des fins d'avoir des
	 * nombres exacts pour les tests, il se peut qu'il y ai des problèmes si des données sont déjà
	 * présentes dans le fichier json.
	 * 
	 ************************************************************************************************/
	
	Date d = new Date();
		
	Projet projetTest1 = new Projet("ProjetTest1",1,10,10,10,10,10,d,d);
	Projet projetTest2 = new Projet("ProjetTest2",1,10,10,10,10,10,d,d);
	Employe employeTest1 = new Employe("employeTest1",1,"Développeur sénior",15,17,d,d,123456789);
	Employe employeTest2 = new Employe("employeTest2",1,"Développeur junior",15,17,d,d,123456789);
	Admin adminTest = new Admin("adminTest",10,"admin",15,17,d,d,123456789);
	Compagnie c = Compagnie.getInstance();

	//test 1: rapport d'état pour un projet choisi
	@Test
	void test1() {
		try {
			//vérifier si les heures travaillées pour une discipline d'un projet qui vont être affichés dans le rapport de projet
			//correspondent au nombre réel travaillées qui est de 0 heures
			if (c.lire_Heures_Travaillees(projetTest1.getNom_Projet(),projetTest1.getListe_Disciplines().get(2).getNom_Discipline()) != 0)
				assert(false);
		} catch (IOException e) {
			assert(false);
		}
	}
	
	//test 2: rapport d'état global
	@Test
	void test2() {
		try {
			//vérifier si les heures travaillées pour un projet qui vont être affichés dans le rapport global
			//correspondent au nombre réel qui est de 0
			if (c.lire_Heures_Travaillees(projetTest2.getNom_Projet()) != 0)
				assert(false);
			
		} catch (IOException e) {
			assert(false);
		}
	}
	
	//test 3: fournir un rapport des valeurs depuis une date
	@Test
	void test3() {
	
	}
	
	//test 4: avoir un talon de paye; il y en a un à chaque 2 semaines
	@Test
	void test4() {
		//vérifier si la paye correspond à ce qui est prédit
		Payroll p = new Payroll();
		p.salaire(employeTest1, d, d);
		assert(p.salaireBrute == 0 && p.salaireNet == 0);
	}
	
	//test 5: avoir un talon de paye avec le total des salaires
	@Test
	void test5() {
		//vérifier si la paye correspond à ce qui est prédit
		Payroll p = new Payroll();
		p.salaireTous(d, d);
		assert(p.salaireBrute == 0 && p.salaireNet == 0);
	}
	
	//test 6: Modification de données par l'admin
	@Test
	void test6() {
		c.setAdmin(adminTest);
		
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		projetTest1.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest2);
		
		//modifier le npe, ajouter un employé, ajouter un projet et assigner un projet, 
		//puis vérifier si le changement a bien été fait
		adminTest.modifier_Npe(3);
		adminTest.ajouter_Employe("test", 0, "testeur", 0, 0, "2023/02/02", "2023/02/02", 0);
		adminTest.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02");
		adminTest.assigner_Projet(employeTest2, projetTest2);
		
		assert(c.getNpe()==3 && c.trouverProjet("test") != null && c.trouverPersonne("test") != null && projetTest2.getListe_Employes().contains(employeTest2));
	}
	
	//test 7: la liste de disciplines par défaut est design1, design2, implémentation, test et déploiement.
	@Test
	void test7() {
		c.ajouterProjet(projetTest1);
				
		ArrayList<Discipline> liste = projetTest1.getListe_Disciplines();
		
		//vérifier si le projet a toutes les disciplines: design1, design2, implémentation, test et déploiement
		assert(liste.get(0).getNom_Discipline().equals("Design1") && liste.get(1).getNom_Discipline().equals("Design2") 
				&& liste.get(2).getNom_Discipline().equals("Implémentation") && liste.get(3).getNom_Discipline().equals("Test") 
				&& liste.get(4).getNom_Discipline().equals("Déploiement"));
	}

	//test 8: le système doit persister les informations au format JSON
	@Test
	void test8() {
		//Il est possible de lire les fichiers JSON. S'il est impossible, le test est faux
		try {
			c.lire_Heures_Travaillees(projetTest1.getNom_Projet());
		} catch (IOException e) {
			assert(false);
		}
		assert(true);
	}
	
	//test 9: assigner des employés aux projets, pas dépasser le NPE
	@Test
	void test9() {
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest1);
		c.setNpe(1);
		
		//assigner un employé à un projet, et vérifier si le nombre de projet ne dépasse pas le npe
		//qui est de 1
		adminTest.assigner_Projet(employeTest2, projetTest2);
		
		adminTest.assigner_Projet(employeTest1, projetTest2);
		assert(!projetTest2.getListe_Employes().contains(employeTest1) && projetTest2.getListe_Employes().contains(employeTest2));
	}
	
	//test 10: l'administrateur se connecte avec mot de passe et nom usager
	@Test
	void test10() {
		//modifier le mot de passe
		adminTest.setId_personne(0);
		
		//si on arrive pas à se connecter avec le nouveau mot de passe, le test n'est pas bon
		assert(adminTest.seconnecter(0));
	}
	
	//test 11: le compte admin peut modifier les nom et id de tous les employés et les siens
	@Test
	void test11() {
		c.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);

		employeTest1.setNom("Nouveau");
		employeTest1.setId_personne(0);
		adminTest.setId_personne(0);
		
		assert(employeTest1.getNom().equals("Nouveau") && employeTest1.getId_personne()==0 && adminTest.getId_personne() == 0);
	}
	
	//test 12: admin peut modifier la liste de projets et leurs caractéristiques,la liste des employés, leurs caractéristiques et leurs assignations aux différents projets.
	@Test
	void test12() {		
		c.setAdmin(adminTest);
		
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		projetTest1.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest2);
		
		adminTest.ajouter_Employe("test", 0, "testeur", 0, 0, "2023/02/02", "2023/02/02", 0);
		c.trouverPersonne("test").setTaux_horaire_base(10);
		
		adminTest.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02");
		c.trouverProjet("test").setNbre_Heures_Budgetes(10);
		
		adminTest.assigner_Projet(employeTest2, projetTest2);
		
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
		c.setAdmin(adminTest);
		
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		projetTest1.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest2);
		
		employeTest1.connecter_Activite(projetTest1, projetTest1.getListe_Disciplines().get(0));
		employeTest1.terminer_Activite();
	}
	
	//test 15: demander le nombre d'heures travaillées de base et supplémentaire
	@Test
	void test15() {
		String dateDebut = "2023/01/01";
		String dateFin = "2024/01/01";
		
		Calendar calendrier = Calendar.getInstance();
		calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7))-1, Integer.parseInt(dateDebut.substring(8)));
		Date debut = calendrier.getTime();
		
		calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7))-1, Integer.parseInt(dateFin.substring(8)));
		Date fin = calendrier.getTime();
		try {
			//vérifier si le nombre d'heures de travail fait est le nombre prévu
			if(c.lire_Heures_Travaillees_Base(employeTest2.getNom(),debut, fin) !=0)
				assert(false);
		} catch (IOException e) {
			//s'il y a une erreur le test échoue
			assert(false);
		}
	}
}
