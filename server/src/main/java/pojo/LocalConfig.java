package pojo;

import java.util.List;

/**
 * Created by salterok on 31.05.2015.
 */
public class LocalConfig {


    public static class CSVReadProps {
        public String delim;
        public String encoding;
    }


    public static class PreviewResult {
        public int columns;
        public List<List<String>> items;
    }
}
