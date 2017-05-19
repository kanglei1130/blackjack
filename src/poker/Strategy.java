package poker;

import com.google.gson.Gson;

import utility.Constants;
import utility.Log;

public class Strategy {
	


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
			{"H", "H", "H", "S", "S", "S", "H","H", "H","H"}, //12
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //13
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //14
			{"H", "S", "S", "S", "S", "S", "H","H", "H","H"}, //15
			{"H", "S", "S", "S", "S", "S", "H","H", "H","S"}, //16
			{"S", "S", "S", "S", "S", "S", "S","S", "S","S"}, //17
			{"S", "S", "S", "S", "S", "S", "S","S", "S","S"}, //18
	};

	//column:(Ace+2)-(Ace+9)
	private static String [][] soft = {
		   //1    2    3     4    5    6    7    8    9    10
			{"H", "H", "H", "S", "S", "S", "H","H", "H","H"}, //1 // in case of cannot split again
			{"H", "H", "H", "H", "D", "D", "H", "H", "H", "H"}, //2
			{"H", "H", "H", "H", "D", "D", "H", "H", "H", "H"}, //3
			{"H", "H", "H", "D", "D", "D", "H", "H", "H", "H"}, //4
			{"H", "H", "H", "D", "D", "D", "H", "H", "H", "H"}, //5
			{"H", "H", "D", "D", "D", "D", "H", "H", "H", "H"}, //6
			{"H", "S", "D", "D", "D", "D", "S", "S", "H", "H"}, //7
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
			{"P", "P", "P", "P", "P", "P", "P", "P", "P", "P"}, //8
			{"S", "P", "P", "P", "P", "P", "S", "P", "P", "S"}, //9
			{"S", "S", "S", "S", "S", "S", "S", "S", "S", "S"},	//10
	};
	
	
	public String bestPairStrategy(OneHand hand, PokerCard card, int hot) {
		return null;
	}
	public String bestSoftHandStrategy(OneHand hand, PokerCard card, int hot) {
		return null;
	}
	public String bestHardHandStrategy(OneHand hand, PokerCard card, int hot) {
		return null;
	}
	
	public String BestStrategy(OneHand hand, PokerCard card, int hot) {
		String stra = Constants.STAND;
		if(hand.isPairs()) {
			stra = bestPairStrategy(hand, card, hot);
		} else if(hand.softHand()) {
			stra = bestSoftHandStrategy(hand, card, hot);
		} else {
			stra = bestHardHandStrategy(hand, card, hot);
		}
		return stra;
	}
	
		
	//dealer's strategy
	public static String DealerStrategy(OneHand hand) 
	{
		if(hand.softHand()) {
			if(hand.softHandValue() < 18) {
				return Constants.HIT;
		    } else {
				return Constants.STAND;
			}	
		} else {
			if(hand.softHandValue() < 17) {
				return Constants.HIT;
		    } else {
				return Constants.STAND;
			}	
		}
	}
	
	//player's strategy
	public static String PlayerStrategy(OneHand hand, PokerCard dealerCard, boolean allowDouble, boolean allowSplit)
	{
		int dealerFirstCard = dealerCard.getTTValue();
		String stra = Constants.STAND;
	
		if(hand.numberOfCards() > 2) {
			allowDouble = false;
		}
		
		if(hand.isPairs() && allowSplit == true) {
			stra = pairStrategy(hand, dealerFirstCard);
		} else if(hand.softHand()) {
			stra = softHandStrategy(hand, dealerFirstCard);
		} else {
			stra = hardHandStrategy(hand, dealerFirstCard);
		}
				
		if(allowDouble == false && stra.equals(Constants.DOUBLE)) {
			stra = Constants.HIT;
		}
		return stra;
	}
	
	public static String pairStrategy(OneHand pair, int dealerFirstCard) {	
		int playerCard = pair.firstCard().getTTValue() - 1;
		int dealerFirstCardIndex = dealerFirstCard - 1;
		String strategy = pairs[playerCard][dealerFirstCardIndex];
		if (strategy.equals("P")) {
			return Constants.SPLIT;
		} else if (strategy.equals("H")) {
			return Constants.HIT;
		} else if (strategy.equals("D")) {
			return Constants.DOUBLE;
		} else if (strategy.equals("S")) {
			return Constants.STAND;
		} else {
			Log.error(TAG, "lack strategy split");
			return null;
		}
	}
	
	public static String softHandStrategy(OneHand softhand, int dealerFirstCard) {
		
		int playerSumExcpAce = 0;
		
		playerSumExcpAce = softhand.softHandValue()-12;
		if(playerSumExcpAce > 7) {
			playerSumExcpAce = 7;
		}
		int dealerFirstCardIndex = dealerFirstCard-1;
		if(dealerFirstCardIndex > 9) {
			dealerFirstCardIndex = 9;
		}
		
		String strategy = soft[playerSumExcpAce][dealerFirstCardIndex];
			
		if (strategy.equals("D")) {
			return Constants.DOUBLE;
		} else if (strategy.equals("H")) {
			return Constants.HIT;
		} else if (strategy.equals("D")) {
			return Constants.DOUBLE;
		} else if (strategy.equals("S")) {
			return Constants.STAND;
		} else {
			Log.error(TAG, "lack strategy softhand");
			return null;
		}
	}
	
	public static String hardHandStrategy(OneHand hand, int dealerFirstCard) {
		int playerSumCard = 0;
		int value = hand.softHandValue();
		if (hand.softHandValue()<18) {
			playerSumCard = value >=5 ? value-5 : 0;
		} else {
			playerSumCard = 13;
		}
			
		int dealerFirstCardIndex = dealerFirstCard-1;
		String strategy = hard[playerSumCard][dealerFirstCardIndex];
			
		if (strategy.equals("H")) {
			return Constants.HIT;
		} else if (strategy.equals("D")) {
			return Constants.DOUBLE;
		} else if (strategy.equals("S")) {
			return Constants.STAND;
		} else {
			Log.error(TAG, "lack strategy hard");
			return null;
		}
	}
}
