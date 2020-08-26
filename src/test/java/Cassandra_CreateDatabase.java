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
    private MeasurementRepository measurementRepository;

    @Before
    public void connect()
    {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 51543);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        userRepository = new UserRepository(session);
        campaignRepository = new CampaignRepository(session);
        deviceRepository = new DeviceRepository(session);
        measurementRepository = new MeasurementRepository(session);
    }

    @Test
    public void createCassandraDatabase()
    {
        schemaRepository.createKeyspace("windMeasurementCampaign", "SimpleStrategy", 2);

        userRepository.cassandraCreateTable();
        campaignRepository.cassandraCreateTable();
        deviceRepository.cassandraCreateTable();
        measurementRepository.cassandraCreateTable();

        // TODO testen ob alle erstellt sind
    }
}
