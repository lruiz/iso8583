package org.tautua.iso8583;

import org.junit.jupiter.api.Test;
import org.tautua.iso8583.ansix915.ANSIx915BitmapSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

/**
 * @author Larry Ruiz
 */
public class MessageSchemaTests {

    public static MessageSchema getRCSMessageSchema() {
        // API tests
        int directorysize = 8;
        int bitmapsize = 32;

        var bmap1 = directorysize + (bitmapsize * 0);
        var storeNumber = Field.builder("storeNumber").Pos(bmap1 + 1).AN().Max(16).build();
        var terminalNumber = Field.builder("terminalNumber").Pos(bmap1 + 2).N().Fixed(8).build();
        var authorizationCode = Field.builder("authorizationCode").Pos(bmap1 + 3).N().Fixed(6).build();
        var amount = Field.parse(bmap1 + 7 + ". n..12 amount");
        var processingCode = Field.builder("processingCode").Pos(bmap1 + 10).N().Fixed(6).build();
        var posEntryMode = Field.builder("posEntryMode").Pos(bmap1 + 11).N().Fixed(3).build();
        var posConditionCode = Field.parse(bmap1 + 12 + ". n8 posConditionCode");
        var transactionDate = Field.parse(bmap1 + 13 + ". n6 transactionDate");
        var transactionTime = Field.parse(bmap1 + 14 + ". n6 transactionTime");
        var idType = Field.parse(bmap1 + 17 + ". n2 idType");

        var bmap2 = directorysize + (bitmapsize * 1);
        var reversalReasonCode = Field.parse(bmap2 + 14 + ". n2 reversalReasonCode");
        var originalDate = Field.parse(bmap2 + 15 + ". n6 originalDate");
        var originalTime = Field.parse(bmap2 + 16 + ". n6 originalTime");
        var originalMessageType = Field.parse(bmap2 + 17 + ". n4 originalMessageType");
        var originalSTAN = Field.parse(bmap2 + 18 + ". n6 originalSTAN");

        var bmap3 = directorysize + (bitmapsize * 2);
        var privateUse = Field.parse(bmap3 + 15 + ". as...999 privateUse");

        var schema = new MessageSchema(ANSIx915BitmapSupplier.INSTANCE);

        schema.addField(storeNumber);
        schema.addField(terminalNumber);
        schema.addField(authorizationCode);
        schema.addField(amount);
        schema.addField(processingCode);
        schema.addField(posEntryMode);
        schema.addField(posConditionCode);
        schema.addField(transactionDate);
        schema.addField(transactionTime);
        schema.addField(idType);

        schema.addField(reversalReasonCode);
        schema.addField(originalDate);
        schema.addField(originalTime);
        schema.addField(originalMessageType);
        schema.addField(originalSTAN);

        schema.addField(privateUse);
        return schema;
    }

    @Test
    public void schemaDuplicateName() {
        var schema = MessageSchemaTests.getRCSMessageSchema();
        try {
            schema.addField(Field.builder("amount").Pos(20).N().Max(8).build());
            shouldHaveThrown(IllegalArgumentException.class);
        } catch(IllegalArgumentException e) {
            System.out.println(e);
        }

        System.out.println("messageclass definition");
        System.out.println(schema);
    }

    @Test
    public void schemaDuplicatePosition() {
        var schema = MessageSchemaTests.getRCSMessageSchema();
        try {
            schema.addField(Field.builder("cashbackAmount").Pos(20).N().Max(8).build());
            shouldHaveThrown(IllegalArgumentException.class);
        } catch(IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println("messageclass definition");
        System.out.println(schema);
    }

    @Test
    public void schemaTests() {
        var schema = MessageSchemaTests.getRCSMessageSchema();

        var fields = schema.getFields();
        assertThat(fields.get(3).getPosition()).isEqualTo(15);
        assertThat(fields.get(3).getName()).isEqualTo("amount");
        // assertThat(AreEqual(fields[2].Item2.Name, "tillNumber");
        System.out.println("messageclass definition");
        System.out.println(schema);
    }
}
