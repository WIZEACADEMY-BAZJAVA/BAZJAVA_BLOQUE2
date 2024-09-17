package com.wizeline.maven.LearningJava.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "usersApi")
@XmlType(propOrder = {"userId", "id", "title", "completed"})
@XmlSeeAlso({PublicApiBean.class})
public class PublicApiBean implements Serializable{
    private static final long serialVersionUID = 4991876266904831460L;

    private Long userId;
    private Long id;
    private String title;
    private boolean completed;

    public PublicApiBean() {super();}

    public PublicApiBean(Long userId, Long id, String title, boolean completed) {
        super();
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}