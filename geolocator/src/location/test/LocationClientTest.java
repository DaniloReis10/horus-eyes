package br.ufc.location.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.ufc.location.geoengine.DevicesPositionControl;
import br.ufc.location.geoengine.GeoPosition;
import br.ufc.servidor.artefatos.Barricada;
import br.ufc.servidor.artefatos.Mina;
import br.ufc.servidor.player.Player;
import br.ufc.util.CommandUtil;
import br.ufc.util.Conexao;
import br.ufc.util.EntityParser;
import br.ufc.util.XMLParser;

public class LocationClientTest {
	static String comando;
	static String [] params;
	static Document doc;
	static NodeList nodes;
	static String response;
	
	public static void main(String[] args) throws IOException {
		//testMovimentacao();		
		//testGameState();
		
		//testRegister();
		testUpdatePosition();
		//testDevicesList();
		//testDisconnect();
		//testCriarMina();
		//testCriarBarricada();
		testDevicesList();
		//testCalculateDistance();
	}

	
	/**
	 * Testa respectivamente a largura e comprimento do bloco do GREat
	 * Foi usado esse site para comparação dos resultados:
	 * http://www.sunearthtools.com/dp/tools/distance.php?lang=pt 
	 * Obs: passou no teste
	 */
	private static void testCalculateDistance() {		
		GeoPosition pos1 = new GeoPosition(new Date(), -3.74641, -38.57812);
		GeoPosition pos2 = new GeoPosition(new Date(), -3.74669 ,-38.57803);
		
		double distance1 = DevicesPositionControl.calculateDistance(pos1, pos2);
		System.out.println("Distancia deve ser 32.7 m. Obtido: " + distance1 + " m");
		
		pos1 = new GeoPosition(new Date(), -3.74671, -38.57807);
		pos2 = new GeoPosition(new Date(), -3.74675, -38.57818);
		
		double distance2 = DevicesPositionControl.calculateDistance(pos1, pos2);
		System.out.println("Distancia deve ser 13 m. Obtido: " + distance2 + " m");
	}

	private static void testCriarBarricada() {
		params = new String [10];
		
		params[0] ="1";		
		params[1] ="0.0000";
		params[2] ="0.0000";
		
		comando = CommandUtil.makeCommand("criarbarricada", params, 3);

		String response = getServerResponse(comando);
	    System.out.println("barricada 1 id = " + response);
		
		Document doc = XMLParser.createXMLDocument(response);
        NodeList nodes = doc.getElementsByTagName("id");
        Element id = (Element)nodes.item(0);
        
        if (id != null) {
			System.out.println("id barricada: " + id.getTextContent());
        }
	}

	private static void testCriarMina() {
		params = new String [10];
		
		params[0] ="2";		
		params[1] ="0";
		params[2] ="0";
		params[3] = "20";
		
		comando = CommandUtil.makeCommand("criarmina", params, 4);

		String response = getServerResponse(comando);
	    System.out.println("mina 1 id = " + response);
		
		Document doc = XMLParser.createXMLDocument(response);
        NodeList nodes = doc.getElementsByTagName("id");
        Element id = (Element)nodes.item(0);
        
        if (id != null) {
			System.out.println("id mina: " + id.getTextContent());
        }
	}

	private static void testDevicesList() {
		comando = CommandUtil.makeCommand("deviceslist", params, 0);
		
		response = getServerResponse(comando);
        System.out.println("List of devices: \n" + response);
        
        CommandUtil.setDevices("player",response,  new Player());
        CommandUtil.setDevices("mina", response, new Mina());
        CommandUtil.setDevices("barricada",response,  new Barricada());   
	}

	private static void testUpdatePosition() {
		params = new String [10];
		
		/*params[0] ="Danilo";
		params[1] ="1";
		params[2] ="1";
		params[3] ="-3.717381";
		params[4] ="38.539906";
		
		comando = CommandUtil.makeCommand("register", params, 5);
		
		doc = XMLParser.createXMLDocument(getServerResponse(comando));
        nodes = doc.getElementsByTagName("id");
        Element node = (Element)nodes.item(0);
        
        if (node != null) {
			System.out.println("id 1: " + node.getTextContent());
        }
		
		params[0] ="Fabio";
		params[1] ="1";
		params[2] ="1";
        params[3] ="-3.717123";
        params[4] ="38.540081";
        
        comando = CommandUtil.makeCommand("register", params, 5);
        
        response = getServerResponse(comando);
        
        doc = XMLParser.createXMLDocument(response);
        nodes = doc.getElementsByTagName("id");
        node = (Element)nodes.item(0);
        
        if (node != null) {
			System.out.println("id 2: " + node.getTextContent());
        }*/
        
		params[0] = "3";//node.getTextContent();
		params[1] = "-3.74641";
		params[2] = "-38.57812";//"38.539906";
		
		comando = CommandUtil.makeCommand("updateposition", params, 5);
		
		response = getServerResponse(comando);
        System.out.println("List of devices: \n" + response);
        
       /* CommandUtil.setDevices("player", response, new Player());
        CommandUtil.setDevices("mina", response, new Mina());
        CommandUtil.setDevices("barricada", response, new Barricada()); 	*/	
	}

	//Registro testado com sucesso com o servidor rodando!
	private static void testRegister() {
		params = new String [10];
		
		params[0] ="Fulano";
		params[1] ="1";
		params[2] ="1";
		params[3] ="-3.74671";
		params[4] ="-38.57807";
		
		comando = CommandUtil.makeCommand("register", params, 5);

		String response = getServerResponse(comando);
	    System.out.println("player 1 id = " + response);
		
		Document doc = XMLParser.createXMLDocument(response);
        NodeList nodes = doc.getElementsByTagName("id");
        Element id = (Element)nodes.item(0);
        
        if (id != null) {
			System.out.println("id 1: " + id.getTextContent());
        }
        
		params[0] ="Beltrano";
		params[1] ="2";
		params[2] ="2";
        params[3] ="-3.74669";
        params[4] ="-38.57803";
        
        comando = CommandUtil.makeCommand("register", params, 5);
        
        response = getServerResponse(comando);
        System.out.println("player 2 id = " + response);
        
        doc = XMLParser.createXMLDocument(response);
        nodes = doc.getElementsByTagName("id");
        id = (Element)nodes.item(0);
        
        if (id != null) {
			System.out.println("id 2: " + id.getTextContent());
        }
	}	

	private static void testGameState() {
		comando = "<gamestate>, " +	"" + ",<gamestate>";		
		ArrayList<Player> playerList = EntityParser.parseMessageToPlayerList(getServerResponse(comando));		
		for (Player player : playerList) {
			System.out.println(player.toString());
		}
	}

	private static void testMovimentacao() {		
		comando = "<movimentacao>," + 
		"Joao" + "," + "1" + "," + "-3.746573" + "," + "-38.577791" + 
		  ",<movimentacao>";
		System.out.println("Movimentacao = " + getServerResponse(comando));
	}
	
	private static void testDisconnect() {		
		comando = "<disconnect>," +	"Joao" + ",<disconnect>";
		System.out.println("Disconnected: " + getServerResponse(comando));
	}	

	private static String getServerResponse(String comando) {
		int porta = 8080;
		String ip = "localhost";
		try {
			return Conexao.executaComando(comando, ip, porta);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error connecting server";
	}
}
