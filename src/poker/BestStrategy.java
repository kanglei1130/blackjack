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
		String res = Constants.HIT;

		if(value >= 17) {
			return Constants.STAND;
		}
		if(value <= 7) {
			return Constants.HIT;
		}
		
		//value between 12 and 16
		if(value >= 12 && value <= 16) {
			//dealer between 2 and 6
			if(dealer >= 2 && dealer <= 6) {
				if(hot >=2) {
					return Constants.STAND;
				} else if(1 == hot) {
					if(value == 12 && (dealer == 2 || dealer == 3)) {
						return Constants.HIT;
					} else {
						return Constants.STAND;
					}
				} else if(0 == hot || -1 == hot) {
					if(value == 12 && (dealer == 2 || dealer == 3 || dealer == 4)) {
						return Constants.HIT;
					} else {
						return Constants.STAND;
					}
				} else if(-2 == hot || -3 == hot) {
					if(value == 12 && (dealer == 2 || dealer == 3 || dealer == 4 || dealer == 5)) {
						return Constants.HIT;
					} else if(value ==13 && dealer == 2) {
						return Constants.HIT;
					} else {
						return Constants.STAND;
					}
				} else if(-4 == hot) {
					if(value == 12 && (dealer == 2 || dealer == 3 || dealer == 4 || dealer == 5)) {
						return Constants.HIT;
					} else if(value ==13 && (dealer == 2 || dealer == 3)) {
						return Constants.HIT;
					} else {
						return Constants.STAND;
					}
				} else {
				
				}
			}
		}
		
		//the interesting value is between 8 and 16
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
		}
		if(value == 9) {
			if(dealer <= 6 && dealer >=2 && hot >= -3) {
				return Constants.DOUBLE;
			}
			if(dealer <= 6 && dealer >=3 && hot == -4) {
				return Constants.DOUBLE;
			}
			if(dealer == 6 && hot >= -5) {
				return Constants.DOUBLE;
			}
		}
		if(value == 10) {
			if(dealer <= 6 && dealer >=2) {
				return Constants.DOUBLE;
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
