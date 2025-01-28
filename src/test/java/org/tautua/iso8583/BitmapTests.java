package org.tautua.iso8583;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Larry Ruiz
 */
class BitmapTests {
    @Test
    public void bitmap32() {
        var bitmap = new Bitmap(0, 32);
        bitmap.markBit(2);
        bitmap.markBit(3);
        bitmap.markBit(4);

        assertThat(bitmap.isBitMarked(2)).isTrue();
        assertThat(bitmap.isBitMarked(3)).isTrue();
        assertThat(bitmap.isBitMarked(4)).isTrue();

        assertThat(bitmap.getHexValue()).isEqualTo("70000000");
        System.out.println(bitmap.getHexValue());

        bitmap.unmarkBit(4);
        assertThat(bitmap.isBitMarked(4)).isFalse();
        System.out.println(bitmap.getHexValue());
    }

    @Test
    public void bitmap64HexValue() {
        var bitmap = new Bitmap(0);
        bitmap.markBit(42);
        bitmap.markBit(50);
        bitmap.markBit(53);
        bitmap.markBit(62);

        assertThat(bitmap.getHexValue()).isEqualTo("0000000000404804");
    }

    @Test
    public void bitmap64MarkUnmark() {
        var bitmap = new Bitmap(0);
        bitmap.markBit(2);
        bitmap.markBit(3);
        bitmap.markBit(4);
        bitmap.markBit(12);
        bitmap.markBit(28);
        bitmap.markBit(32);
        bitmap.markBit(39);
        bitmap.markBit(41);
        bitmap.markBit(42);
        bitmap.markBit(50);
        bitmap.markBit(53);
        bitmap.markBit(62);

        assertThat(bitmap.isBitMarked(2)).isTrue();
        assertThat(bitmap.isBitMarked(3)).isTrue();
        assertThat(bitmap.isBitMarked(4)).isTrue();
        assertThat(bitmap.isBitMarked(12)).isTrue();
        assertThat(bitmap.isBitMarked(28)).isTrue();
        assertThat(bitmap.isBitMarked(32)).isTrue();
        assertThat(bitmap.isBitMarked(39)).isTrue();
        assertThat(bitmap.isBitMarked(41)).isTrue();
        assertThat(bitmap.isBitMarked(42)).isTrue();
        assertThat(bitmap.isBitMarked(50)).isTrue();
        assertThat(bitmap.isBitMarked(53)).isTrue();
        assertThat(bitmap.isBitMarked(62)).isTrue();

        assertThat(bitmap.getHexValue()).isEqualTo("7010001102C04804");
        System.out.println(bitmap.getHexValue());

        bitmap.unmarkBit(62);
        assertThat(bitmap.isBitMarked(62)).isFalse();
        System.out.println(bitmap.getHexValue());
    }

    @Test
    public void bitmap8() {
        var bitmap = new Bitmap(0, 8);
        bitmap.markBit(2);
        bitmap.markBit(3);
        bitmap.markBit(4);

        assertThat(bitmap.isBitMarked(2)).isTrue();
        assertThat(bitmap.isBitMarked(3)).isTrue();
        assertThat(bitmap.isBitMarked(4)).isTrue();

        assertThat(bitmap.getHexValue()).isEqualTo("70");
        System.out.println(bitmap.getHexValue());

        bitmap.unmarkBit(4);
        assertThat(bitmap.isBitMarked(4)).isFalse();
        System.out.println(bitmap.getHexValue());
    }
}