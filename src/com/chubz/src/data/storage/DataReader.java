package com.chubz.src.data.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataReader {
    private Element[] read(String[] lines) {
        return Parser.ParseLines(lines);
    }

    public Element[] read(Path path) throws IOException {
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path);
        return read(lines.toArray(new String[lines.size()]));
    }
}
