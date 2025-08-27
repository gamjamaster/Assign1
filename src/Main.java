import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import com.mathworks.engine.MatlabEngine;

public class Main {
    public static void main(String[] args) {
        String csvFile = "Article.csv"; // CSV file path

        ArticleCsvLoader loader = new ArticleCsvLoader(csvFile);

        List<Article> arrayList = loader.loadToArrayList();
        List<Article> linkedList = loader.loadToLinkedList();

        int M = 30; // number of times to search
        
        Set<Integer> existingIds = new HashSet<>();
        for (Article article : arrayList) {
            existingIds.add(article.getId()); // Assuming Article has getId() method
        }

        // Convert to array for random selection
        Integer[] existingIdsArray = existingIds.toArray(new Integer[0]);

        int[] id = new int[M];
        Random rand = new Random();

        for(int i = 0; i < M; i++){
            if (rand.nextDouble() < 0.8) { // 80% chance
                // Select an existing ID
                int randomIndex = rand.nextInt(existingIdsArray.length);
                id[i] = existingIdsArray[randomIndex];
            } else { // 20% chance
                // Generate a non-existing ID
                int nonExistingId;
                do {
                    nonExistingId = rand.nextInt(50000) + 25000; // Generate from a higher range
                } while (existingIds.contains(nonExistingId));
                id[i] = nonExistingId;
            }
        }

        // Arrays for total operations for each algorithm (8 algorithms)
        long[] operationsSequentialArray = new long[M];
        long[] operationsSequentialLinked = new long[M];
        long[] operationsFiboArray = new long[M];
        long[] operationsFiboLinked = new long[M];
        long[] operationsJumpArray = new long[M];
        long[] operationsJumpLinked = new long[M];
        long[] operationsBinaryArray = new long[M];
        long[] operationsBinaryLinked = new long[M];

        for (int idx = 0; idx < M; idx++) {
            int i = id[idx];
            Article target = new Article(i, "", "", 0,0,0,0,0,0);

            // Create separate AtomicLong for each algorithm
            AtomicLong opCountSequentialArray = new AtomicLong();
            AtomicLong opCountSequentialLinked = new AtomicLong();
            AtomicLong opCountFiboArray = new AtomicLong();
            AtomicLong opCountFiboLinked = new AtomicLong();
            AtomicLong opCountJumpArray = new AtomicLong();
            AtomicLong opCountJumpLinked = new AtomicLong();
            AtomicLong opCountBinaryArray = new AtomicLong();
            AtomicLong opCountBinaryLinked = new AtomicLong();

            Thread t1 = new Thread(new Sequential_Search_ArrayList<>(arrayList, target, opCountSequentialArray));
            Thread t2 = new Thread(new Sequential_Search_LinkedList<>(linkedList, target, opCountSequentialLinked));
            Thread t3 = new Thread(new Fibonacci_Search_ArrayList<>(arrayList, target, opCountFiboArray));
            Thread t4 = new Thread(new Fibonacci_Search_LinkedList<>(linkedList, target, opCountFiboLinked));
            Thread t5 = new Thread(new Jump_Search_ArrayList<>(arrayList, target, opCountJumpArray));
            Thread t6 = new Thread(new Jump_Search_LinkedList<>(linkedList, target, opCountJumpLinked));
            Thread t7 = new Thread(new Binary_Search_ArrayList<>(arrayList, target, opCountBinaryArray));
            Thread t8 = new Thread(new Binary_Search_LinkedList<>(linkedList, target, opCountBinaryLinked));

            t1.start(); 
            t2.start(); 
            t3.start(); 
            t4.start();
            t5.start(); 
            t6.start(); 
            t7.start(); 
            t8.start();

            try {
                t1.join(); 
                t2.join(); 
                t3.join(); 
                t4.join();
                t5.join(); 
                t6.join(); 
                t7.join(); 
                t8.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            operationsSequentialArray[idx] = opCountSequentialArray.get();
            operationsSequentialLinked[idx] = opCountSequentialLinked.get();
            operationsFiboArray[idx] = opCountFiboArray.get();
            operationsFiboLinked[idx] = opCountFiboLinked.get();
            operationsJumpArray[idx] = opCountJumpArray.get();
            operationsJumpLinked[idx] = opCountJumpLinked.get();
            operationsBinaryArray[idx] = opCountBinaryArray.get();
            operationsBinaryLinked[idx] = opCountBinaryLinked.get();
        }

        // Average Operations
        double avgOpsSeqArr = Arrays.stream(operationsSequentialArray).average().orElse(0);
        double avgOpsSeqLinked = Arrays.stream(operationsSequentialLinked).average().orElse(0);
        double avgOpsFiboArr = Arrays.stream(operationsFiboArray).average().orElse(0);
        double avgOpsFiboLinked = Arrays.stream(operationsFiboLinked).average().orElse(0);
        double avgOpsJumpArr = Arrays.stream(operationsJumpArray).average().orElse(0);
        double avgOpsJumpLinked = Arrays.stream(operationsJumpLinked).average().orElse(0);
        double avgOpsBinaryArr = Arrays.stream(operationsBinaryArray).average().orElse(0);
        double avgOpsBinaryLinked = Arrays.stream(operationsBinaryLinked).average().orElse(0);

        // Print results to console
        System.out.println("Average Operations:");
        System.out.println("Sequential Array: " + avgOpsSeqArr);
        System.out.println("Sequential Linked: " + avgOpsSeqLinked);
        System.out.println("Fibonacci Array: " + avgOpsFiboArr);
        System.out.println("Fibonacci Linked: " + avgOpsFiboLinked);
        System.out.println("Jump Array: " + avgOpsJumpArr);
        System.out.println("Jump Linked: " + avgOpsJumpLinked);
        System.out.println("Binary Array: " + avgOpsBinaryArr);
        System.out.println("Binary Linked: " + avgOpsBinaryLinked);

        try {
            MatlabEngine eng = MatlabEngine.startMatlab();

            // Create figure with subplots
            eng.eval("figure('Position', [100, 100, 1200, 800]);");

            // First graph (top) - Actual performance results
            double[] operations = {
                avgOpsSeqArr, 
                avgOpsSeqLinked, 
                avgOpsFiboArr, 
                avgOpsFiboLinked,
                avgOpsJumpArr,
                avgOpsJumpLinked,
                avgOpsBinaryArr,
                avgOpsBinaryLinked
            };

            eng.putVariable("searchOperations", operations);

            eng.eval("subplot(2,1,1);");
            eng.eval("bar(searchOperations);");
            eng.eval("set(gca, 'xticklabel', {'Seq-Arr', 'Seq-Link', 'Fibo-Arr', 'Fibo-Link', 'Jump-Arr', 'Jump-Link', 'Bin-Arr', 'Bin-Link'});");
            eng.eval("ylabel('Number of Operations');");
            eng.eval("title('Actual Average Operations (Experimental Results)');");
            eng.eval("grid on;");

            // Second graph (bottom) - Theoretical time complexity
            TimeComplexityAnalyzer.generateComplexityGraph(eng, arrayList.size());

            Thread.sleep(50000);
            eng.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}