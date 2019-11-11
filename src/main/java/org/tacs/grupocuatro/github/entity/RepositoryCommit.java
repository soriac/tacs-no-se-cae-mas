package org.tacs.grupocuatro.github.entity;

public class RepositoryCommit {
    private String sha;
    private String message;
    private String url;

    private String date;

    private String authorName;
    private String authorEmail;

    public RepositoryCommit(String sha, String message, String url, String date, String authorName, String authorEmail) {
        this.sha = sha;
        this.message = message;
        this.url = url;
        this.date = date;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}

