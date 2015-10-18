package dragonovisinovi.mcts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.mozzartbet.hackathon.Player;
import org.mozzartbet.hackathon.actions.Action;

import dragonovisinovi.simulation.GameState;

public class Node {
	private Action move;
	private Node parrent;
	private List<Node> children;
	private double wins;
	private int visits;
	private List<Action> untriedMoves;
	//private int playerJustMoved;//dodati player
	private Player playerJustMoved;
	private GameState state;
	private final static double epsilon = 1e-6;
	private Random r;
	
	public Node(){
		children = new ArrayList<>();
		untriedMoves = new ArrayList<>();
		wins = 0;
		visits = 0;
		r = new Random();
	}
	
	public Node(Node parrent, Action move, GameState state){
		this();
		this.parrent = parrent;
		this.move = move;
		this.state = state;
		untriedMoves = state.getMoves();
		//playerJustMoved = 
		
	}
	
	public Node selectChild(){
		 Node selected = null;
	        double bestValue = Double.MIN_VALUE;
	        for (Node c : children) {
	            double uctValue = c.wins / (c.visits + epsilon) +
	                       Math.sqrt(Math.log(c.visits+1) / (c.visits + epsilon)) +
	                           r.nextDouble() * epsilon;
	            // small random number to break ties randomly in unexpanded nodes
	            if (uctValue > bestValue) {
	                selected = c;
	                bestValue = uctValue;
	            }
	        }
	        return selected;
	}
	
	public Node addChild(Action action){
		Node node = new Node(this, action, state);
		untriedMoves.remove(action);	//ovo dobro proveriti!!!
		children.add(node);
		return node;
	}
	
	public List<Action> getUntriedMoves(){
		return untriedMoves;
	}
	
	public List<Node> getChildren(){
		return children;
	}
	
	public Action getMove(){
		return move;
	}
	
	public double getValue(){
		return wins;
	}
	
	public void update(double result){
			visits++;
			wins+=result;
	}
	
	public Node getParrent(){
		return parrent;
	}
	
	public int getVisits(){
		return visits;
	}
	
	public String TreeToString(int indent){
		String s = indentString(indent) + toString();
		for(Node n: children){
			s += n.TreeToString(indent + 1); 
		}
		return s;
	}
	
	public String childrenToString(){
		String s = "";
		for(Node n:children){
			s += n.toString() + "\n";
		}
		
		return s;
	}
	
	private String indentString(int indent){
		String s = "\n";
		for(int i = 0; i<indent; i++){
			s += "| ";
		}
		return s;
	}
	
	@Override
	public String toString() {
		return "Move: " + move.toString() +  " W/V: " + wins + "/" + visits;
	}
	
	
}
