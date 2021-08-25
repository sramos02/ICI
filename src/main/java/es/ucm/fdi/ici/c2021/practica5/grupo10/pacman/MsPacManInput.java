
package es.ucm.fdi.ici.c2021.practica5.grupo10.pacman;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Input;
import es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.CBRengine.MsPacManDescription;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput implements Input {

	Integer nearestPill;
	Integer pacmanNPPDist;
	MOVE pacmanLastMove;

	Integer nearestChasingGhostDist; //Dist nearest chasing ghost to pacmanNextInt
	Integer nearestEdibleGhostDist; //Distance to PacmanPos
	Integer nearestNPPChasingDsit;

	Integer score;
	Integer time;
	
	@Override
	public void parseInput(Game game) {
		computeNearestPill(game);
		int pacmanNPP = computePacmanNPPDsitance(game);
		
		pacmanLastMove = game.getPacmanLastMoveMade();
		computeGhost(pacmanNPP, game);
		time = game.getTotalTime();
		score = game.getScore();
	}

	@Override
	public CBRQuery getQuery() {
		MsPacManDescription description = new MsPacManDescription();
		description.setNearestPill(nearestPill);
		description.setPacmanNPPDist(pacmanNPPDist);
		description.setPacmanLastMove(pacmanLastMove);

		description.setNearestChasingGhostDist(nearestChasingGhostDist);
		description.setNearestEdibleGhostDist(nearestEdibleGhostDist);
		description.setNearestNPPChasingDsit(nearestNPPChasingDsit);

		description.setScore(score);
		description.setTime(time);
		
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}

	private void computeNearestPill(Game game) {
		int ret = -1;
		double min = Integer.MAX_VALUE;
		for (int indice : game.getActivePillsIndices()) {
			double distance = game.getDistance(
					game.getPacmanCurrentNodeIndex(),
					indice,
					DM.PATH);
			if (distance < min) {
				min = distance;
				ret = indice;
			}
		}
		
		this.nearestPill = ret;
	}
	
	private void computeGhost(int pacmanNPP, Game game) {
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		int edibleDistance = -1;
		int chasingDistance = -1;
		int nppDistance = -1;

		
		for(GHOST ghost : GHOST.values()) {
			if(game.getGhostLairTime(ghost) == 0) {
								
				if(game.isGhostEdible(ghost)) {
					int distance = game.getShortestPathDistance(pacmanPos, game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
					if (edibleDistance == -1 || distance < edibleDistance) edibleDistance = distance;
				}
				else {
					int distance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), pacmanPos, game.getGhostLastMoveMade(ghost));

					if (chasingDistance == -1 || distance < chasingDistance) chasingDistance = distance;
					if(pacmanNPP != -1) {
						
						int distance2 = game.getShortestPathDistance(
								game.getGhostCurrentNodeIndex(ghost),
								pacmanNPP,
								game.getGhostLastMoveMade(ghost));
						if(nppDistance == -1 || distance2 < nppDistance) nppDistance = distance2;
					}
				}
			}
		}

		this.nearestChasingGhostDist = chasingDistance;
		this.nearestEdibleGhostDist = edibleDistance;
		this.nearestNPPChasingDsit = nppDistance;
	}
	
	public int computePacmanNPPDsitance(Game game) {
		int minDistance = Integer.MAX_VALUE;
		int nearestPPIndex = -1;

		for(int indexPPill : game.getActivePowerPillsIndices()) {
			int distance = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), indexPPill, game.getPacmanLastMoveMade(), DM.PATH);
			if(distance < minDistance) { 
				minDistance = distance;
				nearestPPIndex = indexPPill;
			}
		}

		
		if (minDistance != Integer.MAX_VALUE) this.pacmanNPPDist = minDistance;
		else 
			minDistance = -1;
		
		return nearestPPIndex;
	}

	@Override
	public void setGhost(GHOST g) {
		// TODO Auto-generated method stub	
	}

}
