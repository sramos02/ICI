package es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts.actions.ActionAtacarPacman;
import es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts.actions.ActionBloquearPP;
import es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts.actions.ActionHuirPacmanAcercarte;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import pacman.game.Constants.GHOST;

public class GhostActionSelector implements ActionSelector {

	private final Double NERVIOUS = 12.0; 
	private final Double EMERGENCY = 24.0;
	private final Double EAT_LIMIT = 10.0;
	private final Double BLOCK_LIMIT = 10.0;
	
	GHOST g;
	
	GhostActionSelector(GHOST g){
		this.g = g;
	}
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double runAway = fuzzyOutput.get("runAway");
		Double attack  = fuzzyOutput.get("attack");
		Double block = fuzzyOutput.get("blockPP");
		
		if(runAway > EMERGENCY) 
			return new ActionHuirPacmanAcercarte(g);
				
		if(block > BLOCK_LIMIT) 
			return new ActionBloquearPP(g);
		
		if(attack > EAT_LIMIT) 
			return new ActionAtacarPacman(g);
		
		if (runAway > NERVIOUS) 
			return new ActionHuirPacmanAcercarte(g);
	
		return new ActionHuirPacmanAcercarte(g);
	}

}
