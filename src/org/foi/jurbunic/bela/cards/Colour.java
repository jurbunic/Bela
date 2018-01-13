package org.foi.jurbunic.bela.cards;

public class Colour {
    private static final int DIAMOND = 0;
    private static final int HEART = 1;
    private static final int CLUB = 2;
    private static final int SPADE = 3;

    private int colourId;
    private String colourName;
    private boolean trump;

    private static Colour diamond = new Colour(DIAMOND);
    private static Colour heart = new Colour(HEART);
    private static Colour club = new Colour(CLUB);
    private static Colour spade = new Colour(SPADE);


    public static Colour getColour(int colourId){
        switch (colourId){
            case DIAMOND:
                return diamond;
            case HEART:
                return heart;
            case CLUB:
                return club;
            default:
                return spade;
        }
    }

    private Colour(int colourId){
        this.colourId = colourId;
        setColourName(colourId);
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }

    public int getColourId() {
        return colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public boolean isTrump() {
        return trump;
    }

    private void setColourName(int colourName) {
        switch (colourName){
            case DIAMOND:
                this.colourName = "Diamond";
                break;
            case HEART:
                this.colourName = "Heart";
                break;
            case CLUB:
                this.colourName = "Club";
                break;
            case SPADE:
                this.colourName = "Spade";
                break;
        }
    }
}
