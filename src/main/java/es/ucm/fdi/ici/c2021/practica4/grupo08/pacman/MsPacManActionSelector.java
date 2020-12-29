package es.ucm.fdi.ici.c2021.practica4.grupo08.pacman;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.practica4.grupo08.pacman.actions.ActionComerPP;
import es.ucm.fdi.ici.c2021.practica4.grupo08.pacman.actions.ActionComerPill;
import es.ucm.fdi.ici.c2021.practica4.grupo08.pacman.actions.ActionHuir;
import es.ucm.fdi.ici.c2021.practica4.grupo08.pacman.actions.AtacaMejorGhost;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;

public class MsPacManActionSelector implements ActionSelector {

	private final Double RUN_AWAY_LIMIT = 20.0;
	private final Double EAT_LIMIT = 50.0;
	private final Double EAT_POWERPILL = 50.0;
	
	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double runAway = fuzzyOutput.get("runAway");
		Double eatBlinky = fuzzyOutput.get("eatBLINKY");
		Double eatPinky = fuzzyOutput.get("eatPINKY");
		Double eatInky = fuzzyOutput.get("eatINKY");
		Double eatSue = fuzzyOutput.get("eatSUE");
		
		Double eatPP = fuzzyOutput.get("eatPowerPill");

		
		if(runAway> this.RUN_AWAY_LIMIT) 
			return new ActionHuir();
		
		if (eatPP > EAT_POWERPILL) 
			return new ActionComerPP();
		
		if(eatBlinky > EAT_LIMIT || eatPinky > EAT_LIMIT 
				|| eatInky > EAT_LIMIT || eatSue > EAT_LIMIT) 
			new AtacaMejorGhost();
		
		return new ActionComerPill();
	}

}
