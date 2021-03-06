package model;

import java.util.UUID;

public class Measurement
{
    private UUID campaignId;
    private UUID deviceId;
    private UUID measurementId;
    private double windspeedMs;
    private double heightM;
    private double direction;
    private double cnr;
    private String timestamp;

    public Measurement(UUID campaignId, UUID deviceId, UUID measurementId, double windspeedMs, double heightM, double direction, double cnr, String timestamp)
    {
        this.campaignId = campaignId;
        this.deviceId = deviceId;
        this.measurementId = measurementId;
        this.windspeedMs = windspeedMs;
        this.heightM = heightM;
        this.direction = direction;
        this.cnr = cnr;
        this.timestamp = timestamp;
    }

    public Measurement()
    {

    }

    public UUID getMeasurementId()
    {
        return measurementId;
    }

    public void setMeasurementId(UUID measurementId)
    {
        this.measurementId = measurementId;
    }

    public double getWindspeedMs()
    {
        return windspeedMs;
    }

    public void setWindspeedMs(double windspeedMs)
    {
        this.windspeedMs = windspeedMs;
    }

    public double getHeightM()
    {
        return heightM;
    }

    public void setHeightM(double heightM)
    {
        this.heightM = heightM;
    }

    public double getDirection()
    {
        return direction;
    }

    public void setDirection(double direction)
    {
        this.direction = direction;
    }

    public double getCnr()
    {
        return cnr;
    }

    public void setCnr(double cnr)
    {
        this.cnr = cnr;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public UUID getCampaignId()
    {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId)
    {
        this.campaignId = campaignId;
    }

    public UUID getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId)
    {
        this.deviceId = deviceId;
    }
}
