package org.edloidas.entity.common;

import org.edloidas.entity.CommonEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Entity bean as POJO java class.
 * Basic entity which is a custom source.
 *
 * @author edloidas@gmail.com
 * @see Project
 * @since JPA 2.0
 */
@Entity
@Table(name = "source")
public class Source extends CommonEntity {

    /** Auto incremented identifier */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Source's name */
    @Column(name = "name", nullable = false)
    private String name;

    /** Source's data, as plain text */
    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    /** Date of source been uploaded DD:MM:YYYY HH:MM:SS */
    @Column(name = "uploaded", nullable = false)
    private Timestamp uploaded;

    /** MD5 hash of source file */
    // TODO: Set to unique after creating link many-to-many
    @Column(name = "md5", nullable = false)
    private String md5;

    /** Status of project. If it is included/excluded into project */
    @Column(name = "status", nullable = false)
    private Boolean status;

    /** Link to project. Mapping many-to-one. */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "project", nullable = false)
    private Project project;

    public Source() {
    }

    public Source(int id) {
        this.id = id;
    }

    public Source(String md5) {
        this.md5 = md5;
    }

    public Source(Project project) {
        this.project = project;
    }

    public Source(String name, String text,
                  String md5, Project project) {
        this.name = name;
        this.text = text;
        Date now = new Date();
        this.uploaded = new Timestamp(now.getTime());
        this.md5 = md5;
        this.project = project;
        this.status = true;
    }

    public Source(int id, String name, String text,
                  String md5, Project project) {
        this.id = id;
        this.name = name;
        this.text = text;
        Date now = new Date();
        this.uploaded = new Timestamp(now.getTime());
        this.md5 = md5;
        this.project = project;
        this.status = true;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getUploaded() {
        return uploaded;
    }

    public void setUploaded(Timestamp uploaded) {
        this.uploaded = uploaded;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toJson() {
        this.id = id;
        this.name = name;
        this.text = text;
        Date now = new Date();
        this.uploaded = new Timestamp(now.getTime());
        this.md5 = md5;
        this.project = project;
        this.status = true;

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(this.id);
        if (this.name != null) {
            json.append(",\"name\":\"").append(this.name.replace("\"", "\\\"")).append("\"");
        }
        // Excluded because of great size of response data.
//        if (this.text != null) {
//            json.append(",\"description\":\"").append(this.text.replace("\"", "\\\"")).append("\"");
//        }
        if (this.uploaded != null) {
            json.append(",\"updated\":\"").append(this.uploaded).append("\"");
        }
        if (this.md5 != null) {
            json.append(",\"md5\":\"").append(this.md5).append("\"");
        }
        if (this.status != null) {
            json.append(",\"status\":").append(this.status);
        }
        json.append("}");

        return json.toString();
    }
}
