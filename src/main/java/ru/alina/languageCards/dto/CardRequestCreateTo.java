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
    private LocalDate lastTouch;

    public CardRequestCreateTo() {
    }

    public CardRequestCreateTo(String word, String translation, LocalDate lastTouch) {
        this.word = word;
        this.translation = translation;
        this.lastTouch = lastTouch;
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

    public LocalDate getLastTouch() {
        return lastTouch;
    }

    public void setLastTouch(LocalDate lastTouch) {
        this.lastTouch = lastTouch;
    }

    @Override
    public String toString() {
        return "CardRequestCreateTo{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", lastTouch=" + lastTouch +
                '}';
    }
}
