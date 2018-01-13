package org.foi.jurbunic.bela.cards;

import java.util.List;

public interface CardAlgorithm {
    void setCars(List<Card> cards);
    Card bestAction();
}
