package ru.alina.languageCards.model;

/**
 * Enumeration that represents status of domain objects - ACTIVE, DELETED, etc.
 *
 * @author Alina Davydova
 * @version 1.0
 */

public enum Status {
    ACTIVE, NOT_ACTIVE, DELETED;

    public boolean isActive() {
        return this.name().equals("ACTIVE");
    }
}