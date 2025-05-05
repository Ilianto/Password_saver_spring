package com.vsu.myapp.dto;

import org.hibernate.validator.constraints.Length;

public class ProfileDTO {
    private Long id;
    @Length(min = 6, message = "login must be not less than 6 symbols")
    private String userName;

    public ProfileDTO(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileDTO(String userName) {
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" + ", name='" + userName + '}';
    }
}
