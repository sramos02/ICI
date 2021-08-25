package es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.actions;

import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ActionComerPP implements Action{

	public MOVE execute(Game game) { 
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPill(game), Constants.DM.PATH);
	}

	public int getNearestPill(Game game) {
		int ret = 0;
		double min = Integer.MAX_VALUE;
		for (int indice : game.getActivePillsIndices()) {
			double distance = game.getDistance(
					game.getPacmanCurrentNodeIndex(),
					indice,
					Constants.DM.PATH);
			if (distance < min) {
				min = distance;
				ret = indice;
			}
		}
		return ret;
	}

	@Override
	public String getActionId() {
		return "Comer PowerPill";
	}

	@Override
	public MOVE execute(Game game, GHOST g) {
		// TODO Auto-generated method stub
		return null;
	}
}

