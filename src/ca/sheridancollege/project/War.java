package ca.sheridancollege.project;

/**
 *
 * @author WarCardGame
 */
import java.util.ArrayList;
import java.util.List;

public class War {
    private final List<Warplayer> players;
    private final GroupOfCards deck;
    private final GroupOfCards pile;

    public War(List<String> playerNames) {
        players = new ArrayList<>();
        for (String playerName : playerNames) {
            players.add(new Warplayer(playerName));
        }
        deck = createDeck();
        deck.shuffle();
        distributeCards();
        pile = new GroupOfCards();
    }

    private GroupOfCards createDeck() {
        GroupOfCards deck = new GroupOfCards();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.addCard(new Card(rank, suit));
            }
        }
        return deck;
    }

    private void distributeCards() {
        int currentPlayer = 0;
        while (!deck.isEmpty()) {
            players.get(currentPlayer).addToHand(deck.removeCard());
            currentPlayer = (currentPlayer + 1) % players.size();
        }
    }

    private Warplayer determineRoundWinner(List<Card> playedCards) {
        Card highestCard = playedCards.get(0);
        Warplayer roundWinner = players.get(0);

        for (int i = 1; i < playedCards.size(); i++) {
            Card currentCard = playedCards.get(i);
            if (currentCard.getRank().compareTo(highestCard.getRank()) > 0) {
                highestCard = currentCard;
                roundWinner = players.get(i);
            }
        }

        return roundWinner;
    }

    public String play() {
        int consecutiveTies = 0;
        String gameResult = "";

        while (true) {
            List<Card> playedCards = new ArrayList<>();
            boolean isTie = true;

            for (Warplayer player : players) {
                if (!player.hasCards()) {
                    gameResult = "No players have cards left. It's a tie!";
                    break;
                }
                Card playedCard = player.playCard();
                playedCards.add(playedCard);
                System.out.println(player.getName() + " plays " + playedCard);

                if (!playedCard.equals(playedCards.get(0))) {
                    isTie = false;
                }
            }

            if (!gameResult.isEmpty()) {
                break; // Game has ended, exit loop
            }

            if (isTie) {
                pile.addCards(playedCards);
                System.out.println("It's a tie!");
                consecutiveTies++;
            } else {
                Warplayer roundWinner = determineRoundWinner(playedCards);
                roundWinner.addToHand(playedCards);
                pile.addCards(playedCards);
                playedCards.clear();
                consecutiveTies = 0;

                System.out.println(roundWinner.getName() + " wins the round!");

                if (!roundWinner.hasCards()) {
                    gameResult = roundWinner.getName() + " wins the game!";
                    break; // Game has ended, exit loop
                }
            }

            if (consecutiveTies >= 1) {
                gameResult = "The game ended in a draw due to too many consecutive ties.";
                break; 
            }
        }

        return gameResult;
    }

    public static void main(String[] args) {
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Player1");
        playerNames.add("Player2");
        playerNames.add("Player3");
        playerNames.add("Player4");
        
        War warGame = new War(playerNames);
        String result = warGame.play();

        System.out.println(result);
    }
}