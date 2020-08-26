package repositories;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.User;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserRepository
{
    private static final String KEYSPACE = "windMeasurementCampaign";
    private static final String TABLE_NAME = "user";

    private Session session;
    private ArrayList<User> users = new ArrayList<User>();

    public UserRepository(Session session)
    {
        this.session = session;
    }

    public void cassandraCreateTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("userId uuid PRIMARY KEY, ")
                .append("forename varchar,")
                .append("lastname varchar,")
                .append("email varchar,")
                .append("telephone varchar,")
                .append("mobile varchar,")
                .append("company varchar,")
                .append("password varchar,")
                .append("timestamp timestamp);");

        String query = sb.toString();
        session.execute("USE " + KEYSPACE);
        session.execute(query);
    }

    public void cassandraClearTable()
    {
        session.execute("USE " + KEYSPACE);
        session.execute("TRUNCATE " + KEYSPACE + "." + TABLE_NAME + ";");
    }

    public void cassandraDropTable()
    {
        session.execute("USE " + KEYSPACE);
        session.execute("DROP " + KEYSPACE + "." + TABLE_NAME + ";");
    }

    public void cassandraInsertUser(User user)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME)
                .append("(userId, forename, lastname, email, telephone, mobile, company, password, timestamp) ")
                .append("VALUES (").append(user.getUserId())
                .append(", '").append(user.getForename()).append("'")
                .append(", '").append(user.getLastname()).append("'")
                .append(", '").append(user.getEmail()).append("'")
                .append(", '").append(user.getTelephone()).append("'")
                .append(", '").append(user.getMobile()).append("'")
                .append(", '").append(user.getCompany()).append("'")
                .append(", '").append(user.getPassword()).append("'")
                .append(", '").append(user.getTimestamp()).append("');");

        session.execute("USE " + KEYSPACE);
        String query = sb.toString();
        session.execute(query);
    }

    public void cassandraInsertUsers(ArrayList<User> users)
    {
        users.forEach(this::cassandraInsertUser);
    }

    public ArrayList<User> cassandraCreateTestUsers(int numberOfUsers)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("de"), new RandomService());

        Faker faker = new Faker(new Locale("de"));

        User user;
        for (int i = 0; i < numberOfUsers; i++)
        {
            user = new User();
            user.setUserId(UUIDs.timeBased());
            user.setForename(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setEmail(fakeValuesService.bothify(user.getLastname() + "##@" + faker.company().suffix() + ".com"));
            user.setTelephone(faker.phoneNumber().phoneNumber());
            user.setMobile(faker.phoneNumber().cellPhone());
            user.setCompany(faker.company().name());
            user.setPassword(fakeValuesService.bothify("???###"));
            user.setTimestamp(sdf.format(new Date()));

            users.add(user);
        }

        return users;
    }
}
