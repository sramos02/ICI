package es.ucm.fdi.ici.c2021.practica4.grupo10.pacman;


import java.util.ArrayList;
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
	
	ArrayList<Boolean> isPPeaten = new ArrayList<Boolean>(); 
	double msPacmanNPPdistance = 66.0;
	
	@Override
	public void parseInput(Game game) {
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		configurePPeaten(game);
		
		
		if (game.wasPacManEaten()) { //Reset all states
			for(int i = 0; i < GHOST.values().length; i++) {
				ghostDistance[i] = 108;
				ghostNPPdistance[i] = 84;
				ghostEdibleTime[i] = 0;
				pacmanConfidence[i] = 100;
			}
		}
			
		//Configure PPs
		for(int i = 0; i < game.getPowerPillIndices().length; i++) {
			if(pacmanPos == game.getPowerPillIndices()[i] && !isPPeaten.get(i)) {
				for(GHOST g: GHOST.values()) {
					ghostEdibleTime[g.ordinal()] = 200;
				}
				
				isPPeaten.set(i, true);
			}
		}	
			
		int pacmanNpp = getNPP(pacmanPos, game);
		if(pacmanNpp != -1) msPacmanNPPdistance = game.getDistance(pacmanPos, pacmanNpp, DM.PATH);
		else msPacmanNPPdistance = Double.MAX_VALUE;
		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();

			if (game.wasGhostEaten(g)) { //Reset ghost state
					ghostDistance[index] = game.getDistance(pacmanPos, game.getGhostInitialNodeIndex(), DM.PATH);
					ghostNPPdistance[index] = 84;
					ghostEdibleTime[index] = 0;
					pacmanConfidence[index] = 100;
			}
			
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
	

	private void configurePPeaten(Game game) {
		if(isPPeaten.size() < game.getPowerPillIndices().length) {
			for(int i  = 0; i < game.getPowerPillIndices().length; i++) {
				isPPeaten.add(false);
			}
		}
	}

	private int getNPP(int from, Game game) {
		double bestDistance = Integer.MAX_VALUE;
		int ret = -1;

		for(int i = 0; i < isPPeaten.size(); i++) {
			double actDistance = game.getDistance(from, game.getPowerPillIndices()[i], DM.PATH);	
			if(!isPPeaten.get(i) && actDistance < bestDistance) {
				bestDistance = actDistance;
				ret = game.getPowerPillIndices()[i];
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
