package model;

import java.util.UUID;

public class User
{
    private UUID userId;
    private String forename;
    private String lastname;
    private String email;
    private String telephone;
    private String mobile;
    private String company;
    private String password;
    private String timestamp;

    public User(UUID UserId, String forename, String lastname, String email,
                String telephone, String mobile, String company,
                String password, String timestamp)
    {
        this.userId = UserId;
        this.forename = forename;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.mobile = mobile;
        this.company = company;
        this.password = password;
        this.timestamp = timestamp;
    }

    public User()
    {

    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public String getForename()
    {
        return forename;
    }

    public void setForename(String forename)
    {
        this.forename = forename;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

}
