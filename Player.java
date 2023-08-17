/**
 * SYST 17796 Project Base code.
 */
package ca.sheridancollege.project;

/**
 * A class that models each Player in the game. Players have an identifier, which should be unique.
 *
 * @author WarCardGame
 */
class Player {
    private final String name;
    private final GroupOfCards hand;

    public Player(String name) {
        this.name = name;
        hand = new GroupOfCards();
    }

    public String getName() {
        return name;
    }

    public void addToHand(Card card) {
        hand.addCard(card);
    }

    public Card playCard() {
        return hand.removeCard();
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    public int getHandSize() {
        return hand.size();
    }
}