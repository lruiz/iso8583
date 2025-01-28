package org.tautua.iso8583;

/**
 * @author Larry Ruiz
 */
public class FixedLength implements DataLength {
    private int length;

    public FixedLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
