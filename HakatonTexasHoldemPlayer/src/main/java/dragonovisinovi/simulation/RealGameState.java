package dragonovisinovi.simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.AllInAction;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.CallAction;
import org.mozzartbet.hackathon.actions.CheckAction;
import org.mozzartbet.hackathon.actions.FoldAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.actions.SmallBlindAction;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.TableType;

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
			p.setBet(p.getCash());
			p.payCash(p.getCash());
		}else if(p.getAction() instanceof BetAction){
			data.setPotMoney(data.getPotMoney() + p.getAction().getAmount());
			p.setBet(p.getBet() +p.getAction().getAmount());
			p.payCash(p.getAction().getAmount());
		}else if(p.getAction() instanceof BetAction){
			data.setPotMoney(data.getPotMoney() + data.getBigBlind());
			p.setBet(p.getBet() +data.getBigBlind());
			p.payCash(data.getBigBlind());
		}else if(p.getAction() instanceof CallAction){
			data.setPotMoney(data.getPotMoney() + data.getBet());
			p.setBet(p.getBet() +data.getBigBlind());
			p.payCash(data.getBet());
		}else if(p.getAction() instanceof RaiseAction){
			data.setPotMoney(data.getPotMoney() + p.getAction().getAmount() + data.getBet());
			data.setBet(data.getBet() + p.getAction().getAmount());
			p.setBet(p.getBet() +  p.getAction().getAmount() + data.getBet());
			p.payCash( p.getAction().getAmount());
			data.setRaises(data.getRaises()+ 1);
		}else if(p.getAction() instanceof SmallBlindAction){
			data.setPotMoney(data.getPotMoney() + data.getBigBlind()/2);
			data.setBet(data.getBet() + data.getBigBlind()/2);
			p.setBet(data.getBet() + data.getBigBlind()/2);
			p.payCash(data.getBigBlind()/2);
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
		List<Action> actions = new ArrayList<Action>();
		Player actor = data.getActive().get(data.getCurrentIndex());
        if (actor.isAllIn()) {
            actions.add(Action.CHECK);
        } else {
            int actorBet = actor.getBet();
            if (data.getBet() == 0) {
                actions.add(Action.CHECK);
                if (data.getRaises()< data.getMaxRaises() || data.getActive().size() == 2) {
                    actions.add(Action.BET);
                }
            } else {
                if (actorBet < data.getBet()) {
                    actions.add(Action.CALL);
                    if (data.getRaises()< data.getMaxRaises() || data.getActive().size() == 2) {
                        actions.add(Action.RAISE);
                    }
                } else {
                    actions.add(Action.CHECK);
                    if (data.getRaises()< data.getMaxRaises() || data.getActive().size() == 2) {
                        actions.add(Action.RAISE);
                    }
                }
            }
            actions.add(Action.FOLD);
        }
        return actions;
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
		
		Player p = data.getActive().get(data.getCurrentIndex());
		
		doAction(p);
//		for (Iterator iterator = data.getActive().iterator(); iterator.hasNext();) {
//			Player player2 = (Player) iterator.next();
//			if(player2.getName().equals(p.getName())){
//				break;
//			}
//			id++;
//		}
//		if(p.getAction() instanceof FoldAction){
//			if(id == data.getActive().size() - 1){ 
//				data.setCurrentIndex(0);
//			}else{
//				data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
//			}
//			data.getActive().remove(id);
//			
//		}else{ 
//			
//		
//			if(p.getAction() instanceof AllInAction){
//				data.setPotMoney(data.getPotMoney() + p.getCash());
//				p.payCash(p.getCash());
//			}else if(p.getAction() instanceof BetAction){
//				data.setPotMoney(data.getPotMoney() + p.getAction().getAmount());
//				p.payCash(p.getAction().getAmount());
//			}else if(p.getAction() instanceof BetAction){
//				data.setPotMoney(data.getPotMoney() + data.getBigBlind());
//				p.payCash(data.getBigBlind());
//			}else if(p.getAction() instanceof CallAction){
//				data.setPotMoney(data.getPotMoney() + data.getBet());
//				p.payCash(data.getBet());
//			}else if(p.getAction() instanceof RaiseAction){
//				data.setPotMoney(data.getPotMoney() + p.getAction().getAmount() + data.getBet());
//				data.setBet(data.getBet() + p.getAction().getAmount());
//				p.payCash(+ p.getAction().getAmount());
//			}else if(p.getAction() instanceof SmallBlindAction){
//				data.setPotMoney(data.getBigBlind()/2);
//				data.setBet(data.getBet() + p.getAction().getAmount());
//				p.payCash(data.getBet() + p.getAction().getAmount());
//			}
//			data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
//		}
	}
	

}
