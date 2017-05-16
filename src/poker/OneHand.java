package poker;

import java.util.ArrayList;
import java.util.List;

import utility.Log;

public class OneHand {
	
	private List<PokerCard> hand_ = new ArrayList<PokerCard>(); //
	private double bet_ = 0.0;
	private static final String TAG = "OneHand";
	private static int kBustThreshold = 999;
	

	private int numberOfAce;
	private int handValue;
	
	public OneHand() {
		//dealer's hand no bet
		numberOfAce = 0;
		handValue = 0;
	}
	
	//constructor
	public OneHand(double bet) {
		numberOfAce = 0;
		handValue = 0;
		this.bet_ = bet;
	}
	
	public double getBet() {
		return this.bet_;
	}
	
	public PokerCard firstCard() {
		return this.hand_.get(0);
	}
	
	
	public int numberOfCards() {
		return this.hand_.size();
	}
	
	
	//only entrance where the object accept Card
	public void hit(PokerCard card) {
		if(card.getValue() == 1) {
			this.numberOfAce++;
		}
		this.handValue += card.getTTValue();
		if (this.handValue > 21) {
			this.handValue = kBustThreshold;
		}
		this.hand_.add(card);
	}
	

	
	//to actual split
	public OneHand split() 
	{
		assert this.hand_.size() == 2;
		OneHand anotherhand = new OneHand(this.bet_);
		PokerCard card = this.hand_.remove(1);
		anotherhand.hit(card);
		
		this.handValue -= card.getTTValue();
		if(card.getTTValue() == 1) {
			this.numberOfAce--;
		}
		
		return anotherhand;
	}

	public void doubleDown(PokerCard card) 
	{
		this.bet_ *= 2;
		hit(card);
	}
	
	
	public boolean isBlackJack() 
	{
		if (hand_.size()==2)
		{
			if (hand_.get(0).getValue() == 1 && hand_.get(1).getValue() >= 10)
			{
				return true;
			}
			else if (hand_.get(0).getValue() >= 10 && hand_.get(1).getValue() == 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else 
		{
			return false;
		}
	}
	
	//Ace is 1
	public int hardHandValue() {
		return this.handValue;
	}

	//Ace can be 11 (not necessary)
	public int softHandValue() 
	{
		if (this.handValue < 12 && this.numberOfAce > 0) {
			return this.handValue + 10;
		} else {
			return this.handValue;
		}
	}
	
	public boolean softHand()
	{
		if(isBlackJack()||isPairs()) {
			return false;
		} else {
		    if (this.handValue < 12 && this.numberOfAce > 0) {
		    	return true;
		    } else {
		    	return false;
		    }
         }
	}
	
	public boolean isPairs()
	{
		if(this.hand_.get(0).getTTValue() == this.hand_.get(1).getTTValue() && this.hand_.size()==2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
