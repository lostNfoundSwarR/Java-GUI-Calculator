import java.io.IOException;
import java.lang.Process;
import java.lang.ProcessBuilder;

public class Executer {
     //This functions executes the Java-script file that returns the evaluated value
     static void executeEvaluator() {
          try {
               ProcessBuilder builder = new ProcessBuilder("node", "evaluator.mjs");

               Process process = builder.start();
               process.waitFor();
          }
          catch(IOException | InterruptedException e) {
               System.out.println(e);
          }
     }
}