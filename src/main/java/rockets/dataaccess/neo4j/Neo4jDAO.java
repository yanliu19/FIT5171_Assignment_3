package rockets.dataaccess.neo4j;

import com.google.common.collect.Sets;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import rockets.dataaccess.DAO;
import rockets.model.Entity;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;
import rockets.model.User;

import java.io.File;
import java.util.Collection;

import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

public class Neo4jDAO implements DAO {
    private static final int DEPTH_ENTITY = 1;

    private Session session;
    private SessionFactory sessionFactory;

    public Neo4jDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        if (null == session) {
            session = sessionFactory.openSession();
        }
    }

    public Neo4jDAO(String dbAddress) {
        File file = new File(dbAddress);
        Configuration configuration = new Configuration.Builder()
                //.uri(neoServer.boltURI().toString()) // For Bolt
                //.uri(neoServer.httpURI().toString()) // For HTTP
                .uri(file.toURI().toString()) // For Embedded
                .build();
        EmbeddedDriver driver = new EmbeddedDriver();
        driver.configure(configuration);

        sessionFactory = new SessionFactory(driver, User.class.getPackage().getName());
        session = sessionFactory.openSession();
    }

    @Override
    public <T extends Entity> T load(Class<T> clazz, Long id) {
        return session.load(clazz, id, DEPTH_ENTITY);
    }

    @Override
    public <T extends Entity> T createOrUpdate(T entity) {
        Class clazz = entity.getClass();

        T existingEntity = findExistingEntity(entity, clazz);
        if (null != existingEntity) {
            entity.setId(existingEntity.getId());
        }
        Transaction tx = session.beginTransaction();
        saveOutgoingEntities(entity, clazz);
        session.save(entity);
        tx.commit();
        return entity;
    }

    // Makes sure we save associated entities correctly (only once)
    private <T extends Entity> void saveOutgoingEntities(T entity, Class clazz) {
        if (clazz.equals(Rocket.class)) {
            Rocket rocket = (Rocket) entity;
            for (Launch launch: rocket.getLaunches()) {
                createOrUpdate(launch);
            }
        } else if (clazz.equals(LaunchServiceProvider.class)) {
            LaunchServiceProvider lsp = (LaunchServiceProvider) entity;
            for (Rocket rocket : lsp.getRockets()) {
                createOrUpdate(rocket);
            }
        }
    }

    private <T extends Entity> T findExistingEntity(Entity entity, Class clazz) {
        Entity existingEntity = null;
        Filters filters = new Filters();
        Collection<? extends Entity> collection = Sets.newLinkedHashSet();
        if (clazz.equals(Rocket.class)) {
            Rocket rocket = (Rocket) entity;
            filters.add(new Filter("name", EQUALS, rocket.getName()))
                    .and(new Filter("country", EQUALS, rocket.getCountry()));
            collection = session.loadAll(Rocket.class, filters);
        } else if (clazz.equals(User.class)) {
            User user = (User) entity;
            filters.add(new Filter("email", EQUALS, user.getEmail()));
            collection = session.loadAll(User.class, filters);
        } else if (clazz.equals(Launch.class)) {
            Launch launch = (Launch) entity;
            filters.add(new Filter("launchDate", EQUALS, launch.getLaunchDate()))
                    .and(new Filter("launchVehicle", EQUALS, launch.getLaunchVehicle()))
                    .and(new Filter("launchSite", EQUALS, launch.getLaunchSite()))
                    .and(new Filter("launchOutcome", EQUALS, launch.getLaunchOutcome()));
            collection = session.loadAll(Launch.class, filters);
        } else if (clazz.equals(LaunchServiceProvider.class)) {
            LaunchServiceProvider lsp = (LaunchServiceProvider) entity;
            filters.add(new Filter("name", EQUALS, lsp.getName()))
                    .and(new Filter("yearFounded", EQUALS, lsp.getYearFounded()))
                    .and(new Filter("country", EQUALS, lsp.getCountry()));
            collection = session.loadAll(LaunchServiceProvider.class, filters);
        }
        if (!collection.isEmpty()) {
            existingEntity = collection.iterator().next();
        }
        return (T) existingEntity;
    }

    @Override
    public <T extends Entity> Collection<T> loadAll(Class<T> clazz) {
        return session.loadAll(clazz);
    }

    // TODO: need to be tested!
    public <T extends Entity> void delete(T entity) {
        session.delete(entity);
    }

    @Override
    public User getUserByEmail(String email) {
        Collection<User> users = session.loadAll(User.class, new Filter("email", EQUALS, email));

        if (null == users || users.isEmpty()) {
            return null;
        } else {
            return users.iterator().next();
        }
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
