package es.ucm.fdi.ici.c2021.practica4.grupo10;


import es.ucm.fdi.ici.c2021.practica4.grupo10.ghosts.GhostFuzzy;
import es.ucm.fdi.ici.c2021.practica4.grupo10.pacman.MsPacManFuzzy;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

public class ExecutorTest {

	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setGhostPO(true)
				.setPacmanPO(true)
				.setVisual(true)
				.setScaleFactor(3.0)
				.build();
		
		PacmanController pacMan = new MsPacManFuzzy();
		//PacmanController pacMan = new HumanController(new KeyBoardInput());
		GhostController ghosts = new GhostFuzzy();
		System.out.println( executor.runGame(pacMan, ghosts, 50) );
	}
}