import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
     static String readResult() {
          StringBuilder result = new StringBuilder();

          int fileData;

          try {
               File resultFile = new File("equation.txt");

               FileReader reader = new FileReader(resultFile);

               /* 
                    Reads the file data,
                    reader.read() returns and int where if it equals to -1, it's the end of the file
                */
               while((fileData = reader.read()) != -1) {
                    result.append((char) fileData);
               }

               reader.close();
          }
          catch(IOException e) {
               System.out.println(e);
          }

          return result.toString();
     }
}
