package org.foi.jurbunic.bela.cards;

import java.io.Serializable;

public class Card implements Serializable {

    private String name;
    private String shortName;
    private Colour colour;
    private double value;

    public Card(String name, String shortName, Colour colour, double value) {
        this.name = name;
        this.shortName = shortName;
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
    public double getValue() {
        if(this.getColour().isTrump()){
            if(this.getName().equals("Jack")){
                return 20;
            }
            if(this.getName().equals("Nine")){
                return 14;
            }
        }
        return value;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCardGraphic(){

        return "|"+getShortName()+getColour().getColourSymbol()+"| ";
    }
}
