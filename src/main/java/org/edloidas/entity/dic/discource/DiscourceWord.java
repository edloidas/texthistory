package org.edloidas.entity.dic.discource;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "discource_word")
public class DiscourceWord implements CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Word. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Link to key word (category). Mapping many-to-one. */
    /* 'name' should point at the table to join */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discource_category", nullable = false)
    private DiscourceCategory category;

    public DiscourceWord() {
    }

    public DiscourceWord(int id) {
        this.id = id;
    }

    public DiscourceWord(String name) {
        this.name = name;
    }

    public DiscourceWord(int id, String name) {
        this.id = id;
        this.name = name;
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

    public DiscourceCategory getCategory() {
        return category;
    }

    public void setCategory(DiscourceCategory category) {
        this.category = category;
    }
}
