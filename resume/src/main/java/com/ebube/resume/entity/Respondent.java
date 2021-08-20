package com.ebube.resume.entity;

import javax.persistence.*;

@Entity
@Table (name = "respondents")
public class Respondent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;
    private String body;
    private boolean wantFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWantFile() {
        return wantFile;
    }

    public void setWantFile(boolean wantFile) {
        this.wantFile = wantFile;
    }

    public void trim() {
        subject = subject.trim();
        name = name.trim();
        body = body.trim();
        email = email.trim();
    }
}
