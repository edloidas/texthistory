package org.edloidas.web.json;

import org.edloidas.entity.common.Project;

import java.util.List;

/**
 * Session message json response. Contains code, hash and sometimes data.
 */
public class ProjectListMessage implements Message {

    /** Message code, as additional information. */
    private short code;


    /** Data representation. Can be {@code null}. */
    private List<Project> projects;

    public ProjectListMessage() {
        this.code = Message.CODE_SUCCESS;
        this.projects = null;
    }

    public ProjectListMessage(List<Project> projects) {
        this.code = Message.CODE_SUCCESS;
        this.projects = projects;
    }

    public ProjectListMessage(short code, List<Project> projects) {
        this.code = code;
        this.projects = projects;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        msg.append("{\"code\":").append(code).append(",\"projects\":[");
        if (projects != null) {
            for (int i = 0; i < projects.size(); i++) {
                if (i != 0) {
                    msg.append(",");
                }
                msg.append(projects.get(i).toJson());
            }
        }
        msg.append("]}");

        return msg.toString();
    }

}
