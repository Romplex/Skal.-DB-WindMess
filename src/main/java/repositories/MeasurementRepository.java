package repositories;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import model.Device;
import model.Measurement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MeasurementRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "measurement";

    private Session session;

    public MeasurementRepository(Session session)
    {
        this.session = session;
    }

    private ArrayList<Measurement> measurements = new ArrayList<Measurement>();

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("campaignId uuid, ")
                .append("deviceId uuid, ")
                .append("measurementId uuid, ")
                .append("windspeedMs double,")
                .append("heightM double,")
                .append("direction double,")
                .append("cnr double,")
                .append("timestamp timestamp,")
                .append("PRIMARY KEY(campaignId, deviceId, measurementId)")
                .append(");");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void clearTable()
    {
        session.execute("USE " + KEYSPACE);
        session.execute("TRUNCATE " + KEYSPACE + "." + TABLE_NAME + ";");
    }

    public void dropTable()
    {
        session.execute("USE " + KEYSPACE);
        session.execute("DROP " + KEYSPACE + "." + TABLE_NAME + ";");
    }

    public void insertMeasurement(Measurement measurement)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(campaignId, deviceId, measurementId, windspeedMs, heightM, direction, cnr, timestamp) ")
                .append("VALUES (").append(measurement.getCampaignId())
                .append(", ").append(measurement.getDeviceId())
                .append(", ").append(measurement.getMeasurementId())
                .append(", ").append(measurement.getWindspeedMs())
                .append(", ").append(measurement.getHeightM())
                .append(", ").append(measurement.getDirection())
                .append(", ").append(measurement.getCnr())
                .append(", '").append(measurement.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }

    public void insertMeasurements(ArrayList<Measurement> measurements)
    {
        if(measurements == null)
        {
            return;
        }

        measurements.forEach(this::insertMeasurement);
    }

    public ArrayList<Measurement> createTestMeasurements(int numberOfMeasurements, ArrayList<Device> devices)
    {
        if(devices.size() == 0)
        {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        int randomNumber;

        for (int i = 0; i < numberOfMeasurements; i++)
        {

            randomNumber = (int) (Math.random() * devices.size());

            measurements.add(new Measurement(
                    devices.get(randomNumber).getCampaignId(),
                    devices.get(randomNumber).getDeviceId(),
                    UUIDs.timeBased(),
                    Math.random() * 20,
                    (Math.random() * 220) + 80,
                    Math.random() * 100,
                    (Math.random() * 11) + 1,
                    sdf.format(new Date())));
        }

        return measurements;
    }
}
