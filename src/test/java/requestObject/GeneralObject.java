package requestObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class GeneralObject {
    public GeneralObject(String filePath) {
        try {
            new ObjectMapper().readerForUpdating(this).readValue(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON from: " + filePath, e);
        }
    }
}