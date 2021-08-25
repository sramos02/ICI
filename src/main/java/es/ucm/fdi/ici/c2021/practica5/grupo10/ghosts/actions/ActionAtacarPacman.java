package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions;


import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionAtacarPacman implements Action{

	public MOVE execute(Game game, GHOST ghost) {
		MOVE m = null;
		int pacpos = game.getPacmanCurrentNodeIndex();
		int ghostpos = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		
		m = game.getApproximateNextMoveTowardsTarget(ghostpos, pacpos, lastmove, DM.PATH);
		
		return m;
	}


	@Override
	public String getActionId() {
		return "Atacar msPacman";
	}


	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
}