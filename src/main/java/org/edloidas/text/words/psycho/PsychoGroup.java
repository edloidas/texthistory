package org.edloidas.text.words.psycho;

import org.edloidas.text.words.content.Concordance;

import java.util.ArrayList;
import java.util.List;

public class PsychoGroup {

    private int id;

    private String name;

    private String description;


    private List<Concordance> concordance;

    public PsychoGroup(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Concordance> getConcordance() {
        return concordance;
    }

    public void setConcordance(List<Concordance> concordance) {
        this.concordance = concordance;
    }
}
