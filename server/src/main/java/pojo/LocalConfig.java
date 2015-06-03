package pojo;

import constants.ImportColumns;
import model.dbo.Place;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by salterok on 31.05.2015.
 */
public class LocalConfig {


    public static class CSVReadProps {
        public String operatorName;
        public File file;
        public String delim;
        public String encoding;
        public String billPattern;
        public Map<ImportColumns, Integer> columnsBinding;
    }


    public static class PreviewResult {
        public int columns;
        public List<List<String>> items;
    }

    public static class PlacesResult {
        public List<Place> prevPlaces;
        public List<Place> currPlaces;
    }
}
