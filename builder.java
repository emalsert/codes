@Component
public class Camt106XmlBuilder {

    private final MxProwideMapper mapper;

    public Camt106XmlBuilder(MxProwideMapper mapper) {
        this.mapper = mapper;
    }

    public Camt106Message build(MxDto dto) {
        ChargesPaymentRequestV02 content = mapper.toChargesPaymentRequest(dto);

        MxCamt10600102 mx = new MxCamt10600102();
        mx.setChargesPaymentRequest(content);
        // mx.setAppHdr(...) seulement si la couche aval exige un BAH (head.001)

        MxWriteConfiguration conf = new MxWriteConfiguration();
        conf.indent = true;
        conf.includeXMLDeclaration = true;

        String xml = mx.message(conf);
        return new Camt106Message(dto.getId(), xml); // adapte le getId()
    }
}