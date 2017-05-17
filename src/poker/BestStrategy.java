package poker;

import utility.Constants;

public class BestStrategy {
	public static String bestPairStrategy(OneHand hand, PokerCard card, int hot) {
		int value = hand.hardHandValue();		
		//Pair of A
		if(value == 2) {
			return Constants.SPLIT;
		}
		//Pair of 10
		if(value == 20) {
			
		}
		return Constants.SPLIT;
	}
	public static String bestSoftHandStrategy(OneHand hand, PokerCard card, int hot) {
		int value = hand.softHandValue();		
		int dealer = card.getTTValue();
		boolean allowDouble = (hand.numberOfCards() == 2);
		
		if(value == 20) {
			if(!allowDouble) {
				return Constants.STAND;
			}
			if(dealer == 6 && hot >= 4) {
				return Constants.DOUBLE;
			}
			if(dealer == 5 && hot >= 5) {
				return Constants.DOUBLE;
			}
			return Constants.STAND;
		}
		if(value == 19) {
			if(!allowDouble) {
				return Constants.STAND;
			}
			if(dealer == 6 && hot >= -1) {
				return Constants.DOUBLE;
			}
			if(dealer == 5 && hot >= 2) {
				return Constants.DOUBLE;
			}
			if(dealer == 4 && hot >= 3) {
				return Constants.DOUBLE;
			}
			if(dealer == 3 && hot >= 4) {
				return Constants.DOUBLE;
			}
			return Constants.STAND;
		}
		if(value == 18) {
			if(dealer == 2) {
				if(hot >= 0 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.STAND;
				}
			}
			if(dealer >= 3 && dealer <= 6) {
				if(allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.STAND;
				}
			}
			if(dealer == 7 || dealer == 8) {
				//no matter the hotness, there is small difference
				//between HIT/STAND
				return Constants.STAND;
			}
			if(dealer == 9 || dealer == 10 || dealer == 1) {
				return Constants.HIT;
			}
		}
		if(value == 17) {
			if(dealer == 2) {
				if(hot >= 1 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer >= 3 && dealer <= 6) {
				if(allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
		}
		//make it simple to remember, always hit under soft 17, when against 7,8,9,10,A
		if(value <= 17) {
			if(dealer == 2 || dealer == 7 || dealer == 8 || dealer == 9 || dealer == 10 || dealer == 1) {
				return Constants.HIT;
			}
		}
		///////////////////////////A + 5
		if(value == 16) {
			if(dealer == 3) {
				if(hot >= 3 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 4) {
				if(hot >= -3 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 5 || dealer == 6) {
				if(allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
		}
		/////////////////// A + 4
		if(value <= 15) {
			if(dealer == 3) {
				return Constants.HIT;
			}
		}
		if(value == 15) {
			if(dealer == 4) {
				if(hot >= 0 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 5 || dealer == 6) {
				if(allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
		}
		//A + 3
		if(value == 14) {
			if(dealer == 4) {
				if(hot >= 4 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 5) {
				if(hot >= 0 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 6) {
				if(hot >= -3 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
		}
		//A + 2
		if(value == 13) {
			if(dealer == 4) {
				if(hot >= 5 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 5) {
				if(hot >= 0 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 6) {
				if(hot >= -2 && allowDouble) {
					return Constants.DOUBLE;
				} else {
					return Constants.HIT;
				}
			}
		}
		//A + A
		if(value == 12) {
			return Constants.HIT;
		}
		return Constants.STAND;
	}
	public static String bestHardHandStrategy(OneHand hand, PokerCard card, int hot) {
		int value = hand.hardHandValue();		
		int dealer = card.getTTValue();
		String res = Constants.HIT;
		boolean allowDouble = (hand.numberOfCards() == 2);

		if(value >= 17) {
			return Constants.STAND;
		} 
		if(value==16) {
			if(dealer >= 2 && dealer <= 6) {
				return Constants.STAND;
			}
			if(dealer == 7 || dealer ==8) {
				return Constants.HIT;
			}
			if(dealer == 9) {
				if(hot >= 4) {
					return Constants.STAND;
				} else {
					return Constants.HIT;
				}
			}
			if(dealer == 10) {
				if(hot >=0 ) {
					return Constants.STAND;
				} else {
					return Constants.HIT;
				}
			} 
			if(dealer == 1) {
				if(hot >= 4) {
					return Constants.STAND;
				} else {
					return Constants.HIT;
				}
			}
		}
		if(value==15) {
			if(dealer >= 2 && dealer <= 6) {
				if(hot <= -5 && dealer == 2) {
					//very rare case
					return Constants.HIT;
				}
				return Constants.STAND;
			}
			if(dealer >= 7 && dealer <=9) {
				return Constants.HIT;
			}
			if(dealer == 10) {
				if(hot <= 3) {
					return Constants.HIT;
				} else {
					return Constants.STAND;
				}
			}
			if(dealer == 1) {
				if(hot <= 4) {
					return Constants.HIT;
				} else {
					return Constants.STAND;
				}
			}
		}
		if(value==14) {
			if(dealer >= 2 && dealer <= 6) {
				if(hot>=-4) {
					return Constants.STAND;
				} 
				if(hot <= -5 && (dealer == 2 || dealer == 3)) {
					return Constants.HIT;
				} 
				return Constants.STAND;
			}
			if(dealer >=7 && dealer <= 10) {
				return Constants.HIT;
			}
			if(dealer==1) {
				return Constants.HIT;
			}
		}
		if(value==13) {
			if(dealer >= 2 && dealer <= 6) {
				if(hot>=-2) {
					return Constants.STAND;
				} 
				if(hot <= -3 && dealer == 2) {
					return Constants.HIT;
				} 
				if(hot <= -4 && dealer == 3) {
					return Constants.HIT;
				}
				if(hot <= -5 && dealer ==4) {
					return Constants.HIT;
				}
				return Constants.STAND;
			}
			if(dealer >=7 && dealer <= 10) {
				return Constants.HIT;
			}
			if(dealer==1) {
				return Constants.HIT;
			}
		}
		if(value==12) {
			if(dealer >= 2 && dealer <= 6) {
				if(hot >= 2) {
					return Constants.STAND;
				} 
				if(hot <= 1 && (dealer == 2 || dealer == 3)) {
					return Constants.HIT;
				}
				if(hot <= 0 && dealer == 4) {
					return Constants.HIT;
				}
				if(hot <= -3 && dealer == 5) {
					return Constants.HIT;
				}
				if(hot <= -5 && dealer == 6) {
					return Constants.HIT;
				}
				return Constants.STAND;
				
			}
			if(dealer >=7 && dealer <= 10) {
				return Constants.HIT;
			}
			if(dealer==1) {
				return Constants.HIT;
			}
		}
		
		if(!allowDouble) {
			return Constants.HIT;
		}

		if(value == 11) {
			if(hot <= -5 && dealer == 1) {
				return Constants.HIT;
			} else {
				return Constants.DOUBLE;
			}
		}
		if(value == 10) {
			if(dealer >= 2 && dealer <= 9) {
				return Constants.DOUBLE;
			}
			if(dealer == 10 || dealer == 1) {
				if(hot <= 0) {
					return Constants.HIT;
				} else {
					return Constants.DOUBLE;
				}
			}
		}
		
		if(value == 9) {
			if(dealer == 1 || dealer == 10 || dealer == 9) {
				return Constants.HIT;
			}
			
			if(dealer == 8 && hot >= 3) {
				return Constants.DOUBLE;
			}
			if(dealer == 7 && hot >= -2) {
				return Constants.DOUBLE;
			}
			if(dealer >= 2 && dealer <= 6) {
				if(hot >= -3) {
					return Constants.DOUBLE;
				}
				if(dealer >= 3 && dealer <= 6) {
					return Constants.DOUBLE;
				}
				if(dealer == 2 && hot <= -4) {
					return Constants.HIT;
				}
			}
			return Constants.HIT;
		}
		//the interesting value is between 8 and 16
		//double cases
		if(value == 8) {
			
			if(dealer == 6 && hot >= -1) {
				return Constants.DOUBLE;
			}
			if(dealer == 5 && hot >= 1) {
				return Constants.DOUBLE;
			}
			if(dealer == 4 && hot >= 3) {
				return Constants.DOUBLE;
			}
			if(dealer == 3 && hot >= 5) {
				return Constants.DOUBLE;
			}
			return Constants.HIT;
		}
		
		if(value <= 7) {
			return Constants.HIT;
		}
		return Constants.HIT;
	}
	
	public static String BestStrategy(OneHand hand, PokerCard card, int hot, boolean allowSplit) {
		String stra = Constants.STAND;
		
		if(hand.isPairs() && allowSplit) {
			stra = bestPairStrategy(hand, card, hot);
		} else if(hand.softHand()) {
			stra = bestSoftHandStrategy(hand, card, hot);
		} else {
			stra = bestHardHandStrategy(hand, card, hot);
		}
		return stra;
	}
}
