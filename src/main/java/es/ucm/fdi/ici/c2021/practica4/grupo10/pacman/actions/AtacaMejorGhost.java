package es.ucm.fdi.ici.c2021.practica4.grupo10.pacman.actions;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants;
import pacman.game.Game;

public class AtacaMejorGhost implements Action{
	
	@Override
	public MOVE execute(Game game) {
		int aux = Integer.MAX_VALUE;
		GHOST closestGhost = null;
		
		for(GHOST ghost : GHOST.values()) {
			if(game.getGhostCurrentNodeIndex(ghost) != -1 && game.isGhostEdible(ghost)) {
				int ghostDist =  game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
				if(ghostDist < aux) {
					aux = ghostDist;
					closestGhost = ghost;
				}
			}
		}
		
		//Comprueba que quede algún fantasma edible, por si entra por error
		if(closestGhost != null)
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), game.getPacmanLastMoveMade(), Constants.DM.PATH);
		return MOVE.NEUTRAL;
	}

}