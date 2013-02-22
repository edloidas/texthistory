package org.edloidas.text.words.content.comparator.category;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Category;

import java.util.Comparator;

public class CategoryFrequencyComparator implements Comparator<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryFrequencyComparator.class);

    public int compare(Category o1, Category o2) {
        try {
            Integer freq1 = o1.getRank();
            Integer freq2 = o2.getRank();
            return Integer.compare(freq1, freq2);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
