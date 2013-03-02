package org.edloidas.text;

public interface TextElement {

    public static final short TYPE_ELEMENT = 0;
    public static final short TYPE_SYMBOL = 1;
    public static final short TYPE_WORD = 2;
    public static final short TYPE_SPECIAL = 3;

    public String getElement();

    public void setElement(String element);

    public String getOriginal();

    public void setOriginal(String original);

    public short getType();

    public void setType(short type);

    public double getFrequency();

    public void setFrequency(double frequency);
}
