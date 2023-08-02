import org.json.simplet.JSONObject;

public class employe {

    public static void main(String[] args) {
        JSONObject bil = new JSONObject();
        bil.put("Nom", "Georges");
        bil.put("id", "01");

        JSONObject bil1 = new JSONObject();
        bil1.put("Nom", "Labelle");
        bil1.put("id", "02");

        JSONObject bil2 = new JSONObject();
        bil2.put("Nom", "Moussa");
        bil2.put("id", "03");

        JSONArray List = new JSONArray();
        list.add(bil);
        list.add(bil1);
        list.add(bil2);

        try (FileWriter file = new FileWriter("employe.json")) {
            file.write(list.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStrackTrace();
            // TODO: handle exception
        }

    }

}
