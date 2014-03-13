package br.ufc.location.test;

import java.util.ArrayList;

import br.ufc.servidor.player.Player;

public class GameStateTest {

	private static GameStateTest instance = null;

	public static GameStateTest getInstance() {
		if (instance == null)
			instance = new GameStateTest();

		return instance;
	}

	private ArrayList<Player> players = null;

	//variaveis para contar o numero de jogadores de cada time de forma a distribuir
	//uniformemente os jogadores com preferencia para cangaceiros
	private int cangaceirosContador = 0;
	private int jaguncosContador = 0;

	public GameStateTest() {
		super();
		players = new ArrayList<Player>();
	}

	public void connectPlayer(Player player) {
		players.add(player);
	}

	public void disconnectPlayer(Player player) {
		players.remove(player);
	}
	
	public String disconnectPlayer(String name) {
		if(players != null && players.size() > 0)
		{
			for (Player player : players) {
				if(player.getNome().equals(name))
				{
					players.remove(player);
					return "Player: " + player.getNome() + " disconnected.";
				}				
			}			
		}
		
		return "Player was not connected";
	}

	public void updateOrConnectPlayer(Player player) {
		boolean updated = false;
		if (players != null && players.size() > 0) {
			for (Player p : players) {
				if (player.getNome().equals(p.getNome())) {
					p.setLatitude(player.getLatitude());
					p.setLongitude(player.getLongitude());					
					updated = true;
				}
			}
		}
		
		//Tipo: 1 cangaceiro; Tipo: 2 jagunco
		//Com a evolução da aplicação, haverá outra variavel para definir a "classe" do jogador: 
		//doutô, pexero, etc
		
		if (!updated) {
			if(jaguncosContador >= cangaceirosContador)
			{
				player.setType(1);
				cangaceirosContador++;
			}
			else
			{
				player.setType(2);
				jaguncosContador++;
			}
			
			players.add(player);
		}
	}

	public ArrayList<Player> getPlayerList() {
		return players;
	}

	public ArrayList<Player> getPlayerListByType(int tipo) {
		ArrayList<Player> playersByTipo = new ArrayList<Player>();

		if (players != null && players.size() > 0) {
			for (Player player : players) {
				if (player.getType() == tipo) {
					playersByTipo.add(player);
				}
			}
		}

		return playersByTipo;
	}
}
