import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;




public class lirejson {
	
	

	 public static void main(String[] args) {
	  JSONParser jsonP = new JSONParser();
	  
	  try(FileReader reader = new FileReader("jojo.json")){
	   //Read JSON File
	   Object obj = jsonP.parse(reader);
	   JSONArray empList = (JSONArray) obj;
	   System.out.println(empList);
	   //Iterate over emp array
	//   empList.forEach(emp -> parseEmpObj((JSONObject)emp));
	  }
	  catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	 }
	 private static void parseEmpObj(JSONObject emp) {
	  JSONObject empObj = (JSONObject) emp.get("employee");
	  //get emp firstname, lastname, website
	  String fname = (String) empObj.get("Nom");
	  String id = (String) empObj.get("Id");
	  String Poste = (String) empObj.get("Poste");
	  System.out.println("First Name: " + fname);
	  System.out.println("Last Name: " + id);
	  System.out.println("Website: " + Poste);
	 }
	}
	    	
	    
