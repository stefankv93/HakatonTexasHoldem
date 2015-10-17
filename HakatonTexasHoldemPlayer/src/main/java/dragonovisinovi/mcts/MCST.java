package dragonovisinovi.mcts;

import java.util.Random;

import org.mozzartbet.hackathon.actions.Action;

import dragonovisinovi.simulation.GameState;
import dragonovisinovi.simulation.Player;

public class MCST {
	public static Action MCSTSearch(GameState gState, int numOfIterations, boolean printTree){
		
		Node root = new Node(null, null, gState.clone());
		Random r = new Random();
		
		for(int i = 0; i<numOfIterations; i++){
			Node node = root;
			GameState state = gState.clone();
			
			
			//Selection
			while(node.getUntriedMoves().size() == 0 && node.getChildren().size() != 0){
				node = node.selectChild();
				state.doAction(node.getMove());	
			}
			//Expansion
			if(node.getUntriedMoves().size() != 0){
				int rand = r.nextInt(node.getUntriedMoves().size());
				Action a = node.getUntriedMoves().get(rand);
				state.doAction(a);				//TODO player i oko equals actiona
				node = node.addChild(a);	
			}
			
			//Random Rollout
			while (state.getMoves().size() != 0){
				int rand = r.nextInt(state.getMoves().size());
				Action a = state.getMoves().get(rand);
				state.doAction(a); //TODO player i ovo sranje je sada random...
			}
			
			//Backpropagation
			while (node != null){
				node.update(state.getResult());
				node = node.getParrent();
			}
			
		}
		
		Node max = null; double maxValue=Double.MIN_VALUE;
		for(Node n: root.getChildren()){
			if(n.getValue() > maxValue){
				max = n;
				maxValue = n.getValue();
			}
		}
		
		if(printTree == true) root.TreeToString(0);
		
		
		return max.getMove();
		
	}

	public static Action MCSTSearch(GameState gState, int numOfIterations){
		return MCSTSearch(gState, numOfIterations, false);
	}
}
