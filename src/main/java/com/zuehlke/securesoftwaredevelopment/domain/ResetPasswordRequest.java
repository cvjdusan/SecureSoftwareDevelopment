package com.zuehlke.securesoftwaredevelopment.domain;

public class ResetPasswordRequest {
    private String link;
    private String email;

    // Getters i setters
    // ...

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
