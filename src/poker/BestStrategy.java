package poker;

import utility.Constants;

public class BestStrategy {
	public String bestPairStrategy(OneHand hand, PokerCard card, int hot) {
		return null;
	}
	public String bestSoftHandStrategy(OneHand hand, PokerCard card, int hot) {
		return null;
	}
	public String bestHardHandStrategy(OneHand hand, PokerCard card, int hot) {
		int value = hand.hardHandValue();		
		int dealer = card.getTTValue();
		if(value >= 17) {
			return Constants.STAND;
		}
		if(value <= 7) {
			return Constants.HIT;
		}
		//the interesting value is between 8 and 16
		String res = Constants.HIT;
		//double cases
		if(value == 8) {
			if(dealer == 6 && hot >= 1) {
				res = Constants.DOUBLE;
			}
			if(dealer == 5 && hot >= 3) {
				res = Constants.DOUBLE;
			}
			if(dealer == 4 && hot >= 5) {
				res = Constants.DOUBLE;
			}
			if(dealer == 3 && hot >= 6) {
				res = Constants.DOUBLE;
			}
		}
		if(value == 9) {
			if(dealer <= 6 && dealer >=2 && hot >= -3) {
				res = Constants.DOUBLE;
			}
			if(dealer <= 6 && dealer >=3 && hot == -4) {
				res = Constants.DOUBLE;
			}
			if(dealer == 6 && hot >= -5) {
				res = Constants.DOUBLE;
			}
		}
		if(value == 10) {
			if(dealer <= 6 && dealer >=2) {
				res = Constants.DOUBLE;
			}
		}
		return res;
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
}
