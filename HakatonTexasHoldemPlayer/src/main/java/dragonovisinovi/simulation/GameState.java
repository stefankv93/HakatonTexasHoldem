package dragonovisinovi.simulation;

import java.util.List;

import org.mozzartbet.hackathon.actions.Action;
import org.mozzartbet.hackathon.Player;

public interface GameState {
	public GameState clone();
	public void doAction(Player p);
	public void doAction(Action a);
	public double simulate();
	public List<Action> getMoves();
	public void setResult(double rez);
	public double getResult();
	public double getMoney();
}
