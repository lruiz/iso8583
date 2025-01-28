package org.tautua.iso8583;

/**
 * @author Larry Ruiz
 */
public enum DataType {
    ALPHA,
    NUMERIC,
    ALPHANUMERIC,
    SPECIAL,
    ANS,
    AS,
    NS,
    BINARY,
    TRACKDATA;

    public static DataType fromAbbreviation(String abbreviation) {
        switch(abbreviation) {
            case "a":
                return DataType.ALPHA;
            case "n":
                return DataType.NUMERIC;
            case "s":
                return DataType.SPECIAL;
            case "an":
                return DataType.ALPHANUMERIC;
            case "as":
                return DataType.AS;
            case "ns":
                return DataType.NS;
            case "ans":
                return DataType.ANS;
            case "b":
                return DataType.BINARY;
            case "z":
                return DataType.TRACKDATA;
        }

        throw new IllegalArgumentException(abbreviation);
    }
}
