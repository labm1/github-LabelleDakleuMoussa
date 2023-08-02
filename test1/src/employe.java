import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.simple.JSONObject;



public class employe {

    public static void main(String[] args) {
    	ObjectMapper objectMapper = new ObjectMapper();
        JSONObject bil = new JSONObject();
        bil.put("Nom", "Georgesjjjjjjjjjj");
        bil.put("id", "01");
        bil.put("Poste", "Employe");
        bil.put("Taux_horaire_supp", "150");
        bil.put("Taux_haraire_Base", "100");
        bil.put("Numero_Nas", "10200");
        bil.put("Date_Embauche", "2020-01-01");
        bil.put("Date_Depart", "2023-12-31");
        
        JSONObject empl = new JSONObject();
        empl.put("Employe1", bil);
        
        
        JSONObject bil1 = new JSONObject();
        bil1.put("Nom", "Labelle");
        bil1.put("id", "02");
        bil1.put("Poste", "Employe");
        bil1.put("Taux_horaire_supp", "150");
        bil1.put("Taux_haraire_Base", "100");
        bil1.put("Numero_Nas", "10000");
        bil1.put("Date_Embauche", "2018-01-01");
        bil1.put("Date_Depart", "2025-12-31");
        
        JSONObject empl01 = new JSONObject();
        empl01.put("Employe2", bil1);

        JSONObject bil2 = new JSONObject();
        bil2.put("Nom", "Moussa");
        bil2.put("id", "03");
        bil2.put("Poste", "Admin");
        bil2.put("Taux_horaire_supp", "200");
        bil2.put("Taux_haraire_Base", "150");
        bil2.put("Numero_Nas", "15050");
        bil2.put("Date_Embauche", "2000-01-01");
        bil2.put("Date_Depart", "2027-12-31");
        
        
        JSONObject empl02 = new JSONObject();
        empl02.put("Employe3", bil2);
        
        // Ajout
        JSONArray empList = new JSONArray();
  
        empList.put(bil);
        empList.put(bil1);
        empList.put(bil2);
        
              
       try (FileWriter job = new FileWriter("job.json")){
        	job.write(empList.toString(10));
        	
        	job.flush();
        	System.out.println("Données écrites avec succès dans le fichier JSON.");
      
        }
        catch(IOException e) {e.printStackTrace();}  
        
       
      
       
     
    }

}