package app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by salterok on 31.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    private Config() {}
    private static Config _config;
    public static Config getInstance() {
        if (_config == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream stream = Config.class.getResourceAsStream("/Config.json");
                _config = mapper.readValue(stream, Config.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return _config;
    }


    @JsonProperty("import")
    public Import anImport;
    @JsonProperty("db")
    public Db db;



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Import {
        public List<String> csvDelimiters = Collections.emptyList();
        public List<String> fileEncoding = Collections.emptyList();;
        public Map<String, String> columns = Collections.emptyMap();
        public int previewSize = 100;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Db {
        public String uri = "jdbc:UNSET";
    }
}
