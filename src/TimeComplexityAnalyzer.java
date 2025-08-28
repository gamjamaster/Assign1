import com.mathworks.engine.MatlabEngine;

public class TimeComplexityAnalyzer {
    
    public static void generateComplexityGraph(MatlabEngine eng, int dataSize) {
        try {
            // Create input sizes for analysis (from 1000 to dataSize)
            int numPoints = 10;
            double[] inputSizes = new double[numPoints];
            double[] seqArrayOps = new double[numPoints];
            double[] seqLinkedOps = new double[numPoints];
            double[] fiboArrayOps = new double[numPoints];
            double[] fiboLinkedOps = new double[numPoints];
            double[] jumpArrayOps = new double[numPoints];
            double[] jumpLinkedOps = new double[numPoints];
            double[] binaryArrayOps = new double[numPoints];
            double[] binaryLinkedOps = new double[numPoints];
            
            // Generate data points
            for (int i = 0; i < numPoints; i++) {
                double n = 1000 + (dataSize - 1000) * i / (numPoints - 1);
                inputSizes[i] = n;
                
                // Worst-case time complexities:
                // Sequential Search + ArrayList: O(n)
                seqArrayOps[i] = n;
                
                // Sequential Search + LinkedList: O(n^2) - each get(i) is O(i)
                seqLinkedOps[i] = n * n / 2; // Average position for worst case
                
                // Fibonacci Search + ArrayList: O(log n)
                fiboArrayOps[i] = Math.log(n) / Math.log(2); // log base 2
                
                // Fibonacci Search + LinkedList: O(n log n) - each get(i) is O(i)
                fiboLinkedOps[i] = n * Math.log(n) / Math.log(2) / 2; // Average position
                
                // Jump Search + ArrayList: O(√n)
                jumpArrayOps[i] = Math.sqrt(n);
                
                // Jump Search + LinkedList: O(n√n) - each get(i) is O(i)
                jumpLinkedOps[i] = n * Math.sqrt(n) / 2; // Average position
                
                // Binary Search + ArrayList: O(log n)
                binaryArrayOps[i] = Math.log(n) / Math.log(2); // log base 2
                
                // Binary Search + LinkedList: O(n log n) - each get(i) is O(i)
                binaryLinkedOps[i] = n * Math.log(n) / Math.log(2) / 2; // Average position
            }
            
            // Pass data to MATLAB
            eng.putVariable("inputSizes", inputSizes);
            eng.putVariable("seqArrayComplexity", seqArrayOps);
            eng.putVariable("seqLinkedComplexity", seqLinkedOps);
            eng.putVariable("fiboArrayComplexity", fiboArrayOps);
            eng.putVariable("fiboLinkedComplexity", fiboLinkedOps);
            eng.putVariable("jumpArrayComplexity", jumpArrayOps);
            eng.putVariable("jumpLinkedComplexity", jumpLinkedOps);
            eng.putVariable("binaryArrayComplexity", binaryArrayOps);
            eng.putVariable("binaryLinkedComplexity", binaryLinkedOps);
            
            // Create the complexity graph
            eng.eval("subplot(2,1,2);");
            eng.eval("semilogy(inputSizes, seqArrayComplexity, '-o', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("hold on;");
            eng.eval("semilogy(inputSizes, seqLinkedComplexity, '-s', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, fiboArrayComplexity, '-^', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, fiboLinkedComplexity, '-d', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, jumpArrayComplexity, '-v', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, jumpLinkedComplexity, '->', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, binaryArrayComplexity, '-<', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("semilogy(inputSizes, binaryLinkedComplexity, '-p', 'LineWidth', 2, 'MarkerSize', 4);");
            eng.eval("hold off;");
            
            eng.eval("xlabel('Input Size (n)');");
            eng.eval("ylabel('Operations (log scale)');");
            eng.eval("title('Worst-Case Time Complexity Analysis');");
            eng.eval("legend({'Seq Array O(n)', 'Seq Linked O(n²)', 'Fibo Array O(log n)', 'Fibo Linked O(n log n)', 'Jump Array O(√n)', 'Jump Linked O(n√n)', 'Binary Array O(log n)', 'Binary Linked O(n log n)'}, 'Location', 'northwest');");
            eng.eval("grid on;");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}