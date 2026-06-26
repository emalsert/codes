@Component
public class Camt106XmlBuilder {

    private final MxProwideMapper mapper;

    public Camt106XmlBuilder(MxProwideMapper mapper) {
        this.mapper = mapper;
    }

    public MxDto build(SwiftDto dto) {
        ChargesPaymentRequestV02 content = mapper.toChargesPaymentRequest(dto);

        MxCamt10600102 mx = new MxCamt10600102();
        mx.setChrgsPmtReq(content);

        MxWriteConfiguration conf = new MxWriteConfiguration();
        conf.indent = true;
        conf.includeXMLDeclaration = true;

        return new MxDto(dto.getId(), mx.message(conf));
    }
}