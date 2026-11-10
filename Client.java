import java.util.*;
// Joseph So
// 11/13/2024
// CSE 123
// TA: Kavya Nair
public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        // List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createSimpleScenario();
        System.out.println(scenario);
        
        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }
    
    // Behavior: 
    //  - Returns the allocation that saves the most people and costs the least.
    //  NOTE: most efficient/best allocation means it saves the most people
    //  and costs the least amount of money to do so.
    // Parameters:
    //  - double budget: represents the budget of the disaster relief.
    //  - List<Region> sites: represents the list of regions to be potentially relieved.
    //  Assuming the sites list is not null.
    // Returns:
    //  - Returns the allocation that saves the most people and costs the least.
    public static Allocation allocateRelief(double budget, List<Region> sites) {
        List<List<Region>> permutations = generatePermutations(sites);
        Allocation bestAllocation = new Allocation();

        for (List<Region> perm : permutations) {
            Allocation allocation = allocateReliefRecursive(budget, perm, 0, new Allocation());
            if (allocation.totalPeople() > bestAllocation.totalPeople() ||
                (allocation.totalPeople() == bestAllocation.totalPeople() &&
                    allocation.totalCost() < bestAllocation.totalCost())) {
                bestAllocation = allocation;
            }
        }

        return bestAllocation;
    }

    // Behavior: 
    //  - Searches through every possible Allocation and combination of Regions and
    //  returns the most efficient allocation.
    // Parameters:
    //  - double budget: represents the budget of the disaster relief.
    //  - List<Region> sites: represents the list of regions to be potentially relieved.
    //  - int index: represents the index within the sites the method is on.
    //  - Allocation current: represents the current Allocation.
    // Returns:
    //  - The most efficient Allocation with best combination and order of regions.
    private static Allocation allocateReliefRecursive(double budget, List<Region> sites, 
            int index, Allocation current) {
        if (index >= sites.size()) {
            return current;
        }
    
        Region currentRegion = sites.get(index);
    
        Allocation excludeCurrent = allocateReliefRecursive(budget, sites, index + 1, 
            current);
    
        Allocation includeCurrent = current;
        if (current.totalCost() + currentRegion.getCost(current.size()) <= budget) {
            includeCurrent = allocateReliefRecursive(budget, sites, 
                index + 1, current.withRegion(currentRegion));
        }
    
        if (includeCurrent.totalPeople() > excludeCurrent.totalPeople() ||
            (includeCurrent.totalPeople() == excludeCurrent.totalPeople() &&
             includeCurrent.totalCost() < excludeCurrent.totalCost())) {
            return includeCurrent;
        } else {
            return excludeCurrent;
        }
    }

    // Behavior:
    //  - Ensures all orders of regions are considered.
    //  NOTE: best means saves most people for lowest cost.
    // Parameters:
    //  - List<Region> regions: Represents the list of regions to be ordered.
    // Returns:
    //  - Returns the best ordering and combination of regions.
    private static List<List<Region>> generatePermutations(List<Region> regions) {
        List<List<Region>> permutations = new ArrayList<>();
        permuteHelper(regions, 0, permutations);
        return permutations;
    }
    
    // Behavior:
    //  - Creates every possible ordering of regions.
    // Parameters:
    //  - List<Region> regions: represents the list of regions to be potentially relieved.
    //  - List<List<Region>> permutations: represents the list with every possible
    //  combination of regions.
    //  - int start: represents where the permute helper will start the region ordering.
    private static void permuteHelper(List<Region> regions, int start, 
            List<List<Region>> permutations) {
        
        if (start == regions.size()) {
            permutations.add(new ArrayList<>(regions));
        }
        
        for (int i = start; i < regions.size(); i++) {
            Collections.swap(regions, start, i); // Swap
            permuteHelper(regions, start + 1, permutations);
            Collections.swap(regions, start, i); // Backtrack
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////
    
    /**
    * Prints each allocation in the provided set. Useful for getting a quick overview
    * of all allocations currently in the system.
    * @param allocations Set of allocations to print
    */
    public static void printAllocations(Set<Allocation> allocations) {
        System.out.println("All Allocations:");
        for (Allocation a : allocations) {
            System.out.println("  " + a);
        }
    }

    /**
    * Prints details about a specific allocation result, including the total people
    * helped, total cost, and any leftover budget. Handy for checking if we're
    * within budget limits!
    * @param alloc The allocation to print
    * @param budget The budget to compare against
    */
    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    /**
    * Creates a scenario with numRegions regions by randomly choosing the population 
    * and cost of each region.
    * @param numLocs Number of regions to create
    * @param minPop Minimum population per region
    * @param maxPop Maximum population per region
    * @param minCostPer Minimum cost per person
    * @param maxCostPer Maximum cost per person
    * @return A list of randomly generated regions
    */
    public static List<Region> createRandomScenario(int numLocs, int minPop, int maxPop,
                                                    double minCostPer, double maxCostPer) {
        List<Region> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = RAND.nextInt(minPop, maxPop + 1);
            double cost = RAND.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Region("Region #" + i, pop, round2(cost)));
        }

        return result;
    }

    /**
    * Manually creates a simple list of regions to represent a known scenario.
    * @return A simple list of regions
    */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();

        result.add(new Region("Region #1", 50, 500));
        result.add(new Region("Region #2", 100, 700));
        result.add(new Region("Region #3", 60, 1000));
        result.add(new Region("Region #4", 20, 1000));
        result.add(new Region("Region #5", 200, 900));

        return result;
    }    

    /**
    * Rounds a number to two decimal places.
    * @param num The number to round
    * @return The number rounded to two decimal places
    */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}