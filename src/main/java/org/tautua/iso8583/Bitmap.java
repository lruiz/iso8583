package org.tautua.iso8583;

import java.io.IOException;
import java.io.Writer;
import java.util.HexFormat;

/**
 * @author Larry Ruiz
 */
public class Bitmap {
    public static final HexFormat FORMAT = HexFormat.of().withUpperCase();
    private int position;
    private long bits;
    private int size;

    public Bitmap(int position) {
        this(position, 64);
    }

    public Bitmap(int position, int size) {
        this.position = position;
        this.size = size;
    }

    public int getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public String getHexValue() {
        return FORMAT.toHexDigits(bits >> (64 - size), size / 4);
    }

    public boolean isBitMarked(int position) {
        return (bits & mask(position)) != 0;
    }

    public void markBit(int position) {
        bits |= mask(position);
    }

    public void unmarkBit(int position) {
        bits &= ~mask(position);
    }

    public String toString() {
        return getHexValue();
    }

    public void write(Writer writer) throws IOException {
        if(bits != 0) {
            writer.write(getHexValue());
        }
    }

    private long mask(int position) {
        checkPosition(position);
        return 1L << (64 - position);
    }

    private void checkPosition(int position) {
        if(position < 1 || position > size) {
            throw new IllegalArgumentException("Position should be between 1 and " + size + ".");
        }
    }

}
