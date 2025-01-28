package org.tautua.iso8583;

/**
 * @author Larry Ruiz
 */
public interface DataLength {

    static DataLength parse(String expression) {
        if(expression.contains(".")) {
            int max = Integer.parseInt(expression.replaceAll("\\.", ""));
            return new VariableLength(max);
        } else {
            return new FixedLength(Integer.parseInt(expression));
        }
    }
}
