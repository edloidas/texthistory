package org.edloidas.web.json;

import org.edloidas.entity.common.Project;
import org.edloidas.entity.common.Source;

import java.util.List;

/**
 * Session message json response. Contains code, hash and sometimes data.
 */
public class ProjectMessage implements Message {

    /** Message code, as additional information. */
    private short code;


    /** Data representation. Can be {@code null}. */
    private Project project;

    /** Data representation. Can be {@code null}. */
    private List<Source> sources;

    public ProjectMessage() {
        this.code = Message.CODE_SUCCESS;
        this.project = null;
        this.sources = null;
    }

    public ProjectMessage(Project project, List<Source> sources) {
        this.code = Message.CODE_SUCCESS;
        this.project = project;
        this.sources = sources;
    }

    public ProjectMessage(short code, Project project, List<Source> sources) {
        this.code = code;
        this.project = project;
        this.sources = sources;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        //msg.append("{\"code\":").append(code).append(",\"project\":[");
        msg.append("{\"code\":").append(code).append(",\"project\":");
        if (project != null) {
            msg.append(project.toJson());
        } else {
            msg.append("{}");
        }
        msg.append(",\"source\":[");
        if (sources != null) {
            for (int i = 0; i < sources.size(); i++) {
                if (i != 0) {
                    msg.append(",");
                }
                msg.append(sources.get(i).toJson());
            }
        }
        msg.append("]}");

        return msg.toString();
    }

}
