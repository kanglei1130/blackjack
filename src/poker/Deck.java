package poker;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Deck {

	private List<PokerCard> deck_ = new LinkedList<PokerCard>();//represents a deck of cards
	private int shuffle_point_ = 0; 
	private static final String TAG = "Deck";
	
	private int hot_ = -10;
	// 2,3,4,5,6  => -1
	// 10,j,q,k,1  => 1
	
	public void init(int num) {
		for (int i = 0; i < num; ++i)
		{
			for (int j = 1; j < 14; ++j)
			{
				deck_.add(new PokerCard(j, PokerCard.CLUB));
				deck_.add(new PokerCard(j, PokerCard.DIAMOND));
				deck_.add(new PokerCard(j, PokerCard.HEART));
				deck_.add(new PokerCard(j, PokerCard.SPADE));	
			}
		}
		shuffle_point_ = this.deck_.size()/3;
	}
	
	//change to number of decks
	public Deck(int num)    
	{
		init(num);
	}
	
	public Deck(int num, int hot) {
		init(num);
		this.shuffle();

		if(hot >= 0) {
			for (Iterator<PokerCard> iter = deck_.listIterator(); iter.hasNext() && hot != 0; ) {
				PokerCard a = iter.next();
				if (a.getValue() >= 2 && a.getValue() <= 6) {
					hot--;
					iter.remove();
				}
			}
		} else {
			for (Iterator<PokerCard> iter = deck_.listIterator(); iter.hasNext() && hot != 0; ) {
				PokerCard a = iter.next();
				if (a.getValue() == 1 || a.getTTValue() == 10) {
					hot++;
					iter.remove();
				}
			}
		}
		//shuffle
		this.shuffle();
	}
	


	public void test(int num) {
		
	}
	
	public int numberOfCardLeft() 
	{
		return this.deck_.size();
	}
	
	//check if the deck is need to be shuffled
	public boolean shouldBeShuffled() 
	{
		if (deck_.size() <= shuffle_point_)
		{
			return true;
		}
		else
		{
		    return false;
		}
	 }

		
	//change to one time shuffle
	public void shuffle() 
	{
		Random rand = new Random();
		for(int i=0; i< deck_.size(); i++)
		{
		     int Num = rand.nextInt(deck_.size());
		     Collections.swap(deck_, i, Num);
	    }
	 }
	
	/*
	 * Draws a card from the deck.
	 */
	 public PokerCard drawCard()
	 {	
		 return deck_.remove(0);
	 }
	
	
}
   
  
