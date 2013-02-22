package org.edloidas.text.words.discource;

import org.edloidas.text.words.content.Concordance;

import java.util.ArrayList;
import java.util.List;

public class DiscourceGroup {

    private int id;

    private String name;

    private String example;

    private String effects;

    private List<Concordance> concordance;

    public DiscourceGroup(int id, String name, String example, String effects) {
        this.id = id;
        this.name = name;
        this.example = example;
        this.effects = effects;
        concordance = new ArrayList<>();
    }

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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public List<Concordance> getConcordance() {
        return concordance;
    }

    public void setConcordance(List<Concordance> concordance) {
        this.concordance = concordance;
    }
}
