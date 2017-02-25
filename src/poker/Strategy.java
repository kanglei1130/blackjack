package poker;

import ultility.Log;

public class Strategy {
	
	//enum 
	public static final String HIT = "H"; 
	public static final String DOUBLE = "D";
	public static final String SPLIT = "P";
 	public static final String STAND = "S";

 	private static String TAG = "Strategy";
	
	/*H=Hit, S=Stand, P =Split, 
	 * D=Double(Hit if not allowed),
	 * DS =Double(Stand if not allowed)
	 */
	
	private static String [][] hard = {
		 //   1   2    3     4   5    6    7   8     9   10
			{"H", "H", "H", "H", "H", "H", "H","H", "H","H"}, //5
			{"H", "H", "H", "H", "H", "H", "H","H", "H","H"}, //6
			{"H", "H", "H", "H", "H", "H", "H","H", "H","H"}, //7
			{"H", "H", "H", "H", "H", "H", "H","H", "H","H"}, //8
			{"H", "H", "D", "D", "D", "D", "H","H", "H","H"}, //9
			{"H", "D", "D", "D", "D", "D", "D","D", "D","H"}, //10
			{"H", "D", "D", "D", "D", "D", "D","D", "D","D"}, //11
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //12
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //13
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //14
			{"H", "S", "S", "S", "S", "S", "H","H", "S","H"}, //15
			{"H", "S", "S", "S", "S", "S", "H","S", "S","H"}, //16
			{"S", "S", "S", "S", "S", "S", "S","S", "S","S"}, //17
			{"S", "S", "S", "S", "S", "S", "S","S", "S","S"}, //18
	};

	//column:(Ace+2)-(Ace+9)
	private static String [][] soft = {
		   //1    2    3     4    5    6    7    8    9    10
			{"H", "H", "H", "H", "D", "D", "H", "H", "H", "H"}, //2
			{"H", "H", "H", "H", "D", "D", "H", "H", "H", "H"}, //3
			{"H", "H", "H", "D", "D", "D", "H", "H", "H", "H"}, //4
			{"H", "H", "H", "D", "D", "D", "H", "H", "H", "H"}, //5
			{"H", "H", "D", "D", "D", "D", "H", "H", "H", "H"}, //6
			{"H", "S", "D", "D", "D", "D", "D", "S", "H", "H"}, //7
			{"S", "S", "S", "S", "S", "S", "S", "S", "S", "S"}, //8
			{"S", "S", "S", "S", "S", "S", "S", "S", "S", "S"}, //9
			
	};

	//(2,2)-(A,A)
	private static String [][] pairs = {
		   //1    2    3     4    5    6    7    8    9    10
		    {"P", "P", "P", "P", "P", "P", "P", "P", "P", "P"}, //1
			{"H", "P", "P", "P", "P", "P", "P", "H", "H", "H"}, //2
			{"H", "P", "P", "P", "P", "P", "P", "H", "H", "H"}, //3
			{"H", "H", "H", "H", "P", "P", "H", "H", "H", "H"}, //4
			{"H", "D", "D", "D", "D", "D", "D", "D", "D", "H"}, //5
			{"H", "P", "P", "P", "P", "P", "H", "H", "H", "H"}, //6
			{"H", "P", "P", "P", "P", "P", "P", "H", "H", "H"}, //7
			{"S", "P", "P", "P", "P", "P", "P", "P", "S", "S"}, //8
			{"S", "P", "P", "P", "P", "P", "S", "P", "P", "S"}, //9
			{"S", "S", "S", "S", "S", "S", "S", "S", "S", "S"},	//10
	};
		
	//dealer's strategy
	public String DealerStrategy(OneHand hand) 
	{
		if(hand.softHand())
		{
			if(hand.playerCardValue() < 18)
			{
				return HIT;
		    }
			
			else   
			{
				return STAND;
			}	
		}
		else
		{
			if(hand.playerCardValue()<17)
			{
				return HIT;
		    }
			else   
			{
				return STAND;
			}	
		}
	}
	
	//player's strategy
	public String PlayerStrategy(OneHand hand, int dealerFirstCard)
	{
		if(hand.isPairs())
		{
			return pairStrategy(hand, dealerFirstCard);
		}
		else if(hand.softHand())
		{
			return softHandStrategy(hand, dealerFirstCard);
		}
		else
		{
			return hardHandStrategy(hand, dealerFirstCard);
		}
	}
	
	public String pairStrategy(OneHand pair, int dealerFirstCard) {	
		int playerCard = pair.firstCard().getTTValue() - 1;
		int dealerFirstCardIndex = dealerFirstCard - 1;
		String strategy = pairs[playerCard][dealerFirstCardIndex];
		if (strategy.equals("P")) {
			return SPLIT;
		} else if (strategy.equals("H")) {
			return HIT;
		} else if (strategy.equals("D")) {
			return DOUBLE;
		} else if (strategy.equals("S")) {
			return STAND;
		} else {
			Log.error(TAG, "lack strategy split");
			return null;
		}
	}
	
	public String softHandStrategy(OneHand softhand, int dealerFirstCard) {
		
		int playerSumExcpAce = 0;
		playerSumExcpAce = softhand.playerCardValue()-2-11;
		if(playerSumExcpAce > 7) {
			playerSumExcpAce = 7;
		}
		int dealerFirstCardIndex = dealerFirstCard-1;
		if(dealerFirstCardIndex > 9) {
			dealerFirstCardIndex = 9;
		}
			
		String strategy = soft[playerSumExcpAce][dealerFirstCardIndex];
			
		if (strategy.equals("D")) {
			return DOUBLE;
		} else if (strategy.equals("H")) {
			return HIT;
		} else if (strategy.equals("D")) {
			return DOUBLE;
		} else if (strategy.equals("S")) {
			return STAND;
		} else {
			Log.error(TAG, "lack strategy softhand");
			return null;
		}
	}
	
	public String hardHandStrategy(OneHand hand, int dealerFirstCard) {
		int playerSumCard = 0;
		if (hand.playerCardValue()<18) {
			playerSumCard = hand.playerCardValue()-5;
		} else {
			playerSumCard = 13;
		}
			
		int dealerFirstCardIndex = dealerFirstCard-1;
		String strategy = hard[playerSumCard][dealerFirstCardIndex];
			
		if (strategy.equals("H")) {
			return HIT;
		} else if (strategy.equals("D")) {
			return DOUBLE;
		} else if (strategy.equals("S")) {
			return STAND;
		} else {
			Log.error(TAG, "lack strategy hard");
			return null;
		}
	}
}
