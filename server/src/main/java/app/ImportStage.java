package app;

import constants.ImportColumns;
import model.Database;
import model.dbo.Item;
import model.dbo.RealItem;
import model.dbo.Sprint;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.omg.PortableInterceptor.INACTIVE;
import pojo.LocalConfig;
import utils.CSVReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by salterok on 01.06.2015.
 */
public class ImportStage {

    private List<CSVRecord> getRecords(File file, LocalConfig.CSVReadProps props, int limit) throws IOException {
        CSVFormat format = CSVFormat.EXCEL.withDelimiter(props.delim.charAt(0));
        if (limit < 0) {
            return CSVReader.readFromFile(file, format, Charset.forName(props.encoding));
        }
        else {
            return CSVReader.readFromFile(file, format, Charset.forName(props.encoding), limit);
        }
    }

    private LocalConfig.PreviewResult getData(List<CSVRecord> records) {
        LocalConfig.PreviewResult result = new LocalConfig.PreviewResult();
        List<List<String>> items = new ArrayList<>();
        OptionalInt max = records.stream().mapToInt(CSVRecord::size).max();
        result.columns = max.getAsInt();
        for (CSVRecord rec : records) {
            List<String> item = new ArrayList<>();
            int size = rec.size();
            for (int i = 0; i < max.getAsInt(); i++) {
                if (i < size) {
                    item.add(rec.get(i));
                }
                else {
                    item.add("");
                }
            }
            items.add(item);
        }
        result.items = items;
        return result;
    }

    public LocalConfig.PreviewResult getPreviewData(LocalConfig.CSVReadProps props) throws IOException {
        List<CSVRecord> records = getRecords(props.file, props, Config.getInstance().anImport.previewSize);
        return getData(records);
    }

    public void importSprint(LocalConfig.CSVReadProps props) throws IOException {
        List<CSVRecord> records = getRecords(props.file, props, -1);
        List<Item> items = getMeaningData(records, props);

        Database.transaction(() -> {
            Sprint sprint = new Sprint();
            sprint.dateStart = new Date();
            sprint.operator = props.operatorName;
            Database.sprints.create(sprint);

            for (Item item : items) {
                createItem(sprint, item);
            }

            return null;
        });




    }

    private List<Item> getMeaningData(List<CSVRecord> records, LocalConfig.CSVReadProps props) {
        int[] indexes = new int[] {
            props.columnsBinding.get(ImportColumns.NUM),
            props.columnsBinding.get(ImportColumns.INV_NUM),
            props.columnsBinding.get(ImportColumns.NAME),
            props.columnsBinding.get(ImportColumns.COUNT),
            props.columnsBinding.get(ImportColumns.PRICE)
        };

        List<Item> items = new ArrayList<>();
        String currentBill = null;
        for (CSVRecord record : records) {
            try {
                Item item = mapFromCSV(record, indexes);
                item.bill = currentBill;
                items.add(item);
            }
            catch (Exception ex) {
                // try to read bill
                // match first cell to pattern (starts with)
                String val = record.get(0);
                if (val.startsWith(props.billPattern)) {
                    currentBill = val.replace(props.billPattern, "");
                }
            }
        }
        return items;
    }

    /**
     * Weird method for mapping CSV-row into Item object
     * @param record
     * @param indexes
     * @return
     */
    private Item mapFromCSV(CSVRecord record, int[] indexes) throws ParseException {
        Item item = new Item();
        NumberFormat format = DecimalFormat.getInstance();
        format.setGroupingUsed(true);

        // skip NUM
        String val = record.get(indexes[1]); // INV_NUM
        item.invNumber = Integer.parseInt(val);
        val = record.get(indexes[2]); // NAME
        if (val == null || val.isEmpty()) {
            return null;
        }
        item.name = val;
        val = record.get(indexes[3]); // COUNT
        item.count = Integer.parseInt(val);
        val = record.get(indexes[4]); // PRICE
        item.price = format.parse(val.replace(" ", "")).floatValue();

        return item;
    }

    private void createItem(Sprint sprint, Item item) throws SQLException {
        item.sprint = sprint;
        Database.items.create(item);

        for (int i = 0; i < item.count; i++) {
            RealItem realItem = new RealItem();
            realItem.item = item;
            Database.realItems.create(realItem);
        }
        Database.items.update(item);
    }
}
