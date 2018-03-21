package com.chubz.src.data;

import java.util.LinkedList;

public class DataGenerator {
    public static void addComment(LinkedList<String> data, String comment) {
        data.add("#" + comment);
    }

    public static <T> void addVariable(LinkedList<String> data, String variable, T value) {
        data.add(variable + " = " + value);
    }

    public static void addBreak(LinkedList<String> data) {
        data.add("");
    }
}
