package org.edloidas.text.words.content;

public class Concordance {

    private String before;
    private String word;
    private String after;

    public Concordance(String before, String after, String word) {
        this.before = before;
        this.after = after;
        this.word = word;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
