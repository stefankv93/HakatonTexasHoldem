package dragonovisinovi.simulation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.AllInAction;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.BigBlindAction;
import org.mozzartbet.hackathon.actions.CallAction;
import org.mozzartbet.hackathon.actions.FoldAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.actions.SmallBlindAction;
import org.mozzartbet.hackathon.bots.Bot;

import dragonovisinovi.mcts.MCST;

public class DragOnBot implements Bot {
	private RealGameState gameState = new RealGameState();
	private int handStatus;
	private List<Card> boardCards;
	
	@Override
	public void joinedTable(int bigBlind) {
		// TODO Auto-generated method stub
		gameState.getData().setBigBlind(bigBlind);
		handStatus = OngoingHandStatus.JOINED_TABLE;
	}

	@Override
	public void handStarted(Player dealer) {
		// TODO Auto-generated method stub
		gameState.getData().setDealer(dealer);
		gameState.getData().setPotMoney(0);
		gameState.getData().setBet(0);
		gameState.getData().setRaises(0);
		
		handStatus = OngoingHandStatus.GAME_STARTED;
		
		gameState.getData().getPlayers().clear();
		gameState.getData().getActive().clear();
	}

	@Override
	public void actorRotated(Player actor) {
		// TODO Auto-generated method stub
		
		handStatus = OngoingHandStatus.NEW_ACTOR_PROMOTED;
		
		gameState.getData().setCurrentIndex((gameState.getData().getCurrentIndex() +1)%gameState.getData().getActive().size());
		
		
	}

	@Override
	public void playerUpdated(Player player) {
		// TODO Auto-generated method stub
		List<Player> list = gameState.getData().getPlayers();
		if(handStatus == OngoingHandStatus.GAME_STARTED){
			list.add(player);
			if(player.getName().equals(this.getName())){
				gameState.getData().setMyMoney(player.getCash());
				gameState.getData().setMyIndex(list.indexOf(player));
			}
			if(player.getName().equals(gameState.getData().getDealer().getName())){
				gameState.getData().setCurrentIndex(list.indexOf(player));
				gameState.getData().setDealerIndex(gameState.getData().getCurrentIndex());
			}
			if (player.getCash() >= gameState.getData().getBigBlind()) {
				gameState.getData().getActive().add(player);
            }
		}else{
			int id = 0;
			for (Player player2 : list) {
				if(player2.getName() == player.getName()){
					break;
				}
				id++;
				
			}
			list.set(id, player);
		}
	}

	@Override
	public void boardUpdated(List<Card> cards, int bet, int pot) {
		// TODO Auto-generated method stub
		handStatus = OngoingHandStatus.BOARD_UPDATED;
		gameState.getData().setBoardCards(cards);
		gameState.getData().setBet(bet);
		gameState.getData().setPotMoney(pot);
		
		boardCards = cards;
	}

	@Override
	public void playerActed(Player player) {
		// TODO Auto-generated method stub
		List<Player> players = gameState.getData().getActive();
		

		gameState.doAction(player);
		
	}

	@Override
	public Action act(int minBet, int currentBet, Set<Action> allowedActions, int currentAmount) {
		if(isPreFlop()){
			if(allowedActions.contains(Action.CHECK)){
				return Action.CHECK;
			}else if(allowedActions.contains(Action.CALL)){
				return Action.CALL;
			}else return Action.FOLD;
		}else{
			return MCST.MCSTSearch(gameState, 1000);
		}
		
		
	}
	
	private boolean isPreFlop() {
        return this.boardCards.size() == 0;
    }

	@Override
	public String getName() {
		return "draGAn";
	}

}
