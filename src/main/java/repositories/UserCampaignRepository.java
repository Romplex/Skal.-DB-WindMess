package repositories;

import com.datastax.driver.core.Session;
import model.Measurement;
import model.UserCampaign;

public class UserCampaignRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "user_campaign";


    private Session session;

    public UserCampaignRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("user_campaign_id uuid PRIMARY KEY, ")
                .append("user_id uuid,")
                .append("campaign_id uuid);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void insertUserCampaign(UserCampaign userCampaign)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(user_campaign_id, user_id, campaign_id) ")
                .append("VALUES (").append(userCampaign.getUser_campaign_id())
                .append(", ").append(userCampaign.getUser_id())
                .append(", ").append(userCampaign.getCampaign_id()).append(");");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }
}
