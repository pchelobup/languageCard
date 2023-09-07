package ru.alina.languageCards.util;

import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.UnsuitableEntity;
import ru.alina.languageCards.model.BaseEntity;

public class ValidationUtil {
    public static void isNew(BaseEntity entity) {
        if (!entity.isNew()) throw new UnsuitableEntity("Entity already was created and can not be created again");
    }

    public static void isNotNew(BaseEntity entity) {
        if (entity.isNew()) throw new UnsuitableEntity("Entity already was created and can not be created again");
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
