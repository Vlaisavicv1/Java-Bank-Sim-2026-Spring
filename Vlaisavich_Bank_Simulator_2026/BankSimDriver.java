/*
 * The following is the driver for the bank simulator, the driver iself was rather 
 * simple to write. That being said writing the first sim file (QueueSim) was rough. 
 * After that point it got a fairly easier. 
 */
package banksim;

import java.util.Random;

public class BankSimDriver {

    public static void main(String[] args) {

        Random rand = new Random();

        // This will write the header in the console for simulation output
        System.out.printf("%-6s %7s %12s %12s %12s\n",
                "Type", "Tellers", "Total Time", "Max Wait", "Avg Wait");

        // Maintains the correct order for the output
        String[] types = {"Queue", "Stack", "SJF", "LJF"};

        // Loop through the number of tellers
        for (String type : types) {
            for (int tellers = 2; tellers <= 6; tellers++) {

                switch (type) {
                    case "Queue":
                    	// This runs and prints the Queue simulation
                        QueueSim queueSim = new QueueSim(tellers, rand, "Queue");
                        queueSim.run();
                        queueSim.printResults();
                        break;
                    case "Stack":
                    	// This runs and prints the stack simulation
                        StackSim stackSim = new StackSim(tellers, rand, "Stack");
                        stackSim.run();
                        stackSim.printResults();
                        break;
                    case "SJF":
                    	// This runs and prints the shortest job simulation
                        ShortSim sjfSim = new ShortSim(tellers, rand, "SJF");
                        sjfSim.run();
                        sjfSim.printResults();
                        break;
                    case "LJF":
                    	// This runs and prints the longest job simulation
                        LongSim ljfSim = new LongSim(tellers, rand, "LJF");
                        ljfSim.run();
                        ljfSim.printResults();
                        break;
                }
            }
        }
    }
}
/*
 * Output to follow:
Type   Tellers   Total Time     Max Wait     Avg Wait
Queue        2          866          385      182.650
Queue        3          595          116       58.849
Queue        4          483           15        4.786
Queue        5          481            7        1.240
Queue        6          481            9        0.933
Stack        2          810          806      168.961
Stack        3          588          585       48.548
Stack        4          484          169        8.889
Stack        5          483           97        3.193
Stack        6          481           18        0.405
SJF          2          858          738      119.764
SJF          3          564          473       24.035
SJF          4          496           83        3.598
SJF          5          485           69        2.045
SJF          6          481            8        0.278
LJF          2          894          892      293.904
LJF          3          560          558       52.074
LJF          4          485          112        7.582
LJF          5          481           66        1.983
LJF          6          482           11        0.288
 */

