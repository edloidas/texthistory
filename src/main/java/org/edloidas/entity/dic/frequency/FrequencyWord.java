package org.edloidas.entity.dic.frequency;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;

/**
 * Entity bean as POJO java class.
 * Frequency dictionary entity. Represents word with .
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */

@Entity
@Table(name = "frequency_word")
public class FrequencyWord extends CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Stop word name. */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Stop word name. */
    @Column(name = "freq", nullable = false)
    private double freq;

    public FrequencyWord() {
    }

    public FrequencyWord(int id) {
        this.id = id;
    }

    public FrequencyWord(String name) {
        this.name = name;
    }

    FrequencyWord(float freq) {
        this.freq = freq;
    }

    public FrequencyWord(int id, String name, float freq) {
        this.id = id;
        this.name = name;
        this.freq = freq;
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

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }
}
