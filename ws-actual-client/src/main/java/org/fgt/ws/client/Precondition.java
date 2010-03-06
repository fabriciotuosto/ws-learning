package org.fgt.ws.client;

public abstract class Precondition {

    public static <T> T checkNotNull(T object) {
        return checkNotNull(object, "Parameter shouldn't bee null");
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
