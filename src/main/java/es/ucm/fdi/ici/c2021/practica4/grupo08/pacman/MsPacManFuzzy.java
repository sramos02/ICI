package es.ucm.fdi.ici.c2021.practica4.grupo08.pacman;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzy extends PacmanController {

	private static final String RULES_PATH = "src/main/java/es/ucm/fdi/ici/c2021/practica4/grupo08/pacman/mspacman.fcl";
	FuzzyEngine fuzzyEngine;
	MsPacmanInput input ;
	
	public MsPacManFuzzy()
	{
		ActionSelector actionSelector = new MsPacManActionSelector();
		 input = new MsPacmanInput();
		 
		//ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacman","MsPacmanRules");
		fuzzyEngine = new FuzzyEngine("MsPacman", RULES_PATH, "MsPacmanFuzzy", actionSelector);
		//fuzzyEngine.addObserver(observer);
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {			
		input.parseInput(game);
		HashMap<String, Double> values = input.getFuzzyValues();
		return fuzzyEngine.run(values, game);
	}

}
