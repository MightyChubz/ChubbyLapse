package com.chubz.src.data.storage;

public class Element<T> {
    public String name;
    public T value;

    Element(String name, T value) {
        this.name = name;
        this.value = value;
    }
}
