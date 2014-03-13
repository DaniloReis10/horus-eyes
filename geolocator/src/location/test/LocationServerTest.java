package br.ufc.location.test;

import myserver.kernel.ServerService;

public class LocationServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerService server = new ServerService(8080, 1024, false);
		server.start();
		System.out.println("Inicializando servidor do jogo");
	}

}