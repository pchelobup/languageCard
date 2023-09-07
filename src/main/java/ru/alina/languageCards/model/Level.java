package ru.alina.languageCards.model;

public enum Level {
    ONE, TWO, THREE, FOUR, FIVE, SIX;

    public int getNumberOfLevel(Level level) {
        switch (level) {
            case ONE -> {return 1;}
            case TWO -> {return 2;}
            case THREE -> {return 3;}
            case FOUR -> {return 4;}
            case FIVE -> {return 5;}
            case SIX -> {return 6;}
            default -> throw new IllegalArgumentException();

        }
    }
}
