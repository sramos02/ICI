package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions.ActionAtacarPacman;
import es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions.ActionHuirPacmanAcercarte;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import pacman.game.Constants.GHOST;

public class GhostActionSelector implements ActionSelector {

	private final Double NERVIOUS = 12.0; //TODO Hay que mirar bien este
	private final Double EMERGENCY = 24.0;
	private final Double EAT_LIMIT = 10.0;
	
	GHOST g;
	
	GhostActionSelector(GHOST g){
		this.g = g;
	}
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double runAway = fuzzyOutput.get("runAway");
		Double attack  = fuzzyOutput.get("attack");
		
//		System.out.println();
//		System.out.println("Attack: " + attack);
//		System.out.println("Run: " + runAway);
		
		if(runAway > EMERGENCY) {
			//System.out.println(g.name()+" RUN FOREST RUN");
			return new ActionHuirPacmanAcercarte(g);
		}
		
		if(attack > EAT_LIMIT) {
			//System.out.println(g.name()+" ATTACK");
			return new ActionAtacarPacman(g);
		}
		if (runAway > NERVIOUS) {
			//System.out.println(g.name()+" BE CAREFUL");
			return new ActionHuirPacmanAcercarte(g);
		}
		
		//System.out.println(g.name()+" GET CLOSER");
		return new ActionHuirPacmanAcercarte(g);
	}

}
