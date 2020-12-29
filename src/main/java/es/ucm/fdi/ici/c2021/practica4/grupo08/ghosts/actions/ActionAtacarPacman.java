package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions;


import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionAtacarPacman implements Action{
	GHOST ghost;
	
	public ActionAtacarPacman(GHOST g) {
		this.ghost = g;
	}


	@Override
	public MOVE execute(Game game) {
		MOVE m = null;
		int pacpos = game.getPacmanCurrentNodeIndex();
		int ghostpos = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		
		m = game.getApproximateNextMoveTowardsTarget(ghostpos, pacpos, lastmove, DM.PATH);
		
		return m;
	}
}