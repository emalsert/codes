@Component
public class MxProwideMapper {

    public ChargesPaymentRequestV02 toChargesPaymentRequest(SwiftDto dto) {

        GroupHeader115 grpHdr = new GroupHeader115()
            .setMsgId(dto.getId())
            .setCreDtTm(OffsetDateTime.now());

        ChargesRecord9 record = new ChargesRecord9()
            .setChrgsId(dto.getChargeId())
            .setUndrlygTx(new TransactionReferences7()
                .setAcctSvcrRef(dto.getAcctSvcrRef()))
            .setAmt(new ActiveCurrencyAndAmount()
                .setValue(dto.getAmount())
                .setCcy(dto.getCurrency()))
            .setCdtDbtInd(CreditDebitCode.DBIT)
            .setValDt(new DateAndDateTime2Choice()
                .setDt(dto.getSettlementDate()));

        return new ChargesPaymentRequestV02()
            .setGrpHdr(grpHdr)
            .setChrgs(new Charges3Choice().setSngl(record));
    }
}