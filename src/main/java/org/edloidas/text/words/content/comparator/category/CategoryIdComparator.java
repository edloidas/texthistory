package org.edloidas.text.words.content.comparator.category;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Category;

import java.util.Comparator;

public class CategoryIdComparator implements Comparator<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryIdComparator.class);

    public int compare(Category o1, Category o2) {
        try {
            Integer id1 = o1.getId();
            Integer id2 = o2.getId();
            return Integer.compare(id1, id2);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
