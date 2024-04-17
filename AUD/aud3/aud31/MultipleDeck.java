package Napredno_Programiranje.AUD.aud3.aud31;

public class MultipleDeck {

    private Deck[] decks;

    public MultipleDeck(int numDecks) {
        decks = new Deck[numDecks];
        for(int i=0;i<numDecks;i++){
            decks[i] = new Deck();
        }
//        Without the for loop, you would only have an array of Deck
//        references (decks) that are all initially null. The for loop
//        is essential to ensure that you create actual Deck objects and
//        store them in the decks array, allowing you to work with
//        multiple decks of cards in your MultipleDecks class.
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Deck deck:decks){
            stringBuilder.append(deck.toString());
            stringBuilder.append("\n------------------\n");
        }
        return  stringBuilder.toString();
    }

    public static void main(String[] args) {
        MultipleDeck multipleDecks = new MultipleDeck(2);
        System.out.println(multipleDecks);
        for(int i=0;i<multipleDecks.decks.length;i++){
            multipleDecks.decks[i].shuffle();
        }
        System.out.println(multipleDecks);
    }
}
