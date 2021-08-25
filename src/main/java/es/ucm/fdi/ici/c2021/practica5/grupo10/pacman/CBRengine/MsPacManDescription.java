package es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class MsPacManDescription implements CaseComponent {

	Integer id;
	
	Integer nearestPill;
	Integer pacmanNPPDist;
	MOVE pacmanLastMove;

	Integer nearestChasingGhostDist; //Dist nearest chasing ghost to pacmanNextInt
	Integer nearestEdibleGhostDist; //Distance to PacmanPos
	Integer nearestNPPChasingDsit;

	Integer score;
	Integer time;
	

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManDescription.class);
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getNearestPill() {
		return nearestPill;
	}


	public void setNearestPill(Integer nearestPill) {
		this.nearestPill = nearestPill;
	}


	public Integer getPacmanNPPDist() {
		return pacmanNPPDist;
	}


	public void setPacmanNPPDist(Integer pacmanNPPDist) {
		this.pacmanNPPDist = pacmanNPPDist;
	}


	public MOVE getPacmanLastMove() {
		return pacmanLastMove;
	}


	public void setPacmanLastMove(MOVE pacmanLastMove) {
		this.pacmanLastMove = pacmanLastMove;
	}


	public Integer getNearestChasingGhostDist() {
		return nearestChasingGhostDist;
	}


	public void setNearestChasingGhostDist(Integer nearestChasingGhostDist) {
		this.nearestChasingGhostDist = nearestChasingGhostDist;
	}


	public Integer getNearestEdibleGhostDist() {
		return nearestEdibleGhostDist;
	}


	public void setNearestEdibleGhostDist(Integer nearestEdibleGhostDist) {
		this.nearestEdibleGhostDist = nearestEdibleGhostDist;
	}


	public Integer getNearestNPPChasingDsit() {
		return nearestNPPChasingDsit;
	}


	public void setNearestNPPChasingDsit(Integer nearestNPPChasingDsit) {
		this.nearestNPPChasingDsit = nearestNPPChasingDsit;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public Integer getTime() {
		return time;
	}


	public void setTime(Integer time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "MsPacManDescription [id=" + id + ", nearestPill=" + nearestPill + ", pacmanNPPDist=" + pacmanNPPDist
				+ ", pacmanLastMove=" + pacmanLastMove + ", nearestChasingGhostDist=" + nearestChasingGhostDist
				+ ", nearestEdibleGhostDist=" + nearestEdibleGhostDist + ", nearestNPPChasingDsit="
				+ nearestNPPChasingDsit + ", score=" + score + ", time=" + time + "]";
	}

}
