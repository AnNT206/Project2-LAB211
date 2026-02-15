package tools;

import com.sun.xml.internal.messaging.saaj.util.CharWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileUtils {

    private FileUtils() {

    }

    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();

        File f = new File(path);
        if (!f.exists()) {
            return lines;
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        return lines;
    }

    public static void writeLines(String path, List<String> lines) {
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

        } catch (Exception e) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
