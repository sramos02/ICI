package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionAtacarPrimeraInterseccionPacman implements Action{

	public MOVE execute(Game game, GHOST ghost) {
		MOVE m = null;
		int ghostpos = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastmove = game.getGhostLastMoveMade(ghost);
		int[] path = getPathNextIntersection(game.getPacmanCurrentNodeIndex(),
												game.getPacmanLastMoveMade(), game);
		int pos = path[path.length-1];
		
		m = game.getApproximateNextMoveTowardsTarget(ghostpos, pos, lastmove, DM.PATH);
		return m;
	}
	
	public int[] getPathNextIntersection(int indexFrom, MOVE lastMove, Game game) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		MOVE move = lastMove;
		int actNodeIndex = indexFrom;
		list.add(indexFrom);
		while(!game.isJunction(actNodeIndex)) {
			if(game.getNeighbour(actNodeIndex, move)==-1)
				move = game.getPossibleMoves(actNodeIndex, move)[0];

			actNodeIndex = game.getNeighbour(actNodeIndex, move);
			list.add(actNodeIndex);
		}
		int i = 0;

		int[] ret = new int[list.size()];
		for(int node : list) {
			ret[i] = node;
			++i;
		}
		return ret;
	}


	@Override
	public String getActionId() {
		return "Ataca primera intersección msPacman";
	}

	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
}