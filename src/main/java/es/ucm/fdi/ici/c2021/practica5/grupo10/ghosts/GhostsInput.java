package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts;

import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Input;
import es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.CBRengine.GhostsDescription;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsInput implements Input {

	GHOST ghostAct;
	
	Integer pacmanNPPDist;
	Integer	blinkyPacmanNPPDist;
	Integer inkyPacmanNPPDist;
	Integer pinkyPacmanNPPDist;
	Integer suePacmanNPPDist;
		
	//Ditances to pacmanNextIntersection (si está en una intersección, dist a pacman)
	Integer pacmanDist;
	Integer	blinkyPDist;
	Integer inkyPDist;
	Integer pinkyPDist;
	Integer suePDist;
	
	Integer blinkyEdibleTime;
	Integer inkyEdibleTime;
	Integer pinkyEdibleTime;
	Integer sueEdibleTime;

	MOVE pacmanLastMove;
	MOVE blinkyLastMove;
	MOVE inkyLastMove;
	MOVE pinkyLastMove;
	MOVE sueLastMove;
	
	Integer score;
	Integer time;
	
	@Override
	public void parseInput(Game game) {
		computePacmanNPPDsitances(game);
		computePacmanDistances(game);
		computeGhostInfo(game);
		
		time = game.getTotalTime();
		score = game.getScore();
	}

	private void computeGhostInfo(Game game) {
		if (!game.isGhostEdible(GHOST.BLINKY)) this.blinkyEdibleTime = 0;
		else this.blinkyEdibleTime = game.getGhostEdibleTime(GHOST.BLINKY);
		
		if (!game.isGhostEdible(GHOST.INKY)) this.inkyEdibleTime = 0;
		else this.inkyEdibleTime = game.getGhostEdibleTime(GHOST.INKY);
		
		if (!game.isGhostEdible(GHOST.PINKY)) this.pinkyEdibleTime = 0;
		else this.pinkyEdibleTime = game.getGhostEdibleTime(GHOST.PINKY);
		
		if (!game.isGhostEdible(GHOST.SUE)) this.sueEdibleTime = 0;
		else this.sueEdibleTime = game.getGhostEdibleTime(GHOST.SUE);

		
		this.blinkyLastMove = game.getGhostLastMoveMade(GHOST.BLINKY);
		this.inkyLastMove = game.getGhostLastMoveMade(GHOST.INKY);
		this.pinkyLastMove = game.getGhostLastMoveMade(GHOST.PINKY);
		this.sueLastMove = game.getGhostLastMoveMade(GHOST.SUE);
	}

	private void computePacmanDistances(Game game) {
		int[] path = getPathNextIntersection(game, game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade());
		int pacmanNextInt = game.getPacmanCurrentNodeIndex();
		if(path.length > 0) pacmanNextInt = path[path.length - 1];
				
		this.pacmanDist = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), pacmanNextInt, game.getPacmanLastMoveMade(), DM.PATH);
		this.blinkyPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), pacmanNextInt, game.getGhostLastMoveMade(GHOST.BLINKY), DM.PATH);
		this.inkyPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.INKY), pacmanNextInt, game.getGhostLastMoveMade(GHOST.INKY), DM.PATH);
		this.pinkyPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.PINKY), pacmanNextInt, game.getGhostLastMoveMade(GHOST.PINKY), DM.PATH);
		this.suePDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.SUE), pacmanNextInt, game.getGhostLastMoveMade(GHOST.SUE), DM.PATH);

	}

	private void computePacmanNPPDsitances(Game game) {
		int pacmanNPP = computePacmanNPP(game);
		
		if(pacmanNPP != -1) {
		this.pacmanNPPDist = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), pacmanNPP, game.getPacmanLastMoveMade(), DM.PATH);
		this.blinkyPacmanNPPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.BLINKY), pacmanNPP, game.getGhostLastMoveMade(GHOST.BLINKY), DM.PATH);
		this.inkyPacmanNPPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.INKY), pacmanNPP, game.getGhostLastMoveMade(GHOST.INKY), DM.PATH);
		this.pinkyPacmanNPPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.PINKY), pacmanNPP, game.getGhostLastMoveMade(GHOST.PINKY), DM.PATH);
		this.suePacmanNPPDist = (int) game.getDistance(game.getGhostCurrentNodeIndex(GHOST.SUE), pacmanNPP, game.getGhostLastMoveMade(GHOST.SUE), DM.PATH);
		}
		else {
			this.pacmanNPPDist = -1;
			this.blinkyPacmanNPPDist = -1;
			this.inkyPacmanNPPDist = -1;
			this.pinkyPacmanNPPDist = -1;
			this.suePacmanNPPDist = -1;		
		}
	}

	@Override
	public CBRQuery getQuery() {
		GhostsDescription description = new GhostsDescription();
		
		description.setPacmanNPPDist(pacmanNPPDist);
		description.setBlinkyPacmanNPPDist(blinkyPacmanNPPDist);
		description.setInkyPacmanNPPDist(inkyPacmanNPPDist);
		description.setPinkyPacmanNPPDist(pinkyPacmanNPPDist);
		description.setSuePacmanNPPDist(suePacmanNPPDist);

		description.setPacmanDist(pacmanDist);
		description.setBlinkyPDist(blinkyPDist);
		description.setInkyPDist(inkyPDist);
		description.setPinkyPDist(pinkyPDist);
		description.setSuePDist(suePDist);
		
		description.setBlinkyEdibleTime(blinkyEdibleTime);
		description.setInkyEdibleTime(inkyEdibleTime);
		description.setPinkyEdibleTime(pinkyEdibleTime);
		description.setSueEdibleTime(sueEdibleTime);

		description.setPacmanLastMove(pacmanLastMove);
		description.setBlinkyLastMove(blinkyLastMove);
		description.setInkyLastMove(inkyLastMove);
		description.setPinkyLastMove(pinkyLastMove);
		description.setSueLastMove(sueLastMove);
		
		description.setScore(score);
		description.setTime(time);
		
		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	public int computePacmanNPP(Game game) {
		int minDistance = Integer.MAX_VALUE;
		int nearestPPIndex = -1;

		for(int indexPPill : game.getActivePowerPillsIndices()) {
			int distance = (int) game.getDistance(game.getPacmanCurrentNodeIndex(), indexPPill, game.getPacmanLastMoveMade(), DM.PATH);
			if(distance < minDistance) { 
				minDistance = distance;
				nearestPPIndex = indexPPill;
			}
		}

		return nearestPPIndex;
	}
	
	public int[] getPathNextIntersection(Game game, int indexFrom, MOVE lastMove) {
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
	public void setGhost(GHOST g) {
		this.ghostAct = g;
	}
}
