import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Writer {
     static void writeEquation(String eq) {
          try {
               File eqFile = new File("equation.txt");
               FileWriter writer = new FileWriter(eqFile);

               writer.write(eq);
               writer.close();
          }
          catch(IOException e) {
               System.out.println(e);
          }
     }
}
