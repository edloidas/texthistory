package org.edloidas.text.words.content.comparator.category;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Category;

import java.util.Comparator;

public class CategoryCountComparator implements Comparator<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryCountComparator.class);

    public int compare(Category o1, Category o2) {
        try {
            Integer count1 = o1.getCount();
            Integer count2 = o2.getCount();
            return Integer.compare(count1, count2);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
