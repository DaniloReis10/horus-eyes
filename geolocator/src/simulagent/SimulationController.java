package simulagent;

import trilaceration.ScaleConverter;

/**
 * Classe controller da simulacao
 * @author DaniloReis
 *
 */
public class SimulationController {

    public static void main(String[] args) {
        ScaleConverter.latIni = 0.0;
        ScaleConverter.longIni = 0.0;
        ScaleConverter.latEnd = 4.0;
        ScaleConverter.longEnd = 4.0;
        ScaleConverter.width  = 700;
        ScaleConverter.height = 400;
        
        SimulationFrame frame = new SimulationFrame();
        frame.pack();
        frame.setVisible(true);
    }
}
