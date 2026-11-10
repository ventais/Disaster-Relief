import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstCaseTest() {
        List<Region> sites = List.of(new Region("Region #1", 100, 400), new Region("Region #2", 150, 600));
        Allocation result = Client.allocateRelief(500, sites);
        assertEquals(1, result.size());
        assertEquals(100, result.totalPeople());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondCaseTest() {
        List<Region> sites = List.of(new Region("Region #1", 150, 400), new Region("Region #2", 100, 450));
        Allocation result = Client.allocateRelief(500, sites);
        assertEquals(1, result.size());
        assertEquals(150, result.totalPeople());
    }

    @Test
    @DisplayName("STUDENT TEST - Case #3")
    public void thirdCaseTest() {
        List<Region> sites = List.of(new Region("Region #1", 150, 450), new Region("Region #2", 150, 400));
        Allocation result = Client.allocateRelief(500, sites);
        assertEquals(1, result.size());
        assertEquals(150, result.totalPeople());
    }

    @Test
    @DisplayName("STUDENT TEST - DIY")
    public void diyTest() {
        List<Region> sites = List.of(new Region("Region #1", 200, 300), new Region("Region #2", 100, 100), new Region("Region #3", 50, 50));
        Allocation result = Client.allocateRelief(400, sites);
        // Adjust expected values based on manual calculations for the input.
        assertEquals(2, result.size());
        assertEquals(250, result.totalPeople());
    }
}