package banksim;

import java.util.Stack;
import java.util.Random;

public class StackSim {
	//The line of waiting customers
    private Stack<Customer> line;
    private int[] tellers;
    private int currentTime;
    private int totalWaitTime;
    private int maxWaitTime;
    private int numCustomers;
    private Random rand;
    private String type;

    // This is the inner class for customer in this simulation
    private class Customer implements Comparable<Object> {
        private int entryTime;
        private int serviceTime;

        public Customer(int entryTime, int serviceTime) {
            this.entryTime = entryTime;
            this.serviceTime = serviceTime;
        }

        public int getEntryTime() {
            return entryTime;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Customer)) return 9999;

            Customer other = (Customer) o;

            if (this.serviceTime == other.serviceTime)
                return this.entryTime - other.entryTime;
            else
                return this.serviceTime - other.serviceTime;
        }
    }

    // "Constructor" sets up the simulation. That is tellers, queue, randomization, and type...as can be seen
    public StackSim(int numTellers, Random rand, String type) {
        this.line = new Stack<>();
        this.tellers = new int[numTellers];
        this.rand = rand;
        this.type = type;

        currentTime = 0;
        totalWaitTime = 0;
        maxWaitTime = 0;
        numCustomers = 0;
    }

    // Run the simulation here
    public void run() {

    	//Bank opens
        while (currentTime <= 480) {
            currentTime++;

            int newCustomers = numberOfNewCustomers();

            for (int i = 0; i < newCustomers; i++) {
                Customer c = new Customer(currentTime, generateServiceTime());
                line.push(c); // STACK uses push
                numCustomers++;
            }

            processTellers();
        }

        //Bank closes, wrap it up (finish current line)
        while (!line.isEmpty()) {
            currentTime++;
            processTellers();
        }
    }

    // Process through the tellers
    private void processTellers() {

        for (int i = 0; i < tellers.length; i++) {

            if (tellers[i] == 0) {
                if (!line.isEmpty()) {

                    Customer c = line.pop(); 
                    int waitTime = currentTime - c.getEntryTime();
                    totalWaitTime += waitTime;

                    if (waitTime > maxWaitTime)
                        maxWaitTime = waitTime;

                    tellers[i] = c.getServiceTime();
                }

            } else {
                tellers[i]--;
            }
        }
    }

    // Print the result keeping consistent with the graphs format in the console (ever so carefully)
    public void printResults() {

        double avgWait = (double) totalWaitTime / numCustomers;

        System.out.printf("%-6s %7d %12d %12d %12.3f\n",
                type, tellers.length, currentTime, maxWaitTime, avgWait);
    }

    // Check if it is a busy time
    private boolean busyTime() {
        return (currentTime < 60 ||
               (currentTime >= 210 && currentTime < 270) ||
                currentTime >= 420);
    }

    // Have new customers arrived? How many have arrived?
    private int numberOfNewCustomers() {

        int r = rand.nextInt(100);

        if (busyTime()) {
            if (r < 30) return 0;
            else if (r < 55) return 1;
            else if (r < 75) return 2;
            else if (r < 90) return 3;
            else if (r < 97) return 4;
            else return 5;
        } else {
            if (r < 50) return 0;
            else if (r < 75) return 1;
            else if (r < 91) return 2;
            else if (r < 98) return 3;
            else return 4;
        }
    }

    // How long will the customer take with the teller, based on whether or not the bank is busy
    private int generateServiceTime() {

        int r = rand.nextInt(100);

        if (busyTime()) {
            if (r < 40) return 1;
            else if (r < 75) return 2;
            else if (r < 92) return 3;
            else if (r < 98) return 4;
            else return 5;
        } else {
            if (r < 33) return 1;
            else if (r < 60) return 2;
            else if (r < 75) return 3;
            else if (r < 85) return 4;
            else if (r < 93) return 5;
            else if (r < 98) return 6;
            else return 7;
        }
    }
}