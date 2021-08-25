package es.ucm.fdi.ici.c2021.practica4.grupo10.pacman.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.Constants;

public class ActionComerPill implements Action{


	public MOVE execute(Game game) { 

		MOVE bestMove = MOVE.NEUTRAL;

		//Si hay pills escogemos ese camino
		if(game.getActivePillsIndices().length != 0)
			bestMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPill(game), Constants.DM.PATH);		


		//Calculamos los caminos posibles
		ArrayList<int[]> paths = possiblePathPacMan(game);

		int bestPills = 0;
		for(int j = 0; j < paths.size(); j++) {
			int[] p = paths.get(j);

			//Elimina el que tenga una PP
			if (game.getActivePowerPillsIndices().length != 0) {
				paths.remove(p);
				continue;
			}
		
			//Se queda con el que tenga más pills
			if(paths.contains(p) && game.getActivePillsIndices().length != 0) {
				int actPills = 0;
				for(int i : p) 
					for(int pills : game.getActivePillsIndices())
						if(i == pills) actPills +=1;


				if (actPills > bestPills) {
					bestPills = actPills;
					bestMove = game.getMoveToMakeToReachDirectNeighbour(p[0], p[1]);
				}
			}

		}
		return bestMove;
	}


	public ArrayList<int[]> possiblePathPacMan(Game game){
		ArrayList<int[]> paths = new ArrayList<int[]>();
		int actNode = game.getPacmanCurrentNodeIndex();
		MOVE mov = game.getPacmanLastMoveMade();

		for(int i = 0; i < game.getPossibleMoves(actNode, mov).length; i++) {
			MOVE move = game.getPossibleMoves(actNode, mov)[i];
			int actNodeIndex = game.getNeighbour(actNode, move);

			ArrayList<Integer> path = new ArrayList<Integer>();
			path.add(actNodeIndex);

			while(actNodeIndex != -1 && !game.isJunction(actNodeIndex)) {
				actNodeIndex = game.getNeighbour(actNodeIndex, move);
				if (actNodeIndex != -1) {

					path.add(actNodeIndex);
					if(game.getNeighbour(actNodeIndex, move) == -1)
						move = game.getPossibleMoves(actNodeIndex, move)[0];
				}
			}

			int[] aux = new int[path.size()];
			for(int j=0; j<path.size(); j++)
				aux[j] = path.get(j);
			paths.add(aux);
		}
		return paths;
	}

	public int getNearestPill(Game game) {
		int ret = 0;
		double min = Integer.MAX_VALUE;
		for (int indice : game.getActivePillsIndices()) {
			double distance = game.getDistance(
					game.getPacmanCurrentNodeIndex(),
					indice,
					Constants.DM.PATH);
			if (distance < min) {
				min = distance;
				ret = indice;
			}
		}
		return ret;
	}

}
