package ru.alina.languageCards.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "card")
public class Card extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "word", nullable = false)
    @NotBlank
    private String word;

    @Column(name = "translation", nullable = false)
    @NotBlank
    private String translation;

    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Level level;

    public Card() {
    }

    public Card(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public Card(Long id, String word, String translation, Level level) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.level = level;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Card{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", level=" + level +
                ", id=" + id +
                '}';
    }
}
