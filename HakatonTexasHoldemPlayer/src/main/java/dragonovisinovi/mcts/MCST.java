package dragonovisinovi.mcts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.mozzartbet.hackathon.Hand;
import org.mozzartbet.hackathon.HandEvaluator;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;

import dragonovisinovi.simulation.GameState;
import dragonovisinovi.simulation.RealGameState;

public class MCST {
	public static Action MCSTSearch(GameState gState, int numOfIterations, boolean printTree) {

		Node root = new Node(null, Action.CHECK, gState.clone());
		Random r = new Random();

		for (int i = 0; i < numOfIterations; i++) {
			Node node = root;
			GameState state = gState.clone();
			double startVal = ((RealGameState) state).getData().getStartHandMoney();

			// Selection
			while ((node.getUntriedMoves().size() == 0) && (node.getChildren().size() != 0)) {
				node = node.selectChild();
				state.doAction(node.getMove());
			}
			// Expansion
			if (node.getUntriedMoves().size() != 0 && state.getMoves().size() != 0) {
				int rand = r.nextInt(node.getUntriedMoves().size());
				Action a = node.getUntriedMoves().get(rand);
				state.doAction(a);
				node = node.addChild(a);
			}

			
			// Random Rollout
			RealGameState sstate = (RealGameState) state;
			int n = sstate.getData().getBoardCards().size();
			for(int j = 0; j<5-n; j++){
				sstate.getData().getBoardCards().add(sstate.getData().getDeck().deal());
			}
			
			List<Integer> values = new ArrayList<>();
			for (Iterator iterator = sstate.getData().getActive().iterator(); iterator.hasNext();) {
				Player p = (Player) iterator.next();
				if(!p.getName().equals(sstate.getData().getRealPlayer().getName())){
					Hand h = new Hand();
					if(sstate.getData().getBoardCards().size() > 5){
						int a = 0;
						int b = a++;
					}
					h.addCards(sstate.getData().getBoardCards());
					h.addCard(sstate.getData().getDeck().deal());
					h.addCard(sstate.getData().getDeck().deal());
					HandEvaluator he = new HandEvaluator(h);
					values.add(he.getValue());
				}else{
					Hand h = new Hand();
					if(sstate.getData().getBoardCards().size() > 5){
						int a = 0;
						int b = a++;
					}
					h.addCards(sstate.getData().getBoardCards());
					h.addCard(sstate.getData().getRealPlayer().getCards()[0]);
					h.addCard(sstate.getData().getRealPlayer().getCards()[1]);
					HandEvaluator he = new HandEvaluator(h);
					values.add(he.getValue());
				}
				
			}
			
			int maxIndex = 0; int maxValue = 0;
			for(int j = 0; j<values.size(); j++){
				if(maxValue<values.get(j)){
					maxIndex = j;
					maxValue = values.get(j);
				}
			}
			
			//((RealGameState) state).getData().getActive().get(0).win(((RealGameState) state).getData().getPotMoney());

			sstate.getData().getActive().get(maxIndex).win(sstate.getData().getPotMoney());
			
			double val = state.getMoney() - startVal;
			state.setResult(val);

			// Backpropagation
			while (node != null) {
				node.update(state.getResult()/1000);
				node = node.getParrent();
			}

		}

		Node max = null;
		double maxValue = -1000000000.0;
		for (Node n : root.getChildren()) {
			double val = n.getValue() / (double) n.getVisits();
			if (val > maxValue) {
				max = n;
				maxValue = val;
			}
		}

		if (printTree == true)
			System.out.println(root.TreeToString(0));

		return max.getMove();

	}

	public static Action MCSTSearch(GameState gState, int numOfIterations) {
		return MCSTSearch(gState, numOfIterations, false);
	}
}
