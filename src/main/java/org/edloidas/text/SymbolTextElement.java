package org.edloidas.text;

public class SymbolTextElement implements TextElement {

    private String element;
    private short type;

    public SymbolTextElement(String element) {
        this.element = element;
        this.type = TextElement.TYPE_SYMBOL;
    }

    public SymbolTextElement(String element, short type) {
        this.element = element;
        this.type = type;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getOriginal() {
        return element;
    }

    public void setOriginal(String original) {
        this.element = original;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public double getFrequency() {
        return 0.0d;
    }

    public void setFrequency(double frequency) {

    }
}
