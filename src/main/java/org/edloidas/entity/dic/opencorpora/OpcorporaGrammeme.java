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
@Table(name = "opcorpora_grammeme")
public class OpcorporaGrammeme implements CommonEntity {

    /** Identifier */
    @Id
    @Column(name = "id")
    private int id;

    /** OpcorporaGrammeme name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** OpcorporaGrammeme alias. */
    @Column(name = "alias", nullable = false)
    private String alias;

    /** OpcorporaGrammeme description. */
    @Column(name = "description", nullable = false)
    private String description;

    /** Link to parent grammeme. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opcorpora_grammeme", nullable = true)
    private OpcorporaGrammeme parent;

    /** Link to parent all childrens. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<OpcorporaGrammeme> childrens = new ArrayList<>();

    /** Link to parent all childrens. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grammeme", orphanRemoval = true)
    private List<OpcorporaLemmeKey> lemmes = new ArrayList<>();

    public OpcorporaGrammeme() {
    }

    public OpcorporaGrammeme(int id) {
        this.id = id;
    }

    public OpcorporaGrammeme(String name) {
        this.name = name;
    }

    public OpcorporaGrammeme(int id, String name,
                             String alias, String description) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
    }

    public OpcorporaGrammeme(int id, String name,
                             String alias, String description,
                             OpcorporaGrammeme parent) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.parent = parent;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OpcorporaGrammeme getParent() {
        return parent;
    }

    public void setParent(OpcorporaGrammeme parent) {
        this.parent = parent;
    }

    public List<OpcorporaGrammeme> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<OpcorporaGrammeme> childrens) {
        this.childrens = childrens;
    }
}
