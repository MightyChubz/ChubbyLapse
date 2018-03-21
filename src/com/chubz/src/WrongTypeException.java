package com.chubz.src;

public class WrongTypeException extends Throwable {
    WrongTypeException(String typeName) {
        super("Expected a different type from \"" + typeName + "\"!");
    }
}
