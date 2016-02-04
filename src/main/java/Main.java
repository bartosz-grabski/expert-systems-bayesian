import java.util.HashMap;
import java.util.Map;

/**
 * Created by bartosz on 03/02/16.
 */
public class Main {



    public static void main(String[] args) {

        String networkFile = Main.class.getClassLoader().getResource("network_zoltaczka.xdsl").getPath();

        Map<String,String> outcomesToIds = new HashMap<>();

        outcomesToIds.put("Przedwątrobowa","Zoltaczka_przedwatrobowa");
        outcomesToIds.put("Wątrobowa","Zoltaczka_watrobowa");
        outcomesToIds.put("Pozawątrobowa","Zoltaczka_pozawatrobowa");

        Map<String, String> evidenceToId = new HashMap<>();

        evidenceToId.put("Podwyższona bilirubina pośrednia","Podwyszona_Bilirubina_posrednia");
        evidenceToId.put("Podwyższona bilirubina bezpośrednia","Podwyszona_Bilirubina_bezposrednia");
        evidenceToId.put("Podwyższona bilirubina bezpośrednia w moczu","Podwyszona_Bilirubina_bezposrednia_w_moczu");
        evidenceToId.put("Urobilinogen w moczu","Urobilinogen_w_moczu");
        evidenceToId.put("Świąd skóry","Swiad_skory");
        evidenceToId.put("Obniżona haptoglobina","Obnizona_Haptoglobina");
        evidenceToId.put("Podwyższona fosfataza alkaliczna","PodwyzszibaFosfataza_alkaliczna");
        evidenceToId.put("Bóle mięśni","Bole_miesni");

        BayesianInferer inf = new BayesianInferer(networkFile, outcomesToIds);
        GraphicalInterface gui = new GraphicalInterface("Objawy i predykcja żółtaczki",inf,evidenceToId, outcomesToIds);


    }






}
