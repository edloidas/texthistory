package org.edloidas.text.words.content.comparator.category;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Category;

import java.util.Comparator;

public class CategoryAverageComparator implements Comparator<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryAverageComparator.class);

    public int compare(Category o1, Category o2) {
        try {
            Integer rank1 = o1.getRank();
            Integer rank2 = o2.getRank();
            return Integer.compare(rank1, rank2);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
