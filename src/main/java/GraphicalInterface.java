import smile.SMILEException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bartosz on 03/02/16.
 */
public class GraphicalInterface {

    private BayesianInferer inf;
    private Map<String,String> evidenceToId;
    private Container contentPane;
    private Map<String, String> outcomeToId;

    GraphicalInterface(String title, BayesianInferer inf, Map<String, String> evidenceToId, Map<String, String> outcomeToId) {

        this.inf = inf;
        this.evidenceToId = evidenceToId;
        this.outcomeToId = outcomeToId;

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        Border border = BorderFactory.createTitledBorder(title);
        panel.setBorder(border);
        this.contentPane = frame.getContentPane();

        for (String evidence : evidenceToId.keySet()) {
            JCheckBox check = new JCheckBox(evidence);
            panel.add(check);
        }



        this.contentPane.add(panel, BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new GridLayout(0, 1));

        JButton button = new JButton("Calculate");
        button.addActionListener(new ConfirmButtonListener(panel, summaryPanel, inf));


        for (String outcome : this.outcomeToId.keySet()) {
            JLabel label = new JLabel();
            label.setName(outcome);
            summaryPanel.add(label);
        }

        summaryPanel.add(button);

        this.contentPane.add(summaryPanel, BorderLayout.SOUTH);
        frame.setSize(500, 300);
        frame.setVisible(true);

    }

    private class ConfirmButtonListener implements ActionListener {

        JPanel checkboxesPanel;
        JPanel summaryPanel;
        BayesianInferer inf;

        ConfirmButtonListener(JPanel checkboxesPanel, JPanel summaryPanel, BayesianInferer inf) {
            this.checkboxesPanel = checkboxesPanel;
            this.summaryPanel = summaryPanel;
            this.inf = inf;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Map<String,Boolean> idsToValues = new HashMap<>();

            for (Component cb : this.checkboxesPanel.getComponents()) {

                if (cb instanceof JCheckBox) {
                    JCheckBox jCheckBox = (JCheckBox) cb;
                    //System.out.println(button.getText() + evidenceToId.get(button.getText()) + button.isSelected());
                    idsToValues.put(evidenceToId.get(jCheckBox.getText()), jCheckBox.isSelected());

                }
            }

            try {
                Map<String,double[]> result = inf.infer(idsToValues);

                for (Component cb : this.summaryPanel.getComponents()) {

                    if (cb instanceof JLabel) {
                        JLabel jLabel = (JLabel) cb;
                        String name = jLabel.getName();
                        DecimalFormat df = new DecimalFormat("#.##");
                        jLabel.setText(name + " : " + df.format(result.get(name)[0] * 100) + "%");
                    }

                }
            } catch (SMILEException ex) {
                ex.printStackTrace();

            }
        }
    }

}
