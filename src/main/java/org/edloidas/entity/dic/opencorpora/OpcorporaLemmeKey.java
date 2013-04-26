package org.edloidas.entity.dic.opencorpora;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity bean as POJO java class.
 * OpenCorpora dictionary entity. Represents grammeme.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "opcorpora_lemme_key")
public class OpcorporaLemmeKey extends CommonEntity {

    /** Identifier */
    @Id
    @Column(name = "id")
    private int id;

    /** OpcorporaLemmeKey name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** Link to parent grammeme. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opcorpora_grammeme", nullable = false)
    private OpcorporaGrammeme grammeme;

    /** Link to parent lemme. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "opcorpora_lemme_key", nullable = true)
    private OpcorporaLemmeKey lemme;

    /** Link to parent all lemmes. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "lemme", orphanRemoval = false)
    private List<OpcorporaLemmeKey> lemmes = new ArrayList<>();

    /** Link to parent all childrens. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "key", orphanRemoval = true)
    private List<OpcorporaLemmeWord> childrens = new ArrayList<>();

    public OpcorporaLemmeKey() {
    }

    public OpcorporaLemmeKey(int id) {
        this.id = id;
    }

    public OpcorporaLemmeKey(String name) {
        this.name = name;
    }

    public OpcorporaLemmeKey(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public OpcorporaLemmeKey(int id, String name,
                             OpcorporaGrammeme grammeme) {
        this.id = id;
        this.name = name;
        this.grammeme = grammeme;
    }

    public OpcorporaLemmeKey(int id, String name,
                             OpcorporaLemmeKey lemme) {
        this.id = id;
        this.name = name;
        this.lemme = lemme;
    }

    public OpcorporaLemmeKey(int id, String name,
                             OpcorporaGrammeme grammeme,
                             OpcorporaLemmeKey lemme) {
        this.id = id;
        this.name = name;
        this.grammeme = grammeme;
        this.lemme = lemme;
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

    public OpcorporaGrammeme getGrammeme() {
        return grammeme;
    }

    public void setGrammeme(OpcorporaGrammeme grammeme) {
        this.grammeme = grammeme;
    }

    public OpcorporaLemmeKey getLemme() {
        return lemme;
    }

    public void setLemme(OpcorporaLemmeKey lemme) {
        this.lemme = lemme;
    }

    public List<OpcorporaLemmeKey> getLemmes() {
        return lemmes;
    }

    public void setLemmes(List<OpcorporaLemmeKey> lemmes) {
        this.lemmes = lemmes;
    }

    public List<OpcorporaLemmeWord> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<OpcorporaLemmeWord> childrens) {
        this.childrens = childrens;
    }
}
