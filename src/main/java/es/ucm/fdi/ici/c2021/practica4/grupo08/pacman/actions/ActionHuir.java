package es.ucm.fdi.ici.c2021.practica4.grupo08.pacman.actions;

import pacman.game.Game;

import java.util.ArrayList;

import pacman.game.Constants;
import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ActionHuir implements Action{

	int[] bestPath = null;
	int score = Integer.MAX_VALUE;
	
	public MOVE execute(Game game) {
		ArrayList<GHOST> chasingGhosts = new ArrayList<GHOST>();

		for(GHOST ghost : GHOST.values()) {
			if(game.getGhostLairTime(ghost) > 0) continue;
			if(game.getGhostEdibleTime(ghost) < 5) {
				chasingGhosts.add(ghost);
			}
		}

		int aux = Integer.MAX_VALUE;
		GHOST closestGhost = null;

		for(GHOST ghost : chasingGhosts) {
			if(!game.isGhostEdible(ghost)) {
				int ghostDist =  game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
				if(ghostDist < aux) {
					aux = ghostDist;
					closestGhost = ghost;
				}
			}
		}
		return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), game.getPacmanLastMoveMade(), Constants.DM.PATH);

	}
}