package org.foi.jurbunic.bela.cards;

import java.util.List;

public interface Deck {
    void shuffle();
    void splitEvenly(int numOfPlayers);
    List<Card> getAllCards();
    List<Card> deal();
}
