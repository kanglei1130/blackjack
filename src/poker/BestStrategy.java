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
}
