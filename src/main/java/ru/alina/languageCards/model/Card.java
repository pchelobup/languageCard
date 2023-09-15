package ru.alina.languageCards.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity(name = "card")
public class Card extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "word", nullable = false)
    @NotBlank
    private String word;

    @Column(name = "translation", nullable = false)
    @NotBlank
    private String translation;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private State state;

    @Column(name = "last_touch")
    @NotNull
    private LocalDate lastTouch;

    public Card() {
    }

    public Card(String word, String translation, LocalDate lastTouch) {
        this.word = word;
        this.translation = translation;
        this.lastTouch = lastTouch;
    }

    public Card(Long id, String word, String translation, State state) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.state = state;
    }

    public Card(Long id, String word, String translation, State state, LocalDate lastTouch) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.state = state;
        this.lastTouch = lastTouch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getLastTouch() {
        return lastTouch;
    }

    public void setLastTouch(LocalDate lastTaught) {
        this.lastTouch = lastTaught;
    }

    @Override
    public String toString() {
        return "Card{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", state=" + state +
                ", lastTouch=" + lastTouch +
                ", id=" + id +
                '}';
    }
}
