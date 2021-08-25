package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;

public class ActionBloquearPP implements Action{

	public MOVE execute(Game game, GHOST ghost) {		
		
		if(game.isJunction(game.getGhostCurrentNodeIndex(ghost))) {
		int[] pathPP = configPathPacmanNearstPP(game, ghost);
		
		if(pathPP == null)
			return game.getApproximateNextMoveTowardsTarget(
					game.getGhostCurrentNodeIndex(ghost), 
					game.getPacmanCurrentNodeIndex(), 
					game.getGhostLastMoveMade(ghost), 
					Constants.DM.PATH);
		
		ArrayList<Integer> intersections = new ArrayList<Integer>();
		for(int i: pathPP) {
            if(game.isJunction(i)) 
                intersections.add(i);
            if(intersections.size()>=3)
                break;
        }
		
		int minDistance = Integer.MAX_VALUE;
		int interDestiny = game.getPacmanCurrentNodeIndex();
		
		for(int intersection: intersections) {
			int distance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), intersection);
			if(distance<minDistance) {
				minDistance = distance; 
				interDestiny = intersection;
			}
		}
		
		return game.getApproximateNextMoveTowardsTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				interDestiny, 
				game.getGhostLastMoveMade(ghost), 
				Constants.DM.PATH);
		}
		return MOVE.NEUTRAL;
	}
	
	private int[] configPathPacmanNearstPP(Game game, GHOST ghost) {
		int[] pathPacmanNearstPP = null;
		int minDistance = Integer.MAX_VALUE;
		
		for(int ppIndex : game.getActivePowerPillsIndices()) {
			int[] path = game.getShortestPath(game.getGhostCurrentNodeIndex(ghost), ppIndex, game.getGhostLastMoveMade(ghost));
			
			if(game.getPacmanCurrentNodeIndex() != -1)
				path = game.getShortestPath(game.getPacmanCurrentNodeIndex(), ppIndex, game.getPacmanLastMoveMade());

			if(minDistance > path.length) {
				minDistance = path.length;
				pathPacmanNearstPP = path;
			}
			
		}
		return pathPacmanNearstPP;
	}


	@Override
	public String getActionId() {
		return "Bloquear PP";
	}

	@Override
	public MOVE execute(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
}