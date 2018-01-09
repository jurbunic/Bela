package org.foi.jurbunic.bela.cards;

public class Card {

    private boolean adut;
    private String name;

    // 0 - Diamond
    // 1 - Heart
    // 2 - Club
    // 3 - Spade
    private Integer colour;
    private Integer value;

    public Card(String name, Integer colour, Integer value) {
        this.name = name;
        this.colour = colour;
        this.value = value;
    }

    public void setAdut(boolean adut) {
        this.adut = adut;
    }

    public String getName() {
        return name;
    }

    public Integer getColour() {
        return colour;
    }

    public String getColourName(){
        String colour = "";
        switch (this.colour){
            case 0:
                colour = "Diamond";
                break;
            case 1:
                colour = "Heart";
                break;
            case 2:
                colour = "Club";
                break;
            case 3:
                colour = "Spade";
                break;
        }
        return colour;
    }
    public Integer getValue() {
        return value;
    }
}
