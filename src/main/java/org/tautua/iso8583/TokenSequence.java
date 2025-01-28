package org.tautua.iso8583;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Larry Ruiz
 */
public class TokenSequence {
    private List<Token> tokens = new ArrayList<>();

    public TokenSequence addToken(Token token) {
        tokens.add(token);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        tokens.forEach(token -> buff.append(token.toString()));
        return buff.toString();
    }
}
