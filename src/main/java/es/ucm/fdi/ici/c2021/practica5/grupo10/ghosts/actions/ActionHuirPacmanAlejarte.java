package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions;

import pacman.game.Game;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ActionHuirPacmanAlejarte implements Action{
	
	public MOVE execute(Game game, GHOST ghost) {
		// TODO Valorar si hay tiempo para implementar estrategia de huida
		return game.getApproximateNextMoveAwayFromTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				game.getPacmanCurrentNodeIndex(), 
				game.getGhostLastMoveMade(ghost),
				DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Huir alejarte";
	}

	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
}