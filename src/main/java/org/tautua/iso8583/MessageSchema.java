package org.tautua.iso8583;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Larry Ruiz
 */
public class MessageSchema {
    private Supplier<List<Bitmap>> bitmapsSupplier;
    private final Map<String, Field> namedMap = new HashMap<>();
    private final Map<Integer, Field> indexedMap = new HashMap<>();

    public MessageSchema() {
        this(DefaultISO8583BitmapSupplier.INSTANCE);
    }

    public MessageSchema(Supplier<List<Bitmap>> bitmapsSupplier) {
        this.bitmapsSupplier = bitmapsSupplier;
    }

    public Supplier<List<Bitmap>> getBitmapsSupplier() {
        return bitmapsSupplier;
    }

    public Field getFieldByName(String name) {
        var f = namedMap.get(name);
        if(f == null) {
            throw new IllegalArgumentException("Unknown field '" + name + "'");
        }
        return f;
    }

    public Field getFieldByPosition(int position) {
        var f = indexedMap.get(position);
        if(f == null) {
            throw new IllegalArgumentException("Unknown field at '" + position + "' position");
        }
        return f;
    }

    public List<Field> getFields() {
        return namedMap.values().stream().sorted(Comparator.comparingInt(Field::getPosition)).toList();
    }

    public MessageSchema addField(Field field) {
        if(namedMap.containsKey(field.getName())) {
            throw new IllegalArgumentException("Field " + field.getName() + " already exists");
        }
        if(indexedMap.containsKey(field.getPosition())) {
            throw new IllegalArgumentException("Field with position " + field.getPosition() + " already exists");
        }
        namedMap.put(field.getName(), field);
        indexedMap.put(field.getPosition(), field);
        return this;
    }

}
