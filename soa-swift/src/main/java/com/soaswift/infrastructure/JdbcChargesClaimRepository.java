package com.soaswift.infrastructure;

import com.soaswift.domain.ChargesClaim;
import com.soaswift.domain.ChargesClaimRepository;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Types;

/**
 * Implémentation du port. SqlParameterValue(Types.CLOB, ...) force le type CLOB
 * Oracle pour que le XML ne soit pas tronqué à 4000 bytes. SYSTIMESTAMP est
 * généré côté base (horloge du serveur Oracle), pas côté Java.
 */
@Repository
public class JdbcChargesClaimRepository implements ChargesClaimRepository {

    private final JdbcClient jdbc;

    public JdbcChargesClaimRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean save(ChargesClaim claim) {
        return jdbc.sql("""
                INSERT INTO CAMT106_MESSAGE (MSG_ID, XML_CONTENT, CRE_DT_TM)
                VALUES (?, ?, SYSTIMESTAMP)
                """)
            .param(claim.msgId())
            .param(new SqlParameterValue(Types.CLOB, claim.xml()))
            .update() == 1;
    }

    @Override
    public boolean existsByMsgId(String msgId) {
        return jdbc.sql("SELECT COUNT(1) FROM CAMT106_MESSAGE WHERE MSG_ID = ?")
            .param(msgId)
            .query(Integer.class)
            .single() > 0;
    }
}
