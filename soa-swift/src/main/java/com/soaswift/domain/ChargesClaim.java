package com.soaswift.domain;

/**
 * Entité métier. Java pur : aucune dépendance à Spring, JPA ou JDBC.
 * Record immutable avec validation dans le compact constructor.
 */
public record ChargesClaim(String msgId, String xml) {

    public ChargesClaim {
        if (msgId == null || msgId.isBlank()) {
            throw new IllegalArgumentException("msgId obligatoire");
        }
        if (xml == null || xml.isBlank()) {
            throw new IllegalArgumentException("xml obligatoire");
        }
    }
}
