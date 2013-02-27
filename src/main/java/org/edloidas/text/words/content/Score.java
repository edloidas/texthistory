package org.edloidas.text.words.content;

import org.apache.log4j.Logger;

/**
 *
 */
public class Score {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(Score.class);

    private String word;

    private int count;

    private double score;

    public Score(String word) {
        this.word = word;
        this.count = 1;
        this.score = 0;
    }

    public Score(String word, int count) {
        this.word = word;
        this.count = count;
        this.score = 0;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // Tools

    /** Resets all data and sets count to 1. */
    public void reset() {
        this.count = 1;
        this.score = 0;
    }

    /** Itarates count and calculate zScore. */
    public void iterate() {
        this.count++;
    }

    /**
     * Calculates zScore.
     *
     * @param totalCount    count of the word category B (this word).
     * @param neighborCount count of the word category A (neighbor).
     * @param wordsCount    total words count in text.
     */
    /* Z  - wordsCount or total words count in the text (key or meaningful)
     * Fn - neighborCount or count of the word category A int the text.
     * Fc - totalCount or count of the word category B int the text.
     * K  - count of such pairs as A..B or B..A in text (range is determined by S)
     * S  - range or count of words in from which contains pair A..B/B..A
     *
     * zScore = (|K - E|)^{E * q}
     * q = 1 - p
     * E = p * Fn * S
     * p = Fc / (Z - Fn)
     */
    public void calculate(int totalCount, int neighborCount, int wordsCount) {
        try {
            int S = 2;
            double p = totalCount / (wordsCount - neighborCount);
            double E = p * neighborCount * S;
            double q = 1.0d - p;
            this.score =  Math.pow(Math.abs(((double)this.count - E)), (E * q));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
