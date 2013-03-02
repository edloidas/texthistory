package org.edloidas.web.service;

import org.apache.log4j.Logger;
import org.edloidas.entity.dic.abramov.AbramovKey;
import org.edloidas.entity.dic.abramov.AbramovWord;
import org.edloidas.entity.dic.discource.DiscourceCategory;
import org.edloidas.entity.dic.discource.DiscourceWord;
import org.edloidas.entity.dic.frequency.FrequencyWord;
import org.edloidas.entity.dic.opencorpora.OpcorporaGrammeme;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeKey;
import org.edloidas.entity.dic.opencorpora.OpcorporaLemmeWord;
import org.edloidas.entity.dic.psycho.PsychoWord;
import org.edloidas.entity.dic.stopwords.StopWord;
import org.edloidas.text.SymbolTextElement;
import org.edloidas.text.TextElement;
import org.edloidas.text.WordTextElement;
import org.edloidas.text.regex.LexicalRegex;
import org.edloidas.text.regex.Stemmer;
import org.edloidas.text.words.content.Category;
import org.edloidas.text.words.content.Concordance;
import org.edloidas.text.words.content.KeyWord;
import org.edloidas.text.words.content.Score;
import org.edloidas.text.words.content.comparator.keyword.KeyWordReverseCountComparator;
import org.edloidas.text.words.content.comparator.score.ScoreReverseCountComparator;
import org.edloidas.text.words.discource.DiscourceGroup;
import org.edloidas.text.words.psycho.PsychoGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavaBean
 * Text mining and parsing service.
 * Has static functions for frequency, z-score, etc. calculations.
 *
 * @author edloidas@gmail.com
 */
@Service("textService")
@Transactional
public class TextService {

    /**
     * Logger for common output. Uses root logger output style.
     * See log4j.properties file for more details.
     */
    private static final Logger LOGGER = Logger.getLogger(TextService.class);

    //------------------------------------------------------------------------
    // Services for database interactions
    //------------------------------------------------------------------------

    @Resource(name = "stopWordService")
    private EntityService<StopWord> stopWordService;

    @Resource(name = "frequencyWordService")
    private EntityService<FrequencyWord> frequencyWordService;

    @Resource(name = "abramovKeyService")
    private EntityService<AbramovKey> abramovKeyService;

    @Resource(name = "abramovWordService")
    private EntityService<AbramovWord> abramovWordService;

    @Resource(name = "opcorporaGrammemeService")
    private EntityService<OpcorporaGrammeme> opcorporaGrammemeService;

    @Resource(name = "opcorporaLemmeKeyService")
    private EntityService<OpcorporaLemmeKey> opcorporaLemmeKeyService;

    @Resource(name = "opcorporaLemmeWordService")
    private EntityService<OpcorporaLemmeWord> opcorporaLemmeWordService;

    @Resource(name = "discourceWordService")
    private EntityService<DiscourceWord> discourceWordService;

    @Resource(name = "discourceCategoryService")
    private EntityService<DiscourceCategory> discourceCategoryService;

    @Resource(name = "psychoWordService")
    private EntityService<PsychoWord> psychoWordService;

    //------------------------------------------------------------------------
    // TEXT QUALIFIERS
    //------------------------------------------------------------------------

    /** Total number of words in text. */
    private int wordsCount = 0;

    /** Total number of key words in text. */
    private int keyCount = 0;

    /** Average length of words in text. */
    private int wordsLength = 0;

    //------------------------------------------------------------------------
    // PROCESSED VARIABLES
    //------------------------------------------------------------------------

    /** Text representation as elements. */
    private List<TextElement> elements = null;

    /** Key words / categories. */
    private List<Category> categories = null;

    /** Discource blocks groupss. */
    private List<DiscourceGroup> discourceGroups = null;

    /** Discource blocks groupss. */
    private List<PsychoGroup> psychoGroups = null;

