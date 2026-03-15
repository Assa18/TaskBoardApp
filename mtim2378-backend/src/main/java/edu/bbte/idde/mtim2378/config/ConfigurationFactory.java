package edu.bbte.idde.mtim2378.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);
    private static Configuration instance;
    private static final String propertiesFile = "/application";

    public static synchronized Configuration getConfiguration() {
        if (instance == null) {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

            String propFileName = getPropFileName();
            logger.info("Loading configuration from file: {}", propFileName);

            try (InputStream input = ConfigurationFactory.class
                    .getResourceAsStream(propFileName)) {
                instance = objectMapper.readValue(input, Configuration.class);
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        return instance;
    }

    private static String getPropFileName() {
        StringBuilder sb = new StringBuilder();
        sb.append(propertiesFile);
        String profile = System.getenv("PROFILE");

        if (profile != null && !profile.isBlank()) {
            sb.append('-').append(profile);
        }

        sb.append(".yaml");
        return sb.toString();
    }
}
