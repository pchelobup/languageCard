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

    @Column(name = "last_taught")
    @NotNull
    private LocalDate lastTaught;

    public Card() {
    }

    public Card(String word, String translation, LocalDate lastTaught) {
        this.word = word;
        this.translation = translation;
        this.lastTaught = lastTaught;
    }

    public Card(Long id, String word, String translation, State state) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.state = state;
    }

    public Card(Long id, String word, String translation, State state, LocalDate lastTaught) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.state = state;
        this.lastTaught = lastTaught;
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

    public LocalDate getLastTaught() {
        return lastTaught;
    }

    public void setLastTaught(LocalDate lastTaught) {
        this.lastTaught = lastTaught;
    }

    @Override
    public String toString() {
        return "Card{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", state=" + state +
                ", lastTaught=" + lastTaught +
                ", id=" + id +
                '}';
    }
}
