package com.soaswift.application;

import com.soaswift.domain.ChargesClaim;
import com.soaswift.domain.ChargesClaimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orchestration. La frontière transactionnelle (@Transactional) est définie ici,
 * pas dans le repository : check d'existence + insert dans la même transaction.
 */
@Service
@Transactional
public class PersistChargesClaimUseCase {

    private final ChargesClaimRepository repository;

    public PersistChargesClaimUseCase(ChargesClaimRepository repository) {
        this.repository = repository;
    }

    public PersistResult handle(ChargesClaim claim) {
        if (repository.existsByMsgId(claim.msgId())) {
            return PersistResult.duplicate(claim.msgId());
        }
        repository.save(claim);
        return PersistResult.persisted(claim.msgId());
    }
}
