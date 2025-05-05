package com.vsu.myapp.Entity;

import org.hibernate.validator.constraints.Length;

public class Profile {

    private Long id;
    @Length(min = 6, message = "Login must be at least 6 symbols")
    private String login;

    private String password;

    private String salt;

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Profile(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Profile(Long id, String login, String password, String salt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
