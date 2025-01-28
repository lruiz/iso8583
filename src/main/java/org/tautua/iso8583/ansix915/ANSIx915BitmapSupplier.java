package org.tautua.iso8583.ansix915;

import org.tautua.iso8583.Bitmap;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Larry Ruiz
 */
public class ANSIx915BitmapSupplier implements Supplier<List<Bitmap>> {
    public final static ANSIx915BitmapSupplier INSTANCE = new ANSIx915BitmapSupplier();

    private ANSIx915BitmapSupplier(){
    }

    @Override
    public List<Bitmap> get() {
        return List.of(
                new Bitmap(0, 8),  // directory bitmap
                new Bitmap(1, 32),
                new Bitmap(2, 32),
                new Bitmap(3, 32),
                new Bitmap(4, 32),
                new Bitmap(5, 32),
                new Bitmap(6, 32),
                new Bitmap(7, 32),
                new Bitmap(8, 32)
        );
    }
}
