package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ActionHuirPacmanRandom implements Action{
		GHOST ghost;

		public ActionHuirPacmanRandom(GHOST g) {
			this.ghost = g;
		}


	    @Override
	    public MOVE execute(Game game) {
	    	ArrayList<GHOST> chasingGhosts = new ArrayList<GHOST>();
	        ArrayList<GHOST> edibleGhosts = new ArrayList<GHOST>();

	        for(GHOST ghost : GHOST.values()) {
	            if(game.getGhostLairTime(ghost) > 0) continue;
	            if(game.getGhostEdibleTime(ghost) > 5) {
	                edibleGhosts.add(ghost);
	            }
	            else {
	                chasingGhosts.add(ghost);
	            }
	        }

	        if(chasingGhosts.size() == 0) 
	            return game.getApproximateNextMoveAwayFromTarget(
	                game.getGhostCurrentNodeIndex(ghost),
	                game.getPacmanCurrentNodeIndex(),
	                game.getGhostLastMoveMade(ghost),
	                DM.PATH); 

	        int minDistance = Integer.MAX_VALUE;
	        GHOST closeChasingGhost = null;
	        for(GHOST iGhost : chasingGhosts) {
	            int distanceGhost = 
	                    game.getShortestPathDistance(
	                            game.getGhostCurrentNodeIndex(ghost), 
	                            game.getGhostCurrentNodeIndex(iGhost), 
	                            game.getGhostLastMoveMade(ghost));
	            if( distanceGhost < minDistance ) {
	                minDistance = distanceGhost;
	                closeChasingGhost = ghost;
	            }
	        }

	        return game.getApproximateNextMoveTowardsTarget(
	                game.getGhostCurrentNodeIndex(ghost), 
	                game.getGhostCurrentNodeIndex(closeChasingGhost),
	                game.getGhostLastMoveMade(ghost), 
	                DM.PATH);
	    }
}