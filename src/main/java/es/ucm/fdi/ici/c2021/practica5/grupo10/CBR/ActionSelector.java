package es.ucm.fdi.ici.c2021.practica5.grupo10.CBR;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import pacman.game.Game;

public class ActionSelector {
	
	HashMap<String,Action> map;
	List<Action> actions;
	Game game;
	
	public ActionSelector(List<Action> actions) {
		this.map = new HashMap<String,Action>();
		for(Action a: actions)
			map.put(a.getActionId(), a);
		this.actions = actions;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Method called when the CBREngine is not able to find a suitable action. 
	 * Simplest implementation returns a random one.
	 * @return
	 */
	public Action findAction() {
		int randomIndex = new Random().nextInt(actions.size());
		return actions.get(randomIndex);
	}
	
	/**
	 * Method called when the CBREngine has found a failed action.
	 * Simplest implementation returns another randomly.
	 * @return
	 */	
	public Action findAnotherAction(String failledAction) {
		int randomIndex = new Random().nextInt(actions.size());
		Action other = actions.get(randomIndex);
		if(other.getActionId().equals(failledAction))
			randomIndex = (randomIndex+1)%actions.size();
		return actions.get(randomIndex);
	}

	public Action getAction(String action) {
		return map.get(action);
	}
}
