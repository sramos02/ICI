package es.ucm.fdi.ici.c2021.practica5.grupo10.ghosts.CBRengine;

import java.io.File;
import java.util.Collection;


import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.connector.PlainTextConnector;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import es.ucm.fdi.gaia.jcolibri.method.reuse.CombineQueryAndCasesMethod;
import es.ucm.fdi.gaia.jcolibri.method.reuse.NumericDirectProportionMethod;
import es.ucm.fdi.gaia.jcolibri.util.FileIO;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.Action;
import es.ucm.fdi.ici.c2021.practica5.grupo10.CBR.ActionSelector;
import pacman.game.Constants.GHOST;

public class GhostsCBRengine implements StandardCBRApplication {

	private GHOST g;
	private String casebaseFile;
	private Action action;
	private ActionSelector actionSelector;
	private GhostsStoreManager storageManager;

	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;

	NNConfig simConfig;

	final static String CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2021/practica5/grupo10/ghosts/CBRengine/plaintextconfig.xml"; //Cuidado!! poner el grupo aquí

	/**
	 * Simple extension to allow custom case base files. It also creates a new empty file if it does not exist.
	 */
	public class CustomPlainTextConnector extends PlainTextConnector {
		public void setCaseBaseFile(String casebaseFile) {
			super.PROP_FILEPATH = casebaseFile;
			try {
				File file = new File(casebaseFile);
				System.out.println(file.getAbsolutePath());
				if(!file.exists())
					file.createNewFile();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	public GhostsCBRengine(ActionSelector actionSelector, GhostsStoreManager storageManager)
	{
		this.actionSelector = actionSelector;
		this.storageManager = storageManager;
	}

	public void setCaseBaseFile(String casebaseFile) {
		this.casebaseFile = casebaseFile;
	}


	@Override
	public void configure()  throws ExecutionException{
		//Preconfigure
		connector = new CustomPlainTextConnector();
		caseBase = new CachedLinearCaseBase();

		connector.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		connector.setCaseBaseFile(this.casebaseFile);
		this.storageManager.setCaseBase(caseBase);

		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());

		simConfig.addMapping(new Attribute("pacmanNPPDist",GhostsDescription.class), new Interval(650));
		simConfig.addMapping(new Attribute("pacmanDist",GhostsDescription.class), new Interval(650));
		simConfig.addMapping(new Attribute("pacmanLastMove",GhostsDescription.class), new Equal());

		
		if(g == GHOST.BLINKY) {
			simConfig.addMapping(new Attribute("blinkyPacmanNPPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("blinkyPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("blinkyEdibleTime",GhostsDescription.class), new Equal());
			simConfig.addMapping(new Attribute("blinkyLastMove",GhostsDescription.class), new Equal());
		}
		else if (g == GHOST.INKY) {
			simConfig.addMapping(new Attribute("inkyPacmanNPPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("inkyPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("inkyEdibleTime",GhostsDescription.class), new Equal());
			simConfig.addMapping(new Attribute("inkyLastMove",GhostsDescription.class), new Equal());
		}

		else if (g == GHOST.PINKY) {
			simConfig.addMapping(new Attribute("pinkyPacmanNPPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("pinkyPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("pinkyEdibleTime",GhostsDescription.class), new Equal());
			simConfig.addMapping(new Attribute("pinkyLastMove",GhostsDescription.class), new Equal());
		}
		else {
			simConfig.addMapping(new Attribute("suePacmanNPPDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("suePDist",GhostsDescription.class), new Interval(650));
			simConfig.addMapping(new Attribute("sueEdibleTime",GhostsDescription.class), new Equal());
			simConfig.addMapping(new Attribute("sueLastMove",GhostsDescription.class), new Equal());
		}				
		simConfig.addMapping(new Attribute("score",GhostsDescription.class), new Interval(50000));
		simConfig.addMapping(new Attribute("time",GhostsDescription.class), new Interval(4000));
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		return caseBase;
	}

	public void cycle(CBRQuery query) throws ExecutionException {
		int k = 3;

		if(caseBase.getCases().size() < k) {
			this.action = actionSelector.findAction();
		}else {

			//Using KNN, 3 better results with majority voting.
			//Selecting cases from database using simComfig				
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
			Collection<CBRCase> selectedcases = SelectCases.selectTopK(eval, k);

			//Compute a direct proportion between the two attributes.
			Attribute a,b;
			if(g == GHOST.BLINKY) {
				a = new Attribute("blinkyPacmanNPPDist",GhostsDescription.class);
				b = new Attribute("blinkyPDist",GhostsDescription.class);
			}
			else if (g == GHOST.INKY) {
				a = new Attribute("inkyPacmanNPPDist",GhostsDescription.class);
				b = new Attribute("inkyPDist",GhostsDescription.class);
			}
			else if (g == GHOST.PINKY) {
				a = new Attribute("pinkyPacmanNPPDist",GhostsDescription.class);
				b = new Attribute("pinkyPDist",GhostsDescription.class);
			}
			else {
				a = new Attribute("suePacmanNPPDist",GhostsDescription.class);
				b = new Attribute("suePDist",GhostsDescription.class);
			}

			NumericDirectProportionMethod.directProportion(a, new Attribute("pacmanNPPDist",GhostsDescription.class), query, selectedcases);
			NumericDirectProportionMethod.directProportion(b, new Attribute("pacmanDist",GhostsDescription.class), query, selectedcases);

			Collection<CBRCase> newcases = CombineQueryAndCasesMethod.combine(query, selectedcases);
			CBRCase mostSimilarCase = newcases.iterator().next();

			//Now compute a solution for the query
			GhostsResult result = (GhostsResult) mostSimilarCase.getResult();		
			GhostsSolution solution = (GhostsSolution) mostSimilarCase.getSolution();

			this.action = actionSelector.getAction(solution.getAction()); //Caso base

			//Comprueba que el caso sea bueno y tenga suficiente parecido
			if(eval.iterator().next().getEval() < 0.92) {
				this.action = actionSelector.findAction();
			}
			else if(result.getScore() > 200) 
				this.action = actionSelector.findAnotherAction(solution.getAction());	
		}

		CBRCase newCase = createNewCase(query);
		this.storageManager.storeCase(newCase);
	}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		GhostsDescription newDescription = (GhostsDescription) query.getDescription();
		GhostsResult newResult = new GhostsResult();
		GhostsSolution newSolution = new GhostsSolution();
		int newId = this.caseBase.getCases().size();
		newId+= storageManager.getPendingCases();
		newDescription.setId(newId);
		newResult.setId(newId);
		newSolution.setId(newId);
		newSolution.setAction(this.action.getActionId());
		newCase.setDescription(newDescription);
		newCase.setResult(newResult);
		newCase.setSolution(newSolution);
		return newCase;
	}

	public Action getSolution() {
		return this.action;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.storageManager.close();
		this.caseBase.close();
	}

	public void setGhost(GHOST ghost) {
		this.g = ghost;
	}

}
