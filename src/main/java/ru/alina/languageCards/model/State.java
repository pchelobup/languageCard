package ru.alina.languageCards.model;

public enum State {
    NEW, LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR, LEVEL_FIVE, LEVEL_SIX, FINISH;

    public int getNumberOfLevel(State state) {
        switch (state) {
            case LEVEL_ONE -> {return 1;}
            case LEVEL_TWO -> {return 2;}
            case LEVEL_THREE -> {return 3;}
            case LEVEL_FOUR -> {return 4;}
            case LEVEL_FIVE -> {return 5;}
            case LEVEL_SIX -> {return 6;}
            default -> throw new IllegalArgumentException();

        }
    }
}
