package com.soaswift.application;

/**
 * Objet de retour du use case. Mappé en HTTP status par le controller
 * (201 vs 200). Le use case ne connaît pas HTTP.
 */
public record PersistResult(String msgId, Status status) {

    public enum Status {
        PERSISTED,
        DUPLICATE
    }

    public static PersistResult persisted(String msgId) {
        return new PersistResult(msgId, Status.PERSISTED);
    }

    public static PersistResult duplicate(String msgId) {
        return new PersistResult(msgId, Status.DUPLICATE);
    }
}
