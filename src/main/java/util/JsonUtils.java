package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON serialization and deserialization utility class.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Private constructor.
     */
    private JsonUtils() {
    }

    /**
     * Serialize object to JSON.
     *
     * @param o object to serialize.
     * @return JSON representation of the object.
     */
    public static String serialize(Object o) throws JsonProcessingException {
        return MAPPER.writeValueAsString(o);
    }

    /**
     * Deserialize JSON to Java object.
     * @param json  JSON to deserialize
     * @param clazz Java type of the object to deserialize
     * @param <T>   Type of the deserialized object
     * @return      Deserialized object
     */
    public static <T> T deserialize(String json, Class<T> clazz) throws IOException {
        return MAPPER.readValue(json, clazz);
    }
}
