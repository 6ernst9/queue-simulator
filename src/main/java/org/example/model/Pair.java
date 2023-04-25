package org.example.model;

public class Pair {
    private int index;
    private int value;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }
    public int getIndex() {
        return index;
    }
    public int getValue() {
        return value;
    }
}
