package main;

import java.util.ArrayList;
import java.util.List;

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

		for(int hot = -6; hot <= 6; hot += 1) {
			List<String> cur = hotnessTest(hot);
			ReadWriteTrace.writeFile(cur, output.concat(String.valueOf(hot)));
		}
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
		
		int index = 17;
		int player[][] = {
				{7, 10},				
		};
		String stras[] = {Constants.HIT, Constants.STAND};
		
		PokerCard dealerCard = null, playFirstCard = null, playSecondCard = null;
		for(int upcard = 7; upcard <= 10; ++upcard) {
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
		int round = 100*1000;
		int r = 0;
		double sumbets = 0;
		Strategy dealerStra = new Strategy();
		
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
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				
				if(stra.equals(Constants.HIT)) {
					/*
					if(curhand.numberOfCards() == 2 || curhand.softHandValue() < 12) {
						lei.getOneHand(j).hit(deck.drawCard());
						j--;
					}
					*/
					lei.getOneHand(j).hit(deck.drawCard());
				} else if(stra.equals(Constants.STAND)) {
					continue;
				} else if(stra.equals(Constants.DOUBLE)) {
					lei.getOneHand(j).doubleDown(deck.drawCard());
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
			}
		}
		return sumbets;
	}

}
