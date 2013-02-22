package org.edloidas.entity.dic.stopwords;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 * Stopwords dictionary entity.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "stopwords_word")
public class StopWord implements CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Stop word name. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public StopWord() {
    }

    public StopWord(int id) {
        this.id = id;
    }

    public StopWord(String name) {
        this.name = name;
    }

    public StopWord(int id, String name) {
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
}
