package poker;

import utility.Log;

public class PokerCard {
	
	private static final String TAG = "PokerCard";
	private int value_ = 0;
	private String color_ = "";
	
	public static final String CLUB = "clubs";
	public static final String DIAMOND = "diamonds";
	public static final String HEART = "hearts";
	public static final String SPADE = "spades";
	
	
	public PokerCard(int val, String color) 
	{
		if(val < 1 || val > 13) {
			Log.error(TAG, "Poker card value error:" + val);
		}
		this.value_ = val;
		this.color_ = color; 
	}
	
	public String getColor()
	{
		return color_;	
	}
	
	
	public int getValue() 
	{
		return value_;
	}
	
	public boolean isBigCard() {
		return this.getTTValue() == 10 || this.value_ == 1;
	}
	public boolean isSmallCard() {
		return this.value_ >= 2 && this.value_ <= 6;
	}
	
	public int getTTValue() 
	{
		if(this.value_>9)
		{
			return 10;
		}
		else
		{
		return value_;
		}
	}
	
	public boolean equalValue(PokerCard card) {
		return card.getValue() == this.value_;
	}
}
