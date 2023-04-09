package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> type) {
        return MAPPER.readValue(json, type);
    }

}

