// Created Card.java class - Kylie
public class Card {
    private int value; // Value of card
    private String suit; // Suit of card
    private String rank; // Rank of card

    // card constructor to store information for each card
    public Card(int value, String suit, String rank) {
        this.value = value;
        this.suit = suit;
        this.rank = rank;
    }

    // get value of card
    public int getValue() {
        return value;
    }

    // set value of card
    public void setValue(int value) {
        this.value = value;
    }

    // get suit of card
    public String getSuit() {
        return suit;
    }

    // set suit of card
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // get rank of card
    public String getRank() {
        return rank;
    }

    // set rank of card
    public void setRank(String rank) {
        this.rank = rank;
    }

    // output
    public String toString() {
        return rank + " of " + suit;
    }
}