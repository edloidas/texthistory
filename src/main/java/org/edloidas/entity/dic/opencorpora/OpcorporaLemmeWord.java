package org.edloidas.entity.dic.opencorpora;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 * OpenCorpora dictionary entity. Represents grammeme.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "opcorpora_lemme_word")
public class OpcorporaLemmeWord implements CommonEntity {

    /** Identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** OpcorporaGrammeme name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** Link to parent grammeme. Mapping many-to-one. */
    /* 'name' should point at the table to join */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "opcorpora_lemme_key", nullable = false)
    private OpcorporaLemmeKey key;

    public OpcorporaLemmeWord() {
    }

    public OpcorporaLemmeWord(int id) {
        this.id = id;
    }

    public OpcorporaLemmeWord(String name) {
        this.name = name;
    }

    public OpcorporaLemmeWord(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public OpcorporaLemmeWord(int id, String name,
                              OpcorporaLemmeKey key) {
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

    public OpcorporaLemmeKey getKey() {
        return key;
    }

    public void setKey(OpcorporaLemmeKey key) {
        this.key = key;
    }
}
