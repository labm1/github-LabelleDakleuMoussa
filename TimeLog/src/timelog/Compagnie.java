package timelog;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Compagnie {
	static Scanner scan = new Scanner(System.in);
	private ArrayList<Employe> liste_Employes;
	private Admin admin;
	private ArrayList<Projet> liste_Projets;
	private int npe;
	
	public Compagnie() {
		liste_Employes = new ArrayList<>();
		liste_Projets = new ArrayList<>();
		npe = 2;
	}
	
	public int getNpe() {
		return npe;
	}

	public void setNpe(int npe) {
		this.npe = npe;
	}

	public ArrayList<Projet> getListeProjets() {
		return liste_Projets;
	}

	public void setListeProjets(ArrayList<Projet> listeProjets) {
		this.liste_Projets = listeProjets;
	}

	public void ajouter_Employe(Employe e){
		liste_Employes.add(e);
	}
	
	public void enlever_Employe(Employe e){
		liste_Employes.remove(e);
	}
	
	public ArrayList<Employe> getListe_Employes() {
		return liste_Employes;
	}

	public void setListe_Employes(ArrayList<Employe> liste_Employes) {
		this.liste_Employes = liste_Employes;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public void ajouterProjet(Projet p){
		liste_Projets.add(p);
	}
	
	public void supprimerProjet(Projet p){
		liste_Projets.remove(p);
	}
	
	public Personne trouverPersonne(String nom) {
		if(nom.equals("admin"))
			return this.admin;
		for (Personne p : liste_Employes) {
			if(p.getNom().equals(nom))
				return p;
		}
		return null;
	}
	
	public Projet trouverProjet(String nom) {
		for (Projet p : liste_Projets) {
			if(p.getNom_Projet().equals(nom))
				return p;
		}
		return null;
	}
	
	public double trouverTempsEnHeures(Date debut, Date fin) {
		return (double)(fin.getTime()-debut.getTime())/(1000.0*3600);
	}
	
	public void sauvegarder_Personnes() {
		JSONArray dataPersonnes = new JSONArray();
		
		for(Employe e : this.liste_Employes) {
			JSONObject dataPersonne = new JSONObject();
			dataPersonne.put("nom", e.getNom());
			dataPersonne.put("id", e.getId_personne());
			dataPersonne.put("poste", e.getPoste());
			dataPersonne.put("taux_horaire_supp", e.getTaux_horaire_supp());
			dataPersonne.put("taux_horaire_base", e.getTaux_horaire_base());
			dataPersonne.put("numero_nas", e.getNumero_nas());
			dataPersonne.put("date_embauche", e.getDate_embauche());
			dataPersonne.put("date_depart", e.getDate_depart());
			dataPersonnes.put(dataPersonne);
		}
		JSONObject dataAdmin = new JSONObject();
		dataAdmin.put("nom", admin.getNom());
		dataAdmin.put("id", admin.getId_personne());
		dataAdmin.put("poste", admin.getPoste());
		dataAdmin.put("taux_horaire_supp", admin.getTaux_horaire_supp());
		dataAdmin.put("taux_horaire_base", admin.getTaux_horaire_base());
		dataAdmin.put("numero_nas", admin.getNumero_nas());
		dataAdmin.put("date_embauche", admin.getDate_embauche());
		dataAdmin.put("date_depart", admin.getDate_depart());
		dataPersonnes.put(dataAdmin);
		
		
		 try (FileWriter fileWriter = new FileWriter("test.json")) {
	            fileWriter.write(dataPersonnes.toString(4));
	            fileWriter.flush();
	        } catch (IOException e) {
	            System.err.println("Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
	        }
	}
	
	public void sauvegarder_Projets() {
		JSONArray dataProjets = new JSONArray();
		for(Projet p : this.liste_Projets) {
			JSONObject dataProjet = new JSONObject();
			JSONArray dataDisciplines = new JSONArray();
			JSONArray dataEmployes = new JSONArray();
			dataProjet.put("nom_Projet", p.getNom_Projet());
			dataProjet.put("id", p.getId());
			dataProjet.put("nbre_Heures_Budgetes_Projet", p.getNbre_Heures_Budgetes());
			dataProjet.put("date_Debut", p.getDate_Debut());
			dataProjet.put("date_Fin", p.getDate_Fin());
			for (Employe e : p.getListe_Employes()) {
				JSONObject dataEmploye = new JSONObject();
				dataEmploye.put("nom_Employe", e.getNom());
				dataEmployes.put(dataEmploye);
			}
			for (Discipline d : p.getListe_Disciplines()) {
				JSONObject dataDiscipline = new JSONObject();
				dataDiscipline.put("nom_Discipline", d.getNom_Discipline());
				dataDiscipline.put("nbre_Heures_budgetes_Discipline", d.getNbre_Heures_budgetes());
				dataDisciplines.put(dataDiscipline);
			}
			JSONArray dataProjetAuComplet = new JSONArray();
			dataProjetAuComplet.put(dataProjet);
			dataProjetAuComplet.put(dataEmployes);
			dataProjetAuComplet.put(dataDisciplines);
			

			
			dataProjets.put(dataProjetAuComplet);
			
		}
		JSONObject dataNPE = new JSONObject();
		dataNPE.put("npe", this.npe);
		dataProjets.put(dataNPE);
		
		 try (FileWriter fileWriter = new FileWriter("projets.json")) {
	            fileWriter.write(dataProjets.toString(4));
	            fileWriter.flush();
	            System.out.println("Données ajoutées au fichier JSON avec succès.");
	        } catch (IOException e) {
	            System.err.println("Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
	        }
	}
	
	public void sauvegarder_date_debut(Date date, Projet p, Discipline d, Employe e) {
	    //1. lire le fichier json et trouver les activités de l'employé
	 	//2. vérifier s'il a terminé toute ses activités
	 	//2.1 sinon, erreur pour commencer activité
	 	//3. ajouter le nouvel objet de début d'activité
		
	        // Lire le contenu du fichier JSON en tant que chaîne
		JSONArray jsonArray = null;
			try {
				String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));
			
		         jsonArray = new JSONArray(jsonData.toString());
			     for (int i = 0; i < jsonArray.length(); i++) {
			    	 JSONObject jsonObject = jsonArray.getJSONObject(i);
			    	 if (jsonObject.get("employe").equals(e.getNom())&& jsonObject.get("date_fin").equals("null")) {
			    		 System.out.println("Veuillez finir l'activité commencée avant d'en faire une autre");
			    		 return;
			    	 }
			    }
	        }catch (Exception e1) {
				jsonArray = new JSONArray();
			}
	  try (FileWriter fileWriter = new FileWriter("dates.json")) {
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("projet", p.getNom_Projet());
	    jsonObject.put("discipline", d.getNom_Discipline());
	    jsonObject.put("employe", e.getNom());
	    jsonObject.put("date_debut", date);
	    jsonObject.put("date_fin", "null");
	    jsonObject.put("heures", 0);
	    
	    if(Calendar.HOUR_OF_DAY > 16 || Calendar.HOUR_OF_DAY < 7 || Calendar.DAY_OF_WEEK == 1 || Calendar.DAY_OF_WEEK == 7) {
	    	 jsonObject.put("type", "supp");
	    }else {
	    	jsonObject.put("type", "base");
	    }
	    
	    jsonArray.put(jsonObject);
	    
	    
	    // Écrire les données JSON dans le fichier
	        fileWriter.write(jsonArray.toString(4));
	        fileWriter.flush();
	    } catch (IOException exception) {
	        System.err.println("Erreur lors de l'enregistrement ou la lecture des données d'activité : " + exception.getMessage());
	    }
	}
	
	public void sauvegarder_date_fin(Date date,Employe e) {
	    //1. lire le fichier json et trouver les activités de l'employé
	 	//2. vérifier l'activité qu'il a commencer et la terminer
	 	//3. si activité pas commencé message d'erreur
		
	        // Lire le contenu du fichier JSON en tant que chaîne
		JSONArray jsonArray = null;
			try {
				String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));
			
		         jsonArray = new JSONArray(jsonData.toString());
			     for (int i = 0; i < jsonArray.length(); i++) {
			    	 JSONObject jsonObject = jsonArray.getJSONObject(i);
			    	 if (jsonObject.get("employe").equals(e.getNom())&& jsonObject.get("date_fin").equals("null")) {
			    		 String debut = jsonObject.getString("date_debut");
			    		 jsonObject.put("date_fin", date);
			    		 jsonObject.put("heures", trouverTempsEnHeures(StringToDate(debut), date));
			    		 break;
			    	 }
			    }
	        } catch (IOException e1) {
				e1.printStackTrace();
			}catch (Exception e1) {
				jsonArray = new JSONArray();
			}
	  try (FileWriter fileWriter = new FileWriter("dates.json")) {
		  // Écrire les données JSON dans le fichier
	        fileWriter.write(jsonArray.toString(4));
	        fileWriter.flush();
	    } catch (IOException exception) {
	        System.err.println("Erreur lors de l'enregistrement ou la lecture des données d'activité : " + exception.getMessage());
	    }
	}
	
	public void lire_Personnes() throws NoSuchFileException, IOException{
            String jsonData = new String(Files.readAllBytes(Paths.get("test.json")));

            JSONArray jsonArray = new JSONArray(jsonData.toString());
		     for (int i = 0; i < jsonArray.length(); i++) {
		    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

               // Extraire les données pour créer un nouvel objet Employe
               String nom = (String) jsonObject.get("nom");
               int id_personne = Integer.parseInt(jsonObject.get("id").toString());
               String poste = (String) jsonObject.get("poste");
               double taux_horaire_supp = Double.parseDouble(jsonObject.get("taux_horaire_supp").toString());
               double taux_horaire_base = Double.parseDouble(jsonObject.get("taux_horaire_base").toString());
               int numero_nas = Integer.parseInt(jsonObject.get("numero_nas").toString());
               String date_embauche = (String) jsonObject.get("date_embauche");
               String date_depart = (String) jsonObject.get("date_depart");

               // Créer un nouvel objet Employe avec les données extraites
               Date embauche = StringToDate(date_embauche);
               Date depart = StringToDate(date_depart);
               if(i == jsonArray.length()-1)
            	   setAdmin(new Admin(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, embauche, depart, numero_nas));
               else {
            	   Employe employe = new Employe(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, embauche, depart, numero_nas);
		           liste_Employes.add(employe);
	            }
       
           }

	}
	
	public void lire_Projets() throws NoSuchFileException, IOException{
			String jsonData = new String(Files.readAllBytes(Paths.get("projets.json")));

            JSONArray jsonArray = new JSONArray(jsonData.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                if(i == jsonArray.length()-1) {
                	JSONObject data = jsonArray.getJSONObject(i);
                	this.npe = data.getInt("npe");
                	break;
                }
                
                JSONArray dataProjetAuComplet = jsonArray.getJSONArray(i);
                JSONObject dataProjet = dataProjetAuComplet.getJSONObject(0);
                JSONArray dataEmployes = dataProjetAuComplet.getJSONArray(1);
                JSONArray dataDisciplines = dataProjetAuComplet.getJSONArray(2);

                
                String nom_Projet = dataProjet.getString("nom_Projet");
                int id = dataProjet.getInt("id");
                double nbre_Heures_Budgetes_Projet = dataProjet.getDouble("nbre_Heures_Budgetes_Projet");
                Date debut = StringToDate(dataProjet.getString("date_Debut"));
                Date fin = StringToDate(dataProjet.getString("date_Fin"));
                

                ArrayList<Employe> listeEmployes = new ArrayList<>();
                for (int j = 0; j < dataEmployes.length(); j++) {
                    JSONObject dataEmploye = dataEmployes.getJSONObject(j);
                    Employe employe = (Employe) trouverPersonne(dataEmploye.getString("nom_Employe"));
                    listeEmployes.add(employe);
                }

                ArrayList<Discipline> listeDisciplines = new ArrayList<>();
                for (int j = 0; j < dataDisciplines.length(); j++) {
                    JSONObject dataDiscipline = dataDisciplines.getJSONObject(j);
                    Discipline discipline = new Discipline(dataDiscipline.getString("nom_Discipline"),dataDiscipline.getInt("nbre_Heures_budgetes_Discipline"));
                    listeDisciplines.add(discipline);
                }

                Projet projet = new Projet(nom_Projet,id,listeEmployes,listeDisciplines,nbre_Heures_Budgetes_Projet,debut,fin);
                this.liste_Projets.add(projet);
            }
	
	}
	
	public double lire_Heures_Travaillees_Base(String employe, Date dateMinimum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && jsonObject.get("type").equals("base")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	public double lire_Heures_Travaillees_Supp(String employe, Date dateMinimum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && jsonObject.get("type").equals("supp")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	public double lire_Heures_Travaillees(String projet) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nomProjet = (String) jsonObject.get("projet");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nomProjet.equals(projet) && !date_fin.equals("null")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	public double lire_Heures_Travaillees(String projet, String discipline) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nomProjet = (String) jsonObject.get("projet");
           String nomDiscipline = (String) jsonObject.get("discipline");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nomProjet.equals(projet) && nomDiscipline.equals(discipline) && !date_fin.equals("null")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	
	public void initialisation() {
		JSONArray jsonArray = null;
		try {
			String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));
		
	         jsonArray = new JSONArray(jsonData.toString());
        }catch (Exception e1) {
			jsonArray = new JSONArray();
		}
		
		Date d = new Date();
		Projet projet1 = new Projet("projet1",1,10,10,10,10,10,d,d);
		Projet projet2 = new Projet("projet2",1,10,10,10,10,10,d,d);
		Projet projet3 = new Projet("projet3",1,10,10,10,10,10,d,d);
		
		
		Employe employe1 = new Employe("employe1",1,"Développeur sénior",15,17,d,d,123456789);
		Employe employe2 = new Employe("employe2",2,"Développeur junior",15,17,d,d,123456789);
		Employe employe3 = new Employe("employe3",3,"Développeur junior",15,17,d,d,123456789);
			
		Admin a = new Admin("admin",10,"Admin",15,17,d,d,123456789);
		
  try (FileWriter fileWriter = new FileWriter("dates.json")) {
    
    for(int i = 0; i<14;i++) {
    	for(int j = 0; j<projet1.getListe_Disciplines().size();j++) {
		    Calendar calendrier = Calendar.getInstance();
		    calendrier.add(Calendar.DAY_OF_MONTH, -i);
			JSONObject jsonObject1 = new JSONObject();			
			
		    jsonObject1.put("projet", projet1.getNom_Projet());
		    jsonObject1.put("discipline", projet1.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject1.put("employe", employe1.getNom());
		    jsonObject1.put("date_debut", calendrier.getTime());
		    jsonObject1.put("date_fin", "initialisation");
		    jsonObject1.put("heures", 1.1);
		    jsonObject1.put("type", "base");
		    
		    jsonArray.put(jsonObject1);
		    
		    jsonObject1.put("projet", projet2.getNom_Projet());
		    jsonObject1.put("discipline", projet2.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject1.put("employe", employe1.getNom());
		    jsonObject1.put("date_debut", calendrier.getTime());
		    jsonObject1.put("date_fin", "initialisation");
		    jsonObject1.put("heures", 1.1);
		    jsonObject1.put("type", "base");
		    
		    jsonArray.put(jsonObject1);
		    
		    
		    JSONObject jsonObject2 = new JSONObject();
			
		    jsonObject2.put("projet", projet2.getNom_Projet());
		    jsonObject2.put("discipline", projet2.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject2.put("employe", employe2.getNom());
		    jsonObject2.put("date_debut", calendrier.getTime());
		    jsonObject2.put("date_fin", "initialisation");
		    jsonObject2.put("heures", 1.2);
		    jsonObject2.put("type", "base");
		    
		    jsonArray.put(jsonObject2);
		    
		    jsonObject2.put("projet", projet3.getNom_Projet());
		    jsonObject2.put("discipline", projet3.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject2.put("employe", employe2.getNom());
		    jsonObject2.put("date_debut", calendrier.getTime());
		    jsonObject2.put("date_fin", "initialisation");
		    jsonObject2.put("heures", 1.2);
		    jsonObject2.put("type", "base");
		    
		    jsonArray.put(jsonObject2);
		    
		    
		    JSONObject jsonObject3 = new JSONObject();
			
		    jsonObject3.put("projet", projet3.getNom_Projet());
		    jsonObject3.put("discipline", projet3.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject3.put("employe", employe3.getNom());
		    jsonObject3.put("date_debut", calendrier.getTime());
		    jsonObject3.put("date_fin", "initialisation");
		    jsonObject3.put("heures", 1.3);
		    jsonObject3.put("type", "base");
		    
		    jsonArray.put(jsonObject3);
		    
		    jsonObject3.put("projet", projet1.getNom_Projet());
		    jsonObject3.put("discipline", projet1.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject3.put("employe", employe3.getNom());
		    jsonObject3.put("date_debut", calendrier.getTime());
		    jsonObject3.put("date_fin", "initialisation");
		    jsonObject3.put("heures", 1.3);
		    jsonObject3.put("type", "base");
		    
		    jsonArray.put(jsonObject3);
	    }
    }
    
    // Écrire les données JSON dans le fichier
        fileWriter.write(jsonArray.toString(4));
        fileWriter.flush();
    } catch (IOException exception) {
        System.err.println("Erreur lors de l'enregistrement ou la lecture des données d'activité : " + exception.getMessage());
    }
		setAdmin(a);
		ajouterProjet(projet1);
		ajouterProjet(projet2);
		ajouterProjet(projet3);
		ajouter_Employe(employe1);
		projet1.ajouter_Employe(employe1);
		projet2.ajouter_Employe(employe1);
		ajouter_Employe(employe2);
		projet2.ajouter_Employe(employe2);
		projet3.ajouter_Employe(employe2);
		ajouter_Employe(employe3);
		projet3.ajouter_Employe(employe3);
		projet1.ajouter_Employe(employe3);
		sauvegarder_Personnes();
		sauvegarder_Projets();
	}
	
	public Date StringToDate(String dateString) {
		 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	        try {
	            return sdf.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	
	

	public static void main(String[] args) {
		boolean deconnecter = true;
		Compagnie c = new Compagnie();

		try {
			c.lire_Personnes();
			c.lire_Projets();
		}catch(NoSuchFileException e1) {
			c.initialisation();
        } catch (IOException e) {
			e.printStackTrace();
		}
		
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
			if (p.getNom().equals(c.getAdmin().getNom()))
				deconnecter = c.menuAdmin((Admin)p);
			else
				deconnecter = c.menuEmploye((Employe)p);
		}
	}
	
	
	
	public boolean menuEmploye(Employe e) {
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
			for(int i = 0; i<this.getListeProjets().size();i++) {
				Projet p = this.getListeProjets().get(i);
				if(p.getListe_Employes().contains(e)) {
				System.out.println(i+". "+p.getNom_Projet());
				}
			}
			Projet projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Début d'activité sur la discipline du projet "+projet.getNom_Projet()+":");
			for(int i = 1; i<=projet.getListe_Disciplines().size();i++) {
				System.out.println(i+". "+projet.getListe_Disciplines().get(i-1).getNom_Discipline());
			}
			e.connecter_Activite(projet,projet.getListe_Disciplines().get(scan.nextInt()-1),this);
			break;
		case 2:
			System.out.println("Fin d'activité");
			e.terminer_Activite(this);
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
			for(int i = 2; i<=this.getListeProjets().size()+1;i++) {
				System.out.println(i+". Rapport du projet "+this.getListeProjets().get(i-2).getNom_Projet());
			}
			int choix2 = scan.nextInt();
			if(choix2 > 1) {
				projet = this.getListeProjets().get(choix2-2);
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
	
	
	
	
	public boolean menuAdmin(Admin admin) {
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
			System.out.println("NPE = "+this.getNpe());
			System.out.println("Nouveau NPE:");
			int NPE = scan.nextInt();
			admin.modifier_Npe(this, NPE);
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
				
				admin.ajouter_Projet(nom, id, design1, design2, implementation, test, deploiement, dateDebut, dateFin,this);
				System.out.println("Projet créé!");
			}
			if(choix2 == 2) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=this.getListeProjets().size();i++) {
					System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = this.getListeProjets().get(scan.nextInt()-1);
				System.out.println("Voulez-vous vraiment supprimer "+ projet.getNom_Projet() + " ? (y/n)");
				char rep = scan.next().charAt(0);
				if(rep == 'y') {
					admin.supprimer_Projet(projet,this);
					System.out.println(projet.getNom_Projet() + " supprimé!");
				}else
					System.out.println(projet.getNom_Projet() + " non supprimé, retour au menu");
				
			}
			if(choix2 == 3) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=this.getListeProjets().size();i++) {
					System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = this.getListeProjets().get(scan.nextInt()-1);
				
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
				
				admin.ajouter_Employe(nom, id, poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas, this);
				System.out.println("Employer ajouter");
				
				
			}
			if(choix3 == 2) {
				System.out.println("Choisir le employer: ");
				for (int i = 1; i<=this.getListe_Employes().size();i++) {
					System.out.println(i+". "+this.getListe_Employes().get(i-1).getNom());
				}
				Employe employe = this.getListe_Employes().get(scan.nextInt()-1);
				System.out.println("Voulez-vous vraiment supprimer "+ employe.getNom() + " ? (y/n)");
				char rep = scan.next().charAt(0);
				if(rep == 'y') {
					admin.supprimer_Employe(employe,this);
					System.out.println(employe.getNom() + " supprimé!");
				}else
					System.out.println(employe.getNom() + " non supprimé, retour au menu");
			}
			
				
				if(choix3== 3) {
					System.out.println("Choisir l'employe: ");
					for (int i = 1; i<=this.getListe_Employes().size();i++) {
						System.out.println(i+". "+this.getListe_Employes().get(i-1).getNom());
					}
					Employe employe = this.getListe_Employes().get(scan.nextInt()-1);
					
					System.out.println("Modifier employe " + employe.getNom());
					System.out.println("1.nom");
				    System.out.println("2.id");
				    System.out.println("3.taux_horaire_base");
				    System.out.println("4.taux_horaire_supp");
				   
				    System.out.println("5.date_embauche");
				    System.out.println("6.date_depart");
				    System.out.println("7.numero NAS");
				    
				    int choix4 = scan.nextInt();
				    if(choix4 == 1) {
						System.out.println("Nom = "+ employe.getNom());
						System.out.println("Nouveau nom:");
						employe.setNom(scan.next());
						System.out.println("Le nouveau nom est "+ employe.getNom());
				    }
				    
				    if(choix4 == 2) {
						System.out.println("ID = "+ employe.getId_personne());
						System.out.println("Nouveau ID:");
						employe.setId_personne(scan.nextInt());
						System.out.println("Le nouvel id est "+ employe.getId_personne());
			}
				    if(choix4 == 3) {
						System.out.println("taux_horaire_base = "+ employe.getTaux_horaire_base());
						System.out.println("Nouveau taux_horaire_base:");
						employe.setTaux_horaire_base(scan.nextInt());
						System.out.println("Le nouvel taux_horaire_base est de "+ employe.getTaux_horaire_base());
			}
				    if(choix4 == 4) {
						System.out.println("taux_horaire_supp = "+ employe.getTaux_horaire_supp());
						System.out.println("Nouveau taux_horaire_supp:");
						employe.setTaux_horaire_supp(scan.nextInt());
						System.out.println("Le nouvel taux_horaire_supp est de "+ employe.getTaux_horaire_supp());
			} 
				    
				    
				    if(choix4 == 5) {
						System.out.println("Date d'embauche ="  + employe.getDate_embauche());
						System.out.println("Nouvelle date d'embauche: AAAA/MM/JJ");
						String date_embauche=scan.next() ;
						employe.setDate_embauche(date_embauche);
						System.out.println("Le nouvelle date de début est "+ employe.getDate_embauche());
				    
				    
				}
				    
				    if(choix4 == 6) {
						System.out.println("Date de depart ="  + employe.getDate_depart());
						System.out.println("Nouvelle date d'embauche: AAAA/MM/JJ");
						String date_depart =scan.next() ;
						employe.setDate_depart(date_depart);
						System.out.println("Le nouvelle date de début est "+ employe.getDate_depart());
						
					}
				    
				    if(choix4 == 7) {    
				    	System.out.println("NUMERO NAS = "+ employe.getNumero_nas());
						System.out.println("Nouveau NAS:");
						employe.setNumero_nas(scan.nextInt());
						System.out.println("Le nouveau NAS est "+ employe.getNumero_nas());
			}
				    	
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
			for (int i = 1; i<=this.getListeProjets().size();i++) {
				System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
			}
			
			Projet projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employé: ");
			for (int i = 1; i<=this.getListe_Employes().size();i++) {
				System.out.println(i+". "+this.getListe_Employes().get(i-1).getNom());
			}
			Employe per = this.getListe_Employes().get(scan.nextInt()-1);
			admin.assigner_Projet(per,projet,this);
			
			
			break;
		case 6:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=this.getListeProjets().size();i++) {
				System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
			}
			
			projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employé: ");
			for (int i = 1; i<=projet.getListe_Employes().size();i++) {
				System.out.println(i+". "+projet.getListe_Employes().get(i-1).getNom());
			}
			Employe e = this.getListe_Employes().get(scan.nextInt()-1);
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
			for(int i = 2; i<=this.getListeProjets().size()+1;i++) {
				System.out.println(i+". Rapport du projet "+this.getListeProjets().get(i-2).getNom_Projet());
			}
			choix2 = scan.nextInt();
			if(choix2 > 1) {
				projet = this.getListeProjets().get(choix2-2);
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
