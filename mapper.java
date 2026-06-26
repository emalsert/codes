@Component
public class MxProwideMapper {

    public ChargesPaymentRequestV02 toChargesPaymentRequest(MxDto dto) {
        GroupHeader15 grpHdr = new GroupHeader15()
            .setMsgId(dto.getId())
            .setCreDtTm(OffsetDateTime.now()); // CreDtTm = création du message

        ChargesRecord9 record = new ChargesRecord9()
            .setChrgsId(dto.getChargeId())
            .setAmt(new ActiveCurrencyAndAmount()
                .setValue(dto.getAmount())
                .setCcy(dto.getCurrency()))
            .setCdtDbtInd(CreditDebitCode.DBIT)
            .setValDt(new DateAndDateTime2Choice()
                .setDt(dto.getSettlementDate())); // ValDt = date de règlement

        return new ChargesPaymentRequestV02()
            .setGrpHdr(grpHdr)
            .addChrgs(record);
    }
}