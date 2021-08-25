package es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.POGhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostFuzzy extends POGhostController {

	private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/c2021/practica4/grupo10/ghosts/ghosts.fcl";
	FuzzyEngine[] fuzzyEngine = {null, null, null, null};
	GhostInput[] input = {null, null, null, null};
	
	public GhostFuzzy()
	{
		 for(GHOST ghost : GHOST.values()) {
			int index = ghost.ordinal();
			input[index] = new GhostInput(ghost);
			ActionSelector actionSelector = new GhostActionSelector(ghost);

			fuzzyEngine[index] = new FuzzyEngine(ghost.name(), RULES_PATH, "GhostFuzzy", actionSelector);
			
//			ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver(ghost.name(),"GhostRules");
//			fuzzyEngine[index].addObserver(observer);
		 }

	}

	@Override
	public MOVE getMove(GHOST ghost, Game game, long timeDue) {
		int index = ghost.ordinal();
		input[index].parseInput(game);
		
		if (game.doesGhostRequireAction(ghost)) {
			HashMap<String, Double> fuzzyValues = input[index].getFuzzyValues();
			return fuzzyEngine[index].run(fuzzyValues, game);
		}
		return MOVE.NEUTRAL;
	}

}
