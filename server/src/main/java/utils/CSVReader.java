package utils;

import com.sun.istack.internal.NotNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by salterok on 31.05.2015.
 */
public class CSVReader {
    public static List<CSVRecord> readFromFile(@NotNull File file, @NotNull CSVFormat format) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            final Reader reader = new InputStreamReader(inputStream, "UTF-8");
            return new CSVParser(reader, format).getRecords();
        }
    }

    public static List<CSVRecord> readFromFile(@NotNull File file, @NotNull CSVFormat format, int limit) throws IOException {
        List<CSVRecord> list = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(file)) {
            final Reader reader = new InputStreamReader(inputStream, "UTF-8");
            CSVParser parser = new CSVParser(reader, format);
            Iterator<CSVRecord> it = parser.iterator();
            for (int i = 0; i < limit; i++) {
                if (!it.hasNext()) {
                    break;
                }
                list.add(it.next());
            }
            parser.close();
        }
        return list;
    }
}
