package model;

import java.util.UUID;

public class UserCampaign
{
    private UUID user_campaign_id;
    private UUID user_id;
    private UUID campaign_id;

    public UserCampaign(UUID user_campaign_id, UUID user_id, UUID campaign_id)
    {
        this.user_campaign_id = user_campaign_id;
        this.user_id = user_id;
        this.campaign_id = campaign_id;
    }

    public UUID getUser_campaign_id()
    {
        return user_campaign_id;
    }

    public void setUser_campaign_id(UUID user_campaign_id)
    {
        this.user_campaign_id = user_campaign_id;
    }

    public UUID getUser_id()
    {
        return user_id;
    }

    public void setUser_id(UUID user_id)
    {
        this.user_id = user_id;
    }

    public UUID getCampaign_id()
    {
        return campaign_id;
    }

    public void setCampaign_id(UUID campaign_id)
    {
        this.campaign_id = campaign_id;
    }
}
