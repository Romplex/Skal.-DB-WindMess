import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.*;
import org.junit.Before;
import org.junit.Test;
import repositories.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Cassandra_GenerateData
{
    private final static int USER_GENERATE_NUMBER = 2;
    private final static int CAMPAIGN_GENERATE_NUMBER = 5;
    private final static int DEVICE_GENERATE_NUMBER = 25;
    private final static int MEASUREMENT_GENERATE_NUMBER = 10000;

    private KeyspaceRepository schemaRepository;
    private Session session;

    private UserRepository userRepository;
    private CampaignRepository campaignRepository;
    private DeviceRepository deviceRepository;
    private MeasurementRepository measurementRepository;
//    private MeasurementAvgRepository measurementAvgRepository;



    @Before
    public void connect()
    {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 52495);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        initializeRepos();
    }

    private void initializeRepos()
    {
        // Todo: dynamische Instanziierung

        userRepository = new UserRepository(session);
        campaignRepository = new CampaignRepository(session);
        deviceRepository = new DeviceRepository(session);
        measurementRepository = new MeasurementRepository(session);
//        measurementAvgRepository = new MeasurementAvgRepository(session);
    }

    private void clearRepos()
    {
        // Todo: dynamische Instanziierung

        userRepository.clearTable();
        campaignRepository.clearTable();
        deviceRepository.clearTable();
        measurementRepository.clearTable();
//        measurementAvgRepository.clearTable();
    }

    private void dropRepos()
    {
        userRepository.dropTable();
        campaignRepository.dropTable();
        deviceRepository.dropTable();
        measurementRepository.dropTable();
    }

    @Test
    public void createAndInsertTestData() throws IOException
    {
        ArrayList<User> users = userRepository.createTestUsers(USER_GENERATE_NUMBER);
        userRepository.insertUsers(users);

        ArrayList<Campaign> campaigns = campaignRepository.createTestCampaigns(CAMPAIGN_GENERATE_NUMBER, users);
        campaignRepository.insertCampaigns(campaigns);

        ArrayList<Device> devices = deviceRepository.createTestDevices(DEVICE_GENERATE_NUMBER, campaigns);
        deviceRepository.insertDevices(devices);

        ArrayList<Measurement> measurements = measurementRepository.createTestMeasurements(MEASUREMENT_GENERATE_NUMBER, devices);
        measurementRepository.insertMeasurements(measurements);
    }

    @Test
    public void tempAddCampaignsForOneUser()
    {
        int numberOfGeneratedCampaigns = 1000;
        ArrayList<Campaign> campaigns = new ArrayList<>();

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


        for (int i = 0; i < numberOfGeneratedCampaigns; i++)
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

            UUID uuid = UUID.fromString("7cbfac50-d195-11ea-90a7-114642a5d57e");
            campaign.setUserId(uuid);

            campaigns.add(campaign);

        }

        campaignRepository.insertCampaigns(campaigns);
    }

    @Test
    public void clearTables()
    {
        clearRepos();
    }

    // drop tables, um sie mit ihrer Struktur zu entfernen, falscher Aufruf..
//    @Test
//    public void dropTables()
//    {
//        dropRepos();
//    }


}
