package org.edloidas.text.words.content;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Category {

    /** Common logger. */
    private static final Logger LOGGER = Logger.getLogger(KeyWord.class);

    /** Word identifier */
    private int id;

    /** Equals to first word name */
    private String name;

    /** Rank is equals to index */
    private int rank;

    /** Average weighted index. */
    // AVERAGE == SUMM(word.rank * word.count) / SUMM(word.count)
    private double average;

    /** Total word count */
    // COUNT == SUMM(word.count)
    private int count;

    /** Frequency of all the words */
    // FREQUENCY == COUNT / TOTAL * 100%
    private double frequency;

    /** Intervals as list of integers, that represents category count. */
    private List<Integer> intervals;

    /** List of key words */
    private List<KeyWord> keyWords;

    // Constructors
    public Category(KeyWord word) {
        this.id = 0;
        this.name = word.getName();
        this.rank = 0;
        this.average = 0.0d;
        this.count = 1;
        this.frequency = 0.0d;
        this.keyWords = new ArrayList<>();
        this.keyWords.add(word);
    }

    // Getters and Setters
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

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    // Static tools

    /**
     * Merges two categories.
     *
     * @param a first category to be merged.
     * @param b second category to be merged.
     *
     * @return result of merge - category as or null (if merge failed).
     */
    public static Category merge(Category a, Category b) {
        Category category = null;
        try {
            category = new Category(a.getKeyWords().get(0));
            category.setKeyWords(a.getKeyWords());
            category.getKeyWords().addAll(b.getKeyWords());
            category.frequency += b.getFrequency();
            category.refresh();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return category;
    }

    /**
     * Merges all categories in list. Use it for large amount of data, due to
     * refresh of the result category will be in the end.
     *
     * @param categories list of all categories to be merged.
     *
     * @return new category.
     */
    public static Category mergeAll(List<Category> categories) {
        Category category = null;
        try {
            for (Category c : categories) {
                if (category != null) {
                    category.getKeyWords().addAll(c.getKeyWords());
                    category.frequency += c.getFrequency();
                } else {
                    category = new Category(c.getKeyWords().get(0));
                    category.setKeyWords(c.getKeyWords());
                }
            }
            category.refresh();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return category;
    }

    public static List<Category> split(Category category, int wordsCount) {
        List<Category> categories = null;
        Category cat;
        try {
            categories = new ArrayList<>();
            for (KeyWord word : category.getKeyWords()) {
                cat = new Category(word);
                cat.refresh(wordsCount);
                categories.add(cat);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return categories;
    }

    // Tools
    public boolean mergeWith(Category category) {
        try {
            this.keyWords.addAll(category.getKeyWords());
            this.frequency += category.getFrequency();
            this.refresh();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    public void refresh() {
        int dividend = 0;
        int divider = 0;
        for (KeyWord word : this.keyWords) {
            dividend += word.getCount() * word.getRank();
            divider = word.getCount();
        }
        this.average = (double) ((int) ((double) dividend / (double) divider * 10000)) / 100.0d;
    }

    public void refresh(int wordsCount) {
        int rangScore = 0;
        int totalCount = 0;
        for (KeyWord word : this.keyWords) {
            rangScore += word.getCount() * word.getRank();
            totalCount = word.getCount();
        }
        this.average = (double) ((int) ((double) rangScore / (double) totalCount * 10000)) / 100.0d;

        this.frequency = (double) ((int) ((double) totalCount / (double) wordsCount * 10000)) / 100.0d;
    }
}
