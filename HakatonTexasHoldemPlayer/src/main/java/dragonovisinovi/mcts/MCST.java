package dragonovisinovi.mcts;

import java.util.Random;

import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.BetAction;

import dragonovisinovi.simulation.GameState;
import dragonovisinovi.simulation.RealGameState;

public class MCST {
	public static Action MCSTSearch(GameState gState, int numOfIterations, boolean printTree){
		
		Node root = new Node(null, Action.CHECK, gState.clone());
		Random r = new Random();
		
		for(int i = 0; i<numOfIterations; i++){
			Node node = root;
			GameState state = gState.clone();
			double startVal = ((RealGameState)state).getData().getStartHandMoney();
			
			
			//Selection
			while(node.getUntriedMoves().size() == 0 && node.getChildren().size() != 0){
				node = node.selectChild();
				state.doAction(node.getMove());
			}
			//Expansion
			if(node.getUntriedMoves().size() != 0 && state.getMoves().size()!=0){
				int rand = r.nextInt(node.getUntriedMoves().size());
				Action a = node.getUntriedMoves().get(rand);
				state.doAction(a);
				node = node.addChild(a);	
			}
			
			//Random Rollout
			while (state.getMoves().size() != 0){
				int rand = r.nextInt(state.getMoves().size());
				Action a = state.getMoves().get(rand);
				/*Action action;
				if(a.equals(Action.BET)){
					action = new BetAction(((RealGameState)state).getData().getBigBlind());
				}*/
				state.doAction(a); //TODO ovo sranje je sada random..
			} 
			((RealGameState)state).getData().getActive().get(0).win(((RealGameState)state).getData().getPotMoney());
			
			
			double val = state.getMoney() - startVal;
			state.setResult(val);
			
			
			//Backpropagation
			while (node != null){
				node.update(state.getResult());
				node = node.getParrent();
			}
			
		}
		
		Node max = null; double maxValue=-1000000000.0;
		for(Node n: root.getChildren()){
			double val = n.getValue()/(double)n.getVisits();
			if(val > maxValue){
				max = n;
				maxValue = val;
			}
		}
		
		if(printTree == true) System.out.println(root.TreeToString(0));
		
		
		return max.getMove();
		
	}

	public static Action MCSTSearch(GameState gState, int numOfIterations){
		return MCSTSearch(gState, numOfIterations, false);
	}
}