    //------------------------------------------------------------------------
    // BACKGROUND USAGE METHODS
    //------------------------------------------------------------------------
    private boolean doInitialization() {
        try {
            this.wordsCount = 0;
            this.keyCount = 0;
            this.wordsLength = 0;

            this.elements = new ArrayList<>();
            this.categories = new ArrayList<>();
            this.discourceGroups = null;
            this.psychoGroups = null;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    /**
     * Updates saved data by parsing new source text.
     *
     * @param source text to be parsed
     */
    // NOTE: This version is for RUSSIAN language ONLY!
    public void doUpdate(String source) {
        try {

            // Initialization
            this.doInitialization();

            // VARIABLES =======================================
            // Regular expression for certain language
            Pattern pattern = Pattern.compile(LexicalRegex.RUS);
            Matcher matcher = pattern.matcher(source);

            List<KeyWord> keyWords = new ArrayList<>(); // tmp quick-access storage of categorie's key words
            KeyWord keyWord;             // element of keyWords for shorter access to single element
            Category category;           // element of categories for shorter access to single element

            TextElement elem;            // Text element as word, symbol, etc.

            int intervalsCount = 20;     // Count of intervals for distribution [size = 100% / count]

            String group;                // Matcher group
            String stemmed;              // Word/group after it was stemmed

            // DB elements holders
            List<StopWord> sws;          // Dictionary of stop words

            List<OpcorporaLemmeKey> lks; // OpenCorpora Lemme (multiple)
            OpcorporaLemmeKey lk;        // OpenCorpora Lemme (single) - for shorter access to single element

            List<FrequencyWord> fws;     // Dictionary of word frequencies

            List<AbramovWord> aws;       // Dictionary of synonyms (Abramov dic.) - synonyms for key
            AbramovKey ak;               // Dictionary of synonyms (Abramov dic.) - key value
            //==================================================


            // STEP 1 : GROUPING TEXT INTO ELEMENTS ============
            while (matcher.find()) { // for each found elelemnt in text
                if (matcher.group(2) == null) { // matches word
                    group = matcher.group(1).toLowerCase();

                    /* Single '-' symbol is also marked as word in by parser, due to words like
                     * 'кто-то' or 'когда-нибудь' */
                    if (group.equals("-")) { // Mark as symbol and continue
                        elem = new SymbolTextElement(group);
                        this.elements.add(elem);
                        continue;
                    }

                    elem = new WordTextElement(group);
                    this.wordsCount++;
                    this.wordsLength += group.length();

                    // STEP 1.1 : CHECK FOR STOP WORD ==================
                    sws = stopWordService.getAll(new StopWord(group));
                    if (sws.size() != 0) { // same word was found in dictionary.
                        this.elements.add(elem);
                        continue;
                    }

                    // STEP 1.2 : FIND MAIN FORM OF WORD ===============
                    lks = opcorporaLemmeKeyService.getAll(new OpcorporaLemmeKey(group));
                    if (lks.size() == 0) { // no exact matching (need for main form searching)
                        stemmed = Stemmer.stem(group);
                        lks = opcorporaLemmeKeyService.getAll(new OpcorporaLemmeKey(stemmed));
                        if (lks.size() == 0) { // not found with stemmed form either
                            this.elements.add(elem);
                            continue;
                        }

                        // FIND THE SHORTEST (IDENT => LENGTH ARE EQUALS)
                        lk = lks.get(0); // get first for iteration
                        int ident = 100; // Identity value (reversed, equals min difference)
                        int dif;         // Difference value
                        for (OpcorporaLemmeKey key : lks) { // get more matched
                            dif = key.getName().length() - stemmed.length();
                            if (dif < ident) {
                                ident = dif;
                                lk = key;
                            }
                        } // one excess iteration
                    } else { // exact matching (no need for main for searching)
                        lk = lks.get(0); // get first as basis to start iteration
                        for (OpcorporaLemmeKey key : lks) { // get more matched
                            if (key.getName().equalsIgnoreCase(group)) {
                                lk = key;
                                break;
                            }
                        }
                    }

                    // STEP 1.2 : FIND PARENT OF MAIN FORM =============
                    while (lk != null && lk.getLemme() != null && lk.getLemme().getId() != 0) {
                        lk = opcorporaLemmeKeyService.getById(new OpcorporaLemmeKey(lk.getLemme().getId()));
                    }
                    elem.setElement(lk.getName());

                    // STEP 1.3 : FREQUENCY CHECK ======================
                    // Frequency as word appearance count in 1M word corps
                    fws = frequencyWordService.getAll(new FrequencyWord(elem.getOriginal()));
                    if (fws.size() != 0) { // find by original word form
                        elem.setFrequency(fws.get(0).getFreq());
                    } else {               // try find by main parent word form
                        fws = frequencyWordService.getAll(new FrequencyWord(elem.getElement()));
                        if (fws.size() != 0) {
                            elem.setFrequency(fws.get(0).getFreq());
                        }
                        // else - frequency is defaulf (0)
                    }

                    // Mark words with very high frequency or/and low
                    // Words with default frequency will not be processed in future.
                    if (elem.getFrequency() > 1000.0d
                            || elem.getFrequency() < 1.0d) {
                        //elem.setFrequency(0.0d);
                        this.elements.add(elem);
                        continue;
                    }
                    elem.setType(TextElement.TYPE_SPECIAL);

                    // STEP 1.4 : SINONYMS CHECK========================
                    // TODO: Re-write this part. Dictionary is not well implemented.
                    // Возможность проявления омонимии и полисимии.
                    /*aws = abramovWordService.getAll(new AbramovWord(elem.getElement()));
                    if (aws.size() != 0) {
                        ak = abramovKeyService.getById(aws.get(0).getKey());
                        if (ak != null) {
                            elem.setElement(ak.getName());
                        }
                    }*/
                    // Add only words if they are main. Then find synonims to them.
                } else { // matches symbol
                    group = matcher.group(2);
                    elem = new SymbolTextElement(group);
                }
                this.elements.add(elem);
            }

            // STEP 2 : SETTING METADATA VALID VALUES =========
            this.wordsLength = this.wordsLength / this.wordsCount;


            // STEP 3 : MANAGE CATEGORIES / KEY WORDS ==========
            int i, j, k;
            int wordIndex = 0;
            StringBuilder before, after;
            for (i = 0; i < this.elements.size(); i++) {
                elem = this.elements.get(i);

                if (elem.getType() > TextElement.TYPE_SYMBOL) {
                    wordIndex++;
                }

                if (elem.getType() == TextElement.TYPE_SPECIAL) { // Element is a word with frequency of the key word
                    for (j = 0; j < keyWords.size(); j++) {
                        if (keyWords.get(j).getName().equals(elem.getElement())) {
                            break;
                        }
                    }

                    if (j == keyWords.size()) { // no such word in element list
                        keyWord = new KeyWord(i, elem.getElement());
                        for (k = 0; k < intervalsCount; k++) {
                            keyWord.getIntervals().add(0);
                        }
                        keyWords.add(keyWord);
                        //this.keyCount++;
                    } else {
                        keyWord = keyWords.get(j);
                    }


                    // Concordance =====================================
                    before = new StringBuilder();
                    after = new StringBuilder();
                    for (k = i - 1; k >= 0; k--) {
                        if (this.elements.get(k).getType() < TextElement.TYPE_WORD
                                || (i - k > 5)) {
                            break;
                        }
                        before.insert(0, " ").insert(0, this.elements.get(k).getOriginal());
                    }
                    for (k = i + 1; k < this.elements.size(); k++) {
                        if (this.elements.get(k).getType() < TextElement.TYPE_WORD
                                || (k - i > 5)) {
                            break;
                        }
                        after.append(" ").append(this.elements.get(k).getOriginal());
                    }
                    keyWord.addConcordance(new Concordance(before.toString(), after.toString(), elem.getOriginal()));

                    // Score ===========================================
                    // add left word to score in parent form
                    k = i - 1;
                    if (k >= 0 && this.elements.get(k).getType() == TextElement.TYPE_SPECIAL) {
                        keyWord.addToScores(this.elements.get(k).getElement());
                    }
                    // add right word to score in parent form
                    k = i + 1;
                    if (k < this.elements.size() && this.elements.get(k).getType() == TextElement.TYPE_SPECIAL) {
                        // WARNING: Scrores still must be calculated.
                        keyWord.addToScores(this.elements.get(k).getElement());
                    }

                    // Intervals =======================================
                    //k = (int) (((double) i / (double) this.wordsCount) * intervalsCount); // correct form, incorrect k
                    k = (int) (((double) wordIndex / (double) this.elements.size()) * intervalsCount); // wrong form
                    try {
                        keyWord.getIntervals().set(k, keyWord.getIntervals().get(k) + 1);
                    } catch (IndexOutOfBoundsException indexEx) {
                        LOGGER.warn(indexEx.getMessage(), indexEx);
                    }
                }

            }

            // STEP 4 : MANAGE INDEXES AND RANKS ===============
            int count; // common count
            Collections.sort(keyWords, new KeyWordReverseCountComparator());
            for (i = 0; i < keyWords.size(); i++) {
                keyWord = keyWords.get(i);
                keyWord.setId(i + 1);                 // the bigger count, the lower index.
                keyWord.setRank(keyWords.size() - i); // the bigger count, the higher rank.
                keyWord.setCount(keyWord.getConcordances().size());
                this.keyCount += keyWord.getConcordances().size();

                // Calculating z-score
                for (Score score : keyWord.getScores()) {
                    // TODO: Enhance alg. May be too slow.
                    count = 0;
                    for (TextElement element : this.elements) {
                        if (score.getWord().equals(element.getElement())) {
                            count++;
                        }
                    }
                    score.calculate(count, keyWord.getCount(), this.wordsCount);
                }
                Collections.sort(keyWord.getScores(), new ScoreReverseCountComparator());
            }

            for (i = 0; i < keyWords.size(); i++) {
                category = new Category(keyWords.get(i));
                category.setId(i);
                category.setRank(i + 1);
                category.refresh(this.keyCount); // Set frequency and average
                this.categories.add(category);
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    //------------------------------------------------------------------------
    // GETTERS AND SETTERS
    //------------------------------------------------------------------------

    // Fictitious methods ==============================
    public float getKeyPercentage() {
        float percentage = 0.0f;
        try {
            // Transform into number with 2 digits after dot: '12.34' %
            percentage = (float)((int)(((float)this.keyCount / (float)this.wordsCount) * 10000)) / 100.0f;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return percentage;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public int getWordsLength() {
        return wordsLength;
    }

    public void setWordsLength(int wordsLength) {
        this.wordsLength = wordsLength;
    }

    public List<TextElement> getElements() {
        return elements;
    }

    public void setElements(List<TextElement> elements) {
        this.elements = elements;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    // ORIGINAL

    public List<DiscourceGroup> getDiscourceGroups() {
        if (this.discourceGroups == null) {
            this.discourceGroups = new ArrayList<>();
            List<DiscourceWord> words;
            DiscourceCategory category;
            DiscourceGroup group;
            int i, j;
            for (TextElement elem : this.elements) {
                if (elem.getType() > TextElement.TYPE_SYMBOL) {
                    words = discourceWordService.getAll(new DiscourceWord(elem.getElement()));
                    if (words.size() > 0) {
                        for (i = 0; i < this.discourceGroups.size(); i++) {
                            if (this.discourceGroups.get(i).getName().equals(words.get(0).getName())) {
                                break;
                            }
                        }
                        if (i == this.discourceGroups.size()) { // add new
                            category = discourceCategoryService.getById(words.get(0).getCategory());
                            group = new DiscourceGroup(this.discourceGroups.size(),
                                    words.get(0).getName(), category.getExample(), category.getEffects());
                            // Concordance
                            ArrayList<Concordance> cons = new ArrayList<>();
                            StringBuilder before, after;
                            String word;
                            String disc = words.get(0).getName();
                            for (i = 0; i < elements.size(); i++) {
                                word = elements.get(i).getElement();
                                if (disc.equalsIgnoreCase(word)) {
                                    word = elements.get(i).getOriginal();
                                    before = new StringBuilder();
                                    after = new StringBuilder();

                                    j = i - 1;
                                    while ((j > 0) && (elements.get(j).getType() > TextElement.TYPE_SYMBOL)) {
                                        before.insert(0, elements.get(j).getOriginal()).insert(0, " ");
                                        j--;
                                    }
                                    j = i + 1;
                                    while ((j < elements.size()) && (elements.get(j).getType() > TextElement.TYPE_SYMBOL)) {
                                        after.append(" ").append(elements.get(j).getOriginal());
                                        j++;
                                    }
                                    //cons.add(new Concordance(before.toString(), word, after.toString()));
                                }
                            }
                            group.setConcordance(cons);
                            this.discourceGroups.add(group);
                        }
                    }
                }
            }
        }
        return discourceGroups;
    }

    public void setDiscourceGroups(List<DiscourceGroup> discourceGroups) {
        this.discourceGroups = discourceGroups;
    }

    public List<PsychoGroup> getPsychoGroups() {
        if (this.psychoGroups == null) {
            this.psychoGroups = new ArrayList<>();
            List<PsychoWord> words;
            PsychoGroup group;
            int i, j;
            for (TextElement elem : this.elements) {
                if (elem.getType() > TextElement.TYPE_SYMBOL) {
                    words = psychoWordService.getAll(new PsychoWord(elem.getElement()));
                    if (words.size() > 0) {
                        for (i = 0; i < this.psychoGroups.size(); i++) {
                            if (this.psychoGroups.get(i).getName().equals(words.get(0).getName())) {
                                break;
                            }
                        }
                        if (i == this.psychoGroups.size()) { // add new
                            group = new PsychoGroup(this.psychoGroups.size(),
                                    words.get(0).getName(), words.get(0).getDescription());
                            // Concordance
                            ArrayList<Concordance> cons = new ArrayList<>();
                            StringBuilder before, after;
                            String word;
                            String disc = words.get(0).getName();
                            for (i = 0; i < elements.size(); i++) {
                                word = elements.get(i).getElement();
                                if (disc.equalsIgnoreCase(word)) {
                                    word = elements.get(i).getOriginal();
                                    before = new StringBuilder();
                                    after = new StringBuilder();

                                    j = i - 1;
                                    while ((j > 0) && (elements.get(j).getType() > TextElement.TYPE_SYMBOL)) {
                                        before.insert(0, elements.get(j).getOriginal()).insert(0, " ");
                                        j--;
                                    }
                                    j = i + 1;
                                    while ((j < elements.size()) && (elements.get(j).getType() > TextElement.TYPE_SYMBOL)) {
                                        after.append(" ").append(elements.get(j).getOriginal());
                                        j++;
                                    }
                                    //cons.add(new Concordance(before.toString(), word, after.toString()));
                                }
                            }
                            group.setConcordance(cons);
                            this.psychoGroups.add(group);
                        }
                    }
                }
            }
        }

        return psychoGroups;
    }

    public void setPsychoGroups(List<PsychoGroup> psychoGroups) {
        this.psychoGroups = psychoGroups;
    }
}
