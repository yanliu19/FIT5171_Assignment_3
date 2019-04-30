package rockets.model;

import org.neo4j.ogm.annotation.CompositeIndex;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
@CompositeIndex(properties = {"launchDate", "launchVehicle", "launchSite", "orbit"}, unique = true)
public class Launch extends Entity {

    public enum LaunchOutcome {
        FAILED, SUCCESSFUL
    }

    @Property(name = "launchDate")
    private LocalDate launchDate;

    @Relationship(type = "PROVIDES", direction = INCOMING)
    private Rocket launchVehicle;

    private Set<String> payload;

    @Property(name = "launchSite")
    private String launchSite;

    @Property(name = "orbit")
    private String orbit;

    @Property(name = "function")
    private String function;

    @Property(name = "launchOutcome")
    private LaunchOutcome launchOutcome;

    @Property(name = "price")
    private int price;

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public void setLaunchVehicle(Rocket launchVehicle) {
        this.launchVehicle = launchVehicle;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    public Rocket getLaunchVehicle() {
        return launchVehicle;
    }

    public Set<String> getPayload() {
        return payload;
    }

    public String getLaunchSite() {
        return launchSite;
    }

    public String getOrbit() {
        return orbit;
    }

    public String getFunction() {
        return function;
    }

    public LaunchOutcome getLaunchOutcome() {
        return launchOutcome;
    }

    public void setPayload(Set<String> payload) {
        this.payload = payload;
    }

    public void setLaunchSite(String launchSite) {
        this.launchSite = launchSite;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setLaunchOutcome(LaunchOutcome launchOutcome) {
        this.launchOutcome = launchOutcome;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Launch launch = (Launch) o;
        return Objects.equals(launchDate, launch.launchDate) &&
                Objects.equals(launchVehicle, launch.launchVehicle) &&
                Objects.equals(orbit, launch.orbit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(launchDate, launchVehicle, orbit);
    }
}
