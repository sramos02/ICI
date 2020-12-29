package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions.ActionAtacarPacman;
import es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions.ActionHuirPacmanAcercarte;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import pacman.game.Constants.GHOST;

public class GhostActionSelector implements ActionSelector {

	private final Double RUN_AWAY_LIMIT = 12.0; //TODO Hay que mirar bien este
	private final Double EAT_LIMIT = 10.0;
	GHOST g;
	
	GhostActionSelector(GHOST g){
		this.g = g;
	}
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double runAway = fuzzyOutput.get("runAway");
		Double attack  = fuzzyOutput.get("attack");
		
		System.out.println("Attack: " + attack);
		System.out.println("Run: " + runAway);
		
		
		if(attack > EAT_LIMIT)
			return new ActionAtacarPacman(g);
		
		if (g == GHOST.BLINKY) System.out.println(g.name() + " HUIR");
		if (runAway > RUN_AWAY_LIMIT) return new ActionHuirPacmanAcercarte(g);
		
		return new ActionHuirPacmanAcercarte(g);
	}

}
