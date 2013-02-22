package org.edloidas.entity.dic.psycho;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "psycho_word")
public class PsychoWord implements CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Word name. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Word description. */
    @Lob
    @Column(name = "description")
    private String description;

    public PsychoWord() {
    }

    public PsychoWord(int id) {
        this.id = id;
    }

    public PsychoWord(String name) {
        this.name = name;
    }

    public PsychoWord(int id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
