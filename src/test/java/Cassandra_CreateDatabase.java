import com.datastax.driver.core.Session;
import org.junit.Before;
import org.junit.Test;
import repositories.*;

public class Cassandra_CreateDatabase
{
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
        client.connect("127.0.0.1", 55973);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        userRepository = new UserRepository(session);
        campaignRepository = new CampaignRepository(session);
        deviceRepository = new DeviceRepository(session);
        measurementRepository = new MeasurementRepository(session);
//        measurementAvgRepository = new MeasurementAvgRepository(session);
    }

    @Test
    public void createCassandraDatabase()
    {
        schemaRepository.createKeyspace("schwander3000", "SimpleStrategy", 2);

        userRepository.createTable();
        campaignRepository.createTable();
        deviceRepository.createTable();
        measurementRepository.createTable();
//        measurementAvgRepository.createTable();

        // TODO testen ob alle erstellt sind
    }
}