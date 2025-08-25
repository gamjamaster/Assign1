import com.mathworks.engine.MatlabEngine;

public class Matlab {
    private static MatlabEngine eng;

    public static void main(String[] args) {
        try {
            eng = MatlabEngine.startMatlab();
            eng.eval("x = [-10 : 0.1 : 10];");
            eng.eval("y=x.^2;");
            eng.eval("plot(x,y)");
            Thread.sleep(10000);

        } catch (java.util.concurrent.ExecutionException |
                 InterruptedException e) {
            e.printStackTrace();
        }
    }
}
