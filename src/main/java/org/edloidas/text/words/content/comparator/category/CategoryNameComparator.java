package org.edloidas.text.words.content.comparator.category;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Category;

import java.util.Comparator;

public class CategoryNameComparator implements Comparator<Category> {

    private static final Logger LOGGER = Logger.getLogger(CategoryNameComparator.class);

    public int compare(Category o1, Category o2) {
        try {
            String name1 = o1.getName();
            String name2 = o2.getName();
            return name1.compareTo(name2);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
