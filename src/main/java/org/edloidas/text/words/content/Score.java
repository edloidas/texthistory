package org.edloidas.text.words.content;

import org.apache.log4j.Logger;

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
        this.calculate();
    }

    public Score(String word, int count) {
        this.word = word;
        this.count = count;
        this.score = 0;
        this.calculate();
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
        this.calculate();
    }

    /** Itarates count and calculate zScore. */
    public void iterate() {
        this.count++;
        this.calculate();
    }

    /** Calculates zScore. */
    public void calculate() {
        try {
            //this.score =
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
