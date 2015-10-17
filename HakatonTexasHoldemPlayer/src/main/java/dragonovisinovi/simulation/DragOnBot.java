package dragonovisinovi.simulation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.bots.Bot;

public class DragOnBot implements Bot {
	private RealGameState gameState = new RealGameState();
	private int handStatus;
	
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
		
		
		handStatus = OngoingHandStatus.GAME_STARTED;
		
		gameState.getData().getPlayers().clear();
		gameState.getData().getActive().clear();
	}

	@Override
	public void actorRotated(Player actor) {
		// TODO Auto-generated method stub
		
		handStatus = OngoingHandStatus.NEW_ACTOR_PROMOTED;
		
		
		
	}

	@Override
	public void playerUpdated(Player player) {
		// TODO Auto-generated method stub
		List<Player> list = gameState.getData().getPlayers();
		if(handStatus == OngoingHandStatus.GAME_STARTED){
			list.add(player);
			if(player.getBot().getName().equals(this.getName())){
				gameState.getData().setMyIndex(list.indexOf(player));
			}
			if(player.getBot().getName().equals(gameState.getData().getDealer().getName())){
				gameState.getData().setDealerIndex(list.indexOf(player));
			}
			if (player.getCash() >= gameState.getData().getBigBlind()) {
				gameState.getData().getActive().add(player);
            }
		}else{
			
		}
	}

	@Override
	public void boardUpdated(List<Card> cards, int bet, int pot) {
		// TODO Auto-generated method stub
		handStatus = OngoingHandStatus.BOARD_UPDATED;
		gameState.getData().setBoardCards(cards);
		gameState.getData().setBet(bet);
		gameState.getData().setPotMoney(pot);
		

	}

	@Override
	public void playerActed(Player player) {
		// TODO Auto-generated method stub
		List<Player> players = gameState.getData().getPlayers();
		int id =  0;
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			Player player2 = (Player) iterator.next();
			if(player2.getName().equals(player2.getName())){
				break;
			}
			id++;
		}

		players.set(id, player);
		
	}

	@Override
	public Action act(int minBet, int currentBet, Set<Action> allowedActions, int currentAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
