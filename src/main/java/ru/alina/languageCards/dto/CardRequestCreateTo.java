package ru.alina.languageCards.dto;


import java.time.LocalDate;

public class CardRequestCreateTo {
    private String word;

    private String translation;

    private LocalDate lastTaught;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public LocalDate getLastTaught() {
        return lastTaught;
    }

    public void setLastTaught(LocalDate lastTaught) {
        this.lastTaught = lastTaught;
    }

    @Override
    public String toString() {
        return "CardTo{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", lastTaught=" + lastTaught +
                '}';
    }
}
