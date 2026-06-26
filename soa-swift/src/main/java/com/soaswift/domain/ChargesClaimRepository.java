package com.soaswift.domain;

/**
 * Port sortant (hexagonal). Le domaine déclare le besoin de persistance
 * sans connaître l'implémentation. existsByMsgId sert à l'idempotence (rejeu PEW).
 */
public interface ChargesClaimRepository {

    boolean save(ChargesClaim claim);

    boolean existsByMsgId(String msgId);
}
