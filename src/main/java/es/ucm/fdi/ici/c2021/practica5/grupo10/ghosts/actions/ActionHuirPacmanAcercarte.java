package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions;


import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionHuirPacmanAcercarte implements Action{

	public MOVE execute(Game game, GHOST ghost) {
		return game.getApproximateNextMoveTowardsTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				game.getPacmanCurrentNodeIndex(), 
				game.getGhostLastMoveMade(ghost),
				DM.PATH);
	}


	@Override
	public String getActionId() {
		return "Huir acercarte";
	}


	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
}