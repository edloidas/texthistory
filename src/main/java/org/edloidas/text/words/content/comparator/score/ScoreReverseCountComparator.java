package org.edloidas.text.words.content.comparator.score;

import org.apache.log4j.Logger;
import org.edloidas.text.words.content.Score;

import java.util.Comparator;

public class ScoreReverseCountComparator implements Comparator<Score> {

    private static final Logger LOGGER = Logger.getLogger(ScoreReverseCountComparator.class);

    public int compare(Score o1, Score o2) {
        try {
            Integer count1 = o1.getCount();
            Integer count2 = o2.getCount();
            return Integer.compare(count2, count1);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }
    }
}
