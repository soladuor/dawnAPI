import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestJson {
    // 参考 https://blog.csdn.net/winterking3/article/details/123814280
    static ObjectMapper objectMapper = new ObjectMapper();

    String json = "{\"data\":[\"1\",\"2\",\"3\"],\"msg\":\"success\"}";

    @Test
    public void test1() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        // 将list转为json数组
        ArrayNode arrayNode = objectMapper.createArrayNode();
        List<String> list = Arrays.asList("4", "5", "6");
        list.forEach(arrayNode::add);

        // 将json数组添加到json对象中（覆盖）
        ((ObjectNode) jsonNode).putArray("data").addAll(arrayNode);
        System.out.println(jsonNode);
    }

    @Test
    public void test2() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        ArrayNode data = (ArrayNode) jsonNode.get("data");
        System.out.println("jsonNode = " + jsonNode);

        // 直接修改JSON对象中的数组
        data.removeAll();
        System.out.println("jsonNode = " + jsonNode);
        data.add("4");
        data.add("5");
        data.add("6");
        System.out.println("jsonNode = " + jsonNode);

        List<String> list = Arrays.asList("7", "8", "9");
        list.forEach(data::add);

        System.out.println("jsonNode = " + jsonNode);
    }

    @Test
    public void testGetValue() throws JsonProcessingException {
        String json1 = "{\"success\":true}";
        String json2 = "{\"code\":412,\"message\":\"无效的sign\",\"data\":{\"name\":\"namename\"}}";

        JsonNode jsonNode1 = objectMapper.readTree(json1);
        JsonNode success1 = jsonNode1.path("success");
        System.out.println("success1 = " + success1);

        JsonNode jsonNode2 = objectMapper.readTree(json2);
        JsonNode success2 = jsonNode2.path("success");
        System.out.println("success2.textValue() == null ==> " + (success2.textValue() == null));

        // 使用path避免空指针异常
        System.out.println("使用path而不是get可以避免空指针异常：");
        System.out.println(objectMapper.readTree(json1).path("success").asBoolean());
        System.out.println(objectMapper.readTree(json2).path("success").asBoolean());
        System.out.println("get == null ==> " + (objectMapper.readTree(json2).get("success") == null));

        // 对象不存在时
        // toString 和 asText 会返回空字符串""
        // textValue 会返回 null , 且在获取数字时也会返回 null
        JsonNode successNode = objectMapper.readTree(json2).path("success");
        System.out.println("字段不存在时：");
        System.out.println("toString == \"\" ==> " + (successNode.toString().equals("")));
        System.out.println("asText == \"\" ==> " + (successNode.asText().equals("")));
        System.out.println("textValue() == null ==> " + (successNode.textValue() == null));

        // 获取数字时(推荐使用 asInt() asLong() asDouble() 等方法
        // textValue 在获取数字时会返回 null
        JsonNode codeNode = objectMapper.readTree(json2).path("code");
        System.out.println("获取数字时：");
        System.out.println("toString = " + codeNode.toString());
        System.out.println("asText = " + codeNode.asText());
        System.out.println("textValue = " + codeNode.textValue());

        // 数据为对象时
        // toString 会返回对象的字符串形式(json字符串)
        // asText 会返回空字符串""
        // textValue 会返回 null
        JsonNode dataNode = objectMapper.readTree(json2).path("data");
        System.out.println("数据为对象时：");
        System.out.println("toString = " + dataNode.toString());
        System.out.println("asText = " + dataNode.asText());
        System.out.println("textValue = " + dataNode.textValue());

    }

}
