package com.vsu.myapp.request;

import org.hibernate.validator.constraints.Length;

public class CreateProfileRequest {
    @Length(min = 6, message = "login must be not less than 6 symbols")
    private String userName;
    private String password;

    public CreateProfileRequest() {
    }

    public CreateProfileRequest(String userName) {
        this.userName = userName;
    }

    public CreateProfileRequest( String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                ", name='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
