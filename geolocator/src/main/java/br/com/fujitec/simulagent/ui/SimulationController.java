package br.com.fujitec.simulagent.ui;

import trilaceration.ScaleConverter;

/**
 * Classe controller da simulacao
 * @author DaniloReis
 *
 */
public class SimulationController {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              buildGUI();
            }

            private void buildGUI() {
                ScaleConverter.latIni = 0.0;
                ScaleConverter.longIni = 0.0;
                ScaleConverter.latEnd = 0.00332;
                ScaleConverter.longEnd = 0.00332;
                ScaleConverter.width  = 978;
                ScaleConverter.height = 650;
                
                ScaleConverter.printScalePixelCorrespondence();
                
                SimulationFrame frame = new SimulationFrame();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
