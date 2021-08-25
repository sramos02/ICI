package es.ucm.fdi.ici.c2021.practica5.grupo10.pacman;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.ActionSelector;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.CBRengine.MsPacManCBRengine;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.CBRengine.MsPacManStorageManager;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.actions.ActionComerPP;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.actions.ActionComerPill;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.actions.ActionHuir;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.actions.AtacaMejorGhost;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	MsPacManInput input;
	MsPacManCBRengine cbrEngine;
	ActionSelector actionSelector;
	MsPacManStorageManager storageManager;
	
	final static String FILE_PATH = "bin/es/ucm/fdi/ici/c2021/practica5/grupo10/data/%s.csv"; //Cuidado!! poner el grupo aqui
	
	public MsPacMan()
	{
		this.input = new MsPacManInput();
		
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ActionComerPill());
		actions.add(new ActionComerPP());
		actions.add(new ActionHuir());
		actions.add(new AtacaMejorGhost());		
		
		this.actionSelector = new ActionSelector(actions);

		this.storageManager = new MsPacManStorageManager();
		
		cbrEngine = new MsPacManCBRengine(actionSelector, storageManager);
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
	public MOVE getMove(Game game, long timeDue) {
		
		//This implementation only computes a new action when MsPacMan is in a junction. 
		//This is relevant for the case storage policy
		if(!game.isJunction(game.getPacmanCurrentNodeIndex()))
			return MOVE.NEUTRAL;
		
		
		try {
			input.parseInput(game);
			actionSelector.setGame(game);
			storageManager.setGame(game);
			cbrEngine.cycle(input.getQuery());
			Action action = cbrEngine.getSolution();
			return action.execute(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MOVE.NEUTRAL;
	}

}
