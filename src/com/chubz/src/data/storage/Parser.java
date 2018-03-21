package com.chubz.src.data.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

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
            if (Pattern.matches("-?\\d+\\.\\d+", value))
                arrayList.add(new Element<>(name, Double.parseDouble(value)));
            else if (Pattern.matches("-?\\d+", value))
                arrayList.add(new Element<>(name, Integer.parseInt(value)));
            else if (Pattern.matches("(\"|'{2})", value))
                arrayList.add(new Element<>(name, value.replaceAll("[\"']", "")));
            else
                throw new IllegalArgumentException("Variable doesn't fit to any type!");
        }

        return arrayList.toArray(new Element[arrayList.size()]);
    }
}
