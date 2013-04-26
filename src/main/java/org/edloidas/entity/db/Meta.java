package org.edloidas.entity.db;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Entity bean as POJO java class.
 * Entity represents current database meta information, such as version and creation date.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */
// TODO: Implement Meta for db, or delete, if it's not needed anymore.
@Entity
@Table(name = "meta")
public class Meta extends CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Unique Database version in format [major].[minor] . */
    @Column(name = "version", nullable = false, unique = true)
    private String version;

    /** Date of a database version been released DD:MM:YYYY HH:MM:SS */
    @Column(name = "released", nullable = false)
    private Timestamp released;

    /** Default constructor. */
    Meta() {
    }

    /**
     * Constructor for common tasks (search by ID, etc.).
     *
     * @param id is {@code int} ID of Meta.
     */
    public Meta(int id) {
        this.id = id;
    }

    /**
     * Constructor for common tasks (search by ID, etc.).
     *
     * @param version is {@code String}, that represents version of database release.
     */
    public Meta(String version) {
        this.version = version;
    }

    /**
     * Constructor for common tasks (search by ID, etc.).
     *
     * @param id      is {@code int} ID of Meta.
     * @param version is {@code String}, that represents version of database release.
     */
    public Meta(int id, String version) {
        this.id = id;
        this.version = version;
    }

    /**
     * Constructor for common tasks (search by ID, etc.).
     *
     * @param id       is {@code int} ID of Meta.
     * @param version  is {@code String}, that represents version of database release.
     * @param released is {@code Timestamp}, that represents release time of database version.
     */
    public Meta(int id, String version, Timestamp released) {
        this.id = id;
        this.version = version;
        this.released = released;
    }

    /**
     * Gets value of Meta ID.
     *
     * @return {@code int} as ID of Meta.
     */
    public int getId() {
        return id;
    }

    /**
     * Set value of Meta ID.
     *
     * @param id is {@code int}   Meta ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets version of database in format [major].[minor] .
     *
     * @return {@code String} as database version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets value of database version.
     *
     * @param version is {@code String} database version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets time of database been released or updated.
     *
     * @return {@code Timestamp} as database release time in format DD:MM:YYYY HH:MM:SS
     */
    public Timestamp getReleased() {
        return released;
    }

    /**
     * Sets value of database release time.
     *
     * @param released is {@code Timestamp} database release time.
     */
    public void setReleased(Timestamp released) {
        this.released = released;
    }
}
