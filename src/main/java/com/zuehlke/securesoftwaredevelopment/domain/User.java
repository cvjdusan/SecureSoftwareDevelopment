package com.zuehlke.securesoftwaredevelopment.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private final int id;
    private final String username;
    private final String password;
    private final int passwordChangeAttempts;
    private final Boolean isBlocked;
    private List<GrantedAuthority> authorities;

    public User(int id, String username, String password, int passwordChangeAttempts, Boolean isBlocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordChangeAttempts = passwordChangeAttempts;
        this.isBlocked = isBlocked;
    }



    public int getPasswordChangeAttempts() {
        return passwordChangeAttempts;
    }


    public Boolean getBlocked() {
        return isBlocked;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
