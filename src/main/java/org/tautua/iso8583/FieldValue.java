package org.tautua.iso8583;

import org.tautua.foundation.Strings;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Larry Ruiz
 */
public class FieldValue {
    private Field field;
    private Object value;

    public FieldValue(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return field.getPosition() + ". " + field.getName() + " = " + String.valueOf(value);
    }

    public void write(Writer writer) throws IOException {
        var dataType = field.getType();
        var dataLength = field.getLength();
        var str = value.toString();

        if(dataLength instanceof VariableLength) {
            var vl = (VariableLength)dataLength;
            var l = Strings.lpad(String.valueOf(str.length()), vl.getLengthSize(), '0');
            writer.write(l);
            writer.write(str);
        } else {
            var dl = (FixedLength)dataLength;
            String encoded;
            if(dataType == DataType.NUMERIC) {
                encoded = Strings.lpad(str, dl.getLength(), '0');
            } else {
                encoded = Strings.rpad(str, dl.getLength());
            }
            writer.write(encoded);
        }
    }
}
