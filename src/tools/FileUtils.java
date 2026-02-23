package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

        try (FileInputStream fis = new FileInputStream(f);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_16);
             BufferedReader br = new BufferedReader(isr)) {
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
        try (FileOutputStream fos = new FileOutputStream(path);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_16LE);
             BufferedWriter bw = new BufferedWriter(osw)) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

        } catch (Exception e) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
