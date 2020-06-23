package model;

import java.util.UUID;

public class MeasurementAvg
{
    public MeasurementAvg(UUID measurementAvgId, double windspeedMs, double heightM, double direction, double sigma, int size, int measurementMin, int measurementMax, String timestamp)
    {
        this.measurementAvgId = measurementAvgId;
        this.windspeedMs = windspeedMs;
        this.heightM = heightM;
        this.direction = direction;
        this.sigma = sigma;
        this.size = size;
        this.measurementMin = measurementMin;
        this.measurementMax = measurementMax;
        this.timestamp = timestamp;
    }

    private UUID measurementAvgId;
    private double windspeedMs;
    private double heightM;
    private double direction;
    private double sigma;
    private int size;
    private int measurementMin;

    public UUID getMeasurementAvgId()
    {
        return measurementAvgId;
    }

    public void setMeasurementAvgId(UUID measurementAvgId)
    {
        this.measurementAvgId = measurementAvgId;
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

    public double getSigma()
    {
        return sigma;
    }

    public void setSigma(double sigma)
    {
        this.sigma = sigma;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getMeasurementMin()
    {
        return measurementMin;
    }

    public void setMeasurementMin(int measurementMin)
    {
        this.measurementMin = measurementMin;
    }

    public int getMeasurementMax()
    {
        return measurementMax;
    }

    public void setMeasurementMax(int measurementMax)
    {
        this.measurementMax = measurementMax;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    private int measurementMax;
    private String timestamp;
}
