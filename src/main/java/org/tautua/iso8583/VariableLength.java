package org.tautua.iso8583;

/**
 * @author Larry Ruiz
 */
public class VariableLength implements DataLength {
    private int maxLength;
    private int lengthSize;

    public VariableLength(int maxLength) {
        this.maxLength = maxLength;
        lengthSize = getLengthSize(maxLength);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getLengthSize() {
        return lengthSize;
    }

    private int getLengthSize(int maxLength) {
        var size = Math.abs(maxLength);
        if (size == 0) {
            return 1;
        }
        return (int) Math.log10(size) + 1;
    }
}
