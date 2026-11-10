import java.util.*;

/**
 * The Allocation class represents an unmodifiable relief solution.
 * It provides methods to retrieve the total cost and total helped population
 * of the solution. The ordering of the regions in the solution determines
 * the population that can be helped.
 */
public class Allocation {

    private List<Region> regions;

    /**
     * Creates a new Allocation object representing the given regions.
     * @param regions the regions in the solution
     */
    Allocation(List<Region> regions) {
        this.regions = new ArrayList<>(regions);
    }

    /**
     * Creates a new Allocation object with no regions in it.
     */
    public Allocation() {
        this(new ArrayList<>());
    }

    /**
     * Returns a copy of this allocation's regions.
     */
    public List<Region> getRegions() {
        return new ArrayList<>(regions);
    }

    /**
     * Returns a new Allocation with the contents of this allocation
     * and the passed in region added to it.
     * @param r Region to be added to the end of the new Allocation.
     * @return a new Allocation with r added to it.
     */
    public Allocation withRegion(Region r) {
        if (regions.contains(r)) {
            throw new IllegalArgumentException("Allocation already contains region " + r);
        }
        List<Region> newRegions = new ArrayList<>(regions);
        newRegions.add(r);
        return new Allocation(newRegions);
    }

    /**
     * Returns a new Allocation with the contents of this allocation
     * and the passed in region removed from it.
     * @param r Region to be removed from the new Allocation.
     * @return a new Allocation with r removed from it.
     */
    public Allocation withoutRegion(Region r) {
        if (!regions.contains(r)) {
            throw new IllegalArgumentException("Allocation doesn't contain region " + r);
        }
        List<Region> newRegions = new ArrayList<>(regions);
        newRegions.remove(r);
        return new Allocation(newRegions);
    }

    /**
     * Returns the number of regions in this Allocation.
     */
    public int size() {
        return regions.size();
    }

    /**
     * Calculates and returns the total population that can be helped
     * by this Allocation.
     * @return the total population that can be helped by this Allocation.
     */
    public int totalPeople() {
        int total = 0;
        for (Region r : regions) {
            total += r.getPopulation();
        }
        return total;
    }

    /**
     * Calculates and returns the combined cost of this Allocation.
     * @return the combined cost of this Allocation.
     */
    public double totalCost() {
        double total = 0;
        for (int i = 0; i < regions.size(); i++) {
            total += regions.get(i).getCost(i);
        }
        return total;
    }

    /**
     * Returns a String representation of an Allocation object in the format:
     * "[Region, ..., Region]" where each Region is in its string representation.
     * @return the String representation of an Allocation object
     */
    public String toString() {
        return regions.toString();
    }

    /**
     * Compares the specified object with this allocation for equality. Returns true if the
     * specified object is also an Allocation and the two Allocations have the same
     * collection of regions.
     * @param other object to be compared for equality with this allocation
     * @return true if the specified object is equal to this allocation
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Allocation)) {
            return false;
        }
        Allocation otherAlloc = (Allocation)other;
        return this.regions.equals(otherAlloc.getRegions());
    }

    /**
     * Returns the hash code value for this Allocation
     * @return the hash code value for this Allocation
     */
    @Override
    public int hashCode() {
        return regions.hashCode();
    }
}