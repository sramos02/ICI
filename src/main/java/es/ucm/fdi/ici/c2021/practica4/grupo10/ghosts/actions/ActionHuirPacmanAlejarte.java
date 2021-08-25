package es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts.actions;

import pacman.game.Game;
import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ActionHuirPacmanAlejarte implements Action{
	GHOST ghost;
	
	public ActionHuirPacmanAlejarte(GHOST g) {
		this.ghost = g;
	}

	@Override
	public MOVE execute(Game game) {
		// TODO Valorar si hay tiempo para implementar estrategia de huida
		return game.getApproximateNextMoveAwayFromTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				game.getPacmanCurrentNodeIndex(), 
				game.getGhostLastMoveMade(ghost),
				DM.PATH);
	}
}