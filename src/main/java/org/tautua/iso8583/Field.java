package org.tautua.iso8583;

import java.util.regex.Pattern;

/**
 * @author Larry Ruiz
 */
public class Field {
    private String name;
    private int position;
    private DataType type;
    private DataLength length;

    public Field(String name, int position, DataType type, DataLength length) {
        this.name = name;
        this.position = position;
        this.type = type;
        this.length = length;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public DataLength getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public DataType getType() {
        return type;
    }

    public static Field parse(String expression)
    {
        var regex = Pattern.compile("(\\d+)\\.\\s+([a-z]+)(\\d+|\\.+\\d+)\\s+(\\w+)");
        var match = regex.matcher(expression);

        if(match.find()) {
            var position = Integer.parseInt(match.group(1));
            var type = DataType.fromAbbreviation(match.group(2));
            var lenght = DataLength.parse(match.group(3));
            return new Field(match.group(4), position, type, lenght);
        }

        throw new IllegalArgumentException("Invalid expression: " + expression);
    }



    @Override
    public String toString() {
        return position + ". " + type + length + " " + name;
    }

    public static class Builder {
        private String name;
        private int position;
        private DataType dataType;
        private DataLength length;

        public Builder(String name) {
            this.name = name;
        }

        public Builder A() {
            this.dataType = DataType.ALPHA;
            return this;
        }

        public Builder AN() {
            this.dataType = DataType.ALPHANUMERIC;
            return this;
        }

        public Builder ANS() {
            this.dataType = DataType.ANS;
            return this;
        }

        public Builder B() {
            this.dataType = DataType.BINARY;
            return this;
        }

        public Builder Fixed(int fixedLength) {
            this.length = new FixedLength(fixedLength);
            return this;
        }

        public Builder Max(int maxLength) {
            this.length = new VariableLength(maxLength);
            return this;
        }

        public Builder N() {
            this.dataType = DataType.NUMERIC;
            return this;
        }

        public Builder Pos(int position) {
            this.position = position;
            return this;
        }

        public Field build() {
            if(dataType == null) {
                throw new IllegalArgumentException("Type not defined");
            }

            if(length == null) {
                throw new IllegalArgumentException("Length not defined");
            }
            if(position == 0) {
                throw new IllegalArgumentException("Invalid position");
            }
            return new Field(name, position, dataType, length);
        }
    }
}
