package repositories;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.Campaign;
import model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CampaignRepository
{
    private static final String KEYSPACE = "schwander3000";
    private static final String TABLE_NAME = "campaign";

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
                .append("campaignId uuid PRIMARY KEY, ")
                .append("startTime timestamp,")
                .append("endTime timestamp,")
                .append("name varchar,")
                .append("location varchar,")
                .append("heights varchar);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void clearTable()
    {
        session.execute("USE " + KEYSPACE);
        session.execute("TRUNCATE " + KEYSPACE + "." + TABLE_NAME + ";");
    }

    public void insertCampaign(Campaign campaign)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(campaignId, startTime, endTime, name, location, heights) ")
                .append("VALUES (").append(campaign.getCampaignId())
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

    public ArrayList<Campaign> createTestCampaigns(int numberOfCampaigns)
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

        int randomHours;

        for (int i = 0; i < numberOfCampaigns; i++)
        {
            campaign = new Campaign();
            campaign.setCampaignId(UUIDs.timeBased());

            long yearInMilliseconds = (long) 365 * 24 * 60 * 60 * 1000;

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

            campaigns.add(campaign);
        }

        return campaigns;
    }
}
