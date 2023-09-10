package ru.alina.languageCards.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CardRequestCreateTo {

    @NotBlank
    private String word;

    @NotBlank
    private String translation;

    @NotNull
    private LocalDate lastTaught;

    public CardRequestCreateTo() {
    }

    public CardRequestCreateTo(String word, String translation, LocalDate lastTaught) {
        this.word = word;
        this.translation = translation;
        this.lastTaught = lastTaught;
    }

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
