package timelog;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		boolean deconnecter = true;
		Compagnie c = new Compagnie();
		c.addPersonne(new Employe("Employ�1",1));
		int essais = 0,id;
		String user;
		Personne p;
		
		do {
		Scanner scan = new Scanner(System.in);
		System.out.println("Connexion\nUser:");
		user = scan.next();
		System.out.println("ID:");
		id = scan.nextInt();
		
		p = c.trouverPersonne(user);
		if(!p.seConnecter(id)) {
			System.out.println("Mauvais nom d'utilisateur ou mot de passe; veuillez r�essayer");
			essais++;
			if (essais == )
		}
		else {
			deconnecter = false;
		}
		while(deconnecter && essais<3);
		int NPE = 2;
		
		while(!deconnecter) {
			System.out.println("Bienvenue "+user);
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
				deconnecter = true;
				break;
			default:
				break;
			}
			if (deconnecter) {
				break;
			}
			
			System.out.println("Bienvenue " + user);
			System.out.println("Menu admin");
			System.out.println("1. Modifier NPE");
			System.out.println("2. Modifier Projet");
			System.out.println("3. Modifier employ�");
			System.out.println("4. Modifier ID");
			System.out.println("5. Assigner employ� projet");
			System.out.println("6. Supprimer employ� projet");
			System.out.println("7. Rapport salaire total");
			System.out.println("8. Se d�connecter");
			choix = scan.nextInt();
			
			
			
			switch (choix) {
			case 1:
				System.out.println("NPE = "+NPE);
				System.out.println("Nouveau NPE:");
				NPE = scan.nextInt();
				System.out.println(NPE);
				break;
			case 2:
				System.out.println("Modifier Projet");
				System.out.println("1.Ajouter");
				System.out.println("2.Supprimer");
				System.out.println("3.Modifier");
				int choix2 = scan.nextInt();
				if(choix2 == 1) {
					System.out.println("demander nom,id,date,heure,etc");
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
				System.out.println("ID = "+id);
				System.out.println("Nouveau ID:");
				id = scan.nextInt();
				System.out.println(id);
				break;
			case 5:
				System.out.println("Choisir Projet -> choisir employ�");
				break;
			case 6:
				System.out.println("Choisir Projet -> choisir employ�");
				break;
			case 7:
				System.out.println("Rapport :)");
				break;
			case 8:
				deconnecter = true;
				break;
			default:
				break;
			}
			if(deconnecter) {
				break;
			}
		}
	}
}
