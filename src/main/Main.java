package main;

import java.util.ArrayList;
import java.util.List;

import poker.BestStrategy;
import poker.Deck;
import poker.OneHand;
import poker.Player;
import poker.PokerCard;
import poker.Strategy;
import utility.Constants;
import utility.Log;
import utility.ReadWriteTrace;

import com.google.gson.Gson;


public class Main {

	private static final String TAG = "Main";
	private static final String output = "./strategy/tmp/";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		//Deck deck = new Deck(4);
		//deck.shuffle();
		
		
		//iterateOneHand();

		/*
		for(int hot = -5; hot <= 5; hot += 1) {
			List<String> cur = hotnessTest(hot);
			ReadWriteTrace.writeFile(cur, output.concat(String.valueOf(hot)));
		}
		*/

		
		simulation();
	}	
	
	
	public static List<String> hotnessTest(int hot) {
		List<String> res = new ArrayList<String>();
		
		//soft
		
		/*
		int index = 2;
		int player[][] = {
				{1, 2},
				{1, 3},
				{1, 4},
				{1, 5},
				{1, 6}
		};
		*/
		//hard
		
		int index = 8;
		int player[][] = {
				{6, 2},
		};
		String stras[] = {Constants.HIT, Constants.DOUBLE};
		
		PokerCard dealerCard = null, playFirstCard = null, playSecondCard = null;
		for(int upcard = 1; upcard <= 10; ++upcard) {
			dealerCard = new PokerCard(upcard, PokerCard.SPADE);
			for(int i = 0; i < player.length; ++i) {
				playFirstCard = new PokerCard(player[i][0], PokerCard.CLUB);
				playSecondCard = new PokerCard(player[i][1], PokerCard.CLUB);	
				double bestbets = Double.NEGATIVE_INFINITY;
				String beststra = null;
				for(int j = 0; j < stras.length; ++j) {
					String stra = stras[j];
					double bets = winnings(dealerCard, playFirstCard, playSecondCard, stra, hot);
					Log.d(upcard, hot, stra, bets);
					if(bestbets < bets) {
						bestbets = bets;
						beststra = new String(stra);
					}
				}
				String opt = String.valueOf(upcard) + "," + String.valueOf(i + index) + "," + beststra + "," + String.valueOf(bestbets);
				//Log.d(TAG, opt);
				res.add(opt);
			}
		}
		return res;
	}
	
	public static double winnings(PokerCard dealerCard, PokerCard playFirstCard, PokerCard playSecondCard, String stra, int hot) {
		int round = 1000*1000;
		int r = 0;
		double sumbets = 0;
		Strategy dealerStra = new Strategy();
		String startStra = stra;
		boolean allowSplit = false;
		if(stra.contains(Constants.SPLIT)) {
			allowSplit = true;
		} 
		while(r++ < round) {
			Deck deck = new Deck(1, hot);
			deck.shuffle();
			Player lei = new Player();
			Player dealer = new Player();
			
			OneHand hand = new OneHand(1);
			lei.addOneHand(hand);
			OneHand dealerHand = new OneHand();
			dealer.addOneHand(dealerHand);
			
			dealer.getOneHand(0).hit(dealerCard);
			dealer.getOneHand(0).hit(deck.drawCard());
			
			lei.getOneHand(0).hit(playFirstCard);
			lei.getOneHand(0).hit(playSecondCard);
			
			if(hand.isBlackJack()) {
				if(!dealerHand.isBlackJack()) {
					sumbets += hand.getBet() * 1.5;					
				}
				continue;
			} else if(dealerHand.isBlackJack()) {
				sumbets -= hand.getBet();
				continue;
			}
			
			//player moves
			stra = startStra;
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				/*
				stra = BestStrategy.BestStrategy(curhand, dealerCard, hot, allowSplit);
				if(lei.numberOfHands() >= 3 && stra.contains(Constants.SPLIT)) {
					stra = BestStrategy.BestStrategy(curhand, dealerCard, hot, false);
				}
				*/
				if(curhand.numberOfCards() > 2) {
					stra = BestStrategy.BestStrategy(curhand, dealerCard, hot, allowSplit);					
				}
				if(stra.equals(Constants.HIT)) {
					curhand.hit(deck.drawCard());	
					j--;
				} else if(stra.equals(Constants.STAND)) {
					continue;
				} else if(stra.equals(Constants.DOUBLE)) {
					curhand.doubleDown(deck.drawCard());
					continue;		
				} else if(stra.equals(Constants.SPLIT)) {
					OneHand oneMore = curhand.split();
					curhand.hit(deck.drawCard());
					oneMore.hit(deck.drawCard());
					lei.addOneHand(oneMore);
					j--;
					continue;
				} else {
					
				}
			}
			
			//dealer moves
			while(true) {
				String move = dealerStra.DealerStrategy(dealer.getOneHand(0));
				if(move.equals(Constants.HIT)) {
					dealer.getOneHand(0).hit(deck.drawCard());
				} else if(move.equals(Constants.STAND)) {
					break;
				} else {
					
				}
			}
			
			//sum up

			int dvalue = dealer.getOneHand(0).softHandValue();
			
			//Gson gson = new Gson();
			//Log.d(dvalue, gson.toJson(dealer.getOneHand(0)));

			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curHand = lei.getOneHand(j);
				int myvalue = curHand.softHandValue();
				//Log.d("player", gson.toJson(curHand));
				double bet = curHand.getBet();
				if(myvalue > 21) {
					//player bust
					sumbets -= bet;
				} else if(dvalue > 21) {
					sumbets += bet;
				} else if(myvalue > dvalue){
					sumbets += bet;
				} else if(myvalue < dvalue) {
					sumbets -= bet;
				} else {
					//Log.error(TAG, myvalue, dvalue);
				}
				//Log.d(myvalue, gson.toJson(curHand));
			}
			//Log.d("=========================================================");
		}
		return sumbets;
	}
	
	public static void simulation() {
		Deck deck = new Deck(6, 0);
		deck.shuffle();
		
		Player lei = new Player();
		Player dealer = new Player();
		
		int round = 1000*1000;
		int r = 0;
		while(r++ < round) {
			if(deck.shouldBeShuffled()) {
				deck = new Deck(6, 0);
				deck.shuffle();
			}
			simulateOneRound(deck, dealer, lei, 1);
			dealer.clear();
			lei.clear();
		}
		Log.d(TAG, lei.cash_);
	}
	
	
	public static void simulateOneRound(Deck deck, Player dealer, Player lei, int curbet) {
		
		OneHand dealerHand = new OneHand();
		dealer.addOneHand(dealerHand);
		
		dealer.getOneHand(0).hit(deck.drawCard());
		dealer.getOneHand(0).hit(deck.drawCard());
		
		PokerCard dealerCard = dealer.getOneHand(0).firstCard();
	
		int hot = deck.getTrueCount();
		
		OneHand hand = new OneHand(curbet);
		if(hot >= 2) {
			hand = new OneHand(curbet * 10);
		}
		
		lei.addOneHand(hand);
		
		
		lei.getOneHand(0).hit(deck.drawCard());
		lei.getOneHand(0).hit(deck.drawCard());
		
		if(hand.isBlackJack()) {
			if(dealerCard.getValue() == 1) {
				lei.cash_ += hand.getBet();
			}
			if(!dealerHand.isBlackJack()) {
				lei.cash_ += hand.getBet() * 1.5;					
			}
			return;
		} else if(dealerHand.isBlackJack()) {
			lei.cash_ -= hand.getBet();
			return;
		}
		
		
		for(int j = 0; j < lei.numberOfHands(); ++j) {
			OneHand curhand = lei.getOneHand(j);
			
			//9.96%
			String bestStra = BestStrategy.BestStrategy(curhand, dealerCard, hot, true);
			if(lei.numberOfHands() >= 3 && bestStra.contains(Constants.SPLIT)) {
				bestStra = BestStrategy.BestStrategy(curhand, dealerCard, hot, false);
			}
			
			//8.47%
			String stra = Strategy.PlayerStrategy(curhand, dealerCard, true, true);
			if(lei.numberOfHands() >= 3 && stra.contains(Constants.SPLIT)) {
				stra = Strategy.PlayerStrategy(curhand, dealerCard, true, false);
			}
			/*
			if(!bestStra.endsWith(stra)) {
				Gson gson = new Gson();
				Log.d(TAG, stra, bestStra, hot);
				Log.d(TAG, gson.toJson(dealerCard));
				Log.d(TAG, gson.toJson(curhand), gson.toJson(curhand.softHandValue()));
				Log.d(TAG, gson.toJson(lei.numberOfHands()));
				assert 0 == 1;
			}
			*/
			
			if(stra.equals(Constants.HIT)) {
				curhand.hit(deck.drawCard());	
				j--;
			} else if(stra.equals(Constants.STAND)) {
				continue;
			} else if(stra.equals(Constants.DOUBLE)) {
				curhand.doubleDown(deck.drawCard());
				continue;		
			} else if(stra.equals(Constants.SPLIT)) {
				OneHand oneMore = curhand.split();
				curhand.hit(deck.drawCard());
				oneMore.hit(deck.drawCard());
				lei.addOneHand(oneMore);
				j--;
				continue;
			} else {
				
			}
		}
		
		//dealer moves
		while(true) {
			String move = Strategy.DealerStrategy(dealer.getOneHand(0));
			if(move.equals(Constants.HIT)) {
				dealer.getOneHand(0).hit(deck.drawCard());
			} else if(move.equals(Constants.STAND)) {
				break;
			} else {
				
			}
		}
		
		//sum up

		int dvalue = dealer.getOneHand(0).softHandValue();
		
		//Gson gson = new Gson();
		//Log.d(dvalue, gson.toJson(dealer.getOneHand(0)));

		for(int j = 0; j < lei.numberOfHands(); ++j) {
			OneHand curHand = lei.getOneHand(j);
			int myvalue = curHand.softHandValue();
			//Log.d("player", gson.toJson(curHand));
			double bet = curHand.getBet();
			if(myvalue > 21) {
				//player bust
				lei.cash_ -= bet;
			} else if(dvalue > 21) {
				lei.cash_ += bet;
			} else if(myvalue > dvalue){
				lei.cash_ += bet;
			} else if(myvalue < dvalue) {
				lei.cash_ -= bet;
			} else {
				//Log.error(TAG, myvalue, dvalue);
			}
		}
		
	}

}
