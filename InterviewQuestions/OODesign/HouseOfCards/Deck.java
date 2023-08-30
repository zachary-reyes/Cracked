package HouseOfCards;

import java.util.ArrayList;

/* 7.1 Deck of Cards: Design the data structures for a generic deck of cards. Explain how you would subclass the data structures to implement blackjack.
 * 
 * Hints: 153, 275
 * -> Note that a "card deck" is very broad. You might want to think about a reasonable scope to the problem.
 * -> How, if at all, will you handle aces?
 * 
 * We will assume the deck is a standard 52-card set
*/
public class Deck <T extends Card> {
    private ArrayList<T> cards; // all cards, dealt or not
    private int dealtIndex = 0; // marks first undealt card

    public void setDeckOfCards(ArrayList<T> deckOfCards) {}

    public void shuffle() {}
    public int remainingCards() {
        return cards.size() - dealtIndex;
    }
    // public T[] dealHand(int number) {}
    // public T dealCard() {}
}


