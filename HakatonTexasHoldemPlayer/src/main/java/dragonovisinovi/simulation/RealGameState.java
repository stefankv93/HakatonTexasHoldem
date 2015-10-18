package dragonovisinovi.simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.actions.AllInAction;
import org.mozzartbet.hackathon.actions.BetAction;
import org.mozzartbet.hackathon.actions.BigBlindAction;
import org.mozzartbet.hackathon.actions.CallAction;
import org.mozzartbet.hackathon.actions.CheckAction;
import org.mozzartbet.hackathon.actions.FoldAction;
import org.mozzartbet.hackathon.actions.RaiseAction;
import org.mozzartbet.hackathon.actions.SmallBlindAction;
import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.TableType;

public class RealGameState implements GameState {
	private GameStateData data = new GameStateData();
	private double res;
	
	@Override
	public GameState clone() {
		// TODO Auto-generated method stub
		RealGameState r = new RealGameState();
		r.data = (GameStateData) data.clone();
		return r;
	}

	@Override
	public void doAction(Player pl) {
		// TODO Auto-generated method stub
//		data.getPlayers().remove(p.getName());//ovo mora da se menja!!!!
//		data.getPlayers().add(p);

		int id =  0;
		for (Iterator iterator = data.getActive().iterator(); iterator.hasNext();) {
			Player player2 = (Player) iterator.next();
			if(player2.getName().equals(pl.getName())){
				break;
			}
			id++;
		}
		Player p = data.getActive().get(id);
		p.setAction(pl.getAction());
		
		if(pl.getAction() instanceof FoldAction){
			if(id == data.getActive().size() - 1){ 
				data.setCurrentIndex(0);
			}else{
//				data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
			}
			data.getActive().remove(id);
			
		}else if(pl.getAction() instanceof AllInAction){
			data.setPotMoney(data.getPotMoney() + p.getCash());
			p.setBet(p.getCash());
			p.payCash(p.getCash());
		}else if(pl.getAction() instanceof BetAction){
			data.setPotMoney(data.getPotMoney() + pl.getAction().getAmount());
			p.setBet(p.getBet() + pl.getAction().getAmount());
			p.payCash(pl.getAction().getAmount());
		}else if(pl.getAction() instanceof BigBlindAction){
			data.setPotMoney(data.getPotMoney() + data.getBigBlind());
			p.setBet(p.getBet() +data.getBigBlind());
			p.payCash(data.getBigBlind());
		}else if(pl.getAction() instanceof CallAction){
			data.setPotMoney(data.getPotMoney() + data.getBet());
			p.setBet(p.getBet() +data.getBigBlind());
			p.payCash(data.getBet());
		}else if(pl.getAction() instanceof RaiseAction){
			data.setPotMoney(data.getPotMoney() + pl.getAction().getAmount() + data.getBet());
			data.setBet(data.getBet() + pl.getAction().getAmount());
			p.setBet(p.getBet() +  pl.getAction().getAmount() + data.getBet());
			p.payCash( pl.getAction().getAmount() + data.getBet());
			data.setRaises(data.getRaises()+ 1);
		}else if(pl.getAction() instanceof SmallBlindAction){
			data.setPotMoney(data.getPotMoney() + data.getBigBlind()/2);
			data.setBet(data.getBet() + data.getBigBlind()/2);//TODO mozda ne treba!!!
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
		if(data.getGameState() == GameStateData.HAND_OVER){
			return actions;
		}
		if(data.getActive().size() <= 1) return actions;
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
		return res;
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
		p.setAction(a);
		doAction(p);
		
		if(! (p.getAction() instanceof FoldAction)){
			data.setCurrentIndex((data.getCurrentIndex() + 1 )% data.getActive().size());
		}
		
		if(getData().getDealer().getName().equals(p.getName())){
			//if(getData().getBet() == 0) {
				getData().setGameState(getData().getGameState()+1);
				if(getData().getGameState() < GameStateData.HAND_OVER){
					getData().getBoardCards().add(getData().getDeck().deal());
				}
			//}
		}
		
		
		
	}

	@Override
	public void setResult(double rez) {
		res = rez;
	}

	@Override
	public double getMoney() {
		return data.getPlayers().get(data.getMyIndex()).getCash();//getActive().get(data.getMyIndex()).getCash();
	}
	

}
