package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.Input;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostInput implements Input {

	double pacmanDistance = 108;
	double ghostConfidence = 100;
	double ghostNPPdistance = 84.0;
	double edibleTime = 0.0;
	
	double msPacmanNPPdistance = 66.0;
	GHOST g;
	
	GhostInput(GHOST g){
		this.g = g;
	}
	
	@Override
	public void parseInput(Game game) {
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		int ghostPos = game.getGhostCurrentNodeIndex(g);
		
		if(pacmanPos != -1) { //No est� en un nodo
			int pacmanNpp = getNPP(pacmanPos, game); //Calculamos la NPP de pacman
			msPacmanNPPdistance = game.getDistance(pacmanPos, pacmanNpp, DM.PATH);	

			ghostNPPdistance =  game.getDistance(ghostPos, pacmanNpp, DM.PATH);	
			pacmanDistance = game.getDistance(pacmanPos, ghostPos, DM.PATH);
			ghostConfidence = 100;

			//Esto pretende devolver -1 cuando el fantasma est� pegado a la espalda de pacman
			//if(pacmanDistance < 5 && game.getPacmanLastMoveMade() == game.getGhostLastMoveMade(g)) pacmanDistance = -1; 
			
			if(game.getGhostLairTime(g) == 0 && game.isGhostEdible(g)) 
				edibleTime = game.getGhostEdibleTime(g);		
		}
		else if (ghostConfidence > 0) ghostConfidence-=.1;

	
		//		System.out.println(g.name() +"distance: " + pacmanDistance);
		//		System.out.println(g.name() + "confidence: " + ghostConfidence);
		//		if(g == GHOST.SUE) System.out.println();
	}



	private int getNPP(int from, Game game) {
		double bestDistance = Integer.MAX_VALUE;
		int ret = -1;
		
		int[] pps = game.getPowerPillIndices();
		for(int i = 0; i < pps.length; i++) {
			//if (game.isNodeObservable(pillLocation)(pps[i])) { No es observable...
			double actDistance = game.getDistance(from, pps[i], DM.PATH);	
			if(actDistance < bestDistance) {
				bestDistance = actDistance;
				ret = pps[i];
			}

		}
		
		return ret;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		vars.put("PacmanNPPdistance",msPacmanNPPdistance);
		vars.put("PacmanDistance", pacmanDistance);
		vars.put("GhostConfidence", ghostConfidence);	
		vars.put("GhostNPPdistance", ghostNPPdistance);
		vars.put("GhostEdibleTime", edibleTime);

		return vars;
	}
}
