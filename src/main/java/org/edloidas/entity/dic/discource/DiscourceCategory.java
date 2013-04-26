package org.edloidas.entity.dic.discource;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity bean as POJO java class.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "discource_category")
public class DiscourceCategory extends CommonEntity {

    /** Identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** DiscourceCategory name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** DiscourceCategory example. */
    @Lob
    @Column(name = "example", nullable = false)
    private String example;

    /** DiscourceCategory effects. */
    @Lob
    @Column(name = "effects", nullable = false)
    private String effects;

    /** Link to parent grammeme. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discource_category", nullable = true)
    private DiscourceCategory parent;

    /** Link to parent all childrens. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<DiscourceCategory> childrens = new ArrayList<>();

    /** Link to parent all childrens. Mapping one-to-many. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    private List<DiscourceWord> categorys = new ArrayList<>();

    public DiscourceCategory() {
    }

    public DiscourceCategory(int id) {
        this.id = id;
    }

    public DiscourceCategory(String name) {
        this.name = name;
    }

    public DiscourceCategory(int id, String name,
                             String example, String effects) {
        this.id = id;
        this.name = name;
        this.example = example;
        this.effects = effects;
    }

    public DiscourceCategory(int id, String name,
                             String example, String effects,
                             DiscourceCategory parent) {
        this.id = id;
        this.name = name;
        this.example = example;
        this.effects = effects;
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

    public DiscourceCategory getParent() {
        return parent;
    }

    public void setParent(DiscourceCategory parent) {
        this.parent = parent;
    }

    public List<DiscourceCategory> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<DiscourceCategory> childrens) {
        this.childrens = childrens;
    }
}


