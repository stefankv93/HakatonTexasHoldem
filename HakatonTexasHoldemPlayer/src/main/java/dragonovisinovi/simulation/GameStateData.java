package dragonovisinovi.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mozzartbet.hackathon.Card;
import org.mozzartbet.hackathon.Deck;
import org.mozzartbet.hackathon.Player;

public class GameStateData implements Cloneable{
	public static final int PRE_FLOP = 0;
	public static final int FLOP = 1;
	public static final int TURN = 2;
	public static final int RIVER = 3;
	public static final int HAND_OVER = 4;
	
	
	private List<Card> myCards = new ArrayList<Card>();
	private List<Card> boardCards  = new ArrayList<Card>();
	private int myMoney;
	private int potMoney;
	private List<Player> players = new ArrayList<Player>(), active = new ArrayList<Player>();
	private Player dealer;
	private int dealerIndex;
	private int myIndex;
	private int currentIndex;
	private int bet;
	private int minBeat;
	private int bigBlind;
	private int raises;
	private int startHandMoney;
	private Player realPlayer;
	
	private int gameState;
	private Deck deck;
	
	
	
	private static final int MAX_RAISES = 3;
	
	public Object clone(){
		GameStateData data = new GameStateData();
		data.myMoney = myMoney;
		data.potMoney = potMoney;
		data.dealer = dealer;
		data.minBeat = minBeat;
		data.startHandMoney = startHandMoney;
		data.myIndex = myIndex;
		data.dealerIndex = dealerIndex;
		data.currentIndex = currentIndex;
		data.realPlayer = realPlayer;
		data.bet = bet;
		data.minBeat = minBeat;
		data.bigBlind = bigBlind;
		data.gameState = gameState;
		
		Deck d = new Deck();
		
		int numOfCards = 0;
		try{
			while(true){
				Card c = deck.deal();
				d.deal(c.getRank(),c.getSuit());
				numOfCards++;
			}
		}catch(IllegalStateException i){ }
		
		data.deck = new Deck();
		try{
			while(true){
				Card c = d.deal();
				data.deck.deal(c.getRank(),c.getSuit());
			}
		}catch(IllegalStateException i){ }
		
		if(numOfCards != 0){
			deck.reset();
			deck.deal(51 - numOfCards);
		}
		
		
		for (Iterator iterator = myCards.iterator(); iterator.hasNext();) {
			data.myCards.add((Card) iterator.next());
			
		}
		for (Iterator iterator = boardCards.iterator(); iterator.hasNext();) {
			data.boardCards.add((Card) iterator.next());
			
		}
		
		for (Iterator iterator = active.iterator(); iterator.hasNext();) {
			data.active.add(((Player) iterator.next()).publicClone());
			
		}
		try{
		for (Iterator iterator = players.iterator(); iterator.hasNext();) {
			int excists = 0;
			Player player = (Player) iterator.next();
			for (Iterator iterator2 = data.active.iterator(); iterator2.hasNext();) {
				Player player2 = (Player) iterator2.next();
				if(player.getName().equals(player2.getName())){
					excists = 1;
					data.players.add(player2);
					break;
				}
			}
			if(excists== 0)
				data.players.add(player.publicClone());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return data;
	}
	public Card [] getMyCards() {
		return realPlayer.getCards();
	}

	public void setMyCards(List<Card> myCards) {
		this.myCards = myCards;
	}

	public List<Card> getBoardCards() {
		return boardCards;
	}

	public void setBoardCards(List<Card> potCards) {
		this.boardCards = potCards;
	}

	public int getMyMoney() {
		return myMoney;
	}

	public void setMyMoney(int myMoney) {
		this.myMoney = myMoney;
	}

	public int getPotMoney() {
		return potMoney;
	}

	public void setPotMoney(int potMoney) {
		this.potMoney = potMoney;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List< Player> getActive() {
		return active;
	}

	public void setActive(List<Player> active) {
		this.active = active;
	}

	public Player getDealer() {
		return dealer;
	}

	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}

	public int getMinBeat() {
		return minBeat;
	}

	public void setMinBeat(int minBeat) {
		this.minBeat = minBeat;
	}

	public int getBigBlind() {
		return bigBlind;
	}
	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}
	public int getDealerIndex() {
		return dealerIndex;
	}
	public void setDealerIndex(int dealerIndex) {
		this.dealerIndex = dealerIndex;
	}
	public int getMyIndex() {
		return myIndex;
	}
	public void setMyIndex(int myIndex) {
		this.myIndex = myIndex;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public int getRaises() {
		return raises;
	}
	public void setRaises(int raises) {
		this.raises = raises;
	}
	public static int getMaxRaises() {
		return MAX_RAISES;
	}
	
	public int getStartHandMoney() {
		return startHandMoney;
	}
	public void setStartHandMoney(int startHandMoney) {
		this.startHandMoney = startHandMoney;
	}
	public void setRealPlayer(Player player) {
		realPlayer = player;
		
	}
	
	public int getGameState() {
		return gameState;
	}
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	
	public Deck getDeck() {
		return deck;
	}
	public void setDeck(Deck deck) {
		this.deck = deck;
		deck.shuffle();
	}
	
	public Player getRealPlayer() {
		return realPlayer;
	}
	
}
