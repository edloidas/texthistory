package org.edloidas.text;

public class WordTextElement implements TextElement {

    private String element;
    private String original;
    private short type;
    private double frequency;

    public WordTextElement(String element) {
        this.element = element;
        this.original = element;
        this.type = TextElement.TYPE_WORD;
        this.frequency = 0.0d;
    }

    public WordTextElement(String element, String original) {
        this.element = element;
        this.original = original;
        this.type = TextElement.TYPE_WORD;
        this.frequency = 0.0d;
    }

    public WordTextElement(String element, String original, short type) {
        this.element = element;
        this.original = original;
        this.type = type;
        this.frequency = 0.0d;
    }

    public WordTextElement(String element, String original,
                           short type, double frequency) {
        this.element = element;
        this.original = original;
        this.type = type;
        this.frequency = frequency;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public double getFrequency() {
        return this.frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
