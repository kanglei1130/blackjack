package poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
	List<OneHand> hands_ = null;
	
	Strategy stra_;
	
	private double cash_ = 0.0;
	
	public OneHand getOneHand(int i) {
		return hands_.get(i);
	}
	
	public void removeOneHand(int i) {
		//if(this.hands_ != null)
		this.hands_.remove(i);
	}
	
	public void addOneHand(OneHand hand) {
		hands_.add(hand);
	}
	
	public int numberOfHands() {
		return this.hands_.size();
	}
	
	
	public Player() {
		this.hands_ = new ArrayList<OneHand>();
	}
}
