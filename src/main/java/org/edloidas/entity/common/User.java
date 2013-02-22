package org.edloidas.entity.common;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity bean as POJO java class.
 * Basic entity, that represents user's logging data.
 * <p/>
 * Data will be transfered via HTTPS and checked as MD5 hash sum for double security.
 *
 * @author edloidas@gmail.com
 * @since JPA 2.0
 */
@Entity
@Table(name = "user")
public class User implements CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** User's login. Basically uses email as login. */
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    /** User's password. Stored as MD5 hash. */
    @Column(name = "password", nullable = false)
    private String password;

    /** User's name. */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String login, String password, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}