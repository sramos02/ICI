package es.ucm.fdi.ici.c2021.demofuzzy;

import java.util.HashMap;

import es.ucm.fdi.ici.c2021.demofuzzy.actions.GoToPPillAction;
import es.ucm.fdi.ici.c2021.demofuzzy.actions.RunAwayAction;
import es.ucm.fdi.ici.fuzzy.Action;
import es.ucm.fdi.ici.fuzzy.ActionSelector;

public class MsPacManActionSelector implements ActionSelector {

	private final Double RUN_AWAY_LIMIT = 20.0;

	@Override
	public Action selectAction(HashMap<String, Double> fuzzyOutput) {
		Double runAway = fuzzyOutput.get("runAway");
		if(runAway> this.RUN_AWAY_LIMIT)
			return new RunAwayAction();
		else
			return new GoToPPillAction();
	}

}
