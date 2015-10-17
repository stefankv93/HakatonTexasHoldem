package dragonovisinovi.simulation;

import java.util.List;

import org.mozzartbet.hackathon.actions.Action;
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
		data.getPlayers().remove(p.getName());//ovo mora da se menja!!!!
		data.getPlayers().add(p);
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
		
	}
	

}
