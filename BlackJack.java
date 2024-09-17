
// Group members: Harry Burdick, Connor Reilly, Kylie Scheibli
// Date 9/16/2024

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackJack {

    private static List<Card> cards = new ArrayList<>(); // Use ArrayList to manage the deck - Kylie
    private static int currentCardIndex = 0; // Index to keep track of the next card to deal - Kylie

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        boolean playAgain = true; // Control variable for the game loop

        // Main game loop
        while (playAgain) {
            initializeDeck(); // Initialize the deck of cards
            shuffleDeck(); // Shuffle the deck to randomize card order

            // Keep track of player and dealer hands
            List<Card> playerHand = new ArrayList<>();
            List<Card> dealerHand = new ArrayList<>();

            // Deal initial cards
            dealInitialCards(playerHand, dealerHand);

            // Handle player's turn
            int playerTotal = playerTurn(scanner, playerHand);
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins."); // Player busts if total exceeds 21
            } else {
                // Handle dealer's turn
                int dealerTotal = dealerTurn(dealerHand);
                determineWinner(playerHand, dealerHand); // Determine the winner based on hands
            }

            // Ask the player if they want to play another hand
            // added (yes/no) to clarify output - Kylie
            System.out.println("Would you like to play another hand? (yes/no)");
            String playerDecision = scanner.nextLine().toLowerCase(); // Read user input

            // Validate user input
            while (!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
                System.out.println("Invalid action. Please type 'yes' or 'no'.");
                playerDecision = scanner.nextLine().toLowerCase(); // Read user input again
            }
            playAgain = playerDecision.equals("yes"); // Set playAgain based on user decision
        }

        System.out.println("Thanks for playing!"); // Thank the player after exiting the loop
    }

    // Initialize the deck of cards
    private static void initializeDeck() {
        String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

        // int suitsIndex = 0, rankIndex = 0; // removed - Harry
        for (String suit : SUITS) {
            for (int i = 0; i < RANKS.length; i++) {
                int value = (i < 9) ? i + 2 : 10; // simplfied //Determines Values for cards 2-10, Jack, Queen, King -
                                                  // Harry
                if (RANKS[i].equals("Ace")) {
                    value = 11; // Ace is usually worth 11
                }
                cards.add(new Card(value, suit, RANKS[i])); // Add each card to the deck
            }
        }
    }
    // commented out for simplified version - Harry
    // for (int i = 0; i < cards.length; i++) {
    // //DECK[i] = i;
    // //public Card(int value, String suit, String rank) {
    // int val = 10;
    // if(rankIndex < 9)
    // val = Integer.parseInt(RANKS[rankIndex]);

    // cards[i] = new Card( val, SUITS[suitIndex], RANKS[rankIndex]);
    // suitIndex++;
    // if (suitIndex == 4) {
    // suitIndex = 0;
    // rankIndex++;
    // }
    // }
    // }

    // Shuffle the deck
    private static void shuffleDeck() {
        Collections.shuffle(cards); // Use Collections.shuffle to randomize card order - Connor
        currentCardIndex = 0; // Reset the card index after shuffling

        // Random random = new Random();
        // for (int i = 0; i < cards.length; i++) {
        // int index = random.nextInt(cards.length);
        // Card temp = cards[i];
        // cards[i] = cards[index];
        // cards[index] = temp;
        // }

    }

    // Deal initial cards to the player and dealer
    // Condensed dealing cards to a single method
    private static void dealInitialCards(List<Card> playerHand, List<Card> dealerHand) {
        playerHand.add(dealCard()); // Deal first card to player
        playerHand.add(dealCard()); // Deal second card to player
        dealerHand.add(dealCard()); // Deal first card to dealer (one card face up for dealer)
        // Display initial hands
        System.out.println("Your cards: " + playerHand.get(0) + " and " + playerHand.get(1));
        System.out.println("Dealer's card: " + dealerHand.get(0)); // Only show one dealer card initially
    }

    //
    // algorithm to deal initial player cards
    // private static int dealInitialPlayerCards() {
    // /*int card1 = dealCard();
    // int card2 = dealCard();*/
    // Card card1 = dealCard();
    // Card card2 = dealCard();

    // //System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[card1 /
    // 13] + " and " + RANKS[card2] + " of " + SUITS[card2 / 13]);
    // System.out.println("Your cards: " + card1.getRank() + " of " +
    // card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());

    // //return cardValue(card1) + cardValue(card2);
    // return card1.getValue() + card2.getValue();
    // }
    // // alogrithm to deal initial dealer cards
    // private static int dealInitialDealerCards() {
    // Card card1 = dealCard();
    // System.out.println("Dealer's card: " + card1);
    // return card1.getValue();
    // }

    // Handle player's turn
    private static int playerTurn(Scanner scanner, List<Card> playerHand) {
        while (true) {
            int playerTotal = calculateHandValue(playerHand); // Added to Calculate current hand value based on Aces
                                                              // being present - Connor
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase(); // Read player's action
            if (action.equals("hit")) {
                Card newCard = dealCard(); // Deal a new card to the player
                // playerTotal += newCard.getValue(); // Removed - Harry
                playerHand.add(newCard); // Add the new card to the player's hand - Harry
                System.out.println("You drew a " + newCard); // Display the new card
                if (calculateHandValue(playerHand) > 21) { // Changed to calculateHandValue(playerHand) to include Aces
                                                           // - Connor
                    // System.out.println("You busted! Dealer wins."); // Notify player if they bust
                    playerTotal = 0;
                    return calculateHandValue(playerHand); // End the turn and return the busted value // Changed to
                                                           // calculateHandValue(playerHand) to include Aces - Connor
                }
            } else if (action.equals("stand")) {
                break; // Exit the loop if the player chooses to stand
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'."); // Prompt for valid input
            }
        }
        return calculateHandValue(playerHand); // Return the player's total after their turn // Changed to
                                               // calculateHandValue(playerHand) to include Aces - Connor
    }

    // Modified dealer turn to fix
    private static int dealerTurn(List<Card> dealerHand) {
        while (calculateHandValue(dealerHand) < 17) { // Dealer must hit until reaching at least 17
            Card newCard = dealCard(); // Deal a new card to the dealer
            System.out.println("Dealer drew a " + newCard); // Displays dealers drawn card
            dealerHand.add(newCard); // Add the new card to the dealer's hand
        }
        System.out.println("Dealer's total is " + calculateHandValue(dealerHand)); // Display the dealer's total
        return calculateHandValue(dealerHand); // Return the dealer's total after their turn
    }
    // algorithm for dealer's turn
    // private static int dealerTurn(int dealerTotal) {
    // while (dealerTotal < 17) {
    // Card newCard = dealCard();
    // dealerTotal += newCard.getValue();
    // }
    // System.out.println("Dealer's total is " + dealerTotal);
    // return dealerTotal;
    // }

    // Modified method to Determine the winner - Harry
    private static void determineWinner(List<Card> playerHand, List<Card> dealerHand) {
        int playerTotal = calculateHandValue(playerHand);
        int dealerTotal = calculateHandValue(dealerHand);

        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!"); // Player wins if dealer busts or player has a higher total
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!"); // Tie if totals are equal
        } else {
            System.out.println("Dealer wins!"); // Dealer wins if they have a higher total
        }
    }

    // Algorithm to determine the winner
    // private static void determineWinner(int playerTotal, int dealerTotal) {
    // if (dealerTotal > 21 || playerTotal > dealerTotal) {
    // System.out.println("You win!");
    // } else if (dealerTotal == playerTotal) {
    // System.out.println("It's a tie!");
    // } else {
    // System.out.println("Dealer wins!");
    // playerTotal = 0;
    // }
    // }

    // Deal a card from the deck
    private static Card dealCard() {
        // return cards[currentCardIndex++]; // removed
        return cards.get(currentCardIndex++); // Added to increment selected card index
    }

    // Created method to determine value for Aces since Aces can be 1 or 11 - Connor
    // Calculate the best value of a hand considering Aces as 1 or 11 - Connor
    private static int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;
        // Calculate initial hand value and count Aces in hand
        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }
        // Adjust value for Aces
        while (value > 21 && aceCount > 0) {
            value -= 10; // Treat one Ace as 1 instead of 11
            aceCount--;
        }
        return value; // Return the best value considering Aces value - Connor
    }
}
