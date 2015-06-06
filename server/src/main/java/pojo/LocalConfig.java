package pojo;

import constants.ImportColumns;
import model.dbo.Item;
import model.dbo.Place;

import java.awt.image.BufferedImage;
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

    public static class SettingStageResult {
        public List<Place> prevPlaces;
        public List<Place> currPlaces;
        public List<Item> allItems;
        public List<Item> toGenQR;
        public File saveFile;
    }

    public static class QRTemplate {
        public BufferedImage template;
        public int itemWidth;
        public int itemHeight;
        public int itemDescHeight;

        public QRTemplate(BufferedImage template, int itemWidth, int itemHeight, int itemDescHeight) {
            this.template = template;
            this.itemWidth = itemWidth;
            this.itemHeight = itemHeight;
            this.itemDescHeight = itemDescHeight;
        }
    }
}
