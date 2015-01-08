package br.com.fujitec.simulagent.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.fujitec.location.facade.IGeoPosition;
import br.com.fujitec.location.geoengine.GeoPosition;
import br.com.fujitec.simulagent.ui.SimulationController;
import trilaceration.ScaleConverter;

/**
 * Classe respons??vel pelo armazenamento das posicoes que o agente percorreu em
 * um dia . Considera-se o intervalo de analise de 1 minuto.
 * 
 * @author DaniloReis
 * 
 */
public class Path {

    List<PositionPath> dataPath;// Array com as posicoes do agente no dia
    private ScaleConverter scale= SimulationController.getScaleInstance();

    /**
     * Construtor da classe geral
     */
    public Path() {
        super();
        dataPath = new ArrayList<PositionPath>();
    }

    /**
     * Construtor da classe com os parametros
     * 
     * @param array
     */
    public Path(List<PositionPath> array) {
        super();
        dataPath = array;
    }

    /**
     * Adiciona uma nova posicao na lista de posicoes
     * 
     * @param posicao
     *            dentro da rota
     */
    public void addPoint(PositionPath p) {
        if (p != null)
            dataPath.add(p);
    }

    /**
     * Limpa a lista de posicoes
     */
    public void clear() {
        dataPath.clear();
    }

    /**
     * Retorna o iterator da lista de posicoes
     * 
     * @return iterator da lista de paradas da rota
     */
    public Iterator<PositionPath> iterator() {
        return dataPath.iterator();
    }

    /**
     * Pega a posicao do agente no tempo time no caso em que o agente esta
     * parado em uma posicao
     * 
     * @param time
     *            tempo decorrido em minutos dentro de um dia desde da 0:00 Por
     *            exemplo 1:15 = 1x60 + 15 = 75
     * @return a interface da posicao no momento
     */
    private PositionPath getStaticPosition(int time) {
        Iterator<PositionPath> iterator;
        PositionPath position;

        iterator = dataPath.iterator();
        while (iterator.hasNext()) {
            position = iterator.next();
            // encontrou um local que o ponto estava parado
            if ((position.getArriveTime() <= time) && (position.getLeaveTime() >= time)) {
                return position;
            }
        }
        // o ponto deve estar em movimento
        return null;
    }

    /**
     * Retorna a posicao do agente no tempo especificado. No caso em que o mesmo
     * esteja se deslocando ?? estimada uma posicao baseada no tempo de transicao
     * entre duas paradas.
     * 
     * @param time
     *            tempo em minutos desde 0:00
     * @return interface da posicao estimada
     */
    private IGeoPosition getEstimatedPosition(int time) {
        PositionPath posiPrev;
        PositionPath posiNext;
        int i, n;
        int xc, yc, x0, y0;
        int deltax, deltay;
        float tpercent;
        GeoPosition pos;

        // neste caso devemos encontra entre quais pontos da rota o agente
        // dever?? estar
        n = dataPath.size();
        if (n < 2) {
            return null;
        }
        // Calcula a posicao intermediaria
        for (i = 0; i < n - 1; i++) {
            // Pega duas posicoes sequenciais na rota do agente
            posiPrev = dataPath.get(i);
            // posiPrev.print();
            posiNext = dataPath.get(i + 1);
            // posiNext.print();
            // verifica se no tempo especificado a agente esta entre estes
            // pontos
            if ((posiPrev.getLeaveTime() < time) && (posiNext.getArriveTime() > time)) {
                deltax = (scale.convertToX(posiNext) - scale.convertToX(posiPrev));
                deltay = (scale.convertToY(posiNext) - scale.convertToY(posiPrev));
                x0 = scale.convertToX(posiPrev);
                y0 = scale.convertToY(posiPrev);
                // calcula a posicao corrente
                tpercent = (float) (time - posiPrev.getLeaveTime())
                        / (float) (posiNext.getArriveTime() - posiPrev.getLeaveTime());
                xc = x0 + (int) (deltax * tpercent);
                yc = y0 + (int) (deltay * tpercent);
                // Cria a nova posicao
                pos = new GeoPosition(scale.convertToLatitude(yc),scale.convertToLongitude(xc));

                return pos;
            }
        }
        return null;

    }

    /**
     * Retorna a posicao do agente dentro de um tempo especificado.
     * 
     * @param time
     *            tempo em minutos desde 0:00
     * @return interface da posicao
     */
    public IGeoPosition getPositionAtTime(int time) {
        PositionPath position;

        position = getStaticPosition(time);
        // caso o no tempo especificado o agente nao esteja parado calcula a
        // posicao estimada senao volta a posicao estatica
        return (position == null) ? getEstimatedPosition(time) : position;
    }

    /**
     * <p>
     * Returns agent's first path
     * </p>
     * 
     * 
     * @author tiagoportela <tiagoporteladesouza@gmail.com>
     * @param
     * @return
     */
    public IGeoPosition getFirstPosition() {
        return this.getPositionAtTime(0);
    }

    /**
     * Retorna o array com as posicoes do agente durante o dia
     * 
     * @return ArrayList com as posicoes da rota
     */
    public List<PositionPath> getDataPath() {
        return dataPath;
    }

    /**
     * Seta o array de rota do dia
     * 
     * @param dataPath
     */
    public void setDataPath(List<PositionPath> dataPath) {
        this.dataPath = dataPath;
    }
    
    public void printPos(){
    	IGeoPosition pos;
    	PositionPath pp;
    	double xx,yy;
    	for(int i=0; i < dataPath.size();i++){
    		pp = dataPath.get(i);
    		pp.print();
    	}
	    yy = this.getPositionAtTime(0).getLatitude();
		xx = this.getPositionAtTime(0).getLongitude();
    	for( int i=0 ; i < 24*60*60L;i++){
    		pos = this.getPositionAtTime(i);
    		if((pos.getLatitude()!= yy)||(pos.getLongitude()!= xx)){
    	  		System.out.printf("%d  lat = %f  long = %f\n",i,pos.getLatitude(),pos.getLongitude());
    		}
    	    yy = pos.getLatitude();
    		xx = pos.getLongitude();
    	}
    }
    public static void main(String[] args){
 /*   	PathFactory factory;
    	Path    path;
    	
    	ScaleConverter.latIni = 0.0;
        ScaleConverter.longIni = 0.0;
        ScaleConverter.latEnd = 0.01;
        ScaleConverter.longEnd = 0.01;
        ScaleConverter.width  = 980;
        ScaleConverter.height = 480;
    	factory = new PathFactory();
    	path = factory.createPath(ScaleConverter.width, ScaleConverter.height, 10, Agent.class);
    	path.printPos();
   */ 	
    }
}
