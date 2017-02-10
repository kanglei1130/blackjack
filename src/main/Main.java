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
		
	
		Strategy x = new Strategy();				
		
		Player lei = new Player();
		Player dealer = new Player();
		
	
		while(true) {			
			Deck deck = new Deck(4);
			deck.shuffle();
			OneHand hand = new OneHand(1);
			lei.addOneHand(hand);
			OneHand another = new OneHand();
			dealer.addOneHand(another);
			
			dealer.getOneHand(0).hit(deck.drawCard());
			dealer.getOneHand(0).hit(deck.drawCard());
			
			lei.getOneHand(0).hit(deck.drawCard());
			lei.getOneHand(0).hit(deck.drawCard());
			
			
			for(int m = 0; m < lei.numberOfHands(); ++m) {
				OneHand curhand = lei.getOneHand(m);
				String stra = x.PlayerStrategy(curhand, dealer.getOneHand(0).firstCard().getValue());;				//hit
				if(stra.equals(Strategy.H)) {
					lei.getOneHand(0).hit(deck.drawCard());
				} else if(stra.equals(Strategy.S)) {
					continue;
				} else if(stra.equals(Strategy.D)) {
					lei.getOneHand(0).hit(deck.drawCard());
					continue;		
				} else if(stra.equals(Strategy.P)) {
					OneHand oneMore = curhand.split();
					curhand.hit(deck.drawCard());
					oneMore.hit(deck.drawCard());
					lei.addOneHand(oneMore);
					m --;
					continue;
				}
			}
			
			Log.d(TAG, gson.toJson(lei.getOneHand(0)));
			Log.d(TAG, gson.toJson(dealer.getOneHand(0).firstCard()));
			
			break;
		}
		
	}	
	

	//basic player strategy: winning rate
	

		
		//initial players

	
	//one round
 /*   public static void OneRound(Deck deck, Player[] players, Player dealer) 
	{
		int num = players.length;
		
        List<PokerCard> hand1 = new ArrayList<PokerCard>();
        hand1.add(deck.drawCard());
	    
		if(deck.shouldBeShuffled()) 
		{
			//shuffle the card
			deck.shuffle();
			Gson gson = new Gson();
			Log.d(TAG, gson.toJson(deck));
		}
		for(;deck.drawCard()!=null;) {
			
			//phase 1: 2 cards for each player
			for(int i = 0; i < 2; ++i) {
				for(int j = 0; j < num; ++j) {
					players[j].getOneHand(0).hit(deck.drawCard());
				}
				dealer.getOneHand(0).hit(deck.drawCard());
			}
			//phase 2: check blackjack
		for(int j = 0; j < num; ++j) 
		{
			if(players[j].getOneHand(0).isBlackJack() && dealer.getOneHand(0).isBlackJack())//check if both the user and dealer have blackjack.
	        {
	            Push();
	        }
	        else if(players[j].getOneHand(0).isBlackJack())//check if the user has blackjack.
	        {
	            System.out.println("You have BlackJack!");
	            System.out.println("You win 2x your money back!");
	            Win();
	        }
	        else if(dealer.getOneHand(0).isBlackJack())//check if the dealer has blackjack.
	        {
	            System.out.println("Here is the dealer's hand:");
	            System.out.println(dealer.getOneHand(0));
	            Lose();
	        }
	    }
			//phase 3: strategy starts
			for(int j = 0; j < num; ++j) {
				Player player = players[j];
				int k = player.numberOfHands();
				for(int m = 0; m < k; ++m) {
					OneHand curhand = player.getOneHand(m);
					Strategy x = new Strategy();
					String stra = x.PlayerStrategy(curhand, dealer.getOneHand(0).firstCard().getValue());;
					//hit
					if(stra.equals("STAND")) 
					{
						continue;
					} 
					else if (stra.equals("DOUBLEDOWN")) 
					{
					    double betDOUBLEDOWN = 2*bet;
						continue;
					} 
					else if (stra.equals("SPLIT"))
					{
					//if split down
						OneHand another = curhand.split();
						curhand.hit(deck.drawCard());
						another.hit(deck.drawCard());
						//restart the process
						m = 0;
						continue;
					}
					else if (stra.equals("HIT")) 
					{
						Hit(deck, hand1);
		                System.out.println("Your hand is now:");
		                System.out.println(hand1);
		                handvalue = calcHandValue(hand1);
		                if(checkBust(handvalue))//checks if the user busted
		                {
		                    Lose();
		                    break;
		                }
					}
				}
			}
		}
			}
/*			//dealer strategy
				OneHand hand = dealer.getOneHand(0);
				String stra = Strategy.DealerStrategy(dealer);
				if(stra.equals("HIT"))
				{
					Hit(deck, hand1);
				} 
				else if(stra.equals("STAND")) 
				{
				break;
				} 

			
			//phase 4, compare  
			OneHand you = new OneHand(1);//check who is closer to 21 and determine winner
			OneHand dealerCard = new OneHand(1);
            
            if(you.playerCardValue()==dealerCard.playerCardValue())
            {
                Push();
            }
            if(you.playerCardValue()<dealerCard.playerCardValue())
            {
                Win();
            }
            if(you.playerCardValue()>dealerCard.playerCardValue())
            {
                Lose();
            }
		}
	}
*/

