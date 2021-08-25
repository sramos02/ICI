package es.ucm.fdi.ici.c2021.practica4.grupo10.pacman.actions;

import pacman.game.Game;

import java.util.ArrayList;

import pacman.game.Constants;
import es.ucm.fdi.ici.fuzzy.Action;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class ActionHuir implements Action{

	int[] bestPath = null;
	int score = Integer.MAX_VALUE;

	public MOVE execute(Game game) {
		ArrayList<GHOST> chasingGhosts = new ArrayList<GHOST>();
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		MOVE pacmanMove = game.getPacmanLastMoveMade();
		
		MOVE bestMove = MOVE.NEUTRAL;
		MOVE death = MOVE.NEUTRAL;
		
		for(GHOST ghost : GHOST.values()) {
			if(game.getGhostLairTime(ghost) > 0) continue;
			if(game.getGhostEdibleTime(ghost) < 5) {
				chasingGhosts.add(ghost);
			}
		}

		int aux = Integer.MAX_VALUE;
		for(GHOST ghost : chasingGhosts) {
			int ghostPos = game.getGhostCurrentNodeIndex(ghost);
			
			if(ghostPos != -1 && !game.isGhostEdible(ghost)) {
				int ghostDist =  game.getShortestPathDistance(pacmanPos, ghostPos, pacmanMove);
				if(ghostDist < aux){
					aux = ghostDist;
					bestMove = game.getApproximateNextMoveAwayFromTarget(pacmanPos, ghostPos, pacmanMove, Constants.DM.PATH);		
					death = game.getNextMoveTowardsTarget(pacmanPos, ghostPos, Constants.DM.PATH);
				}
			}
		}

	
		//Calculamos el path con mas pills
		ArrayList<int[]> paths = possiblePathPacMan(game);
		int bestPills = 0;

		for(int[] p : paths) {

			//Si el path no te mata, miramos cual tiene más pills
			if(p.length > 1 && game.getMoveToMakeToReachDirectNeighbour(p[0], p[1]) != death) {
				int actPills = pillsInPath(game, p);
				if(actPills > bestPills) {
					bestPills = actPills;
					bestMove = game.getMoveToMakeToReachDirectNeighbour(p[0], p[1]);
				}
			}
		}

		//Si todos los caminos tienen 0 pills busca donde está la más cercana y va a por ella si puede
		if(bestPills == 0 && getNearestPill(game) != -1) {
			MOVE nearestPill = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPill(game), Constants.DM.PATH);	
			if(nearestPill != death)
				bestMove = nearestPill;
		}

		//Si no encuentra o no puede devuelve random
		return bestMove;

	}


	private int pillsInPath(Game game, int[] p) {
		int actPills = 0;
		for(int i : p) 
			for (int pills : game.getActivePillsIndices()) 
				if(i == pills) actPills++;
		
		return actPills;
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
}