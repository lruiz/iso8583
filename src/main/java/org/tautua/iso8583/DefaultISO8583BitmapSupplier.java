package org.tautua.iso8583;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Larry Ruiz
 */
public class DefaultISO8583BitmapSupplier implements Supplier<List<Bitmap>> {
    public final static DefaultISO8583BitmapSupplier INSTANCE = new DefaultISO8583BitmapSupplier();

    private DefaultISO8583BitmapSupplier(){
    }

    @Override
    public List<Bitmap> get() {
        return List.of(
                new Bitmap(0), // primary
                new Bitmap(1) // secondary
        );
    }
}
