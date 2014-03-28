package soundid;

import java.util.Iterator;

import trilaceration.Centroide;
import trilaceration.LocationArea;
import trilaceration.SensorPosition;

import location.facade.GeopositionFacade;
import location.facade.IMobileDevice;
import location.geoengine.GeoPosition;
/**
 * Classe responsavel por baseado nas medidas de tempo das bases
 * fazer uma estimativa da localizacao da TAG 
 * @author DaniloReis
 *
 */
public class PositionEstimator {
	GeoPosition   position;// Posicao estimada do centro
	double           ratio;// Raio de incerteza em torno do centro

	public PositionEstimator(TimeMeasurePoint measures) {
		Iterator<TimeMeasure> iterator;
		TimeMeasure           tmeasure;
		GeopositionFacade       facade;
		IMobileDevice    baseInterface;
		BaseAgent                 base;
		double             temperature;       
		double                   ratio;
		double      latitude,longitude;
		LocationArea              area;
		SensorPosition          sensor;
		Centroide            centroide;
		
		// Pega o controlador de posicoes do framework
		facade = GeopositionFacade.getInstance();
		// Cria o objeto de calculo da posicao
    	// Captura os limites de coordenadas pelo ambiente
		area      = new LocationArea (SoundIdModel.getLatitudeIni(),
                                      SoundIdModel.getLongitudeIni(),
                                      SoundIdModel.getLatitudeEnd(),
                                      SoundIdModel.getLongitudeEnd(),
                                      SoundIdModel.getWidth(),
                                      SoundIdModel.getHeight());		
		// percorre a lista de medidas de tempos
		iterator = measures.getIterator();
		while(iterator.hasNext()){
			tmeasure = iterator.next();
			// Busca a posicao da base que emitiu a o pulso
	        baseInterface = facade.findMobileDeviceById(tmeasure.getId());
	        if(baseInterface!= null){
	        	// pega a posicao da estacao base
	        	latitude    = baseInterface.getGeoPosition().getLatitude();
	        	longitude   = baseInterface.getGeoPosition().getLongitude();
	        	base        = (BaseAgent)baseInterface;
	        	// pega a temperatura medida na base
	        	temperature = base.getTemperature(); 
	        	// Calcula a distancia estimada
	        	ratio     = tmeasure.getTime() * SoundIdModel.getSoundSpeed(temperature);
                // cria um sensor com a informacao
	    	    sensor    = new SensorPosition(latitude,longitude,ratio);
	    	    // adiciona a informacao no calculo da posicao
	    	    area.addSensorDetection(sensor, ratio);	        	
	        }
		}
		// calcula o centroide da posicao estimada
		centroide = area.getCentroide();
		position = new GeoPosition(centroide.getLatitude(),centroide.getLongitude());
		ratio    = centroide.getRatio();

	}

	public GeoPosition getPosition() {
		return position;
	}

	public void setPosition(GeoPosition position) {
		this.position = position;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}


}
