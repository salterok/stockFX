package app;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pojo.LocalConfig;
import utils.CSVReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Created by salterok on 01.06.2015.
 */
public class ImportStage {

    private List<CSVRecord> getRecords(File file, LocalConfig.CSVReadProps props, int limit) throws IOException {
        CSVFormat format = CSVFormat.newFormat(props.delim.charAt(0));
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

    public LocalConfig.PreviewResult getPreviewData(File file, LocalConfig.CSVReadProps props) throws IOException {
        List<CSVRecord> records = getRecords(file, props, Config.getInstance().anImport.previewSize);
        return getData(records);
    }

    public void importSprint(File file, LocalConfig.CSVReadProps props) {

    }
}