/*
 * Checks if the user has blackjack.
 */
/*
public static boolean hasBlackJack(int handValue)
{
    if(handValue==21)
    {
        return true;
    }
    return false;
}
/*
 * Calculates the value of a player's hand.
 */
public static int calcHandValue(List<PokerCard> hand)
{
    PokerCard[] aHand = new PokerCard[]{};
    aHand = hand.toArray(aHand);
    int handvalue=0;
    for(int i=0; i<aHand.length; i++)
    {
        handvalue += aHand[i].getValue();
        if(aHand[i].getValue()==11)
        {
            AceCounter++;
        }
        while(AceCounter>0 && handvalue>21)
        {
            handvalue-=10;
            AceCounter--;
        }
    }
    return handvalue;
}

/*
 * Asks the user how much he or she would like to bet.
 */
public static int Bet(int cash)
{
    Scanner sc=new Scanner(System.in);
    int bet=sc.nextInt();
    while(bet>cash)
    {
        System.out.println("You cannot bet more cash than you have!");
        System.out.println("How much would you like to bet?");
        bet=sc.nextInt();
    }
    return bet;
}
/*
 * Called if the user wins.
 */
public static void Win()
{
    System.out.println("Congratulations, you win!");
}
/*
 * Called if the user loses.
 */
public static void Lose()
{
    System.out.println("Sorry, you lose!");
}
/*
 * Called if the user pushes
 */
public static void Push()
{
    System.out.println("It's a push!");
}
/*
 * Adds a card to user's hand and calculates the value of that hand. Aces are taken into account.
 */
public static void Hit(Deck deck, List<PokerCard> hand)
{
    hand.add(deck.drawCard());
    PokerCard[] aHand = new PokerCard[]{};
    aHand = hand.toArray(aHand);
    handvalue = 0;
    for(int i=0; i<aHand.length; i++)
    {
        handvalue += aHand[i].getValue();
        if(aHand[i].getValue()==11)
        {
            AceCounter++;
        }
        while(AceCounter>0 && handvalue>21)
        {
            handvalue-=10;
            AceCounter--;
        }
    }
}
/*
 * Determines if a user has input hit or stand.
 */
public static boolean isHitorStand(String hitter)
{
    if(hitter.equals("hit") || hitter.equals("stand"))
    {
        return true;
    }
    return false;
}
/*
 * Determines if a user has busted.
 */
public static boolean checkBust(int handvalue)
{
    if(handvalue>21)
    {
        System.out.println("You have busted!");
        return true;
    }
    return false;
}
/*
 * Determines if a user has input yes or no.
 */
public static boolean isyesorno(String answer)
{
    if(answer.equals("yes") || answer.equals("no"))
    {
        return true;
    }
    return false;
}
}
		
/*		List<Deck> deck = new LinkedList<Deck>();
		Log.d(TAG, "Before shuffle");
		Gson gson = new Gson();
		for(int i = 0; i < deck.size(); ++i) {
			Deck card = deck.get(i);
			Log.d(TAG, gson.toJson(card));

     }
 */
	

	
	/*	private void Another_shuffle(deck);
		Log.d(TAG, "After shuffle");
		for(int i = 0; i < deck.size(); ++i) {
			PokerCard card = deck.get(i);
			Log.d(TAG, gson.toJson(card));
		}
	*/
	
	//Another shuffle method
/*	private static void Another_shuffle(List<PokerCard> deck) {
		List<PokerCard> Another_deck = new LinkedList<PokerCard>();
		
		
		for(int i = 0; i < 13; ++i) {
			PokerCard card = new PokerCard(i + 1, PokerCard.SPADE);
			Another_deck.add(card);
			
			
		}

		Random rand = new Random();	
		for(int i = 0; i < 13; ++i) {
			int rint = rand.nextInt(13);
			Log.d(TAG, rint);
			int Num = rand.nextInt(Another_deck.size());
			PokerCard change = Another_deck.get(Num);
			deck.set(i, change);
			Another_deck.remove(Num);
	}
  }
*/	

