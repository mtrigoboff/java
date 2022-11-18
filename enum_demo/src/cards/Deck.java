package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck
{
    private List<Card>  cards;          // the cards of the deck
    private int         cardsPerLine;   // how many cards to show per line
    
    private static final List<Card> prototypeDeck = new ArrayList<>();

    // initialize prototype deck
    static {
        for (Card.Suit suit : Card.Suit.values())
            for (Card.Rank rank : Card.Rank.values())
                prototypeDeck.add(new Card(rank, suit));
    }

    public Deck(int cardsPerLine)
    {
        this.cardsPerLine = cardsPerLine;
        cards = new ArrayList<>(prototypeDeck);
    }

    public void shuffle()
    {
    	Collections.shuffle(cards);
    }

    public void sort()
    {
    	Collections.sort(cards);
    }

    public String toString()
    {
        int             cardsOnLine = 0;
        StringBuilder   strb = new StringBuilder();
            /* When making a lot of changes to a string,
             * it's better to use a StringBuilder because
             * a StringBuilder instance can be altered.
             * String instances are immutable, so you would
             * be creating many new instances of String and
             * throwing all but one of them away in this method
             * if you used String instead of StringBuilder.      */
        
        for (Card card : cards) {
            if (cardsOnLine != 0)
                strb.append(", ");
            strb.append(card);
            if (++cardsOnLine >= cardsPerLine) {
                strb.append(String.format(",%n"));
                cardsOnLine = 0;
                }
            }
        
        return strb.toString();
    }

    public static void main(String[] args)
    {
        Deck    deck = new Deck(4);
        
        System.out.println(deck);
        deck.shuffle();
        System.out.println(deck);
        deck.sort();
        System.out.println(deck);
    }
}
