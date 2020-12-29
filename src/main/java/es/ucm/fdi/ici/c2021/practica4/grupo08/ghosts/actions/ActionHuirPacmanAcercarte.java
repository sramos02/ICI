package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions;


import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionHuirPacmanAcercarte implements Action{
	GHOST ghost;
	
	public ActionHuirPacmanAcercarte(GHOST g) {
		this.ghost = g;
	}


	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveTowardsTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				game.getPacmanCurrentNodeIndex(), 
				game.getGhostLastMoveMade(ghost),
				DM.PATH);
	}
}