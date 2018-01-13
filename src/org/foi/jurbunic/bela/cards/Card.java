package org.foi.jurbunic.bela.cards;

public class Card {

    private String name;
    private Colour colour;
    private Integer value;

    public Card(String name, Colour colour, Integer value) {
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
    public Integer getValue() {
        return value;
    }
}
