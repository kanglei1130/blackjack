package main;

import java.util.ArrayList;
import java.util.List;

import poker.Deck;
import poker.OneHand;
import poker.Player;
import poker.PokerCard;
import poker.Strategy;
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

		for(int hot = -8; hot <= 12; hot += 4) {
			List<String> cur = hotnessTest(hot);
			ReadWriteTrace.writeFile(cur, output.concat(String.valueOf(hot)));
		}
	}	
	
	public static List<String> hotnessTest(int hot) {
		List<String> res = new ArrayList<String>();
		
		//soft
		
		int index = 2;
		int player[][] = {
				{1, 2},
				{1, 3},
				{1, 4},
				{1, 5},
				{1, 6}
		};
		
		//hard
		/*
		int index = 7;
		int player[][] = {
				{3, 4},
				{3, 5},
				{2, 7},
				{3, 7},
				{2, 9},
				{2, 10},
				{3, 10},
				{4, 10},
				{5, 10},
				{6, 10}
		};
		*/
		String stras[] = {Strategy.DOUBLE, Strategy.HIT, Strategy.STAND};
		
		PokerCard dealerCard = null, playFirstCard = null, playSecondCard = null;
		for(int upcard = 2; upcard <= 6; ++upcard) {
			dealerCard = new PokerCard(upcard, PokerCard.SPADE);
			for(int i = 0; i < player.length; ++i) {
				playFirstCard = new PokerCard(player[i][0], PokerCard.CLUB);
				playSecondCard = new PokerCard(player[i][1], PokerCard.CLUB);	
				double bestbets = Double.NEGATIVE_INFINITY;
				String beststra = null;
				for(int j = 0; j < stras.length; ++j) {
					String stra = stras[j];
					double bets = winnings(dealerCard, playFirstCard, playSecondCard, stra, hot);
					if(bestbets < bets) {
						bestbets = bets;
						beststra = new String(stra);
					}
				}
				String opt = String.valueOf(upcard) + "," + String.valueOf(i + index) + "," + beststra + "," + String.valueOf(bestbets);
				Log.d(TAG, opt);
				res.add(opt);
			}
		}
		return res;
	}
	
	public static double winnings(PokerCard dealerCard, PokerCard playFirstCard, PokerCard playSecondCard, String stra, int hot) {
		int round = 10000;
		int r = 0;
		double sumbets = 0;
		Strategy dealerStra = new Strategy();
		
		while(r++ < round) {
			Deck deck = new Deck(4, hot);
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
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				
				if(stra.equals(Strategy.HIT)) {
					if(curhand.softHand() == true) {
						lei.getOneHand(j).hit(deck.drawCard());
						//j--;
					}
				} else if(stra.equals(Strategy.STAND)) {
					continue;
				} else if(stra.equals(Strategy.DOUBLE)) {
					lei.getOneHand(j).doubleDown(deck.drawCard());
					continue;		
				} else if(stra.equals(Strategy.SPLIT)) {
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
				if(move.equals(Strategy.HIT)) {
					dealer.getOneHand(0).hit(deck.drawCard());
				} else if(move.equals(Strategy.STAND)) {
					break;
				} else {
					
				}
			}
			
			//sum up

			int dvalue = dealer.getOneHand(0).playerCardValue();
			
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curHand = lei.getOneHand(j);
				int myvalue = curHand.playerCardValue();
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
			}
		}
		return sumbets;
	}
	
	public static void iterateOneHand() {
		Strategy x = new Strategy();				
		Gson gson = new Gson();
		PokerCard dealerCard = new PokerCard(10, PokerCard.CLUB);
		PokerCard playFirstCard = new PokerCard(4, PokerCard.CLUB);
		PokerCard playSecondCard = new PokerCard(10, PokerCard.CLUB);
		
		int round = 100*1000;
		int r = 0;
		double sumbets = 0;
		int dealerBust = 0;
		
		while(r++ < round) {
			Deck deck = new Deck(4, 5);
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
			
			PokerCard firstCard = dealerHand.firstCard();
			if(hand.isBlackJack()) {
				if(!dealerHand.isBlackJack()) {
					sumbets += hand.getBet() * 1.5;					
				}
				continue;
			} else if(dealerHand.isBlackJack()) {
				sumbets -= hand.getBet();
				continue;
			}
			
			//Log.d(TAG, gson.toJson(lei.getOneHand(0)));
			//Log.d(TAG, gson.toJson(dealer.getOneHand(0).firstCard()));

			//player moves
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				
				
				String stra = x.PlayerStrategy(curhand, firstCard.getTTValue(), true, true);	
				if(stra.equals(Strategy.HIT)) {
					lei.getOneHand(j).hit(deck.drawCard());
					j--;
				} else if(stra.equals(Strategy.STAND)) {
					continue;
				} else if(stra.equals(Strategy.DOUBLE)) {
					lei.getOneHand(j).doubleDown(deck.drawCard());
					continue;		
				} else if(stra.equals(Strategy.SPLIT)) {
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
				String stra = x.DealerStrategy(dealer.getOneHand(0));
				if(stra.equals(Strategy.HIT)) {
					dealer.getOneHand(0).hit(deck.drawCard());
				} else if(stra.equals(Strategy.STAND)) {
					break;
				} else {
					
				}
			}
			
			//sum up

			int dvalue = dealer.getOneHand(0).playerCardValue();
			if(dvalue < 17) {
				Log.error(TAG, dvalue);
			} else if(dvalue > 21) {
				dealerBust++;
			} else {
				//Log.d("dealer", gson.toJson(dealer.getOneHand(0)));
			}
			
			
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curHand = lei.getOneHand(j);
				int myvalue = curHand.playerCardValue();
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
			}
		}
		Log.d(TAG, sumbets);		
	}
	

	
	public static void simulateOneRound() {
		
		Strategy x = new Strategy();				
		
	
	
		int round = 40 * 5;
		int r = 0;
		double playwin = 0;
		double dealerwin = 0;

		//bad: 49.4%
		//netrue: 49.88%
		//good: 50.41%
		while(r++ < round) {
			Deck deck = new Deck(4, 0);
			deck.shuffle();
			Player lei = new Player();
			Player dealer = new Player();
			
			OneHand hand = new OneHand(1);
			lei.addOneHand(hand);
			OneHand dealerHand = new OneHand();
			dealer.addOneHand(dealerHand);
			
			dealer.getOneHand(0).hit(deck.drawCard());
			dealer.getOneHand(0).hit(deck.drawCard());
			
			lei.getOneHand(0).hit(deck.drawCard());
			lei.getOneHand(0).hit(deck.drawCard());
			
			PokerCard firstCard = dealerHand.firstCard();
			if(hand.isBlackJack()) {
				if(!dealerHand.isBlackJack()) {
					playwin += hand.getBet() * 1.5;					
				}
				continue;
			} else if(dealerHand.isBlackJack()) {
				dealerwin += hand.getBet();
				continue;
			}
			
			//Log.d(TAG, gson.toJson(lei.getOneHand(0)));
			//Log.d(TAG, gson.toJson(dealer.getOneHand(0).firstCard()));

			//player moves
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				
				String stra = x.PlayerStrategy(curhand, firstCard.getTTValue(), true, true);	
				if(stra.equals(Strategy.HIT)) {
					lei.getOneHand(j).hit(deck.drawCard());
					j--;
				} else if(stra.equals(Strategy.STAND)) {
					continue;
				} else if(stra.equals(Strategy.DOUBLE)) {
					lei.getOneHand(j).doubleDown(deck.drawCard());
					continue;		
				} else if(stra.equals(Strategy.SPLIT)) {
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
				String stra = x.DealerStrategy(dealer.getOneHand(0));
				if(stra.equals(Strategy.HIT)) {
					dealer.getOneHand(0).hit(deck.drawCard());
				} else if(stra.equals(Strategy.STAND)) {
					break;
				} else {
					
				}
			}
			
			//sum up

			int firstcard = dealer.getOneHand(0).firstCard().getTTValue();
			int dvalue = dealer.getOneHand(0).playerCardValue();
			if(dvalue < 17) {
				Log.error(TAG, dvalue);
			}
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curHand = lei.getOneHand(j);
				int myvalue = curHand.playerCardValue();
				//Log.d(gson.toJson(curHand), dvalue, firstcard);
				double bet = curHand.getBet();
				if(myvalue > 21) {
					//player bust
					dealerwin += bet;
				} else if(dvalue > 21) {
					playwin += bet;
				} else if(myvalue > dvalue){
					playwin += bet;
				} else if(myvalue < dvalue) {
					dealerwin += bet;
				} else {
					
				}
			}
			
		}
		Log.d(TAG, playwin, dealerwin);
		Log.d(TAG, (double)playwin/(playwin + dealerwin));		
	}
	
}
