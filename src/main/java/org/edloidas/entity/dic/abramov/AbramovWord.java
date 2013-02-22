package org.edloidas.entity.dic.abramov;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 * Basic entity which is a custom project.
 *
 * @author edloidas@gmail.com
 * @see org.edloidas.entity.common.User
 * @since JPA 2.0
 */
@Entity
@Table(name = "abramov_word")
public class AbramovWord {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Project's name */
    @Column(name = "name", nullable = false)
    private String name;

    /** Link to key word (category). Mapping many-to-one. */
    /* 'name' should point at the table to join */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "abramov_key", nullable = false)
    private AbramovKey key;

    public AbramovWord() {
    }

    public AbramovWord(int id) {
        this.id = id;
    }

    public AbramovWord(String name) {
        this.name = name;
    }

    public AbramovWord(int id, String name, AbramovKey key) {
        this.id = id;
        this.name = name;
        this.key = key;
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

    public AbramovKey getKey() {
        return key;
    }

    public void setKey(AbramovKey key) {
        this.key = key;
    }
}