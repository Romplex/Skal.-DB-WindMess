package repositories;

import com.datastax.driver.core.Session;
import model.Measurement;
import model.MeasurementAvg;

public class MeasurementRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "measurement";

    private Session session;

    public MeasurementRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("measurementId uuid PRIMARY KEY, ")
                .append("windspeedMs double,")
                .append("heightM double,")
                .append("direction double,")
                .append("cnr double,")
                .append("timestamp timestamp);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void insertMeasurement(Measurement measurement)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(measurementId, windspeedMs, heightM, direction, cnr, timestamp) ")
                .append("VALUES (").append(measurement.getMeasurementId())
                .append(", ").append(measurement.getWindspeedMs())
                .append(", ").append(measurement.getHeightM())
                .append(", ").append(measurement.getDirection())
                .append(", ").append(measurement.getCnr())
                .append(", '").append(measurement.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }
}
