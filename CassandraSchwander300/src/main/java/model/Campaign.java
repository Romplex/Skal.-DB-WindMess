package model;

import java.util.UUID;

public class Campaign
{
    private UUID campaignId;
    private String startTime;
    private String endTime;
    private String name;
    private String location;
    private String heights;

    public Campaign(UUID campaignId, String startTime, String endTime, String name, String location, String heights)
    {
        this.campaignId = campaignId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.location = location;
        this.heights = heights;
    }

    public Campaign()
    {

    }

    public UUID getCampaignId()
    {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId)
    {
        this.campaignId = campaignId;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getHeights()
    {
        return heights;
    }

    public void setHeights(String heights)
    {
        this.heights = heights;
    }
}
