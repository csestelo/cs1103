import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScrapingWebData {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://docs.oracle.com/javase/7/docs/api/java/util/logging/Logger.html");
            String filePath = JOptionPane.showInputDialog("Input the file path where the scraped data must be stored.");

            InputStream input = url.openStream();
            OutputStream outputFile = new FileOutputStream(filePath);

            copyStream(input, outputFile);

            input.close();
            outputFile.close();

        } catch (IOException e) {
            Map<String, String> info = new HashMap<>();
            info.put("msg", "An error occurred");
            info.put("exception_type", e.getClass().getName());
            info.put("exception_msg", e.getMessage());
            System.out.println(info);
        }
    }   

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        int oneByte = in.read();
        while (oneByte >= 0) { // negative value indicates end-of-stream
            out.write(oneByte);
            oneByte = in.read();
        }
    }
}
