package es.ucm.fdi.ici.c2021.practica4.grupo08.ghosts.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;

public class ActionBloquearPP implements Action{
	GHOST ghost;
	
	public ActionBloquearPP(GHOST ghost) {
		this.ghost = ghost;
	}


	@Override
	public MOVE execute(Game game) {		
		int[] pathPP = configPathPacmanNearstPP(game);
		
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
	
	private int[] configPathPacmanNearstPP(Game game) {
		int[] pathPacmanNearstPP = null;
		int minDistance = Integer.MAX_VALUE;
		
		for(int ppIndex : game.getActivePowerPillsIndices()) {
		
			int[] path = game.getShortestPath(
					game.getPacmanCurrentNodeIndex(), 
					ppIndex, 
					game.getPacmanLastMoveMade());
			
			if(minDistance > path.length) {
				minDistance = path.length;
				pathPacmanNearstPP = path;
			}
			
		}
		return pathPacmanNearstPP;
	}
}