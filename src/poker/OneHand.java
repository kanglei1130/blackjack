package poker;

import java.util.ArrayList;
import java.util.List;
import ultility.Log;

public class OneHand {
	
	private List<PokerCard> hand_ = new ArrayList<PokerCard>(); //
	private double bet_ = 0.0;
	private static final String TAG = "OneHand";
	private static int kBustThreshold = 999;
	
	private boolean splitted_ = false;
	private int split_index_ = -1;

	public OneHand() {
		//dealer's hand no bet
	}
	
	//constructor
	public OneHand(double bet) {
		this.bet_ = bet;
	}
	
	public double getBet() {
		return this.bet_;
	}
	
	public PokerCard firstCard() {
		return this.hand_.get(0);
	}
	
	public PokerCard secondCard() {
		return this.hand_.get(1);
	}
		
	public List<PokerCard> getCards() {
		return this.hand_;
	}
	
	public void hit(PokerCard card) {
		this.hand_.add(card);
	}
	
	public void setSplitIndex(int index) {
		this.splitted_ = true;
		this.split_index_ = index;
	}
	
	//to actual split
	//agreement: do not change Lei's code
	public OneHand split() 
	{
		assert this.hand_.size() == 2;
		OneHand anotherhand = new OneHand(this.bet_);
		PokerCard card = this.hand_.remove(1);
		anotherhand.hit(card);
		if(false == this.splitted_) {
			this.split_index_ = 0;
			this.splitted_ = true;
		}
		anotherhand.setSplitIndex(this.split_index_ + 1);
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

	//the value of onehand
	public int playerCardValue() 
	{
		int numOfAce = 0;
		int handvalue_ = 0;
		for (int i = 0; i < hand_.size(); ++i)
		{
			if (hand_.get(i).getValue() >= 10)
			{
				handvalue_ += 10;
			}
			else 
			{
				handvalue_ += hand_.get(i).getValue();
			}

			if (hand_.get(i).getValue() == 1)
			{
				numOfAce++;
			}
		}
		if (handvalue_ < 12 && numOfAce > 0)
		{
			handvalue_ += 10;
			numOfAce--;
		}
		if (handvalue_ > 21) 
		{
			handvalue_ = kBustThreshold;
		}
		return handvalue_;
	}
	
	public boolean softHand()
	{
		if(isBlackJack()||isPairs())
		{
			return false;
		}
		else
		{
			int numOfAce = 0;
			int handvalue_ = 0;
		    for (int i = 0; i < hand_.size(); i++)
		    {
			     if (hand_.get(i).getValue() >= 10)
			     {
				     handvalue_ += 10;
			     }
			     else 
			     {
			    	 handvalue_ += hand_.get(i).getValue();
			     }
			     if (hand_.get(i).getValue() == 1)
			     {
			    	 numOfAce++;
			     }
			     }
		    if (handvalue_ < 12 && numOfAce > 0)
		    {
		    	return true;
		    }
		    else
		    {
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
