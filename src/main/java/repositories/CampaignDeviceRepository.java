package repositories;

import com.datastax.driver.core.Session;
import model.Campaign;
import model.CampaignDevice;

public class CampaignDeviceRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "campaign_device";


    private Session session;

    public CampaignDeviceRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("campaign_device_id uuid PRIMARY KEY, ")
                .append("campaign_id uuid,")
                .append("device_id uuid);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void insertCampaignDevice(CampaignDevice campaignDevice)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(campaign_device_id, campaign_id, device_id) ")
                .append("VALUES (").append(campaignDevice.getCampaign_device_id())
                .append(", ").append(campaignDevice.getCampaign_id())
                .append(", ").append(campaignDevice.getDevice_id()).append(");");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }
}
