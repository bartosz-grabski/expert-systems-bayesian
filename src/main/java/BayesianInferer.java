import smile.Network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bartosz on 03/02/16.
 */
public class BayesianInferer {

    private Network net;
    private Map<String,String> outcomeToIds;


    public BayesianInferer(String file, Map<String,String> outcomeToIds) {
        this.net = new Network();
        this.outcomeToIds = outcomeToIds;
        net.readFile(file);
    }


    public Map<String,double[]> infer(Map<String,Boolean> idsToValues) {

        net.clearAllEvidence();

        for (String s : idsToValues.keySet()) {
            if (idsToValues.get(s) == true) net.setEvidence(s, idsToValues.get(s).toString());
        }

        net.updateBeliefs();

        Map<String, double[]> vals = new HashMap<String, double[]>();


        for (String outcomeKey : this.outcomeToIds.keySet()) {
            double[] value = net.getNodeValue(this.outcomeToIds.get(outcomeKey));
            vals.put(outcomeKey, value);
        }

        return vals;
    }
}
