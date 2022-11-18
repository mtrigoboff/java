package cards;

public class Card
	implements Comparable<Card>
{
    public enum Rank
    {
    	DEUCE, THREE, FOUR, FIVE, SIX, SEVEN,
        EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    public enum Suit
    {
    	HEARTS, CLUBS, DIAMONDS, SPADES
    }

    private final Rank rank;
    private final Suit suit;

    Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }
    
    public int compareTo(Card card)
    {
    	int		rankCompare = rank.compareTo(card.rank);
    	int		suitCompare = suit.compareTo(card.suit);

    	if (rankCompare != 0)
    		return rankCompare;
    	else
    		return suitCompare;
    }

    public Rank rank()          { return rank; }
    public Suit suit()          { return suit; }
    public String toString()    { return rank + " of " + suit; }
}
