package dragonovisinovi;

import java.util.List;

import org.mozzartbet.hackathon.actions.Action;

public interface GameState {
	public GameState clone();
	public void doAction(Player p, Action a);
	public double simulate();
	public List<Action> getMoves();
	public double getResult();
}
