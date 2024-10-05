package org.example.wms_be.exception;

public class BadSqlGrammarException extends RuntimeException  {
    public BadSqlGrammarException(String message) {
        super("Bad SQL grammar: " + message);
    }
}
