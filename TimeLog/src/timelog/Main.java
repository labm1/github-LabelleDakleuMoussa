package timelog;

import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		boolean deconnecter = true;
		Date d = new Date();
		
		
		Projet pro = new Projet("Projet1",1,10,10,10,10,10,d,d);
		Employe e = new Employe("Employ�1",1,15,17,d,d,123456789);
		Employe e2 = new Employe("Employ�2",1,15,17,d,d,123456789);
		Admin a = new Admin("admin",10,15,17,d,d,123456789);
		Compagnie c = new Compagnie(a);
		
		c.ajouterProjet(pro);
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
				System.out.println("Mauvais nom d'utilisateur ou mot de passe; veuillez r�essayer");
				essais++;
				if (essais == 3) 
					System.out.println("Mauvais identifant 3 fois de suite: fin du programme");
			}
			else
				deconnecter = false;
		}
		while(deconnecter && essais<3);
		
		while(!deconnecter) {
			if (p.getNom().equals("admin"))
				deconnecter = menuAdmin((Admin)p,c);
			else
				deconnecter = menuEmploye(p,c);
		}
	}
	
	public static boolean menuEmploye(Personne p, Compagnie c) {
		System.out.println("\n\n1Bienvenue "+p.getNom());
		System.out.println("Menu Employ�");
		System.out.println("1. D�but d'activit�");
		System.out.println("2. Fin d'activit�");
		System.out.println("3. Talon");
		System.out.println("4. Heures travaill�es de base");
		System.out.println("5. Heures travaill�es suppl�mentaires");
		System.out.println("6. Se d�connecter");
		int choix = scan.nextInt();
		
		switch (choix) {
		case 1:
			System.out.println("1. projet1");
			System.out.println("2. projet2");
			System.out.println("3. projet3");
			break;
		case 2:
			System.out.println("Fin d'activit�");
			break;
		case 3:
			System.out.println("Salaire brut et salaire net");
			break;
		case 4:
			System.out.println("Heure par projet par discipline");
			break;
		case 5:
			System.out.println("Heures suppl�mentaires par projet par discipline");
			break;
		case 6:
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
		System.out.println("3. Modifier employ�");
		System.out.println("4. Modifier ID");
		System.out.println("5. Assigner employ� projet");
		System.out.println("6. Supprimer employ� projet");
		System.out.println("7. Rapport salaire total");
		System.out.println("8. Se d�connecter");
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
				
				System.out.println("heures Impl�mentation:");
				double implementation = scan.nextDouble();
				
				System.out.println("heures Test:");
				double test = scan.nextDouble();
				
				System.out.println("heures D�ploiement:");
				double deploiement = scan.nextDouble();
				
				System.out.println("Date d�but: AAAA/MM/JJ");
				String dateDebut = scan.next();
				
				System.out.println("Date fin: AAAA/MM/JJ");
				String dateFin = scan.next();
				
				admin.ajouter_Projet(nom, id, design1, design2, implementation, test, deploiement, dateDebut, dateFin,c);
				System.out.println("Projet cr��!");
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
					System.out.println(projet.getNom_Projet() + " supprim�!");
				}else
					System.out.println(projet.getNom_Projet() + " non supprim�, retour au menu");
				
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
				System.out.println("3.heures budg�t�s des disciplines");
				System.out.println("4.date de d�but");
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
					
				}
				if(choix3 == 4) {
					System.out.println("Date de d�but = "+ projet.getDate_Debut().toString());
					System.out.println("Nouvelle date de d�but: AAAA/MM/JJ");
					String dateDebut = scan.next();
					projet.setDate_Debut(dateDebut);
					System.out.println("Le nouvelle date de d�but est "+ projet.getDate_Debut().toString());
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
			System.out.println("Modifier Employ�");
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
			
			System.out.println("Choisir l'employ�: ");
			for (int i = 1; i<=c.getListe_Employes().size();i++) {
				System.out.println(i+". "+c.getListe_Employes().get(i-1).getNom());
			}
			Employe per = c.getListe_Employes().get(scan.nextInt()-1);
			projet.ajouter_Employe(per);
			System.out.println("Employ� "+ per.getNom() + " a �t� ajout� au projet "+ projet.getNom_Projet());
			
			break;
		case 6:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=c.getListeProjets().size();i++) {
				System.out.println(i+". "+c.getListeProjets().get(i-1).getNom_Projet());
			}
			
			projet = c.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employ�: ");
			for (int i = 1; i<=projet.getListe_Employes().size();i++) {
				System.out.println(i+". "+projet.getListe_Employes().get(i-1).getNom());
			}
			Employe e = c.getListe_Employes().get(scan.nextInt()-1);
			projet.supprimer_Employe(e);
			System.out.println("Employ� "+ e.getNom() + " a �t� supprim� du projet "+ projet.getNom_Projet());
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
