package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.CBRengine;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class GhostsDescription implements CaseComponent {

	Integer id;
	
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
	public String toString() {
		return "GhostsDescription [id=" + id + ", pacmanNPPDist=" + pacmanNPPDist + ", blinkyPacmanNPPDist="
				+ blinkyPacmanNPPDist + ", inkyPacmanNPPDist=" + inkyPacmanNPPDist + ", pinkyPacmanNPPDist="
				+ pinkyPacmanNPPDist + ", suePacmanNPPDist=" + suePacmanNPPDist + ", pacmanDist=" + pacmanDist
				+ ", blinkyPDist=" + blinkyPDist + ", inkyPDist=" + inkyPDist + ", pinkyPDist=" + pinkyPDist
				+ ", suePDist=" + suePDist + ", blinkyEdibleTime=" + blinkyEdibleTime + ", inkyEdibleTime="
				+ inkyEdibleTime + ", pinkyEdibleTime=" + pinkyEdibleTime + ", sueEdibleTime=" + sueEdibleTime
				+ ", pacmanLastMove=" + pacmanLastMove + ", blinkyLastMove=" + blinkyLastMove + ", inkyLastMove="
				+ inkyLastMove + ", pinkyLastMove=" + pinkyLastMove + ", sueLastMove=" + sueLastMove + ", score="
				+ score + ", time=" + time + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPacmanNPPDist() {
		return pacmanNPPDist;
	}

	public void setPacmanNPPDist(Integer pacmanNPPDist) {
		this.pacmanNPPDist = pacmanNPPDist;
	}

	public Integer getBlinkyPacmanNPPDist() {
		return blinkyPacmanNPPDist;
	}

	public void setBlinkyPacmanNPPDist(Integer blinkyPacmanNPPDist) {
		this.blinkyPacmanNPPDist = blinkyPacmanNPPDist;
	}

	public Integer getInkyPacmanNPPDist() {
		return inkyPacmanNPPDist;
	}

	public void setInkyPacmanNPPDist(Integer inkyPacmanNPPDist) {
		this.inkyPacmanNPPDist = inkyPacmanNPPDist;
	}

	public Integer getPinkyPacmanNPPDist() {
		return pinkyPacmanNPPDist;
	}

	public void setPinkyPacmanNPPDist(Integer pinkyPacmanNPPDist) {
		this.pinkyPacmanNPPDist = pinkyPacmanNPPDist;
	}

	public Integer getSuePacmanNPPDist() {
		return suePacmanNPPDist;
	}

	public void setSuePacmanNPPDist(Integer suePacmanNPPDist) {
		this.suePacmanNPPDist = suePacmanNPPDist;
	}

	public Integer getPacmanDist() {
		return pacmanDist;
	}

	public void setPacmanDist(Integer pacmanDist) {
		this.pacmanDist = pacmanDist;
	}

	public Integer getBlinkyPDist() {
		return blinkyPDist;
	}

	public void setBlinkyPDist(Integer blinkyPDist) {
		this.blinkyPDist = blinkyPDist;
	}

	public Integer getInkyPDist() {
		return inkyPDist;
	}

	public void setInkyPDist(Integer inkyPDist) {
		this.inkyPDist = inkyPDist;
	}

	public Integer getPinkyPDist() {
		return pinkyPDist;
	}

	public void setPinkyPDist(Integer pinkyPDist) {
		this.pinkyPDist = pinkyPDist;
	}

	public Integer getSuePDist() {
		return suePDist;
	}

	public void setSuePDist(Integer suePDist) {
		this.suePDist = suePDist;
	}

	public Integer getBlinkyEdibleTime() {
		return blinkyEdibleTime;
	}

	public void setBlinkyEdibleTime(Integer blinkyEdibleTime) {
		this.blinkyEdibleTime = blinkyEdibleTime;
	}

	public Integer getInkyEdibleTime() {
		return inkyEdibleTime;
	}

	public void setInkyEdibleTime(Integer inkyEdibleTime) {
		this.inkyEdibleTime = inkyEdibleTime;
	}

	public Integer getPinkyEdibleTime() {
		return pinkyEdibleTime;
	}

	public void setPinkyEdibleTime(Integer pinkyEdibleTime) {
		this.pinkyEdibleTime = pinkyEdibleTime;
	}

	public Integer getSueEdibleTime() {
		return sueEdibleTime;
	}

	public void setSueEdibleTime(Integer sueEdibleTime) {
		this.sueEdibleTime = sueEdibleTime;
	}

	public MOVE getPacmanLastMove() {
		return pacmanLastMove;
	}

	public void setPacmanLastMove(MOVE pacmanLastMove) {
		this.pacmanLastMove = pacmanLastMove;
	}

	public MOVE getBlinkyLastMove() {
		return blinkyLastMove;
	}

	public void setBlinkyLastMove(MOVE blinkyLastMove) {
		this.blinkyLastMove = blinkyLastMove;
	}

	public MOVE getInkyLastMove() {
		return inkyLastMove;
	}

	public void setInkyLastMove(MOVE inkyLastMove) {
		this.inkyLastMove = inkyLastMove;
	}

	public MOVE getPinkyLastMove() {
		return pinkyLastMove;
	}

	public void setPinkyLastMove(MOVE pinkyLastMove) {
		this.pinkyLastMove = pinkyLastMove;
	}

	public MOVE getSueLastMove() {
		return sueLastMove;
	}

	public void setSueLastMove(MOVE sueLastMove) {
		this.sueLastMove = sueLastMove;
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
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostsDescription.class);
	}
	
}
