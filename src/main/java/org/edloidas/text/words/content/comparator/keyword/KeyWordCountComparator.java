package org.edloidas.text.words.content.comparator.keyword;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.KeyWord;

import java.util.Comparator;

public class KeyWordCountComparator implements Comparator<KeyWord> {

    private static final Logger LOGGER = Logger.getLogger(KeyWordCountComparator.class);

    public int compare(KeyWord o1, KeyWord o2) {
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
