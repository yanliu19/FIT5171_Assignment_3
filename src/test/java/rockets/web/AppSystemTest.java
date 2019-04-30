package rockets.web;

import com.google.common.collect.Lists;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rockets.dataaccess.DAO;
import rockets.dataaccess.neo4j.Neo4jDAO;
import rockets.model.Launch;
import rockets.model.LaunchServiceProvider;
import rockets.model.Rocket;
import spark.resource.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.logging.log4j.core.util.Closer.closeSilently;
import static org.mockito.Mockito.spy;

public class AppSystemTest {
    private static DAO dao;
    private List<Rocket> rockets;
    private List<LaunchServiceProvider> lsps;
    private List<Launch> launches;
    private static String dbAddress;

    @BeforeAll
    public static void classSetUp() throws Exception {
        ClassPathResource resource = new ClassPathResource("app.properties");
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = resource.getInputStream();
            properties.load(stream);
            int port = Integer.parseInt(properties.getProperty("spark.port"));
            JWebUnit.setBaseUrl("http://localhost:" + port);

            dbAddress = properties.getProperty("neo4j.dir");
            dao = new Neo4jDAO(dbAddress);
            App.setDao(dao);
        } finally {
            closeSilently(stream);
        }

        App.main(null);

        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    @AfterAll
    public static void tearDown() throws IOException {
        dao.close();
        App.stop();
        File testDir = new File(dbAddress);
        FileUtils.deleteDirectory(testDir);
    }

    @BeforeEach
    public void setUp() {
        rockets = Lists.newArrayList();

        lsps = createLSPs();

        // index of lsp of each rocket
        int[] lspIndex = new int[]{0, 0, 0, 1, 1};

        // 5 rockets
        for (int i = 0; i < 5; i++) {
            Rocket rocket = new Rocket("rocket_" + i, "USA", lsps.get(lspIndex[i]));
            lsps.get(lspIndex[i]).getRockets().add(rocket);
//            dao.createOrUpdate(rocket);
            rockets.add(rocket);
        }

        Calendar calendar = new GregorianCalendar(2017, 01, 01);

        // month of each launch
        int[] months = new int[]{1, 6, 4, 3, 4, 11, 6, 5, 12, 5};

        // index of rocket of each launch
        int[] rocketIndex = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};

        // 10 launches
        launches = IntStream.range(0, 10).mapToObj(i -> {
            Launch l = new Launch();
            l.setLaunchDate(LocalDate.of(2017, months[i], 1));
            l.setLaunchVehicle(rockets.get(rocketIndex[i]));
            l.setLaunchSite("VAFB");
            l.setOrbit("LEO");
            this.rockets.get(rocketIndex[i]).getLaunches().add(l);
            spy(l);
            return l;
        }).collect(Collectors.toList());
    }

    private ArrayList<LaunchServiceProvider> createLSPs() {
        return Lists.newArrayList(
                dao.createOrUpdate(new LaunchServiceProvider("ULA", 1990, "USA")),
                dao.createOrUpdate(new LaunchServiceProvider("SpaceX", 2002, "USA")),
                dao.createOrUpdate(new LaunchServiceProvider("ESA", 1975, "Europe "))
        );
    }

    @Test
    public void shouldNotRecreateUser() {
        JWebUnit.beginAt("/");
        JWebUnit.gotoPage("register");
        JWebUnit.assertTextPresent("User Registration");

        JWebUnit.setTextField("email", "abc@example.com");
        JWebUnit.setTextField("password", "1234");
        JWebUnit.setTextField("firstName", "John");
        JWebUnit.setTextField("lastName", "Doe");

        JWebUnit.submit();

        JWebUnit.assertTextPresent("Welcome back: John Doe!");

        JWebUnit.clickLinkWithExactText("Logout");

        JWebUnit.gotoPage("register");
        JWebUnit.assertTextPresent("User Registration");

        JWebUnit.setTextField("email", "abc@example.com");
        JWebUnit.setTextField("password", "1234");
        JWebUnit.setTextField("firstName", "Jane");
        JWebUnit.setTextField("lastName", "Who");

        JWebUnit.submit();

        JWebUnit.assertTextNotPresent("Welcome back: Jane Who!");
    }

    @Test
    public void shouldHaveWelcomeInBasePage() {
        String path = "/";
        JWebUnit.beginAt(path);
        JWebUnit.assertTextPresent("Welcome");
    }
}
