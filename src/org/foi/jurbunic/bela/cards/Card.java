package org.foi.jurbunic.bela.cards;

import java.io.Serializable;

public class Card implements Serializable {

    private String name;
    private Colour colour;
    private int value;
    private boolean isPlayed;

    public Card(String name, Colour colour, int value) {
        this.name = name;
        this.colour = colour;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public String getColourName(){
        return colour.getColourName();
    }
    public int getValue() {
        return value;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
}
