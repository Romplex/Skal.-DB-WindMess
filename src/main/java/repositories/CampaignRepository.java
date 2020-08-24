package repositories;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.Campaign;
import model.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class CampaignRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "campaign";
    private static final double PROBABILITY_OF_USER_TO_CAMPAIGN_ASSIGNMENT = 0.05;

    private Session session;
    private ArrayList<Campaign> campaigns = new ArrayList<Campaign>();

    public CampaignRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("userId uuid,")
                .append("campaignId uuid, ")
                .append("startTime timestamp,")
                .append("endTime timestamp,")
                .append("name varchar,")
                .append("location varchar,")
                .append("heights varchar,")
                .append("PRIMARY KEY(userId, campaignId)")
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

    public void insertCampaign(Campaign campaign)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(userId, campaignId, startTime, endTime, name, location, heights) ")
                .append("VALUES (").append(campaign.getUserId())
                .append(", ").append(campaign.getCampaignId())
                .append(", '").append(campaign.getStartTime()).append("'")
                .append(", '").append(campaign.getEndTime()).append("'")
                .append(", '").append(campaign.getName()).append("'")
                .append(", '").append(campaign.getLocation()).append("'")
                .append(", '").append(campaign.getHeights()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }

    public void insertCampaigns(ArrayList<Campaign> campaigns)
    {
        campaigns.forEach(this::insertCampaign);
    }

    public ArrayList<Campaign> createTestCampaigns(int numberOfCampaigns, ArrayList<User> users)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("de"), new RandomService());

        Faker faker = new Faker(new Locale("de"));

        Campaign campaign;
        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        Date randomDateInPast;
        Date firstDate;
        Date secondDate;

        long yearInMilliseconds = (long) 365 * 24 * 60 * 60 * 1000;
        int randomNumber;
        boolean flag = false;

        for (int i = 0; i < numberOfCampaigns; i++)
        {
            campaign = new Campaign();
            campaign.setCampaignId(UUIDs.timeBased());

            randomDateInPast = new Date(yesterday.getTime() - yearInMilliseconds);

            firstDate = faker.date().between(randomDateInPast, yesterday);
            secondDate = faker.date().between(randomDateInPast, yesterday);

            if (firstDate.after(secondDate))
            {
                campaign.setStartTime(sdf.format(secondDate));
                campaign.setEndTime(sdf.format(firstDate));
            } else {
                campaign.setStartTime(sdf.format(firstDate));
                campaign.setEndTime(sdf.format(secondDate));
            }

            campaign.setName(fakeValuesService.bothify("Kampagne##"));
            campaign.setLocation(faker.address().latitude() + ", " + faker.address().longitude());
            campaign.setHeights(String.valueOf(faker.number().numberBetween(3, 30)));

            flag = false;
            for (User user : users)
            {
                randomNumber = (int) (Math.random() * 20);
                if (randomNumber == 1)
                {
                    campaigns.add(new Campaign(
                            user.getUserId(),
                            campaign.getCampaignId(),
                            campaign.getStartTime(),
                            campaign.getEndTime(),
                            campaign.getName(),
                            campaign.getLocation(),
                            campaign.getHeights()));
                    flag = true;
                }
            }

            // falls der campaign kein user zugewiesen wurde, mache dies jetzt
            if(!flag && users.size() != 0)
            {
                randomNumber = (int) (Math.random() * users.size());

                campaigns.add(new Campaign(
                        users.get(randomNumber).getUserId(),
                        campaign.getCampaignId(),
                        campaign.getStartTime(),
                        campaign.getEndTime(),
                        campaign.getName(),
                        campaign.getLocation(),
                        campaign.getHeights()));
            }
        }

        return campaigns;
    }
}
