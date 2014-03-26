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
	ArrayList<RFAgent>                agents;// lista de agentes criados
	public static final int STATIC_AGENT = 0;
	public static final int MOBILE_AGENT = 1;
	

	/**
	 * Retorna a instancia do modelo (Padrão de projeto singleton)
	 * @return
	 */
	public static ModelEnviroment getInstance(){
		
		if( instance == null){
			instance = new ModelEnviroment();
		}
		return instance;
	}

	/**
	 * Construtor generico
	 */
	public ModelEnviroment() {
		super();
	    factory = new PathFactory();
	    agents  = new ArrayList<RFAgent> ();
	}
	/**
	 * Cria agentes moveis no ambiente
	 * @param n Numero de agentes a serem criados
	 * @param nPositions - numero de posicoes estaticas que o agente terá na sua rota diária. 
	 */
	public void createMobileAgents(int n,int nPositions){
		int              i;
		AgentPath     path;
		MobileAgent    agent;
		
		for( i = 0; i < n; i++){
			path = factory.create(ScaleConverter.width, ScaleConverter.height, nPositions);
			agent = new MobileAgent();
			agent.setPath(path);
			agent.setMobility(ModelEnviroment.MOBILE_AGENT);
			agent.setCurrentPosition(agent.getPath().getPositionAtTime(0));
			agents.add(agent);
		}
	}
	/**
	 * Cria agentes fixos no ambiente
	 * @param n numero de agentes a serem criados
	 */
	public void createStaticAgents(int n){
		int                     i;
		RFAgent             agent;
		GeoPosition      position;
		double latitude,longitude;

		
		for( i = 0; i < n; i++){
			agent = new RFAgent();
			latitude  = ScaleConverter.latIni + Math.random()*(ScaleConverter.latEnd - ScaleConverter.latIni);
			longitude = ScaleConverter.longIni + Math.random()*(ScaleConverter.longEnd - ScaleConverter.longIni);
			position  = new GeoPosition(latitude,longitude);
			agent.setCurrentPosition(position);
			agents.add(agent);
		}
	}
	/**
	 * Seta os parametros de escala do ambiente
	 * @param latitude0 latitude do canto superior esquerdo (0,0)
	 * @param longitude0 longitude do canto superior direito (0,0)
	 * @param latitude1 latitude do canto inferior direito (width,height)
	 * @param longitude1 longitude do canto inferior direito (width,height)
	 * @param width largura da imagem
	 * @param height altura da imagem
	 */
	public void setScaleEnviroment(double latitude0,double longitude0,double latitude1,double longitude1,int width,int height){
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


	/**
	 * Metodos da interface de animaçao do ambiente
	 */
	@Override
	public void drawImage(BufferedImage image, Color color) {
		RFAgent              agent;
		Iterator<RFAgent> iterator;
		
		// Percorre todos os agentes do ambiente desenhando cada um.
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.drawImage(image, color);
		}
		
	}


	@Override
	public void moveImage() {
		RFAgent              agent;
		Iterator<RFAgent> iterator;
		
		// Percorre todos os agentes do ambiente movendo no modelo cada um.
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.moveImage();
		}
				
		
	}


	@Override
	public void clearImage(BufferedImage image) {
		RFAgent              agent;
		Iterator<RFAgent> iterator;
		// Percorre todos os agentes do ambiente apagando cada um.
		iterator = agents.iterator();
		while(iterator.hasNext()){
			agent = iterator.next();
			agent.clearImage(image);
		}
		
	}

}
