package com.soaswift.adapter.in;

import com.soaswift.application.PersistChargesClaimUseCase;
import com.soaswift.application.PersistResult;
import com.soaswift.domain.ChargesClaim;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint REST. La conversion MxDto -> ChargesClaim se fait ici, à la frontière :
 * le domaine ne voit jamais le DTO. 201 CREATED vs 200 OK signale au PEW
 * « nouveau » vs « déjà reçu, idempotent ». Pas de 409 : un rejeu n'est pas une
 * erreur dans un contexte at-least-once.
 */
@RestController
@RequestMapping("/api/v1/camt106")
public class SwiftController {

    private final PersistChargesClaimUseCase useCase;

    public SwiftController(PersistChargesClaimUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<SwiftResponseDto> persist(@RequestBody MxDto dto) {
        ChargesClaim claim = new ChargesClaim(dto.id(), dto.xml());
        PersistResult result = useCase.handle(claim);

        SwiftResponseDto response = new SwiftResponseDto(
            result.msgId(),
            result.status().name()
        );

        return switch (result.status()) {
            case PERSISTED -> ResponseEntity.status(HttpStatus.CREATED).body(response);
            case DUPLICATE -> ResponseEntity.ok(response);
        };
    }
}
