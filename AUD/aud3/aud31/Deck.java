package Napredno_Programiranje.AUD.aud3.aud31;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
    private PlayingCard[] cards;
    private boolean[] isDealt;
    private int dealtTotal;

    public Deck(){
        cards = new PlayingCard[52];
        isDealt = new boolean[52];//default false takaso dobri sme
        dealtTotal = 0;
        for(int i=0;i< PlayingCardType.values().length;i++){
            for(int j=0;j<13;j++){
                cards[i*13+j] = new PlayingCard(j+2, PlayingCardType.values()[i]);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Arrays.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cards);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(PlayingCard card : cards){
            s.append(card.toString());
            s.append("\n");
        }
        return s.toString();
        //TOSTRING e VAZNO!!!
    }//nema logika inace ali aj

    public boolean hasCardsLeft(){
        return (cards.length-dealtTotal)>0;
    }
    public PlayingCard[] shuffle(){
        //Arrays to list
        List<PlayingCard> playingCardList = Arrays.asList(cards);//tuka koristi generici???!
        //Collections
        Collections.shuffle(playingCardList);
        //vika nema potreba da praime nazad u niza.. ok, demek ista memorija so vraper si igrame so nizata, ne bilo deep copy
        return cards;
    }
    public PlayingCard dealCard(){
        if(!hasCardsLeft())return null;
        int card = (int)(Math.random() *52);
        //nema logika ali aj
        if(!isDealt[card]){
            isDealt[card] = true;
            dealtTotal++;
            return cards[card];
        }
        return dealCard();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);

        deck.shuffle();
        System.out.println(deck);

        PlayingCard card;
        while ((card = deck.dealCard())!=null){
            System.out.println(card);
        }
    }
}
