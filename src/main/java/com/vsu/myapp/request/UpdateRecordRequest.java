package com.vsu.myapp.request;

public class UpdateRecordRequest {
    private Long id;
    private String siteAddress;
    private String login;
    private String password;

    public UpdateRecordRequest(String siteAddress, String login, String password) {
        this.siteAddress = siteAddress;
        this.login = login;
        this.password = password;
    }
    public UpdateRecordRequest() {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSiteAddress() { return siteAddress; }
    public void setSiteAddress(String siteAddress) { this.siteAddress = siteAddress; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
