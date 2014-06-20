package simulagent;

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
                ScaleConverter.latEnd = 0.01;
                ScaleConverter.longEnd = 0.01;
                ScaleConverter.width  = 978;
                ScaleConverter.height = 670;
                
                SimulationFrame frame = new SimulationFrame();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
