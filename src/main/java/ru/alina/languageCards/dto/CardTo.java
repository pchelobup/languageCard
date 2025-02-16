package ru.alina.languageCards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CardTo {

    @NotNull
    private Long id;

    @NotBlank
    private String word;

    @NotBlank
    private String translation;

    @NotNull
    private LocalDate lastTouch;

    public CardTo() {
    }

    public CardTo(Long id, String word, String translation) {
        this.id = id;
        this.word = word;
        this.translation = translation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "CardTo{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", lastTouch=" + lastTouch +
                '}';
    }
}
