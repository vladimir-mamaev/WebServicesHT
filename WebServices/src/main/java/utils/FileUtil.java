package utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;


public class FileUtil {

    public static String generateStringFromFile(String fileName) throws IOException {

        return FileUtils.readFileToString(new File("src/main/java/test_data/" + fileName + ".json"), defaultCharset());
    }
}
