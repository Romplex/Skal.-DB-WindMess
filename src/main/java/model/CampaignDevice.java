package model;

import java.util.UUID;

public class CampaignDevice
{
    private UUID campaign_device_id;
    private UUID campaign_id;
    private UUID device_id;

    public CampaignDevice(UUID campaign_device_id, UUID campaign_id, UUID device_id)
    {
        this.campaign_device_id = campaign_device_id;
        this.campaign_id = campaign_id;
        this.device_id = device_id;
    }

    public CampaignDevice()
    {

    }

    public UUID getCampaign_device_id()
    {
        return campaign_device_id;
    }

    public void setCampaign_device_id(UUID campaign_device_id)
    {
        this.campaign_device_id = campaign_device_id;
    }

    public UUID getCampaign_id()
    {
        return campaign_id;
    }

    public void setCampaign_id(UUID campaign_id)
    {
        this.campaign_id = campaign_id;
    }

    public UUID getDevice_id()
    {
        return device_id;
    }

    public void setDevice_id(UUID device_id)
    {
        this.device_id = device_id;
    }

}
