package es.ucm.fdi.ici.c2021.demofuzzy;

import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzy extends PacmanController {

	private static final String RULES_PATH = "bin/es/ucm/fdi/ici/practica4/demofuzzy/";
	FuzzyEngine fuzzyEngine;
	MsPacManInput input ;
	
	public MsPacManFuzzy()
	{
		ActionSelector actionSelector = new MsPacManActionSelector();
		 input = new MsPacManInput();
		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		input.parseInput(game);
		return fuzzyEngine.run(input.getFuzzyValues(),game);
	}

}
