package com.chubz.src.data.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    public static Element[] ParseLines(String[] lines) {
        ArrayList<Element> arrayList = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("#") || line.equals("")) {
                continue;
            }

            List<String> split = new LinkedList<>(Arrays.asList(line.split(" ")));
            split.removeIf(e -> e.equals("="));
            String name = split.get(0);
            String value = split.get(1);
            arrayList.add(new Element(name, value));
        }

        return arrayList.toArray(new Element[arrayList.size()]);
    }
}
