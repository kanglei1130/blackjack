package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import poker.Deck;
import poker.OneHand;
import poker.Player;
import poker.PokerCard;
import poker.Strategy;
import ultility.Log;

import com.google.gson.Gson;


public class Main {
	private static int AceCounter;//how many aces are in the user's hand
	private static int handvalue;//the value of the user's hand
	private static int bet;//how much the user wants to bet
	private static final String TAG = "Main";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		//Deck deck = new Deck(4);
		//deck.shuffle();
		
		iterateOneHand();

	}	
	public static void iterateOneHand() {
		Strategy x = new Strategy();				
		Gson gson = new Gson();
		PokerCard dealerCard = new PokerCard(8, PokerCard.CLUB);
		PokerCard playFirstCard = new PokerCard(10, PokerCard.CLUB);
		PokerCard playSecondCard = new PokerCard(6, PokerCard.CLUB);
		
		
		int round = 1000*1000;
		int r = 0;
		double playwin = 0;
		double dealerwin = 0;
		int dealerBust = 0;


		//16 against a 10
		//hit: 21.23%
		//stand: 21.4%
		
		//15 against a 10
		//hit: 23.05%
		//stand: 23.06%
		
		//15 against a 9
		//hit: 24.95%
		//stand: 23%
		
		//16 against a 9
		//hit: 23.02%
		//stand: 22.8961%
		
		
		//16 against a 8
		//hit: 25.64%
		//stand:24.55%
		
		while(r++ < round) {
			Deck deck = new Deck(4, 0);
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
					playwin += hand.getBet() * 1.5;					
				}
				continue;
			} else if(dealerHand.isBlackJack()) {
				//dealerwin += hand.getBet();
				continue;
			}
			
			//Log.d(TAG, gson.toJson(lei.getOneHand(0)));
			//Log.d(TAG, gson.toJson(dealer.getOneHand(0).firstCard()));

			//player moves
			for(int j = 0; j < lei.numberOfHands(); ++j) {
				OneHand curhand = lei.getOneHand(j);
				
				String stra = x.PlayerStrategy(curhand, firstCard.getTTValue());;	
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
					dealerwin += bet;
				} else if(dvalue > 21) {
					playwin += bet;
				} else if(myvalue > dvalue){
					playwin += bet;
				} else if(myvalue < dvalue) {
					dealerwin += bet;
				} else {
					//Log.error(TAG, myvalue, dvalue);
				}
			}
			//Log.d("-====================================================-");
		}
		Log.d(TAG, playwin, dealerwin);
		Log.d(TAG, (double)playwin/(playwin + dealerwin));		
		Log.d(TAG, playwin, dealerBust);
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
				
				String stra = x.PlayerStrategy(curhand, firstCard.getTTValue());;	
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
