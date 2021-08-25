package es.ucm.fdi.ici.c2021.practica5.grupo10.pacman.CBRengine;

import java.awt.List;
import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
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

public class MsPacManCBRengine implements StandardCBRApplication {

	private String casebaseFile;
	private Action action;
	private ActionSelector actionSelector;
	private MsPacManStorageManager storageManager;

	CustomPlainTextConnector connector;
	CBRCaseBase caseBase;
	NNConfig simConfig;
	
	
	
	final static String CONNECTOR_FILE_PATH = "es/ucm/fdi/ici/c2021/practica5/grupo10/pacman/CBRengine/plaintextconfig.xml"; //Cuidado!! poner el grupo aquí

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
	
	
	public MsPacManCBRengine(ActionSelector actionSelector, MsPacManStorageManager storageManager)
	{
		this.actionSelector = actionSelector;
		this.storageManager = storageManager;
	}
	
	public void setCaseBaseFile(String casebaseFile) {
		this.casebaseFile = casebaseFile;
	}
	
	@Override
	public void configure() throws ExecutionException {
		connector = new CustomPlainTextConnector();
		caseBase = new CachedLinearCaseBase();
		
		connector.initFromXMLfile(FileIO.findFile(CONNECTOR_FILE_PATH));
		connector.setCaseBaseFile(this.casebaseFile);
		this.storageManager.setCaseBase(caseBase);
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());

		Attribute npp = new Attribute("nearestPill",MsPacManDescription.class);
		simConfig.addMapping(npp, new Interval(650));
		simConfig.setWeight(npp, 0.2);

		Attribute nppDist = new Attribute("pacmanNPPDist",MsPacManDescription.class);
		simConfig.addMapping(nppDist, new Interval(650));
		simConfig.setWeight(nppDist, 0.2);
		
		Attribute lastMove = new Attribute("pacmanLastMove",MsPacManDescription.class);
		simConfig.addMapping(lastMove, new Equal()); 
		simConfig.setWeight(lastMove, 0.2);

		
		Attribute nCGhostDist = new Attribute("nearestChasingGhostDist",MsPacManDescription.class);
		simConfig.addMapping(nCGhostDist, new Interval(650));
		simConfig.setWeight(nCGhostDist, 0.5);

		
		Attribute nEGhostDist = new Attribute("nearestEdibleGhostDist",MsPacManDescription.class);
		simConfig.addMapping(nEGhostDist, new Interval(650));
		simConfig.setWeight(nEGhostDist, 0.4);

		Attribute nPdist = new Attribute("nearestNPPChasingDsit",MsPacManDescription.class);
		simConfig.addMapping(nPdist, new Interval(650));
		simConfig.setWeight(nPdist, 0.4);

		
		simConfig.addMapping(new Attribute("score",MsPacManDescription.class), new Interval(50000));
		simConfig.addMapping(new Attribute("time",MsPacManDescription.class), new Interval(4000));
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);
		return caseBase;
	}
	
	
	
//	private Double computeSimilarity(CaseComponent description, CaseComponent description2) {
//
//		MsPacManDescription _query = (MsPacManDescription)description;
//		MsPacManDescription _case = (MsPacManDescription)description2;
//
//		double simil = 0;
//
//		simil += Math.abs(_query.getNearestPill()-_case.getNearestPill())/650;
//		simil += Math.abs(_query.getPacmanNPPDist()-_case.getPacmanNPPDist())/650;
//		simil += _query.getPacmanLastMove().equals(_case.getPacmanLastMove()) ? 1.0 : 0.0;
//
//		simil += Math.abs(_query.getNearestChasingGhostDist()-_case.getNearestChasingGhostDist())/650;
//		simil += Math.abs(_query.getNearestEdibleGhostDist()-_case.getNearestEdibleGhostDist())/650;
//		simil += Math.abs(_query.getNearestNPPChasingDsit()-_case.getNearestNPPChasingDsit())/650;
//
//
//		simil += Math.abs(_query.getScore()-_case.getScore())/150000;
//		simil += Math.abs(_query.getTime()-_case.getTime())/4000;
//
//		return simil/5.0;
//	}
//
//	private Collection<RetrievalResult> customNN(Collection<CBRCase> cases, CBRQuery query) {
//
//		// Parallel stream
//		List<RetrievalResult> res = cases.parallelStream()
//				.map(c -> new RetrievalResult(c, computeSimilarity(query.getDescription(), c.getDescription())))
//				.collect(Collectors.toList());
//		
//		// Sort the result
//		res.sort(RetrievalResult::compareTo);
//		
//		return res;
//
//	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		int k = 3;
					
		if(caseBase.getCases().size() < k) {
			this.action = actionSelector.findAction();
		}else {
			
			//Using KNN, 3 better results with majority voting.
			
			//Selecting cases from database using simComfig
			Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
			//Collection<RetrievalResult> eval = customNN(caseBase.getCases(),query);
			
			
			Collection<CBRCase> selectedcases = SelectCases.selectTopK(eval, k);
			
			//Reuse
			//Compute a direct proportion between the two attributes.
			NumericDirectProportionMethod.directProportion(new Attribute("nearestNPPChasingDsit",MsPacManDescription.class), 
					new Attribute("pacmanNPPDist",MsPacManDescription.class), query, selectedcases);
		
			Collection<CBRCase> newcases = CombineQueryAndCasesMethod.combine(query, selectedcases);
			
			CBRCase mostSimilarCase = newcases.iterator().next();
			
			//Now compute a solution for the query
			MsPacManResult result = (MsPacManResult) mostSimilarCase.getResult();
			MsPacManSolution solution = (MsPacManSolution) mostSimilarCase.getSolution();
			this.action = actionSelector.getAction(solution.getAction()); //Caso base
			
			//Comprueba que el caso sea bueno y tenga suficiente parecido
			if(eval.iterator().next().getEval() < 0.92) 
				this.action = actionSelector.findAction();
			
			else if(!isGoodResult(mostSimilarCase))//result.getScore() > 100) 
				this.action = actionSelector.findAnotherAction(solution.getAction());	
		}
		
		CBRCase newCase = createNewCase(query);
		this.storageManager.storeCase(newCase);
	}

	private boolean isGoodResult(CBRCase mostSimilarCase) {
		boolean ret = false;
		MsPacManResult result = (MsPacManResult) mostSimilarCase.getResult();
		MsPacManSolution solution = (MsPacManSolution) mostSimilarCase.getSolution();

		if(solution.getAction().equals("AtacaMejorGhost") && result.getScore() > 200) return true;
		if(solution.getAction().equals("ActionComerPP") &&  result.getScore() > 50) return true;
		if(solution.getAction().equals("ActionComerPill") &&  result.getScore() >	 10) return true;
		if(solution.getAction().equals("ActionHuir")) return true;

		return ret;
	}

	/**
	 * Creates a new case using the query as description, 
	 * storing the action into the solution and 
	 * setting the proper id number
	 */
	private CBRCase createNewCase(CBRQuery query) {
		CBRCase newCase = new CBRCase();
		MsPacManDescription newDescription = (MsPacManDescription) query.getDescription();
		MsPacManResult newResult = new MsPacManResult();
		MsPacManSolution newSolution = new MsPacManSolution();
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

}
