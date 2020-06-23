package model;

import java.util.UUID;

public class Device
{
    private UUID deviceId;
    private String deviceName;
    private int diskSpaceKb;
    private String timeSync;
    private int available;
    private String productKey;
    private String timestamp;

    public Device(UUID deviceId, String deviceName, int diskSpaceKb, String timeSync, int available, String productKey, String timestamp)
    {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.diskSpaceKb = diskSpaceKb;
        this.timeSync = timeSync;
        this.available = available;
        this.productKey = productKey;
        this.timestamp = timestamp;
    }

    public Device()
    {

    }

    public UUID getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public int getDiskSpaceKb()
    {
        return diskSpaceKb;
    }

    public void setDiskSpaceKb(int diskSpaceKb)
    {
        this.diskSpaceKb = diskSpaceKb;
    }

    public String getTimeSync()
    {
        return timeSync;
    }

    public void setTimeSync(String timeSync)
    {
        this.timeSync = timeSync;
    }

    public int getAvailable()
    {
        return available;
    }

    public void setAvailable(int available)
    {
        this.available = available;
    }

    public String getProductKey()
    {
        return productKey;
    }

    public void setProductKey(String productKey)
    {
        this.productKey = productKey;
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
