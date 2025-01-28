package org.tautua.iso8583;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Stream;

import static org.tautua.foundation.Exceptions.tryIt;

/**
 * @author Larry Ruiz
 */
public class Message {
    public static final HexFormat FORMAT = HexFormat.of().withUpperCase();

    private MessageSchema schema;
    private int messageType;
    private List<Bitmap> bitmaps;
    private Map<String, FieldValue> values = new HashMap<>();

    public Message(MessageSchema schema) {
        this(schema, -1);
    }

    public Message(MessageSchema schema, int messageType) {
        this.schema = schema;
        this.messageType = messageType;
        this.bitmaps = schema.getBitmapsSupplier().get();
    }

    public static Message read(Reader reader, MessageSchema schema) throws IOException {
        return new Message(schema);
    }

    public String encode() {
        var writer = new StringWriter();
        tryIt(() -> write(writer));
        return writer.toString();
    }

    public void write(Writer writer) throws IOException {
        writer.write(FORMAT.toHexDigits(messageType, 4));
        bitmaps.forEach(bitmap -> tryIt(() -> bitmap.write(writer)));
        getOrderedFieldValueStream()
                .forEach(fv -> tryIt(() -> fv.write(writer)));
    }

    public Message setData(String name, Object value) {
        var f = schema.getFieldByName(name);
        values.put(name, new FieldValue(f, value));
        markBit(f.getPosition());
        return this;
    }

    public Object getData(String name) {
        return values.get(name);
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        getOrderedFieldValueStream().forEach(fv -> buff.append(fv.toString()).append("\n"));
        return buff.toString();
    }

    private void markBit(int position) {
        int offset = 0;
        for(int i = 0; i < bitmaps.size(); i++) {
            var bitmap = bitmaps.get(i);
            if(position >= offset + bitmap.getSize()) {
                offset += bitmap.getSize();
            } else {
                bitmap.markBit(position - offset);
                if(i > 0) {
                    // mark bitmap present on bitmap directory
                    bitmaps.get(0).markBit(i);
                }
                break;
            }
        }
    }

    private boolean isBitMarked(int position)
    {
        int offset = 0;
        for(Bitmap bitmap : bitmaps) {
            if(position >= offset + bitmap.getSize()) {
                offset += bitmap.getSize();
            } else {
                return bitmap.isBitMarked(position - offset);
            }
        }

        return false;
    }

    private Stream<FieldValue> getOrderedFieldValueStream() {
        return values.values().stream()
                .sorted(Comparator.comparingInt(fv -> fv.getField().getPosition()));
    }

    private Stream<Field> getPresentFieldStream() {
        return schema.getFields().stream().filter(f -> isBitMarked(f.getPosition()));
    }
}
