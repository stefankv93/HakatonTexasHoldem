package dragonovisinovi.simulation;

import java.util.Iterator;
import java.util.List;

import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.AllInAction;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.CallAction;
import org.mozzartbet.hackathon.actions.CheckAction;
import org.mozzartbet.hackathon.actions.FoldAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.actions.SmallBlindAction;
import org.mozzartbet.hackathon.Player;

public class RealGameState implements GameState {
	private GameStateData data;
	
	@Override
	public GameState clone() {
		// TODO Auto-generated method stub
		RealGameState r = new RealGameState();
		r.data = (GameStateData) data.clone();
		return r;
	}

	@Override
	public void doAction(Player p) {
		// TODO Auto-generated method stub
//		data.getPlayers().remove(p.getName());//ovo mora da se menja!!!!
//		data.getPlayers().add(p);

		int id =  0;
		for (Iterator iterator = data.getActive().iterator(); iterator.hasNext();) {
			Player player2 = (Player) iterator.next();
			if(player2.getName().equals(p.getName())){
				break;
			}
			id++;
		}
		if(p.getAction() instanceof FoldAction){
			if(id == data.getActive().size() - 1){ 
				data.setCurrentIndex(0);
			}else{
				data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
			}
			data.getActive().remove(id);
			
		}else if(p.getAction() instanceof AllInAction){
			data.setPotMoney(data.getPotMoney() + p.getCash());
			p.payCash(p.getCash());
		}else if(p.getAction() instanceof BetAction){
			data.setPotMoney(data.getPotMoney() + p.getAction().getAmount());
			p.payCash(p.getAction().getAmount());
		}else if(p.getAction() instanceof BetAction){
			data.setPotMoney(data.getPotMoney() + data.getBigBlind());
			p.payCash(data.getBigBlind());
		}else if(p.getAction() instanceof CallAction){
			data.setPotMoney(data.getPotMoney() + data.getBet());
			p.payCash(data.getBet());
		}else if(p.getAction() instanceof RaiseAction){
			data.setPotMoney(data.getPotMoney() + p.getAction().getAmount() + data.getBet());
			data.setBet(data.getBet() + p.getAction().getAmount());
			p.payCash(+ p.getAction().getAmount());
		}else if(p.getAction() instanceof SmallBlindAction){
			data.setPotMoney(data.getBigBlind()/2);
			data.setBet(data.getBet() + p.getAction().getAmount());
			p.payCash(data.getBet() + p.getAction().getAmount());
		}
	}

	@Override
	public double simulate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Action> getMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	public GameStateData getData() {
		return data;
	}

	public void setData(GameStateData data) {
		this.data = data;
	}

	@Override
	public void doAction(Action a) {
		// TODO Auto-generated method stub
		int id =  0;
		Player p = data.getActive().get(data.getCurrentIndex());
		for (Iterator iterator = data.getActive().iterator(); iterator.hasNext();) {
			Player player2 = (Player) iterator.next();
			if(player2.getName().equals(p.getName())){
				break;
			}
			id++;
		}
		if(p.getAction() instanceof FoldAction){
			if(id == data.getActive().size() - 1){ 
				data.setCurrentIndex(0);
			}else{
				data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
			}
			data.getActive().remove(id);
			
		}else{ 
			
		
			if(p.getAction() instanceof AllInAction){
				data.setPotMoney(data.getPotMoney() + p.getCash());
				p.payCash(p.getCash());
			}else if(p.getAction() instanceof BetAction){
				data.setPotMoney(data.getPotMoney() + p.getAction().getAmount());
				p.payCash(p.getAction().getAmount());
			}else if(p.getAction() instanceof BetAction){
				data.setPotMoney(data.getPotMoney() + data.getBigBlind());
				p.payCash(data.getBigBlind());
			}else if(p.getAction() instanceof CallAction){
				data.setPotMoney(data.getPotMoney() + data.getBet());
				p.payCash(data.getBet());
			}else if(p.getAction() instanceof RaiseAction){
				data.setPotMoney(data.getPotMoney() + p.getAction().getAmount() + data.getBet());
				data.setBet(data.getBet() + p.getAction().getAmount());
				p.payCash(+ p.getAction().getAmount());
			}else if(p.getAction() instanceof SmallBlindAction){
				data.setPotMoney(data.getBigBlind()/2);
				data.setBet(data.getBet() + p.getAction().getAmount());
				p.payCash(data.getBet() + p.getAction().getAmount());
			}
			data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
		}
	}
	

}
