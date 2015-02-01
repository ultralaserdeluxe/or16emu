import java.io.*;

/**
 * Created by Alexander on 2015-02-01.
 */
public class FileReader {
    public static void readFileToMemory(File file, Memory memory) {
        try {
            InputStream in = new FileInputStream(file);
            int i = 0;
            while (true) {
                int byte1 = in.read();
                int byte2 = in.read();
                if (byte1 != -1 && byte2 != -1) {
                    int value = (byte1 << 8) | byte2;
                    memory.write(i, value);
                    i++;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
