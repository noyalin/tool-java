package util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.SystemException;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JacksonUtil {

    /**
     * 根据json字符串返回对应java类型
     *
     * @param obj Object
     * @return String
     */
    public static String bean2Json(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();

        JsonGenerator gen;

        try {
            gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, obj);
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }

        if (gen != null) {
            try {
                gen.close();
            } catch (IOException e) {
                throw new SystemException(e.getMessage(), e);
            }
        }

        return sw.toString();
    }

    public static String bean2JsonNotNull(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    /**
     * 根据json字符串返回对应java类型
     *
     * @param jsonStr String
     * @param cls     Class<T>
     * @return T
     */
    public static <T> T json2Bean(String jsonStr, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(jsonStr, cls);
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 根据json字符串返回对应java类型
     *
     * @param jsonStr String
     * @param cls     Class<T>
     * @return T
     */
    public static <T> T json2BeanWithDateCovert(String jsonStr, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(format);
        try {
            return mapper.readValue(jsonStr, cls);
        } catch (IOException e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 根据json字符串返回对应Map类型
     *
     * @param jsonString String
     * @return Map result
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据json字符串返回对应java类型List
     *
     * @param jsonString String
     * @param cls        Class
     * @return List
     */
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, cls);
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据json字符串返回对应Map类型List
     *
     * @param jsonString String
     * @return List
     */
    public static List<Map<String, Object>> jsonToMapList(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametrizedType(ArrayList.class, List.class, Map.class);
        return mapper.readValue(jsonString, javaType);
    }

}
