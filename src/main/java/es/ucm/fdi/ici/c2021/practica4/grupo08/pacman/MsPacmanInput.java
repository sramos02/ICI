package es.ucm.fdi.ici.c2021.practica4.grupo08.pacman;


import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.Input;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacmanInput implements Input {

	double[] ghostDistance = {108,108,108,108};
	double[] ghostNPPdistance = {84,84,84,84};
	double[] ghostEdibleTime = {0,0,0,0};
	double[] pacmanConfidence = {100,100,100,100};
	
	double msPacmanNPPdistance = 66.0;

	
	@Override
	public void parseInput(Game game) {
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		
		int pacmanNpp = getNPP(pacmanPos, game);
		msPacmanNPPdistance = game.getDistance(pacmanPos, pacmanNpp, DM.PATH);
		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int ghostPos = game.getGhostCurrentNodeIndex(g);

			if(ghostPos != -1) { //Posición invalida
				double distance = game.getDistance(game.getPacmanCurrentNodeIndex(), ghostPos, DM.PATH);
				if(distance != -1) { //Esta muerto
					pacmanConfidence[index] = 100;
					ghostDistance[index] = distance;
					ghostNPPdistance[index] = game.getDistance(ghostPos, pacmanNpp, DM.PATH); //Miramos la distancia de fantas a pp mas cerca a pacman
					if (game.isGhostEdible(g)) ghostEdibleTime[index] = game.getGhostEdibleTime(g); 
				}
			} 
			else { 
				if (pacmanConfidence[index] > 0) pacmanConfidence[index]-=.1;
				if (ghostEdibleTime[index] > 0) ghostEdibleTime[index]-=.1;
			}
		}
	}
	
	private int getNPP(int from, Game game) {
		double bestDistance = Integer.MAX_VALUE;
		int ret = -1;
		
		for(int index : game.getPowerPillIndices()) { //No se pueden ver las active, mejor esto que nada
			double actDistance = game.getDistance(from, index, DM.PATH);	
			if(actDistance < bestDistance) {
				bestDistance = actDistance;
				ret = index;
			}
		}

		return ret;
	}
	
	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
	
		vars.put("PacmanNPPdistance", msPacmanNPPdistance);
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   ghostDistance[g.ordinal()]);
			vars.put(g.name()+"confidence", pacmanConfidence[g.ordinal()]);	
			vars.put(g.name()+"EdibleTime", ghostEdibleTime[g.ordinal()]);			
			vars.put(g.name()+"NPPdistance", ghostNPPdistance[g.ordinal()]);			
		}
		return vars;
	}

}
