package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;

/**
 * Created by simin on 2017/10/25.
 */
public class JsonUtil {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    private static ObjectMapper objectMapper;


    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
    }

    public static JsonNode getJsonObjectByString(String json)
            throws JsonMappingException, JsonParseException, IOException {
        JsonNode df = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        df = mapper.readValue(json, JsonNode.class);
        return df;
    }

    /**
     * POJO to JSON string with Jackson lib.
     *
     * @param value
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public static String entityToJSONString(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        return objectMapper.writeValueAsString(value);
    }

    public static <T extends Object> T jsonStringToObject(String json, Class<T> valueType) {
        T object = null;
        try {
            object = objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            object = null;
        }
        return object;
    }

    public static void main(String args[]) throws IOException {
        String jsonString = "{\"result\":false,\"resultId\":1000,\"resultMSG\":\"ϵͳ����\"}";
        JsonNode jsonNode = getJsonObjectByString(jsonString);
        System.out.println(jsonNode.get("resultMSG").getIntValue());
    }

    /**
     * 根据json字符串返回对应java类型
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T jsonToBean(String jsonString, Class<T> cls) {
        T t = null;

        Gson gson = new Gson();
        t = gson.fromJson(jsonString, cls);

        return t;
    }

    /**
     * 根据json字符串返回对应Map类型
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String jsonString) {
        Map<String, Object> map;
        Gson gson = new Gson();
        map = gson.fromJson(jsonString, Map.class);

        return map;
    }

    /**
     * 根据json字符串返回对应Map类型List
     */
    public static List<Map<String, Object>> jsonToMapList(String jsonString) {
        List<Map<String, Object>> list;

        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
        }.getType());

        return list;
    }


}
