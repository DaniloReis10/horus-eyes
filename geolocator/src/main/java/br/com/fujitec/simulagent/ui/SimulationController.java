package br.com.fujitec.simulagent.ui;

import trilaceration.ScaleConverter;

/**
 * Classe controller da simulacao
 * @author DaniloReis
 *
 */
public class SimulationController {
	private static ScaleConverter     scale;
	
	public static ScaleConverter getScaleInstance(){
		if(scale == null){
			scale = new ScaleConverter(0.0,0.00332,0.0,0.00332,978,650);
		}
		return scale;
	}

    public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              buildGUI();
            }

            private void buildGUI() {
            	scale = new ScaleConverter(0.0,0.00332,0.0,0.00332,978,650);
                
                scale.printScalePixelCorrespondence();
                
                SimulationFrame frame = new SimulationFrame();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
