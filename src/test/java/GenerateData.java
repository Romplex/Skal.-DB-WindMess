import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.*;
import org.junit.Before;
import org.junit.Test;
import repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GenerateData
{
    private final static int USER_GENERATE_NUMBER = 100;
    private final static int CAMPAIGN_GENERATE_NUMBER = 100;
    private final static int DEVICE_GENERATE_NUMBER = 100;

    private KeyspaceRepository schemaRepository;
    private Session session;

    private UserRepository userRepository;
    private CampaignRepository campaignRepository;
    private DeviceRepository deviceRepository;
    private UserCampaignRepository userCampaignRepository;
    private CampaignDeviceRepository campaignDeviceRepository;
    private MeasurementRepository measurementRepository;
    private MeasurementAvgRepository measurementAvgRepository;



    @Before
    public void connect()
    {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 9042);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        initializeRepos();
    }

    private void initializeRepos()
    {
        userRepository = new UserRepository(session);
        campaignRepository = new CampaignRepository(session);
        deviceRepository = new DeviceRepository(session);
        userCampaignRepository = new UserCampaignRepository(session);
        campaignDeviceRepository = new CampaignDeviceRepository(session);
        measurementRepository = new MeasurementRepository(session);
        measurementAvgRepository = new MeasurementAvgRepository(session);
    }

    private void clearRepos()
    {
        userRepository.clearTable();
        campaignRepository.clearTable();
        deviceRepository.clearTable();
        userCampaignRepository.clearTable();
        campaignDeviceRepository.clearTable();
        measurementRepository.clearTable();
        measurementAvgRepository.clearTable();
    }

    @Test
    public void createAndInsertTestData()
    {
        ArrayList<User> users = userRepository.createTestUsers(USER_GENERATE_NUMBER);
        userRepository.insertUsers(users);

        ArrayList<Campaign> campaigns = campaignRepository.createTestCampaigns(CAMPAIGN_GENERATE_NUMBER);
        campaignRepository.insertCampaigns(campaigns);

        ArrayList<UserCampaign> userCampaigns = userCampaignRepository.createTestUserCampaigns(users, campaigns);
        userCampaignRepository.insertUserCampaigns(userCampaigns);

        ArrayList<Device> devices = deviceRepository.createTestDevices(DEVICE_GENERATE_NUMBER);
        deviceRepository.insertDevices(devices);

        ArrayList<CampaignDevice> campaignDevices = campaignDeviceRepository.createTestCampaignDevices(campaigns, devices);
        campaignDeviceRepository.insertCampaignDevices(campaignDevices);
    }

    @Test
    public void clearTables()
    {
        clearRepos();
    }
}
