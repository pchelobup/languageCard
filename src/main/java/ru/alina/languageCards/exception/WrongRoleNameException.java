package ru.alina.languageCards.exception;

public class WrongRoleNameException extends RuntimeException {
    public WrongRoleNameException(String message) {
        super(message);
    }
}
