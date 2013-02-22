package org.edloidas.entity.dic.abramov;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity bean as POJO java class.
 * Abramov dictionary entity. Represents.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "abramov_key")
public class AbramovKey implements CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** User's login. Basically uses email as login. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "key", orphanRemoval = true)
    private List<AbramovWord> words = new ArrayList<>();

    public AbramovKey() {
    }

    public AbramovKey(int id) {
        this.id = id;
    }

    public AbramovKey(String name) {
        this.name = name;
    }

    public AbramovKey(int id, String name) {
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

    public List<AbramovWord> getWords() {
        return words;
    }

    public void setWords(List<AbramovWord> words) {
        this.words = words;
    }
}
