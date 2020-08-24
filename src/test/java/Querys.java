import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


import org.junit.Before;
import org.junit.Test;
import repositories.*;

import java.util.List;
import java.util.stream.Collectors;


import static org.junit.Assert.assertEquals;

public class Querys
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

    private void initializeRepos()
    {
        userRepository = new UserRepository(session);
        campaignRepository = new CampaignRepository(session);
        deviceRepository = new DeviceRepository(session);
//        userCampaignRepository = new UserCampaignRepository(session);
//        campaignDeviceRepository = new CampaignDeviceRepository(session);
        measurementRepository = new MeasurementRepository(session);
//        measurementAvgRepository = new MeasurementAvgRepository(session);
    }

    @Before
    public void connect()
    {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 52893);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);

        initializeRepos();
    }

    @Test
    public void getAllUsers()
    {
        long startTime = System.nanoTime();

        ResultSet result = session.execute(
                "SELECT * FROM schwander3000.user;");

        result.forEach(System.out::println);

        long stopTime = System.nanoTime();

        System.out.println("Zeit für alle Userabfragen (72646) = " + (stopTime - startTime));
    }
}
