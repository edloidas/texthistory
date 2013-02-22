package org.edloidas.text.words.content;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class KeyWord {
    // средневзешенный коэффициент - SUMM(индекс * число вхождений) / SUMM()
    // число вхождений  -  среди оригинальных слов
    // ЧАСТОТА - целое число
    /** Common logger */
    private static final Logger LOGGER = Logger.getLogger(KeyWord.class);

    /** Word identifier */
    private int id;

    /** Word name */
    private String name;

    /** Rank as reverse index of words, sorted by count. */
    private int rank; // REVERSE ORDER

    /** Total word count among original words. */
    private int count;

    /** Intervals as list of integers, that represents word count. */
    private List<Integer> intervals;

    /** Concordance block. Simply two strings. */
    private List<Concordance> concordances;

    /** Score list with rating block. */
    private List<Score> scores;

    public KeyWord(int id, String name) {
        this.id = id;
        this.name = name;
        this.rank = 0;
        this.count = 0; // o is default. count will be updated after concordance added.
        this.intervals = new ArrayList<>();
        this.concordances = new ArrayList<>();
        this.scores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Integer> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Integer> intervals) {
        this.intervals = intervals;
    }

    public List<Concordance> getConcordances() {
        return concordances;
    }

    public void setConcordances(List<Concordance> concordances) {
        this.concordances = concordances;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    // Tools

    /**
     * Adding new concordance for the word.
     *
     * @param concordance concordance block.
     */
    public void addConcordance(Concordance concordance) {
        try {
            this.count++; // each concordance block is a new appearance of the word
            this.concordances.add(concordance);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * Adds new element to scores or iterate existed.
     *
     * @param word existed or new element to recalculate or generate score.
     */
    public void addToScores(String word) {
        try {
            for (Score score : scores) {
                if (word.equalsIgnoreCase(score.getWord())) {
                    score.iterate();
                    return;
                }
            }
            // word not found
            scores.add(new Score(word));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
