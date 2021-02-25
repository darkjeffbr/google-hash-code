import java.util.List;
import java.util.stream.Collectors;

public class Car {
    List<Street> route;

    public String toString() {
        return "[ Car " + route.stream().map(s -> s.name).collect(Collectors.joining(", ")) + " ]";
    }
}
