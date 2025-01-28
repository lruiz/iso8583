package org.tautua.iso8583;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tautua.iso8583.ansix915.ANSIx915BitmapSupplier;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Larry Ruiz
 */
public class MessageTests {

    public static final String REVERSAL_PROCESSING_CODE = "000000";
    public static final String REASON_CUSTOMER_CANCEL = "08";

    private static MessageSchema rcsSchema;
    
    @BeforeAll
    public static void setup() {
        rcsSchema = new MessageSchema(ANSIx915BitmapSupplier.INSTANCE)
                .addField(Field.parse("9. an..16 cardAcceptorId")) //1,1
                .addField(Field.parse("10. n8 cardAcceptorTerminalId")) //1,2
                .addField(Field.parse("11. n6 systemTraceAuditNumber")) //1,3
                .addField(Field.parse("13. z76 track1Data")) //1,5
                .addField(Field.parse("14. z37 track2Data")) //1,6
                .addField(Field.parse("15. n..12 transactionAmount")) //1,7
                .addField(Field.parse("16. an..28 cardholderIdNumber")) //1,8
                .addField(Field.parse("17. n4 expirationDate")) //1,9
                .addField(Field.parse("18. n6 processingCode")) //1,10
                .addField(Field.parse("19. n3 posEntryMode")) //1,11
                .addField(Field.parse("20. n8 posConditionCode")) //1,12
                .addField(Field.parse("21. n6 transactionDate")) //1,13
                .addField(Field.parse("22. n6 transactionTime")) //1,14
                .addField(Field.parse("25. n2 idType")) //1,17
                .addField(Field.parse("27. an2 responseCode")) //1,19
                .addField(Field.parse("28. an6 authIdResponse")) //1,20
                .addField(Field.parse("31. n7 terminalType")) //1,23
                .addField(Field.parse("35. an...999 transportData")) //1,27
                .addField(Field.parse("36. an....9999 displayData")) //1,28
                .addField(Field.parse("41. n4 merchantType")) //2,1
                .addField(Field.parse("42. n10 authDateTime")) //2,2
                .addField(Field.parse("43. n10 saleDateTime")) //2,3
                .addField(Field.parse("44. n2 authSourceCode")) //2,4
                .addField(Field.parse("45. n10 transmissionDateTime")) //2,5
                .addField(Field.parse("52. an1 completionCode")) //2,12
                .addField(Field.parse("54. n2 reversalReasonCode")) //2,14
                .addField(Field.parse("55. n6 originalTransactionDate")) //2,15
                .addField(Field.parse("56. n6 originalTransactionTime")) //2,16
                .addField(Field.parse("57. n4 originalMessageType")) //2,17
                .addField(Field.parse("58. n6 originalSTAN")) //2,18
                .addField(Field.parse("59. n8 origCardAcceptorTermId")) //2,19
                .addField(Field.parse("60. n10 originalTransmissionDateTime")) //2,20
                .addField(Field.parse("61. an..16 origCardAcceptorId")) //2,21
                .addField(Field.parse("74. n10 batchNumber")) //3,2
                .addField(Field.parse("87. ans...999 privateUse")) //3,15
                .addField(Field.parse("96. an...120 additionalAmounts")) //3,24
                .addField(Field.parse("103. an.9 clerkId")) //3,31
                .addField(Field.parse("115. an40 cardAcceptorNameLoc")) //4,11
                .addField(Field.parse("121. an12 retrievalReferenceNumber")) //4,17
                .addField(Field.parse("128. ans...999 biometricInformation")) //4,24
                .addField(Field.parse("177. ans....9999 processorSpecificData")) //6,9
                .addField(Field.parse("181. an....9999 additionalTokens")) //6,13
        ;
    }
    
    @Test
    public void decode() throws IOException {
        var schema = MessageSchemaTests.getRCSMessageSchema();
        var original = GetSaleReversalMessage();

        StringWriter writer = new StringWriter();
        original.write(writer);
        var encoded = writer.toString();

        StringReader reader = new StringReader(encoded);
        var decoded = Message.read(reader, schema);

        assertThat(decoded).isNotNull();

    }

    @Test
    public void ansix915messageValues() {
        var message = GetSaleReversalMessage();

        var encoded = message.encode();

        System.out.println("message");
        System.out.println(message);
        System.out.println(encoded);

        assertThat(encoded).isNotNull().isEqualTo("0400E0E27C80000007C00000020000041553000000160000010399900000001100000000241019141500000824100111300002000012380070041116");
    }

    private static Message GetSaleReversalMessage() {
        var originalDate = LocalDateTime.parse("2024-10-01 11:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var transactionDate = LocalDateTime.parse("2024-10-19 14:15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var folio = "V15531100001238";
        var trnNumber = folio.substring(folio.length() - 6);
        var refFolio = "R15531100000001";
        var refNumber = refFolio.substring(refFolio.length() - 6);

        var terminalNumber = "16";
        var tokenSeq = new TokenSequence().addToken(new Token("11", terminalNumber));

        var message = new Message(rcsSchema, 0x0400);
        message
                .setData("cardAcceptorId", "1553")
                .setData("cardAcceptorTerminalId", terminalNumber)
                .setData("systemTraceAuditNumber", refNumber)
                .setData("transactionAmount", "999")
                .setData("processingCode", REVERSAL_PROCESSING_CODE)
                .setData("posEntryMode", 11)
                .setData("posConditionCode", 0)
                .setData("transactionDate", transactionDate.format(DateTimeFormatter.ofPattern("yyMMdd")))
                .setData("transactionTime", transactionDate.format(DateTimeFormatter.ofPattern("HHmmss")))
                .setData("idType", 0)
                .setData("reversalReasonCode", REASON_CUSTOMER_CANCEL)
                .setData("originalTransactionDate", originalDate.format(DateTimeFormatter.ofPattern("yyMMdd")))
                .setData("originalTransactionTime", originalDate.format(DateTimeFormatter.ofPattern("HHmmss")))
                .setData("originalMessageType", "0200")
                .setData("originalSTAN", trnNumber)
                .setData("privateUse", tokenSeq);
        return message;
    }

    @Test
    public void messageEncode() {
        var schema = MessageSchemaTests.getRCSMessageSchema();
        var message = new Message(schema, 0x0400);
        message.setData("storeNumber", "1553");
        message.setData("terminalNumber", "11");

        var encoded = message.encode();
        assertThat(encoded).isEqualTo("040080C000000004155300000011");
    }

    @Test
    public void messageValues() {
        var schema = MessageSchemaTests.getRCSMessageSchema();
        var message = new Message(schema);
        message.setData("storeNumber", "1553");
        message.setData("terminalNumber", "11");

        System.out.println("message");
        System.out.println(message);
    }


}
