package org.edloidas.text;

/**
 * Provides structured storage of analysis data for session.
 */
/* Result JSON should look like:
{
    category:
        [{id: 1, name: "Name", rank: 1, count: 0, average: 0, freq: 0}],
    key:
        [{id: 2, name: "two", rank: 2, count: 1, link: 2,
                interval: [1, 0, 1, 1, 0, 2, 0, 2, 2, 0, 0, 0, 1, 0, 0, 0, 0, 1, 2, 0],
                concord: [{before: "before ", after: " after"}],
                score: [{word: "ScoreWord", count: 2, score: 1.78}]}
        ],
    text: 'text with \\\" and \\' replacements',
    count: 77,
    avLength: 5.52
};
 */
public class SessionData {

    public String toJson() {
        return "";
    }
}
