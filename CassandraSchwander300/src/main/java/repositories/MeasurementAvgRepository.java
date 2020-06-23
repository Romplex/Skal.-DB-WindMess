package repositories;

import com.datastax.driver.core.Session;
import model.Campaign;
import model.MeasurementAvg;

public class MeasurementAvgRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "measurementAvg";

    private Session session;

    public MeasurementAvgRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("measurementAvgId uuid PRIMARY KEY, ")
                .append("windspeedMs double,")
                .append("heightM double,")
                .append("direction double,")
                .append("sigma double,")
                .append("size int,")
                .append("measurementMin int,")
                .append("measurementMax int,")
                .append("timestamp timestamp);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void insertMeasurementAvg(MeasurementAvg measurementAvg)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(measurementAvgId, windspeedMs, heightM, direction, sigma, size, measurementMin, measurementMax, timestamp) ")
                .append("VALUES (").append(measurementAvg.getMeasurementAvgId())
                .append(", ").append(measurementAvg.getWindspeedMs())
                .append(", ").append(measurementAvg.getHeightM())
                .append(", ").append(measurementAvg.getDirection())
                .append(", ").append(measurementAvg.getSigma())
                .append(", ").append(measurementAvg.getSize())
                .append(", ").append(measurementAvg.getMeasurementMin())
                .append(", ").append(measurementAvg.getMeasurementMax())
                .append(", '").append(measurementAvg.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }
}
