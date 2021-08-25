package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.*;
import es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.CBRengine.GhostsCBRengine;
import es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.CBRengine.GhostsStoreManager;
import es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions.*;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	Input input;
	GhostsCBRengine cbrEngine;
	ActionSelector actionSelector;
	GhostsStoreManager storageManager;
	
	final static String FILE_PATH = "bin/es/ucm/fdi/ici/c2021/practica5/grupo10/data/%s.csv"; //Cuidado!! poner el grupo aqui
	
	public Ghosts()
	{
		this.input = new GhostsInput();
		
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ActionAtacarPacman());
		actions.add(new ActionAtacarPrimeraInterseccionPacman());
		actions.add(new ActionBloquearPP());
		actions.add(new ActionHuirPacmanAcercarte());		
		actions.add(new ActionHuirPacmanAlejarte());
		
		this.actionSelector = new ActionSelector(actions);

		this.storageManager = new GhostsStoreManager();
		
		cbrEngine = new GhostsCBRengine(actionSelector, storageManager);
	}
	
	@Override
	public void preCompute(String opponent) {
		cbrEngine.setCaseBaseFile(String.format(FILE_PATH, opponent));
		try {
			cbrEngine.configure();
			cbrEngine.preCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void postCompute() {
		try {
			cbrEngine.postCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		//This implementation only computes a new action when MsPacMan is in a junction. 
		//This is relevant for the case storage policy
		EnumMap<GHOST, MOVE> ret = new EnumMap<>(GHOST.class);
		for(GHOST g: GHOST.values()) {
			
			if(!game.isJunction(game.getGhostCurrentNodeIndex(g)) || game.getGhostLairTime(g) != 0)
				ret.put(g, MOVE.NEUTRAL);
			else {
				try {
					input.parseInput(game);
					actionSelector.setGame(game);
					storageManager.setGame(game);
					cbrEngine.setGhost(g);
					cbrEngine.cycle(input.getQuery());
					
					Action action = cbrEngine.getSolution();
					ret.put(g, action.execute(game, g));
				} catch (Exception e) {
					e.printStackTrace();
					ret.put(g, MOVE.NEUTRAL);
				}
			}
		}
		
		return ret;
	}
}
