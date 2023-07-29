package timelog;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		boolean deconnecter = true;
		Date d = new Date();
		
		
		Projet pro = new Projet("Projet1",1,10,10,10,10,10,d,d);
		Employe e = new Employe("Employé1",1,15,17,d,d,123456789);
		Admin a = new Admin("admin",10,15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		c.ajouterProjet(pro);
		c.ajouter_Employe(e);
		pro.ajouter_Employe(e);
		
		
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
		}
		while(deconnecter && essais<3);
		
		while(!deconnecter) {
			if (p.nom.equals("admin"))
				deconnecter = menuAdmin(p,c);
			else
				deconnecter = menuEmploye(p,c);
		}
	}
	
	public static boolean menuEmploye(Personne p, Compagnie c) {
		System.out.println("\n\n1Bienvenue "+p.getNom());
		System.out.println("Menu Employé");
		System.out.println("1. Début d'activité");
		System.out.println("2. Fin d'activité");
		System.out.println("3. Talon");
		System.out.println("4. Heures travaillées de base");
		System.out.println("5. Heures travaillées supplémentaires");
		System.out.println("6. Se déconnecter");
		int choix = scan.nextInt();
		
		switch (choix) {
		case 1:
			System.out.println("1. projet1");
			System.out.println("2. projet2");
			System.out.println("3. projet3");
			break;
		case 2:
			System.out.println("Fin d'activité");
			break;
		case 3:
			System.out.println("Salaire brut et salaire net");
			break;
		case 4:
			System.out.println("Heure par projet par discipline");
			break;
		case 5:
			System.out.println("Heures supplémentaires par projet par discipline");
			break;
		case 6:
			return true;
		default:
			break;
		}
		return false;
	}
	
	public static boolean menuAdmin(Personne p, Compagnie c) {
		System.out.println("\n\nBienvenue " + p.getNom());
		System.out.println("Menu admin");
		System.out.println("1. Modifier NPE");
		System.out.println("2. Modifier Projet");
		System.out.println("3. Modifier employé");
		System.out.println("4. Modifier ID");
		System.out.println("5. Assigner employé projet");
		System.out.println("6. Supprimer employé projet");
		System.out.println("7. Rapport salaire total");
		System.out.println("8. Se déconnecter");
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
				
				Calendar calendrier = Calendar.getInstance();
				calendrier.set(Integer.parseInt(dateDebut.substring(0, 4)), Integer.parseInt(dateDebut.substring(5, 7)), Integer.parseInt(dateDebut.substring(8)));
				Date debut = calendrier.getTime();
				
				calendrier.set(Integer.parseInt(dateFin.substring(0, 4)), Integer.parseInt(dateFin.substring(5, 7)), Integer.parseInt(dateFin.substring(8)));
				Date fin = calendrier.getTime();
				
				Projet projet = new Projet(nom, id, design1, design2, implementation, test, deploiement, debut, fin);
				c.ajouterProjet(projet);
				System.out.println("Projet créé!");
			}
			if(choix2 == 2) {
				System.out.println("choisir projet");
			}
			if(choix2 == 3) {
				System.out.println("Modifier");
				System.out.println("1.nom");
				System.out.println("2.id");
				System.out.println("3.heure");
				System.out.println("4.date");
			}
			break;
		case 3:
			System.out.println("Modifier Employé");
			System.out.println("1.Ajouter");
			System.out.println("2.Supprimer");
			System.out.println("3.Modifier");
			int choix3 = scan.nextInt();
			if(choix3 == 1) {
				System.out.println("demander nom/id");
			}
			if(choix3 == 2) {
				System.out.println("demander nom,id,date,poste,etc");
			}
			if(choix3 == 3) {
				System.out.println("Modifier");
				System.out.println("1.nom");
				System.out.println("2.id");
				System.out.println("3.poste");
				System.out.println("4.date");
			}
			break;
		case 4:
			System.out.println("ID = "+p.getId_personne());
			System.out.println("Nouveau ID:");
			int id = scan.nextInt();
			p.setId_personne(id);
			System.out.println("Le nouvel ID est "+id);
			break;
		case 5:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=c.getListeProjets().size();i++) {
				System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
			}
			
			Projet projet = c.getListeProjets().get(scan.nextInt()-1);
			
			for (int i = 1; i<=c.getListe_Employes().size();i++) {
				System.out.println("Choisir l'employé: ");
				System.out.println(i+". "+c.getListe_Employes().get(i-1).getNom());
			}
			Employe per = (Employe) c.getListe_Employes().get(scan.nextInt()-1);
			projet.ajouter_Employe(per);
			System.out.println("Employé "+ per.getNom() + " a été ajouté au projet "+ projet.getNom_Projet());
			
			break;
		case 6:
			System.out.println("Choisir Projet -> choisir employé");
			break;
		case 7:
			System.out.println("Rapport :)");
			break;
		case 8:
			return true;
		default:
			break;
		}
		return false;
	}
}
