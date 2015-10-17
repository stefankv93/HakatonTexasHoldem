package dragonovisinovi.simulation;

import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.bots.Bot;
import org.mozzartbet.hackathon.util.PokerConstants;

public class RandomBot implements Bot {
	private int bigBlind = 0;
	private Card[] cards = null;
	private static int stID = 0;
	private int id = ++stID;
	
	@Override
	public void joinedTable(int bigBlind) {
		// TODO Auto-generated method stub
		this.bigBlind = bigBlind;
		
	}

	@Override
	public void handStarted(Player dealer) {
		// TODO Auto-generated method stub
		cards = null;
	}

	@Override
	public void actorRotated(Player actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerUpdated(Player player) {
		// TODO Auto-generated method stub
		 if (player.getCards().length == PokerConstants.NO_OF_HOLE_CARDS) {
	            this.cards = player.getCards();
	        }
	}

	@Override
	public void boardUpdated(List<Card> cards, int bet, int pot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerActed(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Action act(int minBet, int currentBet, Set<Action> allowedActions, int currentAmount) {
		// TODO Auto-generated method stub
		
		while(true){
			int choice = (int)Math.random()*8;
			switch (choice) {
			case 0:
				if(allowedActions.contains(Action.ALL_IN)){
					return Action.ALL_IN;
				}
				break;
			case 1:
				if(allowedActions.contains(Action.BET)){
					return new BetAction(minBet);
				}
				break;
			case 2:
				if(allowedActions.contains(Action.BIG_BLIND)){
					return Action.BIG_BLIND;
				}
				break;
			case 3:
				if(allowedActions.contains(Action.CALL)){
					return Action.CALL;
				}
				break;
			case 4:
				if(allowedActions.contains(Action.CHECK)){
					return Action.CHECK;
				}
				break;
			case 5:
				if(allowedActions.contains(Action.FOLD)){
					return Action.FOLD;
				}
				break;
			case 6:
				if(allowedActions.contains(Action.RAISE)){
					return new RaiseAction(minBet);
				}
				break;
			case 7:
				if(allowedActions.contains(Action.SMALL_BLIND)){
					return Action.SMALL_BLIND;
				}
				break;

			default:
				break;
			}
		}
		
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "RandomBot" + id;
	}

}
