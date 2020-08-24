package repositories;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.Campaign;
import model.Device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DeviceRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "device";

    private Session session;
    private ArrayList<Device> devices = new ArrayList<Device>();

    public DeviceRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("campaignId uuid, ")
                .append("deviceId uuid, ")
                .append("deviceName varchar,")
                .append("diskSpaceKb int,")
                .append("timeSync varchar,")
                .append("available int,")
                .append("productKey varchar,")
                .append("timestamp timestamp,")
                .append("PRIMARY KEY(campaignId, deviceId)")
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

    public void insertDevice(Device device)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(campaignId, deviceId, deviceName, diskSpaceKb, timeSync, available, productKey, timestamp) ")
                .append("VALUES (").append(device.getCampaignId())
                .append(", ").append(device.getDeviceId())
                .append(", '").append(device.getDeviceName()).append("'")
                .append(", ").append(device.getDiskSpaceKb())
                .append(", '").append(device.getTimeSync()).append("'")
                .append(", ").append(device.getAvailable())
                .append(", '").append(device.getProductKey()).append("'")
                .append(", '").append(device.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }

    public void insertDevices(ArrayList<Device> devices)
    {
        if(devices == null)
        {
            return;
        }

        devices.forEach(this::insertDevice);
    }

    public ArrayList<Device> createTestDevices(int numberOfDevices, ArrayList<Campaign> campaigns)
    {
        if(campaigns.size() == 0)
        {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("de"), new RandomService());

        Faker faker = new Faker(new Locale("de"));

        Device device;
        int randomNumber;

        for (int i = 0; i < numberOfDevices; i++)
        {
            device = new Device();
            device.setDeviceId(UUIDs.timeBased());
            device.setDeviceName(fakeValuesService.bothify("Device###"));
            device.setDiskSpaceKb((int)(Math.random() * 32000000));
            device.setTimeSync(faker.bothify("###########"));
            device.setAvailable((int)(Math.random() * 10));
            device.setProductKey(fakeValuesService.bothify("#??#?###?#???##"));
            device.setTimestamp(sdf.format(new Date()));


            randomNumber = (int) (Math.random() * campaigns.size());

            device.setCampaignId(campaigns.get(randomNumber).getCampaignId());


            devices.add(device);
        }

        return devices;
    }
}
