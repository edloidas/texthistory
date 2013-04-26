package org.edloidas.entity.common;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity bean as POJO java class.
 * Basic entity which is a custom project.
 *
 * @author edloidas@gmail.com
 * @see User
 * @since JPA 2.0
 */
@Entity
@Table(name = "project")
public class Project extends CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Project's name */
    @Column(name = "name", nullable = false)
    private String name;

    /** Project's description */
    @Lob
    @Column(name = "description", nullable = true)
    private String description;

    /** Date of creation DD:MM:YYYY HH:MM:SS */
    @Column(name = "created", nullable = false)
    private Timestamp created;

    /** Date of update DD:MM:YYYY HH:MM:SS */
    @Column(name = "updated", nullable = false)
    private Timestamp updated;

    /** Link to creator. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "project", orphanRemoval = true)
    private List<Source> sources = new ArrayList<>();

    public Project() {
    }

    public Project(int id) {
        this.id = id;
    }

    public Project(User user) {
        this.user = user;
    }

    public Project(String name, String description,
                   User user) {
        this.name = name;
        this.description = description;
        Date now = new Date();
        this.created = new Timestamp(now.getTime());
        this.updated = new Timestamp(now.getTime());
        this.user = user;
    }

    public Project(int id, String name, String description,
                   User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        Date now = new Date();
        this.created = new Timestamp(now.getTime());
        this.updated = new Timestamp(now.getTime());
        this.user = user;
    }

    public Project(int id, String name, String description,
                   Timestamp created, Timestamp updated,
                   User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.user = user;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void update() {
        Date now = new Date();
        this.updated = new Timestamp(now.getTime());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(this.id);
        if (this.name != null) {
            json.append(",\"name\":\"").append(this.name.replace("\"", "\\\"")).append("\"");
        }
        if (this.description != null) {
            json.append(",\"description\":\"").append(this.description).append("\"");
        }
        if (this.description != null) {
            json.append(",\"description\":\"").append(this.description).append("\"");
        }
        if (this.created != null) {
            json.append(",\"created\":\"").append(this.created).append("\"");
        }
        if (this.updated != null) {
            json.append(",\"updated\":\"").append(this.updated).append("\"");
        }
        json.append("}");

        return json.toString();
    }
}
