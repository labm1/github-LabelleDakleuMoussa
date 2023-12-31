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
import java.util.InputMismatchException;
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
	private static Compagnie c;
	
	private Compagnie() {
		liste_Employes = new ArrayList<>();
		liste_Projets = new ArrayList<>();
		npe = 2;
	}
	
	public static Compagnie getInstance() {
		if (c == null)
			c = new Compagnie();
		return c;
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
		this.sauvegarder_Personnes();
	}

	public void ajouterProjet(Projet p){
		liste_Projets.add(p);
	}
	
	public void supprimerProjet(Projet p){
		liste_Projets.remove(p);
	}
	
	
	/**
	 * Trouve un objet Personne � partir de son nom
	 * @param nom nom du projet � trouver
	 * @return un objet Personne qui correspond au nom
	 */
	public Personne trouverPersonne(String nom) {
		if(nom.equals("admin"))
			return this.admin;
		for (Personne p : liste_Employes) {
			if(p.getNom().equals(nom))
				return p;
		}
		return null;
	}
	
	
	/**
	 * Trouve un objet Projet � partir de son nom
	 * @param nom nom du projet � trouver
	 * @return un objet Projet qui correspond au nom
	 */
	public Projet trouverProjet(String nom) {
		for (Projet p : liste_Projets) {
			if(p.getNom_Projet().equals(nom))
				return p;
		}
		return null;
	}
	
	/**
	 * Trouve le temps en heure �coul� entre deux dates
	 * @param debut date de d�but
	 * @param fin date de fin
	 * @return le nombre d'heures entre deux objets Date
	 */
	public double trouverTempsEnHeures(Date debut, Date fin) {
		return (double)(fin.getTime()-debut.getTime())/(1000.0*3600);
	}
	
	
	/**
	 * sauvegarde l'ensemble de la liste des projets dans Compagnie dans "projets.json" 
	 */
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
		
		
		 try (FileWriter fileWriter = new FileWriter("personnes.json")) {
	            fileWriter.write(dataPersonnes.toString(4));
	            fileWriter.flush();
	        } catch (IOException e) {
	            System.err.println("Erreur lors de l'�criture dans le fichier JSON : " + e.getMessage());
	        }
	}
	
	/**
	 * sauvegarde l'ensemble de la liste des projets dans Compagnie dans "projets.json" 
	 */
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
	            System.out.println("Donn�es ajout�es au fichier JSON avec succ�s.");
	        } catch (IOException e) {
	            System.err.println("Erreur lors de l'�criture dans le fichier JSON : " + e.getMessage());
	        }
	}
	
	
	/**
	 * sauvegarde la date de d�but, le projet, la discipline et l'employ� de l'activit� dans "dates.json" 
	 * @param date date de d�but d'activit�
	 * @param e employ� qui commence l'activit�
	 * @param d dicipline dans laquelle l'activit� est commenc�e
	 * @param p projet dans lequel l'activit� est commenc�e
	 */
	public void sauvegarder_date_debut(Date date, Projet p, Discipline d, Employe e) {
	    //1. lire le fichier json et trouver les activit�s de l'employ�
	 	//2. v�rifier s'il a termin� toute ses activit�s
	 	//2.1 sinon, erreur pour commencer activit�
	 	//3. ajouter le nouvel objet de d�but d'activit�
		
	        // Lire le contenu du fichier JSON en tant que cha�ne
		JSONArray jsonArray = null;
			try {
				String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));
			
		         jsonArray = new JSONArray(jsonData.toString());
			     for (int i = 0; i < jsonArray.length(); i++) {
			    	 JSONObject jsonObject = jsonArray.getJSONObject(i);
			    	 if (jsonObject.get("employe").equals(e.getNom())&& jsonObject.get("date_fin").equals("null")) {
			    		 System.out.println("Veuillez finir l'activit� commenc�e avant d'en faire une autre");
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
	    
	    Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		;
	    if(calendar.get(Calendar.HOUR_OF_DAY) > 16 || calendar.get(Calendar.HOUR_OF_DAY) < 7 || calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK)== 7) {
	    	System.out.println(Calendar.HOUR_OF_DAY);
	    	System.out.println(Calendar.DAY_OF_WEEK);
	    	 jsonObject.put("type", "supp");
	    }else {
	    	jsonObject.put("type", "base");
	    }
	    
	    jsonArray.put(jsonObject);
	    
	    
	    // �crire les donn�es JSON dans le fichier
	        fileWriter.write(jsonArray.toString(4));
	        fileWriter.flush();
	    } catch (IOException exception) {
	        System.err.println("Erreur lors de l'enregistrement ou la lecture des donn�es d'activit� : " + exception.getMessage());
	    }
	}
	
	
	/**
	 * sauvegarde la date de fin d'activit� dans "dates.json" 
	 * @param date date de fin d'activit�
	 * @param e employ� qui termine l'activit�
	 */
	public void sauvegarder_date_fin(Date date,Employe e) {
	    //1. lire le fichier json et trouver les activit�s de l'employ�
	 	//2. v�rifier l'activit� qu'il a commencer et la terminer
	 	//3. si activit� pas commenc� message d'erreur
		
	        // Lire le contenu du fichier JSON en tant que cha�ne
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
		  // �crire les donn�es JSON dans le fichier
	        fileWriter.write(jsonArray.toString(4));
	        fileWriter.flush();
	    } catch (IOException exception) {
	        System.err.println("Erreur lors de l'enregistrement ou la lecture des donn�es d'activit� : " + exception.getMessage());
	    }
	}
	
	
	/**
	 * Lit les personnes du fichier "personnes.json" et cr�� les instances des personnes avec les
	 * objets JSON
	 */
	public void lire_Personnes() throws NoSuchFileException, IOException{
            String jsonData = new String(Files.readAllBytes(Paths.get("personnes.json")));

            JSONArray jsonArray = new JSONArray(jsonData.toString());
		     for (int i = 0; i < jsonArray.length(); i++) {
		    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

               // Extraire les donn�es pour cr�er un nouvel objet Employe
               String nom = (String) jsonObject.get("nom");
               int id_personne = Integer.parseInt(jsonObject.get("id").toString());
               String poste = (String) jsonObject.get("poste");
               double taux_horaire_supp = Double.parseDouble(jsonObject.get("taux_horaire_supp").toString());
               double taux_horaire_base = Double.parseDouble(jsonObject.get("taux_horaire_base").toString());
               int numero_nas = Integer.parseInt(jsonObject.get("numero_nas").toString());
               String date_embauche = (String) jsonObject.get("date_embauche");
               String date_depart = (String) jsonObject.get("date_depart");

               // Cr�er un nouvel objet Employe avec les donn�es extraites
               Date embauche = StringToDate(date_embauche);
               Date depart = StringToDate(date_depart);
               if(poste.equals("admin"))
            	   setAdmin(new Admin(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, embauche, depart, numero_nas));
               else {
            	   Employe employe = new Employe(nom, id_personne, poste, taux_horaire_base, taux_horaire_supp, embauche, depart, numero_nas);
		           liste_Employes.add(employe);
	            }
       
           }

	}
	
	
	/**
	 * Lit les projets du fichier "projets.json" et cr�� les instances des projets avec les
	 * objets JSON
	 */
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
	
	
	/**
	 * Lit les heures travaill�es de base du fichier "dates.json" 
	 * d'un employ� dans une intervalle de temps
	 * @return double le nombre d'heures qu'un employ� a travaill� sur un projet
	 * @param employe l'employ� dont on veut connaitre le nombre d'heures travaill�es sur un projet
	 * @param dateMinimum la date minimum de l'intervalle de temps
	 * @param dateMaximum la date maximum de l'intervalle de temps
	 */
	public double lire_Heures_Travaillees_Base(String employe, Date dateMinimum, Date dateMaximum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && dateMaximum.after(debut) && jsonObject.get("type").equals("base")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Lit les heures travaill�es de base du fichier "dates.json" sur un projet 
	 * d'un employ� dans une intervalle de temps
	 * @return le nombre d'heures qu'un employ� a travaill� sur un projet
	 * @param projet le projet dont on veut trouver le nombre d'heures travaill�es
	 * @param employe l'employ� dont on veut connaitre le nombre d'heures travaill�es sur un projet
	 * @param dateMinimum la date minimum de l'intervalle de temps
	 * @param dateMaximum la date maximum de l'intervalle de temps
	 */
	public double lire_Heures_Travaillees_Base(String employe,String projet,Date dateMinimum, Date dateMaximum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           String nomProjet = (String) jsonObject.get("projet");
           Date debut = StringToDate(date_debut);
           
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && dateMaximum.after(debut) && jsonObject.get("type").equals("base") && nomProjet.equals(projet)) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Lit les heures travaill�es suppl�mentaires du fichier "dates.json" 
	 * d'un employ� dans une intervalle de temps
	 * @return double le nombre d'heures qu'un employ� a travaill� sur un projet
	 * @param employe l'employ� dont on veut connaitre le nombre d'heures travaill�es sur un projet
	 * @param dateMinimum la date minimum de l'intervalle de temps
	 * @param dateMaximum la date maximum de l'intervalle de temps
	 */
	public double lire_Heures_Travaillees_Supp(String employe, Date dateMinimum, Date dateMaximum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           Date debut = StringToDate(date_debut);
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && jsonObject.get("type").equals("supp")&& dateMaximum.after(debut)) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Lit les heures travaill�es suppl�mentaires du fichier "dates.json" sur un projet 
	 * d'un employ� dans une intervalle de temps
	 * @return double le nombre d'heures qu'un employ� a travaill� sur un projet
	 * @param projet le projet dont on veut trouver le nombre d'heures travaill�es
	 * @param employe l'employ� dont on veut connaitre le nombre d'heures travaill�es sur un projet
	 * @param dateMinimum la date minimum de l'intervalle de temps
	 * @param dateMaximum la date maximum de l'intervalle de temps
	 */
	public double lire_Heures_Travaillees_Supp(String employe,String projet,Date dateMinimum, Date dateMaximum) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nom = (String) jsonObject.get("employe");
           String date_debut = (String) jsonObject.get("date_debut");
           String date_fin = (String) jsonObject.get("date_fin");
           String nomProjet = (String) jsonObject.get("projet");
           Date debut = StringToDate(date_debut);
           
          if(nom.equals(employe) && !date_fin.equals("null") && dateMinimum.before(debut) && dateMaximum.after(debut) && jsonObject.get("type").equals("supp") && nomProjet.equals(projet)) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Lit les heures travaill�es du fichier "dates.json" sur un projet
	 * @return double le nombre d'heures travaill�es sur un projet
	 * @param projet le projet dont on veut trouver le nombre d'heures trvaill�es
	 */
	public double lire_Heures_Travaillees(String projet) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nomProjet = (String) jsonObject.get("projet");
           String date_fin = (String) jsonObject.get("date_fin");           
          if(nomProjet.equals(projet) && !date_fin.equals("null")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Lit les heures travaill�es du fichier "dates.json" sur une discipline d'un projet
	 * @return double le nombre d'heures travaill�es sur une discipline d'un projet
	 * @param projet le projet d'une discipline
	 * @param discipline la discipline dont on veut trouver le nombre d'heures trvaill�es
	 */
	public double lire_Heures_Travaillees(String projet, String discipline) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));

		double heures = 0;
		
        JSONArray jsonArray = new JSONArray(jsonData.toString());
	     for (int i = 0; i < jsonArray.length(); i++) {
	    	 JSONObject jsonObject = jsonArray.getJSONObject(i);

           String nomProjet = (String) jsonObject.get("projet");
           String nomDiscipline = (String) jsonObject.get("discipline");
           String date_fin = (String) jsonObject.get("date_fin");
           
          if(nomProjet.equals(projet) && nomDiscipline.equals(discipline) && !date_fin.equals("null")) {
        	  heures += jsonObject.getDouble("heures");
          }
       }
	     return heures;
	}
	
	
	/**
	 * Initialise avec les donn�es d'initialisation
	 */
	public void initialisation() {
		JSONArray jsonArray = null;
		try {
			String jsonData = new String(Files.readAllBytes(Paths.get("dates.json")));
		
	         jsonArray = new JSONArray(jsonData.toString());
        }catch (Exception e1) {
			jsonArray = new JSONArray();
		}
		
		//cr�ation des projets, des employ�s, et des admins
		Date d = new Date();
		Projet projet1 = new Projet("projet1",1,100,100,100,100,100,d,d);
		Projet projet2 = new Projet("projet2",2,100,100,100,100,100,d,d);
		Projet projet3 = new Projet("projet3",3,100,100,100,100,100,d,d);
		
		
		Employe employe1 = new Employe("employe1",1,"D�veloppeur s�nior",15,17,d,d,123456789);
		Employe employe2 = new Employe("employe2",2,"D�veloppeur junior",15,17,d,d,123456789);
		Employe employe3 = new Employe("employe3",3,"D�veloppeur junior",15,17,d,d,123456789);
			
		Admin a = new Admin("admin",0,"admin",15,17,d,d,123456789);
		
  try (FileWriter fileWriter = new FileWriter("dates.json")) {
	  //ajouter les heures travaill�es chaque jour durant les deux derni�res semaines;
	  //employ�1 ayant travaill� 1.1 heure pour chaque discipline de deux projet projet1 et projet2; 
	  //employ�2 ayant travaill� 1.2 heures pour chaque discipline de deux projet projet2 et projet3; 
	  //employ�3 ayant travaill� 1.3 heure pour chaque discipline de deux projet projet3 et projet1.
	  for(int i = 0; i<14;i++) {
		  for(int j = 0; j<projet1.getListe_Disciplines().size();j++) {
		    Calendar calendrier = Calendar.getInstance();
		    calendrier.add(Calendar.DAY_OF_MONTH, -i);
			JSONObject jsonObject1 = new JSONObject();			
			
			//Employ� 1
		    jsonObject1.put("projet", projet1.getNom_Projet());
		    jsonObject1.put("discipline", projet1.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject1.put("employe", employe1.getNom());
		    jsonObject1.put("date_debut", calendrier.getTime());
		    jsonObject1.put("date_fin", "initialisation");
		    jsonObject1.put("heures", 1.1);
		    jsonObject1.put("type", "base");
		    
		    jsonArray.put(jsonObject1);
		    JSONObject jsonObject11 = new JSONObject();
		    jsonObject11.put("projet", projet2.getNom_Projet());
		    jsonObject11.put("discipline", projet2.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject11.put("employe", employe1.getNom());
		    jsonObject11.put("date_debut", calendrier.getTime());
		    jsonObject11.put("date_fin", "initialisation");
		    jsonObject11.put("heures", 1.1);
		    jsonObject11.put("type", "base");
		    
		    jsonArray.put(jsonObject11);
		    
		    
		    JSONObject jsonObject2 = new JSONObject();
			
		    //Employ� 2
		    jsonObject2.put("projet", projet2.getNom_Projet());
		    jsonObject2.put("discipline", projet2.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject2.put("employe", employe2.getNom());
		    jsonObject2.put("date_debut", calendrier.getTime());
		    jsonObject2.put("date_fin", "initialisation");
		    jsonObject2.put("heures", 1.2);
		    jsonObject2.put("type", "base");
		    
		    jsonArray.put(jsonObject2);
		    
		    JSONObject jsonObject22 = new JSONObject();
		    jsonObject22.put("projet", projet3.getNom_Projet());
		    jsonObject22.put("discipline", projet3.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject22.put("employe", employe2.getNom());
		    jsonObject22.put("date_debut", calendrier.getTime());
		    jsonObject22.put("date_fin", "initialisation");
		    jsonObject22.put("heures", 1.2);
		    jsonObject22.put("type", "base");
		    
		    jsonArray.put(jsonObject22);
		    
		    
		    JSONObject jsonObject3 = new JSONObject();
			
		    //Employ� 3
		    jsonObject3.put("projet", projet3.getNom_Projet());
		    jsonObject3.put("discipline", projet3.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject3.put("employe", employe3.getNom());
		    jsonObject3.put("date_debut", calendrier.getTime());
		    jsonObject3.put("date_fin", "initialisation");
		    jsonObject3.put("heures", 1.3);
		    jsonObject3.put("type", "base");
		    
		    jsonArray.put(jsonObject3);
		    JSONObject jsonObject33 = new JSONObject();
		    
		    jsonObject33.put("projet", projet1.getNom_Projet());
		    jsonObject33.put("discipline", projet1.getListe_Disciplines().get(j).getNom_Discipline());
		    jsonObject33.put("employe", employe3.getNom());
		    jsonObject33.put("date_debut", calendrier.getTime());
		    jsonObject33.put("date_fin", "initialisation");
		    jsonObject33.put("heures", 1.3);
		    jsonObject33.put("type", "base");
		    
		    jsonArray.put(jsonObject33);
	    }
    }
    
    // �crire les donn�es JSON dans le fichier
        fileWriter.write(jsonArray.toString(4));
        fileWriter.flush();
    } catch (IOException exception) {
        System.err.println("Erreur lors de l'enregistrement ou la lecture des donn�es d'activit� : " + exception.getMessage());
    }
  	//ajouter les personnes et projets � la compagnie
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
	
	
	/**
	 * Transforme une String en objet Date
	 * @return Date un objet date qui correspond au String en param�tre
	 * @param dateString une date sous forme "EEE MMM dd HH:mm:ss zzz yyyy"
	 */
	public Date StringToDate(String dateString) {
		 SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	        try {
	            return sdf.parse(dateString);
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	
	
	//MAIN
	public static void main(String[] args) {
		boolean deconnecter = true;
		
		Compagnie c = Compagnie.getInstance();
		
		//essayer de lire les fichiers json. s'ils n'existent pas, 
		//initialiser avec les donn�es de base
		try {
			c.lire_Personnes();
			c.lire_Projets();
		}catch(NoSuchFileException e) {
			c.initialisation();
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		int essais = 0,id;
		String user;
		Personne p;
		
		//Connexion au syst�me
		try {
			do {
				//entrer le user et mot de passe/id
				System.out.println("Connexion\nUser:");
				user = scan.next();
				System.out.println("ID/Mot De Passe:");
				id = scan.nextInt();
				
				//essayer de trouver la personne dans la liste des personnes;
				//si le user de la personne n'est pas trouv� ou que la m�thode
				//se connecter retourne faux(id non valide), on affiche un message d'erreur.
				//si apr�s 3 essais il n'y a pas le bon mot de passe ou user, le syst�me se 
				//d�connecte
				p = c.trouverPersonne(user);
				if(p == null || !p.seconnecter(id)) {
					System.out.println("Mauvais nom d'utilisateur ou mot de passe; veuillez r�essayer");
					essais++;
					if (essais == 3) 
						System.out.println("Mauvais identifant 3 fois de suite: fin du programme");
				}
				else
					deconnecter = false;
			} while(deconnecter && essais<3);
			
			//Afficher le menu en continu
			//menuAdmin si admin, et
			//menuEmploye si employ�
			while(!deconnecter) {
				if (p.getNom().equals(c.getAdmin().getNom()))
					deconnecter = c.menuAdmin((Admin)p);
				else
					deconnecter = c.menuEmploye((Employe)p);
			}
		}
		catch(InputMismatchException e1) {
			System.out.println("Erreur : vous devez entrer un numero Valide.");
		}
	}
	
	/**
	 * Affiche le menu pour les employ�s
	 * @return boolean Si l'employ� a �t� d�connect� du menu ou non
	 * @param e L'employ� qui a ouvert le menu
	 */
	public boolean menuEmploye(Employe e) {
		
		//afficher le menu principal de l'employ�
		System.out.println("\n\nBienvenue "+e.getNom());
		System.out.println("Menu Employ�");
		System.out.println("1. D�but d'activit�");
		System.out.println("2. Fin d'activit�");
		System.out.println("3. Talon");
		System.out.println("4. Heures travaill�es de base");
		System.out.println("5. Heures travaill�es suppl�mentaires");
		System.out.println("6. Rapport de progression");
		System.out.println("7. Salaire brut par projet");
		System.out.println("8. Se d�connecter");
		int choix = scan.nextInt();
		
		switch (choix) {
		
		//1. D�but d'activit�
		case 1:
			System.out.println("D�but d'activit� sur le projet:");
			for(int i = 0; i<this.getListeProjets().size();i++) {
				Projet p = this.getListeProjets().get(i);
				if(p.getListe_Employes().contains(e)) {
				System.out.println(i+1+". "+p.getNom_Projet());
				}
			}
			Projet projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("D�but d'activit� sur la discipline du projet "+projet.getNom_Projet()+":");
			for(int i = 1; i<=projet.getListe_Disciplines().size();i++) {
				System.out.println(i+". "+projet.getListe_Disciplines().get(i-1).getNom_Discipline());
			}
			e.connecter_Activite(projet,projet.getListe_Disciplines().get(scan.nextInt()-1));
			break;
			
		//2. Fin d'activit�
		case 2:
			System.out.println("Fin d'activit�");
			e.terminer_Activite();
			break;
			
		//3. Talon
		case 3:
			System.out.println("Talon de paye");
			System.out.println("Talon � partir d'une certaine date ou non? (y/n)");
			char rep = scan.next().charAt(0);
			if(rep == 'y') {
				System.out.println("Veuillez indiquer � partir de quelle date: (AAAA/MM/JJ)");
				e.demander_Periode(scan.next());
			}else {
				System.out.println("Depuis la derni�re p�riode de paye");
				e.demander_Talon();
			}
		
			break;
			
		//4. Heures travaill�es de base
		case 4:
			try {
			System.out.println("Heures de base travaill�es");
			System.out.println("Veuillez indiquer la date de d�but de la p�riode d�sir�e (AAAA/MM/JJ)");
			String debut = scan.next();
			System.out.println("Veuillez indiquer la date de fin de la p�riode d�sir�e (AAAA/MM/JJ)");
			String fin = scan.next();
			e.demander_Nbre_Heure_Travaille(debut,fin);
			}
			catch (NumberFormatException e6) {
				System.out.println("mettre date valide");
			}
			break;
			
		//5. Heures travaill�es suppl�mentaires
		case 5:
			try {
			System.out.println("Heures suppl�mentaires travaill�e");
			System.out.println("Veuillez indiquer la date de d�but de la p�riode d�sir�e (AAAA/MM/JJ)");
			String debut = scan.next();
			System.out.println("Veuillez indiquer la date de fin de la p�riode d�sir�e (AAAA/MM/JJ)");
		    String 	fin = scan.next();
			e.demander_Nbre_Heure_Supp_Travaille(debut,fin);
			}
			catch (NumberFormatException e6) {
				System.out.println("mettre date valide");
			}
			break;
			
		//6. Rapport de progression
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
		//7. 
		case 7:
			System.out.println("salaire � partir d'une certaine date ou non? (y/n)");
			rep = scan.next().charAt(0);
			if(rep == 'y') {
				System.out.println("Veuillez indiquer � partir de quelle date: (AAAA/MM/JJ)");
				e.rapport(scan.next());
			}else {
				System.out.println("Depuis la derni�re semaine impaire");
				e.rapport("non");
			}
		
			break;
		//8. Se d�connecter
		case 8:
			return true;
		default:
			break;
		}
		return false;
	}
	
	
	/**
	 * Affiche le menu pour l'admin
	 * @return boolean Si l'admin a �t� d�connect� du menu ou non
	 * @param admin L'admin qui a ouvert le menu
	 */
	public boolean menuAdmin(Admin admin) {
		System.out.println("\n\nBienvenue " + admin.getNom());
		System.out.println("Menu admin");
		System.out.println("1. Modifier NPE");
		System.out.println("2. Modifier Projet");
		System.out.println("3. Modifier employ�");
		System.out.println("4. Modifier mot de passe");
		System.out.println("5. Assigner employ� projet");
		System.out.println("6. Supprimer employ� projet");
		System.out.println("7. Rapport salaire total");
		System.out.println("8. Rapport de progression");
		System.out.println("9. Se d�connecter");
		int choix = scan.nextInt();
		
		
		
		switch (choix) {
		//1. Modifier NPE
		case 1:
			System.out.println("NPE = "+this.getNpe());
			System.out.println("Nouveau NPE:");
			int NPE = scan.nextInt();
			admin.modifier_Npe(NPE);
			System.out.println("Le nouveau npe est "+ NPE);
			c.sauvegarder_Projets();
			break;
		//2. Modifier Projet
		case 2:
			System.out.println("Modifier Projet");
			System.out.println("1.Ajouter");
			System.out.println("2.Supprimer");
			System.out.println("3.Modifier");
			int choix2 = scan.nextInt();
			
			//2.1 Ajouter un nouveau projet
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
				
				admin.ajouter_Projet(nom, id, design1, design2, implementation, test, deploiement, dateDebut, dateFin);
				System.out.println("Projet cr��!");
			}
			
			//2.2 supprimer un projet
			if(choix2 == 2) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=this.getListeProjets().size();i++) {
					System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = this.getListeProjets().get(scan.nextInt()-1);
				System.out.println("Voulez-vous vraiment supprimer "+ projet.getNom_Projet() + " ? (y/n)");
				char rep = scan.next().charAt(0);
				if(rep == 'y') {
					admin.supprimer_Projet(projet);
					System.out.println(projet.getNom_Projet() + " supprim�!");
				}else
					System.out.println(projet.getNom_Projet() + " non supprim�, retour au menu");
				
			}
			
			//2.3 modifier un projet
			if(choix2 == 3) {
				System.out.println("Choisir le projet: ");
				for (int i = 1; i<=this.getListeProjets().size();i++) {
					System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
				}
				Projet projet = this.getListeProjets().get(scan.nextInt()-1);
				
				System.out.println("Modifier Projet " + projet.getNom_Projet());
				System.out.println("1.nom");
				System.out.println("2.id");
				System.out.println("3.heures budg�t�s des disciplines");
				System.out.println("4.date de d�but");
				System.out.println("5.date de fin");
				
				int choix3 = scan.nextInt();
				
				//2.3.1. modifier nom
				if(choix3 == 1) {
					System.out.println("Nom = "+ projet.getNom_Projet());
					System.out.println("Nouveau nom:");
					projet.setNom_Projet(scan.next());
					System.out.println("Le nouveau nom est "+ projet.getNom_Projet());
				}
				
				//2.3.2. modifier id
				if(choix3 == 2) {
					System.out.println("ID = "+ projet.getId());
					System.out.println("Nouveau ID:");
					projet.setId(scan.nextInt());
					System.out.println("Le nouvel id est "+ projet.getId());
				}
				
				//2.3.3. modifier heures budg�t�s
				if(choix3 == 3) {
					System.out.println("Changer les heures budg�t�es des disciplines:");
					for(int i = 1; i <= projet.getListe_Disciplines().size();i++) {
						System.out.println(i+". " + projet.getListe_Disciplines().get(i-1).getNom_Discipline());
					}
					Discipline d = projet.getListe_Disciplines().get(scan.nextInt()-1);
					System.out.println("L'heure budg�t�e de la discipline "+d.getNom_Discipline()+" est de "+ d.getNbre_Heures_budgetes() +"h");
					System.out.println("Nouvelle heure budg�t�e: ");
					d.setNbre_Heures_budgetes(scan.nextDouble());
					System.out.println("Le nouveau nombre d'heure budg�t� de la discipline "+d.getNom_Discipline()+" est de "+d.getNbre_Heures_budgetes()+"h");
				}
				
				//2.3.4. modifier date de d�but
				if(choix3 == 4) {
					System.out.println("Date de d�but = "+ projet.getDate_Debut().toString());
					System.out.println("Nouvelle date de d�but: AAAA/MM/JJ");
					String dateDebut = scan.next();
					projet.setDate_Debut(dateDebut);
					System.out.println("Le nouvelle date de d�but est "+ projet.getDate_Debut().toString());
				}
				
				//2.3.5. modifier date de fin
				if(choix3 == 5) {
					System.out.println("Date de Fin = "+ projet.getDate_Fin().toString());
					System.out.println("Nouvelle date de fin: AAAA/MM/JJ");
					String dateFin = scan.next();
					projet.setDate_Fin(dateFin);
					System.out.println("Le nouvelle date de fin est "+ projet.getDate_Fin().toString());
				}
			}
			c.sauvegarder_Projets();
			break;
			
		//3. Modifier employ�
		case 3:
			System.out.println("Modifier Employ�");
			System.out.println("1.Ajouter");
			System.out.println("2.Supprimer");
			System.out.println("3.Modifier");
			int choix3 = scan.nextInt();
			//3.1 ajouter un nouvel employ�
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
				
				admin.ajouter_Employe(nom, id, poste, taux_horaire_base, taux_horaire_supp, date_embauche, date_depart, numero_nas);
				System.out.println("Employer ajouter");
			}
			
			//3.2 supprimer employ�
			if(choix3 == 2) {
				System.out.println("Choisir le employer: ");
				for (int i = 1; i<=this.getListe_Employes().size();i++) {
					System.out.println(i+". "+this.getListe_Employes().get(i-1).getNom());
				}
				Employe employe = this.getListe_Employes().get(scan.nextInt()-1);
				System.out.println("Voulez-vous vraiment supprimer "+ employe.getNom() + " ? (y/n)");
				char rep = scan.next().charAt(0);
				if(rep == 'y') {
					admin.supprimer_Employe(employe);
					System.out.println(employe.getNom() + " supprim�!");
				}else
					System.out.println(employe.getNom() + " non supprim�, retour au menu");
			}
			
			//3.3 modifier employ�
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
			    
			    //3.3.1 modifier nom
			    if(choix4 == 1) {
					System.out.println("Nom = "+ employe.getNom());
					System.out.println("Nouveau nom:");
					employe.setNom(scan.next());
					System.out.println("Le nouveau nom est "+ employe.getNom());
			    }
			    
			    //3.3.2 modifier id
			    if(choix4 == 2) {
					System.out.println("ID = "+ employe.getId_personne());
					System.out.println("Nouveau ID:");
					employe.setId_personne(scan.nextInt());
					System.out.println("Le nouvel id est "+ employe.getId_personne());
			    }
			    
			    //3.3.3 modifier taux horaire de base
			    if(choix4 == 3) {
					System.out.println("taux_horaire_base = "+ employe.getTaux_horaire_base());
					System.out.println("Nouveau taux_horaire_base:");
					employe.setTaux_horaire_base(scan.nextInt());
					System.out.println("Le nouvel taux_horaire_base est de "+ employe.getTaux_horaire_base());
			    }
			    
			    //3.3.4 modifier taux horaire suppl�mentaire
			    if(choix4 == 4) {
					System.out.println("taux_horaire_supp = "+ employe.getTaux_horaire_supp());
					System.out.println("Nouveau taux_horaire_supp:");
					employe.setTaux_horaire_supp(scan.nextInt());
					System.out.println("Le nouvel taux_horaire_supp est de "+ employe.getTaux_horaire_supp());
			    } 
			    
			    //3.3.5 modifier date d'embauche
			    if(choix4 == 5) {
					System.out.println("Date d'embauche ="  + employe.getDate_embauche());
					System.out.println("Nouvelle date d'embauche: AAAA/MM/JJ");
					String date_embauche=scan.next() ;
					employe.setDate_embauche(date_embauche);
					System.out.println("Le nouvelle date de d�but est "+ employe.getDate_embauche());
			    }
			    
			    //3.3.6 modifier date de d�part
			    if(choix4 == 6) {
					System.out.println("Date de depart ="  + employe.getDate_depart());
					System.out.println("Nouvelle date d'embauche: AAAA/MM/JJ");
					String date_depart =scan.next() ;
					employe.setDate_depart(date_depart);
					System.out.println("Le nouvelle date de d�but est "+ employe.getDate_depart());
				}
			    
			    //3.3.7 modifier nas
			    if(choix4 == 7) {    
			    	System.out.println("NUMERO NAS = "+ employe.getNumero_nas());
					System.out.println("Nouveau NAS:");
					employe.setNumero_nas(scan.nextInt());
					System.out.println("Le nouveau NAS est "+ employe.getNumero_nas());
			    }
			}
			c.sauvegarder_Personnes();
			break;
			
		//4. Modifier mot de passe
		case 4:
			System.out.println("ID = "+admin.getId_personne());
			System.out.println("Nouveau ID:");
			int id = scan.nextInt();
			admin.setId_personne(id);
			System.out.println("Le nouvel ID est "+id);
			c.sauvegarder_Personnes();
			break;
		
		//5. Assigner employ� projet
		case 5:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=this.getListeProjets().size();i++) {
				System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
			}
			
			Projet projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employ�: ");
			for (int i = 1; i<=this.getListe_Employes().size();i++) {
				System.out.println(i+". "+this.getListe_Employes().get(i-1).getNom());
			}
			Employe per = this.getListe_Employes().get(scan.nextInt()-1);
			admin.assigner_Projet(per,projet);
			c.sauvegarder_Projets();
			break;
			
		//6. Supprimer employ� projet
		case 6:
			System.out.println("Choisir le projet: ");
			for (int i = 1; i<=this.getListeProjets().size();i++) {
				System.out.println(i+". "+this.getListeProjets().get(i-1).getNom_Projet());
			}
			
			projet = this.getListeProjets().get(scan.nextInt()-1);
			
			System.out.println("Choisir l'employ�: ");
			for (int i = 1; i<=projet.getListe_Employes().size();i++) {
				System.out.println(i+". "+projet.getListe_Employes().get(i-1).getNom());
			}
			Employe e = this.getListe_Employes().get(scan.nextInt()-1);
			projet.supprimer_Employe(e);
			System.out.println("Employ� "+ e.getNom() + " a �t� supprim� du projet "+ projet.getNom_Projet());
			c.sauvegarder_Projets();
			break;
			
		//7. Rapport salaire total
		case 7:
			System.out.println("Talon de paye");
			System.out.println("Talon � partir d'une certaine date ou non? (y/n)");
			char rep = scan.next().charAt(0);
			if(rep == 'y') {
				System.out.println("Veuillez indiquer � partir de quelle date: (AAAA/MM/JJ)");
				admin.rapport_Salaire(scan.next());
			}else {
				System.out.println("Depuis la derni�re p�riode de paye");
				admin.rapport_Salaire();
			}
			break;
			
		//8. Rapport de progression
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
			
		//9. Se d�connecter
		case 9:
			c.sauvegarder_Personnes();
			c.sauvegarder_Projets();
			return true;
		default:
			break;
		}
		return false;
	}
}
