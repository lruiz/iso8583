package org.tautua.iso8583;

import org.tautua.foundation.Strings;

/**
 * @author Larry Ruiz
 */
public class Token {
    private String name;
    private String data;

    public Token(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return Strings.lpad(String.valueOf(name.length() + data.length()), 3, '0') + name + data;
    }
}
