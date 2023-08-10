package timelog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class Tests {
	/************************************************************************************************
	 * 
	 * Tous les tests sont automatis�s, il suffit de faire jouer les tests pour obtenir les r�sultats
	 * 
	 * ATTENTION: Supprimer les fichier json avant de faire jouer le main 
	 * 				ou avant de faire jouer les test
	 * 
	 * Comme les tests sont initialis�s de mani�re diff�rente que le programme � des fins d'avoir des
	 * nombres exacts pour les tests, il se peut qu'il y ai des probl�mes si des donn�es sont d�j�
	 * pr�sentes dans le fichier json.
	 * 
	 ************************************************************************************************/
	
	Date d = new Date();
		
	Projet projetTest1 = new Projet("ProjetTest1",1,10,10,10,10,10,d,d);
	Projet projetTest2 = new Projet("ProjetTest2",1,10,10,10,10,10,d,d);
	Employe employeTest1 = new Employe("employeTest1",1,"D�veloppeur s�nior",15,17,d,d,123456789);
	Employe employeTest2 = new Employe("employeTest2",1,"D�veloppeur junior",15,17,d,d,123456789);
	Admin adminTest = new Admin("adminTest",10,"admin",15,17,d,d,123456789);
	Compagnie c = Compagnie.getInstance();

	//test 1: rapport d'�tat pour un projet choisi
	@Test
	void test1() {
		try {
			//v�rifier si les heures travaill�es pour une discipline d'un projet qui vont �tre affich�s dans le rapport de projet
			//correspondent au nombre r�el travaill�es qui est de 0 heures
			if (c.lire_Heures_Travaillees(projetTest1.getNom_Projet(),projetTest1.getListe_Disciplines().get(2).getNom_Discipline()) != 0)
				assert(false);
		} catch (IOException e) {
			assert(false);
		}
	}
	
	//test 2: rapport d'�tat global
	@Test
	void test2() {
		try {
			//v�rifier si les heures travaill�es pour un projet qui vont �tre affich�s dans le rapport global
			//correspondent au nombre r�el qui est de 0
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
	
	//test 4: avoir un talon de paye; il y en a un � chaque 2 semaines
	@Test
	void test4() {
		//v�rifier si la paye correspond � ce qui est pr�dit
		Payroll p = new Payroll();
		p.salaire(employeTest1, d, d);
		assert(p.salaireBrute == 0 && p.salaireNet == 0);
	}
	
	//test 5: avoir un talon de paye avec le total des salaires
	@Test
	void test5() {
		//v�rifier si la paye correspond � ce qui est pr�dit
		Payroll p = new Payroll();
		p.salaireTous(d, d);
		assert(p.salaireBrute == 0 && p.salaireNet == 0);
	}
	
	//test 6: Modification de donn�es par l'admin
	@Test
	void test6() {
		c.setAdmin(adminTest);
		
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		projetTest1.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest2);
		
		//modifier le npe, ajouter un employ�, ajouter un projet et assigner un projet, 
		//puis v�rifier si le changement a bien �t� fait
		adminTest.modifier_Npe(3);
		adminTest.ajouter_Employe("test", 0, "testeur", 0, 0, "2023/02/02", "2023/02/02", 0);
		adminTest.ajouter_Projet("test", 0, 0, 0, 0, 0, 0, "2023/02/02", "2023/02/02");
		adminTest.assigner_Projet(employeTest2, projetTest2);
		
		assert(c.getNpe()==3 && c.trouverProjet("test") != null && c.trouverPersonne("test") != null && projetTest2.getListe_Employes().contains(employeTest2));
	}
	
	//test 7: la liste de disciplines par d�faut est design1, design2, impl�mentation, test et d�ploiement.
	@Test
	void test7() {
		c.ajouterProjet(projetTest1);
				
		ArrayList<Discipline> liste = projetTest1.getListe_Disciplines();
		
		//v�rifier si le projet a toutes les disciplines: design1, design2, impl�mentation, test et d�ploiement
		assert(liste.get(0).getNom_Discipline().equals("Design1") && liste.get(1).getNom_Discipline().equals("Design2") 
				&& liste.get(2).getNom_Discipline().equals("Impl�mentation") && liste.get(3).getNom_Discipline().equals("Test") 
				&& liste.get(4).getNom_Discipline().equals("D�ploiement"));
	}

	//test 8: le syst�me doit persister les informations au format JSON
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
	
	//test 9: assigner des employ�s aux projets, pas d�passer le NPE
	@Test
	void test9() {
		c.ajouterProjet(projetTest1);
		c.ajouterProjet(projetTest2);
		c.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);
		projetTest1.ajouter_Employe(employeTest1);
		c.setNpe(1);
		
		//assigner un employ� � un projet, et v�rifier si le nombre de projet ne d�passe pas le npe
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
		
		//si on arrive pas � se connecter avec le nouveau mot de passe, le test n'est pas bon
		assert(adminTest.seconnecter(0));
	}
	
	//test 11: le compte admin peut modifier les nom et id de tous les employ�s et les siens
	@Test
	void test11() {
		c.ajouter_Employe(employeTest1);
		c.ajouter_Employe(employeTest2);

		employeTest1.setNom("Nouveau");
		employeTest1.setId_personne(0);
		adminTest.setId_personne(0);
		
		assert(employeTest1.getNom().equals("Nouveau") && employeTest1.getId_personne()==0 && adminTest.getId_personne() == 0);
	}
	
	//test 12: admin peut modifier la liste de projets et leurs caract�ristiques,la liste des employ�s, leurs caract�ristiques et leurs assignations aux diff�rents projets.
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
	
	//test 13: un employ� se connecte avec nom usager et ID
	@Test
	void test13() {
		Date d = new Date();
		
		Employe e = new Employe("Employe1",1,"D�veloppeur s�nior",15,17,d,d,123456789);		
		
		assert(e.seconnecter(1));
	}
	
	//test 14: signaler d�but et fin d'activit�
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
	
	//test 15: demander le nombre d'heures travaill�es de base et suppl�mentaire
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
			//v�rifier si le nombre d'heures de travail fait est le nombre pr�vu
			if(c.lire_Heures_Travaillees_Base(employeTest2.getNom(),debut, fin) !=0)
				assert(false);
		} catch (IOException e) {
			//s'il y a une erreur le test �choue
			assert(false);
		}
	}
}
