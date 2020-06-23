package repositories;

import com.datastax.driver.core.Session;
import model.Campaign;
import model.Device;

public class DeviceRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "device";

    private Session session;

    public DeviceRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("deviceId uuid PRIMARY KEY, ")
                .append("deviceName varchar,")
                .append("diskSpaceKb int,")
                .append("timeSync varchar,")
                .append("available int,")
                .append("productKey varchar,")
                .append("timestamp timestamp);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void insertDevice(Device device)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(deviceId, deviceName, diskSpaceKb, timeSync, available, produktKey, timestamp) ")
                .append("VALUES (").append(device.getDeviceId())
                .append(", '").append(device.getDeviceName()).append("'")
                .append(", ").append(device.getDiskSpaceKb())
                .append(", '").append(device.getTimeSync()).append("'")
                .append(", ").append(device.getAvailable())
                .append(", '").append(device.getProductKey())
                .append(", '").append(device.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }
}
