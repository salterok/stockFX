package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by salterok on 31.05.2015.
 */

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



    public static class Import {
        public List<String> csvDelimiters;
        public List<String> fileEncoding;
        public Map<String, String> columns;
    }
}
