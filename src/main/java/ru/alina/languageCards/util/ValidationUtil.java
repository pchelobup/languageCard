package ru.alina.languageCards.util;

import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.UnsuitableArgument;
import ru.alina.languageCards.model.BaseEntity;

import java.util.Collection;

public class ValidationUtil {
    public static void isNew(BaseEntity entity) {
        if (!entity.isNew()) throw new UnsuitableArgument("Entity already was created and can not be created again");
    }

    public static void isNotNew(BaseEntity entity) {
        if (entity.isNew()) throw new UnsuitableArgument("Entity already was created and can not be created again");
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

    public static boolean notNullAndNotEmpty(String... strings) {
        for (String s : strings) {
           notNullAndNotEmpty(s,"arguments can not be null or empty");
        }
        return true;
    }

    public static boolean notNull(Object... objects) {
        for (Object o : objects) {
            notNullAndNotEmpty(o,"arguments can not be null");
        }
        return true;
    }

    public static void notNullAndNotEmpty(Collection collection) {
        if (collection == null || collection.size()==0) {
            throw new UnsuitableArgument("arguments can not be null or empty");
        }
    }

    private static void notNullAndNotEmpty(String string, String msg) {
        if (string == null || string.trim().isEmpty()) {
            throw new UnsuitableArgument(msg);
        }
    }

    private static void notNullAndNotEmpty(Object o, String msg) {
        if (o == null) {
            throw new UnsuitableArgument(msg);
        }
    }
}
