package com.vsu.myapp.Entity;

public class Record {
    private Long id;
    private Long profileId;
    private String siteAddress;
    private String login;
    private String password;

    public Record(Long profileId, String siteAddress, String login, String password) {
        this.profileId = profileId;
        this.siteAddress = siteAddress;
        this.login = login;
        this.password = password;
    }

    public Record(Long id, Long profileId, String siteAddress, String login, String password) {
        this.id = id;
        this.profileId = profileId;
        this.siteAddress = siteAddress;
        this.login = login;
        this.password = password;
    }

    public Record() {

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public String getSiteAddress() { return siteAddress; }
    public void setSiteAddress(String siteAddress) { this.siteAddress = siteAddress; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}