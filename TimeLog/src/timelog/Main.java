package timelog;

import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		boolean deconnecter = true;
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
		
		
		int essais = 0,id;
		String user;
		Personne p;
		
		do {
			System.out.println("Connexion\nUser:");
			user = scan.next();
			System.out.println("ID:");
			id = scan.nextInt();
			
			p = c.trouverPersonne(user);
			if(p == null || !p.seconnecter(id)) {
				System.out.println("Mauvais nom d'utilisateur ou mot de passe; veuillez réessayer");
				essais++;
				if (essais == 3) 
					System.out.println("Mauvais identifant 3 fois de suite: fin du programme");
			}
			else
				deconnecter = false;
		} while(deconnecter && essais<3);
		
		while(!deconnecter) {
			if (p.getNom().equals("admin"))
				deconnecter = menuAdmin((Admin)p,c);
			else
				deconnecter = menuEmploye((Employe)p,c);
		}
	}
	
	
	
	public static boolean menuEmploye(Employe e, Compagnie c) {
		System.out.println("\n\nBienvenue "+e.getNom());
		System.out.println("Menu Employé");
		System.out.println("1. Début d'activité");
		System.out.println("2. Fin d'activité");
		System.out.println("3. Talon");
		System.out.println("4. Heures travaillées de base");
		System.out.println("5. Heures travaillées supplémentaires");
		System.out.println("6. Rapport de progression");
		System.out.println("7. Se déconnecter");
		int choix = scan.nextInt();
		
		switch (choix) {
		case 1:
			System.out.println("Début d'activité sur le projet:");
			int j = 1;
			for(int i = 0; i<c.getListeProjets().size();i++) {
				Projet p = c.getListeProjets().get(i);
				if(p.getListe_Employes().contains(e)) {
				System.out.println(j+". "+p.getNom_Projet());
				}
			}
			Projet projet = c.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Début d'activité sur la discipline du projet "+projet.getNom_Projet()+":");
			for(int i = 1; i<=projet.getListe_Disciplines().size();i++) {
				System.out.println(i+". "+projet.getListe_Disciplines().get(i-1).getNom_Discipline());
			}
			e.connecter_Activite(projet,projet.getListe_Disciplines().get(scan.nextInt()-1));
			break;
		case 2:
			System.out.println("Fin d'activité");
			e.terminer_Activite();
			break;
		case 3:
			System.out.println("Talon de paye");
			System.out.println("Salaire à partir d'une certaine date ou non? (y/n)");
			char rep = scan.next().charAt(0);
			if(rep == 'y') {
				System.out.println("Veuillez indiquer à partir de quelle date: (AAAA/MM/JJ)");
				e.demander_Periode(scan.next());
			}else {
				System.out.println("Depuis la dernière période de paye");
				e.demander_Talon();
			}
			break;
		case 4:
			System.out.println("Heure par projet par discipline");
			System.out.println("Veuillez indiquer la date de début de la période désirée (AAAA/MM/JJ)");
			String debut = scan.next();
			System.out.println("Veuillez indiquer la date de fin de la période désirée (AAAA/MM/JJ)");
			String fin = scan.next();
			e.demander_Nbre_Heure_Travaille(debut,fin);
			break;
		case 5:
			System.out.println("Heures supplémentaires par projet par discipline");
			System.out.println("Heure par projet par discipline");
			System.out.println("Veuillez indiquer la date de début de la période désirée (AAAA/MM/JJ)");
			debut = scan.next();
			System.out.println("Veuillez indiquer la date de fin de la période désirée (AAAA/MM/JJ)");
			fin = scan.next();
			e.demander_Nbre_Heure_Supp_Travaille(debut,fin);
			break;
		case 6:
			System.out.println("Rapport de progression");
			System.out.println("Indiquez quel type de rapport vous voulez:");
			System.out.println("1. Rapport global");
			for(int i = 2; i<=c.getListeProjets().size()+1;i++) {
				System.out.println(i+". Rapport du projet "+c.getListeProjets().get(i-2).getNom_Projet());
			}
			int choix2 = scan.nextInt();
			if(choix2 > 1) {
				projet = c.getListeProjets().get(choix2-2);
				e.rapport_Etat_Projet(projet);
			} else {
				e.rapport_Total_Projet();
			}
			break;
		case 7:
			return true;
		default:
			break;
		}
		return false;
	}
	
	
	
	
	public static boolean menuAdmin(Admin admin, Compagnie c) {
		System.out.println("\n\nBienvenue " + admin.getNom());
		System.out.println("Menu admin");
		System.out.println("1. Modifier NPE");
		System.out.println("2. Modifier Projet");
		System.out.println("3. Modifier employé");
		System.out.println("4. Modifier ID");
		System.out.println("5. Assigner employé projet");
		System.out.println("6. Supprimer employé projet");
		System.out.println("7. Rapport salaire total");
		System.out.println("8. Rapport de progression");
		System.out.println("9. Se déconnecter");
		int choix = scan.nextInt();
		
		
		
		switch (choix) {
		case 1:
			System.out.println("NPE = "+c.getNpe());
			System.out.println("Nouveau NPE:");
			int NPE = scan.nextInt();
			c.setNpe(NPE);
			System.out.println("Le nouveau npe est "+ NPE);
			break;
		case 2:
			System.out.println("Modifier Projet");
			System.out.println("1.Ajouter");
			System.out.println("2.Supprimer");
			System.out.println("3.Modifier");
			int choix2 = scan.nextInt();
			if(choix2 == 1) {
				System.out.println("nom du projet:");
				String nom = scan.next();
				
				System.out.println("id du projet:");
				int id = scan.nextInt();
				
				System.out.println("heures Design1:");
				double design1 = scan.nextDouble();
				
				System.out.println("heures Design2:");
				double design2 = scan.nextDouble();
				
				System.out.println("heures Implémentation:");
				double implementation = scan.nextDouble();
				
				System.out.println("heures Test:");
				double test = scan.nextDouble();
				
				System.out.println("heures Déploiement:");
				double deploiement = scan.nextDouble();
				
				System.out.println("Date début: AAAA/MM/JJ");
				String dateDebut = scan.next();
				
				System.out.println("Date fin: AAAA/MM/JJ");
				String dateFin = scan.next();
				
				admin.ajouter_Projet(nom, id, design1, design2, implementation, test, deploiement, dateDebut, dateFin,c);
				System.out.println("Projet créé!");
			}
			if(choix2 == 2) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=c.getListeProjets().size();i++) {
					System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = c.getListeProjets().get(scan.nextInt()-1);
				System.out.println("Voulez-vous vraiment supprimer "+ projet.getNom_Projet() + " ? (y/n)");
				char rep = scan.next().charAt(0);
				if(rep == 'y') {
					admin.supprimer_Projet(projet,c);
					System.out.println(projet.getNom_Projet() + " supprimé!");
				}else
					System.out.println(projet.getNom_Projet() + " non supprimé, retour au menu");
				
			}
			if(choix2 == 3) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=c.getListeProjets().size();i++) {
					System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = c.getListeProjets().get(scan.nextInt()-1);
				
				System.out.println("Modifier Projet " + projet.getNom_Projet());
				System.out.println("1.nom");
				System.out.println("2.id");
				System.out.println("3.heures budgétés des disciplines");
				System.out.println("4.date de début");
				System.out.println("5.date de fin");
				
				int choix3 = scan.nextInt();
				
				if(choix3 == 1) {
					System.out.println("Nom = "+ projet.getNom_Projet());
					System.out.println("Nouveau nom:");
					projet.setNom_Projet(scan.next());
					System.out.println("Le nouveau nom est "+ projet.getNom_Projet());
				}
				if(choix3 == 2) {
					System.out.println("ID = "+ projet.getId());
					System.out.println("Nouveau ID:");
					projet.setId(scan.nextInt());
					System.out.println("Le nouvel id est "+ projet.getId());
				}
				if(choix3 == 3) {
					System.out.println("Changer les heures budgétées des disciplines:");
					for(int i = 1; i <= projet.getListe_Disciplines().size();i++) {
						System.out.println(i+". " + projet.getListe_Disciplines().get(i-1).getNom_Discipline());
					}
					Discipline d = projet.getListe_Disciplines().get(scan.nextInt()-1);
					System.out.println("L'heure budgétée de la discipline "+d.getNom_Discipline()+" est de "+ d.getNbre_Heures_budgetes() +"h");
					System.out.println("Nouvelle heure budgétée: ");
					d.setNbre_Heures_budgetes(scan.nextDouble());
					System.out.println("Le nouveau nombre d'heure budgété de la discipline "+d.getNom_Discipline()+" est de "+d.getNbre_Heures_budgetes()+"h");
				}
				if(choix3 == 4) {
					System.out.println("Date de début = "+ projet.getDate_Debut().toString());
					System.out.println("Nouvelle date de début: AAAA/MM/JJ");
					String dateDebut = scan.next();
					projet.setDate_Debut(dateDebut);
					System.out.println("Le nouvelle date de début est "+ projet.getDate_Debut().toString());
				}
				if(choix3 == 5) {
					System.out.println("Date de Fin = "+ projet.getDate_Fin().toString());
					System.out.println("Nouvelle date de fin: AAAA/MM/JJ");
					String dateFin = scan.next();
					projet.setDate_Fin(dateFin);
					System.out.println("Le nouvelle date de fin est "+ projet.getDate_Fin().toString());
				}
			}
			break;
		case 3:
			System.out.println("Modifier Employé");
			System.out.println("1.Ajouter");
			System.out.println("2.Supprimer");
			System.out.println("3.Modifier");
			int choix3 = scan.nextInt();
			if(choix3 == 1) {
				System.out.println("demander nom");
				String nom = scan.next();
				
				System.out.println(" id:");
				int id = scan.nextInt();
				
				System.out.println("taux horaire de base");
				int taux_horaire_base = scan.nextInt();
				
				System.out.println("taux horaire supplementaire");
				int taux_horaire_supp = scan.nextInt(); 
				
				System.out.println("poste:");
				String poste = scan.next();
				
				System.out.println("Date embauche: AAAA/MM/JJ");
				String date_embauche = scan.next();
				
				System.out.println("Date depart: AAAA/MM/JJ");
				String date_depart = scan.next();
				
				System.out.println(" numero de nas:");
				int numero_nas = scan.nextInt();
				
				admin.ajouter_Employe(nom, id, poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas, c);
				System.out.println("Employer ajouter");
				
				
			}
			if(choix3 == 2) { //À FINIR
				System.out.println("demander nom,id,date,poste,etc");
			}
			if(choix3 == 3) { //À FINIR
				System.out.println("Modifier");
				System.out.println("1.nom");
				System.out.println("2.id");
				System.out.println("3.poste");
				System.out.println("4.date d'embauche");
				System.out.println("5.date de départ");
				
			}
			break;
		case 4:
			System.out.println("ID = "+admin.getId_personne());
			System.out.println("Nouveau ID:");
			int id = scan.nextInt();
			admin.setId_personne(id);
			System.out.println("Le nouvel ID est "+id);
			break;
		case 5:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=c.getListeProjets().size();i++) {
				System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
			}
			
			Projet projet = c.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employé: ");
			for (int i = 1; i<=c.getListe_Employes().size();i++) {
				System.out.println(i+". "+c.getListe_Employes().get(i-1).getNom());
			}
			Employe per = c.getListe_Employes().get(scan.nextInt()-1);
			admin.assigner_Projet(per,projet,c);
			
			
			break;
		case 6:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=c.getListeProjets().size();i++) {
				System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
			}
			
			projet = c.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employé: ");
			for (int i = 1; i<=projet.getListe_Employes().size();i++) {
				System.out.println(i+". "+projet.getListe_Employes().get(i-1).getNom());
			}
			Employe e = c.getListe_Employes().get(scan.nextInt()-1);
			projet.supprimer_Employe(e);
			System.out.println("Employé "+ e.getNom() + " a été supprimé du projet "+ projet.getNom_Projet());
			break;
		case 7:
			admin.rapport_Salaire();
			break;
		case 8:
			System.out.println("Rapport de progression");
			System.out.println("Indiquez quel type de rapport vous voulez:");
			System.out.println("1. Rapport global");
			for(int i = 2; i<=c.getListeProjets().size()+1;i++) {
				System.out.println(i+". Rapport du projet "+c.getListeProjets().get(i-2).getNom_Projet());
			}
			choix2 = scan.nextInt();
			if(choix2 > 1) {
				projet = c.getListeProjets().get(choix2-2);
				admin.rapport_Etat_Projet(projet);
			} else {
				admin.rapport_Total_Projet();
			}
			break;
		case 9:
			return true;
		default:
			break;
		}
		return false;
	}
}
