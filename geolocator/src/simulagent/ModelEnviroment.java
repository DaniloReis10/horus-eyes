package simulagent;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import location.geoengine.GeoPosition;

import trilaceration.ScaleConverter;
/**
 * Classe responsável por criar o modelo do ambiente dos agentes sensores ,fixos e moveis.
 * Nesta classe sao criados os agentes e implementados os metodos de animacao dos mesmos.
 * Nesta classe tambem é criada uma escala do ambiente para conversao entre latitude e longitude
 * e linhas e colunas na imagem. esta classe segue o padrão de projeto singleton, sendo tambem um Model
 * @author DaniloReis
 *
 */
public class ModelEnviroment implements IActionTick{

	private static ModelEnviroment  instance;// Instancia do modelo 
	PathFactory                      factory;// Fabrica de agentes
	ArrayList<WifiAgent>              agents;// lista de agentes criados
	public static final int STATIC_AGENT = 0;
	public static final int MOBILE_AGENT = 1;
	

	public static ModelEnviroment getInstance(){
		
		if( instance == null){
			instance = new ModelEnviroment();
		}
		return instance;
	}

	
	public ModelEnviroment() {
		super();
	    factory = new PathFactory();
	    agents  = new ArrayList<WifiAgent> ();
	}
	
	public void createMobileAgents(int n,int nPositions){
		int              i;
		AgentPath     path;
		WifiAgent    agent;
		
		for( i = 0; i < n; i++){
			path = factory.create(ScaleConverter.width, ScaleConverter.height, nPositions);
			agent = new WifiAgent();
			agent.setPath(path);
			agent.setType(ModelEnviroment.MOBILE_AGENT);
			agents.add(agent);
		}
	}
	public void createStaticAgents(int n){
		int                     i;
		WifiAgent           agent;
		GeoPosition      position;
		
		double latitude,longitude;

		latitude  = ScaleConverter.latIni + Math.random()*(ScaleConverter.latEnd - ScaleConverter.latIni);
		longitude = ScaleConverter.longIni + Math.random()*(ScaleConverter.longEnd - ScaleConverter.longIni);
		position  = new GeoPosition(latitude,longitude);
		
		for( i = 0; i < n; i++){
			agent = new WifiAgent();
			agent.setType(ModelEnviroment.STATIC_AGENT);
			agent.setCurrentPosition(position);
			agents.add(agent);
		}
	}
	
	public void setScaleEnviroment(float latitude0,float longitude0,float latitude1,float longitude1,int width,int height){
		ScaleConverter.width   = width;
		ScaleConverter.height  = height;
		ScaleConverter.latIni  = latitude0;
		ScaleConverter.longIni = longitude0;
		ScaleConverter.latEnd  = latitude1;
		ScaleConverter.longEnd = longitude1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}


	@Override
	public void drawImage(BufferedImage image, Color color) {
		WifiAgent              agent;
		Iterator<WifiAgent> iterator;
		
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.drawImage(image, color);
		}
		
	}


	@Override
	public void moveImage() {
		WifiAgent              agent;
		Iterator<WifiAgent> iterator;
		
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.moveImage();
		}
				
		
	}


	@Override
	public void clearImage(BufferedImage image) {
		WifiAgent              agent;
		Iterator<WifiAgent> iterator;
		
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.clearImage(image);
		}
		
	}

}
